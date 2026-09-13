package com.setu.controller;

import com.setu.entity.*;
import com.setu.repository.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;
    @Autowired private VolunteerRepository volunteerRepository;
    @Autowired private DonationRepository donationRepository;
    @Autowired private RequestRepository requestRepository;
    @Autowired private CategoryRepository categoryRepository;

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
                                  HttpSession session, Model model) {

        User donor = getLoggedInUser(session);

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setTitle(title);
        donation.setDescription(description);
        donation.setQuantity(quantity);
        donation.setStatus("PENDING");
        categoryRepository.findById(categoryId).ifPresent(donation::setCategory);

        if (requestId != null) {
            requestRepository.findById(requestId).ifPresent(req -> {
                donation.setNgo(req.getNgo());
            });
        }

        donationRepository.save(donation);
        model.addAttribute("successMsg", "Donation submitted successfully!");
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
                                   HttpSession session, Model model) {

        User donor = getLoggedInUser(session);

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setTitle(title);
        donation.setDescription(description);
        donation.setQuantity(quantity);
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
            model.addAttribute("successMsg", "Item claimed successfully! It's now assigned to you.");
        }

        model.addAttribute("availableList", donationRepository.findByNgoIsNullAndStatus("AVAILABLE"));
        return "ngo-available-donations";
    }
    
    
    
    @GetMapping("/donor/my-donations")
    public String myDonations(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        model.addAttribute("donationList", donationRepository.findByDonorOrderByDonationDateDesc(user));
        return "my-donations";
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
                                     HttpSession session, Model model) {

        NGO ngo = getLoggedInNgo(session);

        Request request = new Request();
        request.setNgo(ngo);
        request.setTitle(title);
        request.setDescription(description);
        request.setQuantity(quantity);
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
        model.addAttribute("receivedDonations", donationRepository.findByNgo(ngo));
        return "ngo-donations-received";
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
        model.addAttribute("volunteer", volunteer);
        return "volunteer-dashboard";
    }

    @GetMapping("/volunteer/assignments")
    public String volunteerAssignments(HttpSession session, Model model) {
        return "volunteer-assignments";
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
        });
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/reject-ngo")
    public String rejectNgo(@RequestParam Long id) {
        ngoRepository.deleteById(id);
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

    @GetMapping("/admin/reports")
    public String adminReports(Model model) {
        model.addAttribute("totalDonations", donationRepository.count());
        model.addAttribute("requestsFulfilled", requestRepository.findByStatus("FULFILLED").size());
        return "admin-reports";
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
}