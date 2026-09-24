package com.setu.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.setu.entity.Assignment;
import com.setu.entity.Donation;
import com.setu.entity.NGO;
import com.setu.entity.Request;
import com.setu.entity.User;
import com.setu.entity.Volunteer;
import com.setu.repository.AssignmentRepository;
import com.setu.repository.CategoryRepository;
import com.setu.repository.DonationRepository;
import com.setu.repository.NGORepository;
import com.setu.repository.RequestRepository;
import com.setu.repository.UserRepository;
import com.setu.repository.VolunteerRepository;
import com.setu.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;
    @Autowired private VolunteerRepository volunteerRepository;
    @Autowired private DonationRepository donationRepository;
    @Autowired private RequestRepository requestRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private EmailService emailService;

    // ---------- HOME ----------
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalDonors", userRepository.findAll().stream()
                .filter(u -> "DONOR".equals(u.getRole())).count());
        model.addAttribute("totalNgos", ngoRepository.count());
        model.addAttribute("totalDonations", donationRepository.count());
        return "home";
    }

    // ---------- DONOR ----------
    @GetMapping("/donor/dashboard")
    public String donorDashboard(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        List<Donation> donations = donationRepository.findByDonorOrderByDonationDateDesc(user);

        model.addAttribute("totalDonations", donations.size());
        model.addAttribute("recentDonations", donations.size() > 5 ? donations.subList(0, 5) : donations);
        model.addAttribute("trackingByDonation", assignmentsFor(donations));
        return "donor-dashboard";
    }

    @GetMapping("/donor/browse-ngos")
    public String browseNgos(Model model) {
        model.addAttribute("requestList", requestRepository.findByStatus("PENDING"));
        return "browse-ngos";
    }

    @GetMapping("/donor/donate")
    public String donatePage(@RequestParam(required = false) Long requestId, Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        if (requestId != null) {
            requestRepository.findById(requestId).ifPresent(r -> model.addAttribute("selectedRequest", r));
        }
        return "donate";
    }

    @PostMapping("/donor/donate")
    public String submitDonation(@RequestParam(required = false) Long requestId,
                                  @RequestParam String title,
                                  @RequestParam Long categoryId,
                                  @RequestParam Integer quantity,
                                  @RequestParam(required = false) String description,
                                  @RequestParam String pickupAddress,
                                  HttpSession session, Model model) {

        User donor = getLoggedInUser(session);

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setTitle(title);
        donation.setDescription(description);
        donation.setQuantity(quantity);
        donation.setPickupAddress(pickupAddress);
        donation.setStatus("PENDING");
        categoryRepository.findById(categoryId).ifPresent(donation::setCategory);

        if (requestId != null) {
            requestRepository.findById(requestId).ifPresent(req -> {
                donation.setNgo(req.getNgo());
            });
        }

        donationRepository.save(donation);
        assignAvailableVolunteer(donation);
        model.addAttribute("successMsg", "Thank you for your support! Your donation has been submitted and will be assigned for pickup.");
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "donate";
    }

 // ---------- DONOR: OFFER AN ITEM (no specific NGO) ----------
    @GetMapping("/donor/offer-item")
    public String offerItemPage(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "donor-offer-item";
    }

    @PostMapping("/donor/offer-item")
    public String submitOfferItem(@RequestParam String title,
                                   @RequestParam Long categoryId,
                                   @RequestParam Integer quantity,
                                   @RequestParam(required = false) String description,
                                   @RequestParam String pickupAddress,
                                   HttpSession session, Model model) {

        User donor = getLoggedInUser(session);

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setTitle(title);
        donation.setDescription(description);
        donation.setQuantity(quantity);
        donation.setPickupAddress(pickupAddress);
        donation.setStatus("AVAILABLE");
        categoryRepository.findById(categoryId).ifPresent(donation::setCategory);

        donationRepository.save(donation);
        model.addAttribute("successMsg", "Item offered successfully! NGOs can now claim it.");
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "donor-offer-item";
    }

    // ---------- NGO: BROWSE & ACCEPT AVAILABLE ITEMS ----------
    @GetMapping("/ngo/available-donations")
    public String availableDonations(Model model) {
        model.addAttribute("availableList", donationRepository.findByNgoIsNullAndStatus("AVAILABLE"));
        return "ngo-available-donations";
    }

    @PostMapping("/ngo/accept-donation")
    public String acceptDonation(@RequestParam Long id, HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        int updated = donationRepository.claimDonation(id, ngo);

        if (updated == 0) {
            model.addAttribute("errorMsg", "Sorry, this item was already claimed by another NGO.");
        } else {
            donationRepository.findById(id).ifPresent(this::assignAvailableVolunteer);
            model.addAttribute("successMsg", "Item claimed successfully! It's now assigned to you.");
        }

        model.addAttribute("availableList", donationRepository.findByNgoIsNullAndStatus("AVAILABLE"));
        return "ngo-available-donations";
    }
    
    
    
    @GetMapping("/donor/my-donations")
    public String myDonations(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        List<Donation> donations = donationRepository.findByDonorOrderByDonationDateDesc(user);
        model.addAttribute("donationList", donations);
        model.addAttribute("trackingByDonation", assignmentsFor(donations));
        return "my-donations";
    }

    @GetMapping("/donor/donations/{donationId}/tracking")
    @ResponseBody
    public Map<String, Object> donationTracking(@PathVariable Long donationId, HttpSession session) {
        User donor = getLoggedInUser(session);
        Donation donation = donationRepository.findById(donationId).orElseThrow();
        if (donation.getDonor() == null || !donation.getDonor().getId().equals(donor.getId())) {
            throw new IllegalArgumentException("Donation does not belong to this donor.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", donation.getStatus());
        assignmentRepository.findByDonationId(donationId).ifPresent(assignment -> {
            response.put("assignmentStatus", assignment.getStatus());
            response.put("volunteerName", assignment.getVolunteer().getName());
            response.put("trackingEnabled", assignment.isTrackingEnabled());
            response.put("lastLocationUpdate", assignment.getLastLocationUpdate());
            if (assignment.isTrackingEnabled() && "PICKED_UP".equals(assignment.getStatus())) {
                response.put("latitude", assignment.getCurrentLatitude());
                response.put("longitude", assignment.getCurrentLongitude());
            }
        });
        return response;
    }

    @GetMapping("/donor/profile")
    public String donorProfilePage(HttpSession session, Model model) {
        model.addAttribute("donorProfile", getLoggedInUser(session));
        return "donor-profile";
    }

    @PostMapping("/donor/profile")
    public String updateDonorProfile(@RequestParam String name,
                                      @RequestParam String phone,
                                      @RequestParam String address,
                                      HttpSession session, Model model) {

        User user = getLoggedInUser(session);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        userRepository.save(user);
        session.setAttribute("userName", name);

        model.addAttribute("successMsg", "Profile updated successfully!");
        model.addAttribute("donorProfile", user);
        return "donor-profile";
    }

    // ---------- NGO ----------
    @GetMapping("/ngo/dashboard")
    public String ngoDashboard(HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        List<Request> requests = requestRepository.findByNgoId(ngo.getId());
        List<Donation> received = donationRepository.findByNgo(ngo);

        model.addAttribute("openRequests", requests.stream().filter(r -> "PENDING".equals(r.getStatus())).count());
        model.addAttribute("donationsReceived", received.size());
        model.addAttribute("myRequests", requests);
        model.addAttribute("ngoApproved", ngo.isApproved());
        return "ngo-dashboard";
    }

    @GetMapping("/ngo/post-requirement")
    public String postRequirementPage(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "post-requirement";
    }

    @PostMapping("/ngo/post-requirement")
    public String submitRequirement(@RequestParam String title,
                                     @RequestParam Long categoryId,
                                     @RequestParam String description,
                                     @RequestParam Integer quantity,
                                     @RequestParam(required = false, defaultValue = "Low") String urgency,
                                     HttpSession session, Model model) {

        NGO ngo = getLoggedInNgo(session);

        Request request = new Request();
        request.setNgo(ngo);
        request.setTitle(title);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setUrgency(urgency);
        request.setStatus("PENDING");
        categoryRepository.findById(categoryId).ifPresent(request::setCategory);

        requestRepository.save(request);
        model.addAttribute("successMsg", "Requirement posted successfully!");
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "post-requirement";
    }

    @GetMapping("/ngo/requests")
    public String ngoRequests(HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        model.addAttribute("myRequests", requestRepository.findByNgoId(ngo.getId()));
        return "ngo-requests";
    }

    @GetMapping("/ngo/donations-received")
    public String donationsReceived(HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        List<Donation> donations = donationRepository.findByNgo(ngo);
        model.addAttribute("receivedDonations", donations);
        model.addAttribute("trackingByDonation", assignmentsFor(donations));
        model.addAttribute("availableVolunteers", volunteerRepository.findAll().stream()
                .filter(Volunteer::isApproved)
                .filter(Volunteer::isActive)
                .collect(Collectors.toList()));
        return "ngo-donations-received";
    }

    @PostMapping("/ngo/assign-volunteer")
    public String assignVolunteer(@RequestParam Long donationId,
                                  @RequestParam Long volunteerId,
                                  HttpSession session) {
        NGO ngo = getLoggedInNgo(session);
        Donation donation = donationRepository.findById(donationId).orElseThrow();
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();

        if (donation.getNgo() == null || !donation.getNgo().getId().equals(ngo.getId())) {
            throw new IllegalArgumentException("Donation does not belong to this charitable home.");
        }
        if (!volunteer.isApproved() || !volunteer.isActive()) {
            throw new IllegalArgumentException("Volunteer is not available.");
        }
        if (assignmentRepository.findByDonationId(donationId).isEmpty()) {
            Assignment assignment = new Assignment();
            assignment.setDonation(donation);
            assignment.setVolunteer(volunteer);
            assignment.setStatus("ASSIGNED");
            assignmentRepository.save(assignment);
        }
        return "redirect:/ngo/donations-received";
    }

    @GetMapping("/ngo/profile")
    public String ngoProfilePage(HttpSession session, Model model) {
        model.addAttribute("ngoProfile", getLoggedInNgo(session));
        return "ngo-profile";
    }

    @PostMapping("/ngo/profile")
    public String updateNgoProfile(@RequestParam String name,
                                    @RequestParam String phone,
                                    @RequestParam String address,
                                    @RequestParam String description,
                                    HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        ngo.setName(name);
        ngo.setPhone(phone);
        ngo.setAddress(address);
        ngo.setDescription(description);
        ngoRepository.save(ngo);
        session.setAttribute("userName", name);

        model.addAttribute("successMsg", "Profile updated!");
        model.addAttribute("ngoProfile", ngo);
        return "ngo-profile";
    }

    // ---------- VOLUNTEER ----------
    @GetMapping("/volunteer/dashboard")
    public String volunteerDashboard(HttpSession session, Model model) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        List<Assignment> assignments = assignmentRepository.findByVolunteer(volunteer);
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("assignments", assignments);
        model.addAttribute("activeAssignments", assignments.stream()
            .filter(assignment -> !"DELIVERED".equals(assignment.getStatus())).count());
        model.addAttribute("completedTasks", assignments.stream()
            .filter(assignment -> "DELIVERED".equals(assignment.getStatus())).count());
        return "volunteer-dashboard";
    }

    @GetMapping("/volunteer/assignments")
    public String volunteerAssignments(HttpSession session, Model model) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        model.addAttribute("assignments", assignmentRepository.findByVolunteer(volunteer));
        return "volunteer-assignments";
    }

    @PostMapping("/volunteer/assignments/status")
    public String updateAssignmentStatus(@RequestParam Long assignmentId,
                                         @RequestParam String status,
                                         HttpSession session) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        Assignment assignment = assignmentForVolunteer(assignmentId, volunteer);

        if ("PICKED_UP".equals(status) && "ASSIGNED".equals(assignment.getStatus())) {
            assignment.setStatus("PICKED_UP");
            assignment.setPickedUpDate(LocalDateTime.now());
        } else if ("DELIVERED".equals(status) && "PICKED_UP".equals(assignment.getStatus())) {
            assignment.setStatus("DELIVERED");
            assignment.setDeliveredDate(LocalDateTime.now());
            assignment.setTrackingEnabled(false);
            assignment.getDonation().setStatus("COMPLETED");
            donationRepository.save(assignment.getDonation());
        } else {
            throw new IllegalArgumentException("Invalid assignment status transition.");
        }

        assignmentRepository.save(assignment);
        return "redirect:/volunteer/assignments";
    }

    @PostMapping("/volunteer/assignments/tracking/start")
    public String startTracking(@RequestParam Long assignmentId, HttpSession session) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        Assignment assignment = assignmentForVolunteer(assignmentId, volunteer);
        if (!"PICKED_UP".equals(assignment.getStatus())) {
            throw new IllegalArgumentException("Tracking starts only after pickup confirmation.");
        }
        assignment.setTrackingEnabled(true);
        assignmentRepository.save(assignment);
        return "redirect:/volunteer/assignments";
    }

    @PostMapping("/volunteer/assignments/tracking/location")
    @ResponseBody
    public Map<String, Object> updateLocation(@RequestParam Long assignmentId,
                                              @RequestParam Double latitude,
                                              @RequestParam Double longitude,
                                              HttpSession session) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        Assignment assignment = assignmentForVolunteer(assignmentId, volunteer);
        if (!assignment.isTrackingEnabled() || !"PICKED_UP".equals(assignment.getStatus())) {
            throw new IllegalArgumentException("Live tracking is not active.");
        }
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid GPS coordinates.");
        }
        assignment.setCurrentLatitude(latitude);
        assignment.setCurrentLongitude(longitude);
        assignment.setLastLocationUpdate(LocalDateTime.now());
        assignmentRepository.save(assignment);
        return Map.of("success", true, "lastLocationUpdate", assignment.getLastLocationUpdate());
    }

    @GetMapping("/volunteer/profile")
    public String volunteerProfilePage(HttpSession session, Model model) {
        model.addAttribute("volunteerProfile", getLoggedInVolunteer(session));
        return "volunteer-profile";
    }

    @PostMapping("/volunteer/profile")
    public String updateVolunteerProfile(@RequestParam String name,
                                          @RequestParam String phone,
                                          @RequestParam String address,
                                          @RequestParam String availability,
                                          HttpSession session, Model model) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        volunteer.setName(name);
        volunteer.setPhone(phone);
        volunteer.setAddress(address);
        volunteer.setAvailability(availability);
        volunteerRepository.save(volunteer);
        session.setAttribute("userName", name);

        model.addAttribute("successMsg", "Profile updated!");
        model.addAttribute("volunteerProfile", volunteer);
        return "volunteer-profile";
    }

    // ---------- ADMIN ----------
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalDonors", userRepository.findAll().stream()
                .filter(u -> "DONOR".equals(u.getRole())).count());
        model.addAttribute("totalNgos", ngoRepository.count());
        model.addAttribute("pendingApprovals", ngoRepository.findByApprovedFalse().size());
        model.addAttribute("totalDonations", donationRepository.count());
        model.addAttribute("pendingNgoList", ngoRepository.findByApprovedFalse());
        return "admin-dashboard";
    }

    @GetMapping("/admin/approve-ngo")
    public String approveNgo(@RequestParam Long id) {
        ngoRepository.findById(id).ifPresent(ngo -> {
            ngo.setApproved(true);
            ngoRepository.save(ngo);
            emailService.sendNgoApprovedEmail(ngo.getEmail(), ngo.getName());
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/reject-ngo")
    public String rejectNgo(@RequestParam Long id) {
        ngoRepository.findById(id).ifPresent(ngo -> {
            emailService.sendNgoRejectedEmail(ngo.getEmail(), ngo.getName());
            // Also remove the linked User account so no orphaned NGO-role
            // user is left behind that could otherwise bypass the approval
            // check at login (see AuthController.login()).
            userRepository.findByEmail(ngo.getEmail()).ifPresent(userRepository::delete);
            ngoRepository.deleteById(id);
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "admin-manage-users";
    }

    @GetMapping("/admin/manage-ngos")
    public String manageNgos(Model model) {
        model.addAttribute("ngoList", ngoRepository.findAll());
        return "admin-manage-ngos";
    }

    @GetMapping("/admin/manage-volunteers")
    public String manageVolunteers(Model model) {
        model.addAttribute("volunteerList", volunteerRepository.findAll());
        return "admin-manage-volunteers";
    }
    @GetMapping("/admin/approve-volunteer")
    public String approveVolunteer(@RequestParam Long id) {
        volunteerRepository.findById(id).ifPresent(v -> {
            v.setApproved(true);
            volunteerRepository.save(v);
            emailService.sendVolunteerApprovedEmail(v.getEmail(), v.getName());
        });
        return "redirect:/admin/manage-volunteers";
    }

    
    @GetMapping("/admin/reports")
    public String adminReports(Model model) {
        model.addAttribute("totalDonations", donationRepository.count());
        model.addAttribute("requestsFulfilled", requestRepository.findByStatus("FULFILLED").size());
        return "admin-reports";
    }

    @GetMapping("/admin/delete-user")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/admin/delete-ngo")
    public String deleteNgoAccount(@RequestParam Long id) {
        NGO ngo = ngoRepository.findById(id).orElse(null);
        if (ngo != null) {
            userRepository.findByEmail(ngo.getEmail()).ifPresent(userRepository::delete);
            ngoRepository.deleteById(id);
        }
        return "redirect:/admin/manage-ngos";
    }

    @GetMapping("/admin/delete-volunteer")
    public String deleteVolunteerAccount(@RequestParam Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        if (volunteer != null) {
            userRepository.findByEmail(volunteer.getEmail()).ifPresent(userRepository::delete);
            volunteerRepository.deleteById(id);
        }
        return "redirect:/admin/manage-volunteers";
    }
   
    
    // ---------- HELPER METHODS ----------
    private User getLoggedInUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userRepository.findById(userId).orElseThrow();
    }

    private NGO getLoggedInNgo(HttpSession session) {
        User user = getLoggedInUser(session);
        return ngoRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("NGO profile not found"));
    }

    private Volunteer getLoggedInVolunteer(HttpSession session) {
        User user = getLoggedInUser(session);
        return volunteerRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Volunteer profile not found"));
    }

    private Assignment assignmentForVolunteer(Long assignmentId, Volunteer volunteer) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        if (assignment.getVolunteer() == null
                || !assignment.getVolunteer().getId().equals(volunteer.getId())) {
            throw new IllegalArgumentException("Assignment does not belong to this volunteer.");
        }
        return assignment;
    }

    private Map<Long, Assignment> assignmentsFor(List<Donation> donations) {
        Map<Long, Assignment> assignments = new HashMap<>();
        for (Donation donation : donations) {
            assignmentRepository.findByDonationId(donation.getId())
                    .ifPresent(assignment -> assignments.put(donation.getId(), assignment));
        }
        return assignments;
    }

    private void assignAvailableVolunteer(Donation donation) {
        if (donation.getNgo() == null || assignmentRepository.findByDonationId(donation.getId()).isPresent()) {
            return;
        }

        volunteerRepository.findAll().stream()
                .filter(Volunteer::isApproved)
                .filter(Volunteer::isActive)
                .findFirst()
                .ifPresent(volunteer -> {
                    Assignment assignment = new Assignment();
                    assignment.setDonation(donation);
                    assignment.setVolunteer(volunteer);
                    assignment.setStatus("ASSIGNED");
                    assignmentRepository.save(assignment);
                });
    }
}