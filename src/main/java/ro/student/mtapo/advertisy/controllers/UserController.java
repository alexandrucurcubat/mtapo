package ro.student.mtapo.advertisy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.student.mtapo.advertisy.models.Announcement;
import ro.student.mtapo.advertisy.models.User;
import ro.student.mtapo.advertisy.services.AnnouncementService;
import ro.student.mtapo.advertisy.services.UserService;
import ro.student.mtapo.advertisy.util.AccountDetails;
import ro.student.mtapo.advertisy.util.AnnouncementDetails;

import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    UserService userService;
    AnnouncementService announcementService;

    public UserController(
            UserService userService,
            AnnouncementService announcementService
    ) {
        this.userService = userService;
        this.announcementService = announcementService;
    }

    @GetMapping("account")
    public String userAccountForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail((String) authentication.getPrincipal()));
        model.addAttribute("userAccountFragment", true);
        model.addAttribute("counties", userService.getAllCounties());
        return "index";
    }

    @GetMapping("image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable int userId) {
        return userService.getUserImage(userId);
    }

    @GetMapping("announcement/image/{announcementId}")
    public ResponseEntity<byte[]> getAnnouncementImage(@PathVariable int announcementId) {
        return announcementService.getAnnouncementImage(announcementId);
    }

    @PostMapping("update")
    public String updateAccount(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String passwordConfirmation,
            @RequestParam String countyId,
            @RequestParam String place,
            @RequestParam String streetAddress,
            @RequestParam MultipartFile userImage,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        if (password.equals(passwordConfirmation)) {
            AccountDetails details = new AccountDetails();
            details.setEmail(email);
            details.setUsername(username);
            details.setPhone(phone);
            details.setPassword(password);
            details.setCountyId(Integer.parseInt(countyId));
            details.setPlace(place);
            details.setStreetAddress(streetAddress);
            details.setUserImage(userImage);
            if (userService.updateAccount(details) != null) {
                redirectAttributes.addFlashAttribute("accountUpdated", true);
            } else {
                redirectAttributes.addFlashAttribute("passwordError", true);
            }
        } else {
            redirectAttributes.addFlashAttribute("passwordMatchError", true);
        }
        return "redirect:/user/account";
    }

    @GetMapping("announcements")
    public String userAnnouncements(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail((String) authentication.getPrincipal());
        model.addAttribute("userAnnouncements", true);
        model.addAttribute("announcements", announcementService.getAnnouncementsByUserId(user.getId()));
        return "index";
    }

    @GetMapping("announcement/{announcementId}")
    public String userAnnouncementForm(@PathVariable int announcementId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail((String) authentication.getPrincipal());
        Announcement announcement = announcementService.getAnnouncementById(announcementId);
        if (announcement != null) {
            boolean isAnnouncementCreator = user.getId() == announcement.getUser().getId();
            if (isAnnouncementCreator) {
                model.addAttribute("userAnnouncementDetails", true);
                model.addAttribute("announcement", announcement);
                model.addAttribute("currencies", announcementService.getCurrencies());
                model.addAttribute("categories", announcementService.getAnnouncementCategories());
            } else {
                model.addAttribute("userAnnouncements", true);
                model.addAttribute("announcements", announcementService.getAnnouncementsByUserId(user.getId()));
            }
        } else {
            model.addAttribute("userAnnouncements", true);
            model.addAttribute("announcements", announcementService.getAnnouncementsByUserId(user.getId()));
        }
        return "index";
    }

    @PostMapping("announcement/update")
    public String userAnnouncementUpdate(
            @RequestParam String announcementId,
            @RequestParam String categoryId,
            @RequestParam String title,
            @RequestParam String shortDescription,
            @RequestParam String longDescription,
            @RequestParam String price,
            @RequestParam String currencyId,
            @RequestParam MultipartFile announcementImage,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        AnnouncementDetails details = new AnnouncementDetails();
        details.setAnnouncementId(Integer.parseInt(announcementId));
        details.setCategoryId(Integer.parseInt(categoryId));
        details.setTitle(title);
        details.setShortDescription(shortDescription);
        details.setLongDescription(longDescription);
        details.setPrice(Double.parseDouble(price));
        details.setCurrencyId(Integer.parseInt(currencyId));
        details.setAnnouncementImage(announcementImage);
        Announcement announcement = announcementService.updateAnnouncement(details);
        if (announcement != null) {
            redirectAttributes.addFlashAttribute("announcementUpdated", true);
            return "redirect:/user/announcement/" + announcement.getId();
        }
        redirectAttributes.addFlashAttribute("updateError", true);
        return "redirect:/user/announcements";
    }

    @PostMapping("announcement/delete")
    public String deleteAnnouncement(
            @RequestParam String announcementId,
            RedirectAttributes redirectAttributes
    ) {
        announcementService.deleteAnnouncement(Integer.parseInt(announcementId));
        redirectAttributes.addFlashAttribute("announcementDeleted", true);
        return "redirect:/user/announcements";
    }

    @GetMapping("announcement/hide/{announcementId}")
    public String hideAnnouncement(@PathVariable int announcementId) {
        announcementService.hideAnnouncement(announcementId);
        return "redirect:/user/announcement/" + announcementId;
    }

    @GetMapping("announcement/show/{announcementId}")
    public String showAnnouncement(@PathVariable int announcementId) {
        announcementService.showAnnouncement(announcementId);
        return "redirect:/user/announcement/" + announcementId;
    }

    @GetMapping("announcement/create")
    public String userAnnouncementCreateForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail((String) authentication.getPrincipal()));
        model.addAttribute("userAnnouncementCreate", true);
        return "index";
    }

    @PostMapping("announcement/create")
    public String userAnnouncementCreate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail((String) authentication.getPrincipal()));
        model.addAttribute("userAnnouncementCreate", true);
        return "redirect:/user/announcement/create";
    }
}
