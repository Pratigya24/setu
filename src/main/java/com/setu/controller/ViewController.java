package com.setu.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.setu.entity.OccasionBooking;
import com.setu.repository.OccasionBookingRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    private static final String DONATION_PHOTO_DIR = "uploads/donation-photos/";

    private static final Set<String> OCCASION_TYPES =
            Set.of("Birthday", "Anniversary", "Festival", "Memorial", "Graduation", "Other");

    @Autowired private UserRepository userRepository;
    @Autowired private NGORepository ngoRepository;
    @Autowired private VolunteerRepository volunteerRepository;
    @Autowired private DonationRepository donationRepository;
    @Autowired private RequestRepository requestRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private OccasionBookingRepository bookingRepository;
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

    @GetMapping("/donor/charitable-homes")
    public String verifiedCharitableHomes(Model model) {
        model.addAttribute("ngoList", ngoRepository.findByApproved(true));
        return "verified-charitable-homes";
    }

    @GetMapping("/donor/donate")
    public String donatePage(@RequestParam(required = false) Long requestId,
                             HttpSession session, Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("donorProfile", getLoggedInUser(session));
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
                                  @RequestParam(required = false) MultipartFile donationPhoto,
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
        try {
            donation.setPhotoPath(saveDonationPhoto(donationPhoto));
        } catch (IOException e) {
            model.addAttribute("errorMsg", "The donation photo could not be uploaded. Please try again.");
            model.addAttribute("categoryList", categoryRepository.findAll());
            model.addAttribute("donorProfile", donor);
            return "donate";
        }

        if (requestId != null) {
            requestRepository.findById(requestId).ifPresent(req -> {
                donation.setNgo(req.getNgo());
            });
        }

        donationRepository.save(donation);
        model.addAttribute("successMsg", "Thank you for your support! Your donation has been sent to the charitable home for acceptance.");
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
                                   @RequestParam(required = false) MultipartFile donationPhoto,
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
        try {
            donation.setPhotoPath(saveDonationPhoto(donationPhoto));
        } catch (IOException e) {
            model.addAttribute("errorMsg", "The donation photo could not be uploaded. Please try again.");
            model.addAttribute("categoryList", categoryRepository.findAll());
            return "donor-offer-item";
        }

        donationRepository.save(donation);
        model.addAttribute("successMsg", "Item offered successfully! NGOs can now claim it.");
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "donor-offer-item";
    }

    private String saveDonationPhoto(MultipartFile photo) throws IOException {
        if (photo == null || photo.isEmpty()) {
            return null;
        }
        if (photo.getContentType() == null || !photo.getContentType().startsWith("image/")) {
            throw new IOException("Only image files are allowed.");
        }
        Path uploadDirectory = Paths.get(DONATION_PHOTO_DIR).toAbsolutePath().normalize();
        Files.createDirectories(uploadDirectory);
        String originalName = photo.getOriginalFilename() == null ? "" : photo.getOriginalFilename();
        int dot = originalName.lastIndexOf('.');
        String extension = dot >= 0 ? originalName.substring(dot).toLowerCase() : "";
        Path destination = uploadDirectory.resolve(UUID.randomUUID() + extension).normalize();
        if (!destination.startsWith(uploadDirectory)) {
            throw new IOException("Invalid photo path.");
        }
        Files.copy(photo.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return DONATION_PHOTO_DIR + destination.getFileName();
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
            model.addAttribute("successMsg", "Item claimed! Volunteers can now pick it up.");
        }

        model.addAttribute("availableList", donationRepository.findByNgoIsNullAndStatus("AVAILABLE"));
        return "ngo-available-donations";
    }

    @PostMapping("/ngo/accept-request-donation")
    public String acceptRequestDonation(@RequestParam Long id, HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        Donation donation = donationRepository.findById(id).orElseThrow();

        if (donation.getNgo() == null || !donation.getNgo().getId().equals(ngo.getId())) {
            throw new IllegalArgumentException("Donation does not belong to this charitable home.");
        }
        if (!"PENDING".equals(donation.getStatus())) {
            model.addAttribute("errorMsg", "This donation is no longer awaiting acceptance.");
        } else {
            donation.setStatus("ACCEPTED");
            donationRepository.save(donation);
            model.addAttribute("successMsg", "Donation accepted. It is now open for volunteers to pick up.");
        }

        List<Donation> received = donationRepository.findByNgo(ngo);
        model.addAttribute("receivedDonations", received);
        model.addAttribute("trackingByDonation", assignmentsFor(received));
        return "ngo-donations-received";
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
                                      @RequestParam(required = false) String address,
                                      @RequestParam(required = false) String addressLine1,
                                      @RequestParam(required = false) String addressLine2,
                                      @RequestParam(required = false) String landmark,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String state,
                                      @RequestParam(required = false) String postalCode,
                                      @RequestParam(required = false) String country,
                                      HttpSession session, Model model) {

        User user = getLoggedInUser(session);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        user.setAddressLine1(addressLine1);
        user.setAddressLine2(addressLine2);
        user.setLandmark(landmark);
        user.setCity(city);
        user.setState(state);
        user.setPostalCode(postalCode);
        user.setCountry(country);
        userRepository.save(user);
        session.setAttribute("userName", name);

        model.addAttribute("successMsg", "Profile updated successfully!");
        model.addAttribute("donorProfile", user);
        return "donor-profile";
    }

    // ---------- DONOR: OCCASION BOOKING ----------
    @GetMapping("/donor/book-occasion")
    public String bookOccasionPage(@RequestParam Long ngoId, HttpSession session, Model model) {
        NGO ngo = ngoRepository.findById(ngoId).filter(NGO::isApproved).orElseThrow();
        model.addAttribute("ngo", ngo);
        model.addAttribute("donorProfile", getLoggedInUser(session));
        return "occasion-booking";
    }

    @PostMapping("/donor/book-occasion")
    public String submitOccasionBooking(@RequestParam Long ngoId,
            @RequestParam String occasionType,
            @RequestParam(required = false) String occasionTitle,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate eventDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @RequestParam Integer guestCount,
            @RequestParam String contactPhone,
            @RequestParam String description,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        User donor = getLoggedInUser(session);
        NGO ngo = ngoRepository.findById(ngoId).filter(NGO::isApproved).orElseThrow();

        String error = null;
        if (!OCCASION_TYPES.contains(occasionType)) {
            error = "Please select a valid occasion.";
        } else if ("Other".equals(occasionType) && (occasionTitle == null || occasionTitle.isBlank())) {
            error = "Please enter the name of your occasion.";
        } else if (eventDate.isBefore(LocalDate.now())
                || (eventDate.isEqual(LocalDate.now()) && startTime.isBefore(LocalTime.now()))) {
            error = "Please choose a future date and time.";
        } else if (!endTime.isAfter(startTime)) {
            error = "End time must be after the start time.";
        } else if (guestCount < 1) {
            error = "Guest count must be at least 1.";
        } else if (bookingRepository.existsApprovedOverlap(ngo, eventDate, startTime, endTime)) {
            error = "This charitable home already has a confirmed booking in that time. Please pick another slot.";
        }

        if (error != null) {
            model.addAttribute("errorMsg", error);
            model.addAttribute("ngo", ngo);
            model.addAttribute("donorProfile", donor);
            return "occasion-booking";
        }

        OccasionBooking booking = new OccasionBooking();
        booking.setDonor(donor);
        booking.setNgo(ngo);
        booking.setOccasionType(occasionType);
        booking.setOccasionTitle("Other".equals(occasionType) ? occasionTitle.trim() : null);
        booking.setEventDate(eventDate);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setGuestCount(guestCount);
        booking.setContactPhone(contactPhone);
        booking.setDescription(description);
        bookingRepository.save(booking);

        redirectAttributes.addFlashAttribute("successMsg",
                "Request sent! The charitable home will confirm your slot shortly.");
        return "redirect:/donor/my-bookings";
    }

    @GetMapping("/donor/my-bookings")
    public String myBookings(HttpSession session, Model model) {
        model.addAttribute("bookingList",
                bookingRepository.findByDonorOrderByCreatedAtDesc(getLoggedInUser(session)));
        return "donor-bookings";
    }

    @PostMapping("/donor/cancel-booking")
    public String cancelBooking(@RequestParam Long bookingId, HttpSession session) {
        User donor = getLoggedInUser(session);
        OccasionBooking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getDonor() == null || !booking.getDonor().getId().equals(donor.getId())) {
            throw new IllegalArgumentException("Booking does not belong to this donor.");
        }
        if ("PENDING".equals(booking.getStatus()) || "APPROVED".equals(booking.getStatus())) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        }
        return "redirect:/donor/my-bookings";
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
        return "ngo-donations-received";
    }

    // ---------- NGO: OCCASION BOOKING REQUESTS ----------
    @GetMapping("/ngo/bookings")
    public String ngoBookings(HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        model.addAttribute("bookingList", bookingRepository.findByNgoOrderByCreatedAtDesc(ngo));
        return "ngo-bookings";
    }

    @PostMapping("/ngo/booking-respond")
    public String respondToBooking(@RequestParam Long bookingId,
                                   @RequestParam String action,
                                   @RequestParam(required = false) String note,
                                   HttpSession session, RedirectAttributes redirectAttributes) {
        NGO ngo = getLoggedInNgo(session);
        OccasionBooking booking = bookingRepository.findById(bookingId).orElseThrow();

        if (booking.getNgo() == null || !booking.getNgo().getId().equals(ngo.getId())) {
            throw new IllegalArgumentException("Booking does not belong to this charitable home.");
        }
        if (!"PENDING".equals(booking.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMsg", "This request was already handled.");
            return "redirect:/ngo/bookings";
        }

        if ("APPROVE".equals(action)) {
            if (bookingRepository.existsApprovedOverlap(ngo, booking.getEventDate(),
                    booking.getStartTime(), booking.getEndTime())) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "You already approved another booking that overlaps with this time.");
                return "redirect:/ngo/bookings";
            }
            booking.setStatus("APPROVED");
        } else if ("REJECT".equals(action)) {
            booking.setStatus("REJECTED");
        } else {
            throw new IllegalArgumentException("Invalid action.");
        }
        booking.setNgoResponse(note);
        bookingRepository.save(booking);

        redirectAttributes.addFlashAttribute("successMsg", "Booking " + booking.getStatus().toLowerCase() + ".");
        return "redirect:/ngo/bookings";
    }

    @GetMapping("/ngo/profile")
    public String ngoProfilePage(HttpSession session, Model model) {
        model.addAttribute("ngoProfile", getLoggedInNgo(session));
        return "ngo-profile";
    }

    @PostMapping("/ngo/profile")
    public String updateNgoProfile(@RequestParam String name,
                                    @RequestParam String phone,
                                    @RequestParam(required = false) String address,
                                    @RequestParam(required = false) String addressLine1,
                                    @RequestParam(required = false) String addressLine2,
                                    @RequestParam(required = false) String landmark,
                                    @RequestParam(required = false) String city,
                                    @RequestParam(required = false) String state,
                                    @RequestParam(required = false) String postalCode,
                                    @RequestParam(required = false) String country,
                                    @RequestParam String description,
                                    HttpSession session, Model model) {
        NGO ngo = getLoggedInNgo(session);
        ngo.setName(name);
        ngo.setPhone(phone);
        ngo.setAddress(address);
        ngo.setAddressLine1(addressLine1);
        ngo.setAddressLine2(addressLine2);
        ngo.setLandmark(landmark);
        ngo.setCity(city);
        ngo.setState(state);
        ngo.setPostalCode(postalCode);
        ngo.setCountry(country);
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

    // ---------- VOLUNTEER: SELF-ACCEPT PICKUPS ----------
    @GetMapping("/volunteer/available-pickups")
    public String availablePickups(Model model) {
        model.addAttribute("pickupList", donationRepository.findUnassignedAccepted());
        return "volunteer-available-pickups";
    }

    @PostMapping("/volunteer/claim-pickup")
    public String claimPickup(@RequestParam Long donationId, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Volunteer volunteer = getLoggedInVolunteer(session);
        Donation donation = donationRepository.findById(donationId).orElseThrow();

        if (!volunteer.isApproved() || !volunteer.isActive()) {
            throw new IllegalArgumentException("Volunteer is not available.");
        }
        if (!"ACCEPTED".equals(donation.getStatus())
                || assignmentRepository.findByDonationId(donationId).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Sorry, this pickup was already taken.");
            return "redirect:/volunteer/available-pickups";
        }

        try {
            Assignment assignment = new Assignment();
            assignment.setDonation(donation);
            assignment.setVolunteer(volunteer);
            assignment.setStatus("ASSIGNED");
            assignmentRepository.save(assignment);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Sorry, another volunteer just took this pickup.");
            return "redirect:/volunteer/available-pickups";
        }

        redirectAttributes.addFlashAttribute("successMsg", "Pickup accepted! It is now in your assignments.");
        return "redirect:/volunteer/assignments";
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
}