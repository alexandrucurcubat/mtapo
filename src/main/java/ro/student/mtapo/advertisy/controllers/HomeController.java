package ro.student.mtapo.advertisy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.student.mtapo.advertisy.models.Announcement;
import ro.student.mtapo.advertisy.models.AnnouncementReport;
import ro.student.mtapo.advertisy.services.AnnouncementService;
import ro.student.mtapo.advertisy.util.AnnouncementDetails;

@Controller
@RequestMapping("")
public class HomeController {

    AnnouncementService announcementService;

    public HomeController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:announcements";
    }

    @GetMapping("announcements")
    public String announcements(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("principal", authentication.getPrincipal());
        model.addAttribute("homeFragment", true);
        model.addAttribute("showAnnouncements", true);
        model.addAttribute("announcements", announcementService.getVisibleAndActiveAnnouncements());
        model.addAttribute("categories", announcementService.getAnnouncementCategories());
        model.addAttribute("activeCategory", "all");
        return "index";
    }

    @GetMapping("announcement/{announcementId}")
    public String announcement(@PathVariable int announcementId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("principal", authentication.getPrincipal());
        model.addAttribute("homeFragment", true);
        model.addAttribute("categories", announcementService.getAnnouncementCategories());
        Announcement announcement = announcementService.getAnnouncementById(announcementId);
        if ((announcement.getIsActive() && announcement.getIsVisible())
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            announcementService.incrementViewCount(announcementId);
            model.addAttribute("showAnnouncementDetails", true);
            model.addAttribute("announcement", announcement);
            model.addAttribute("activeCategory", announcementService.getAnnouncementCategoryByAnnouncementId(announcementId).getId());
        } else {
            model.addAttribute("showAnnouncements", true);
            model.addAttribute("announcements", announcementService.getVisibleAndActiveAnnouncements());
            model.addAttribute("activeCategory", "all");
        }
        return "index";
    }

    @GetMapping("announcements/category/{categoryId}")
    public String announcementsByCategory(@PathVariable int categoryId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("principal", authentication.getPrincipal());
        model.addAttribute("homeFragment", true);
        model.addAttribute("showAnnouncements", true);
        model.addAttribute("announcements", announcementService.getAnnouncementsByCategoryId(categoryId));
        model.addAttribute("categories", announcementService.getAnnouncementCategories());
        model.addAttribute("activeCategory", categoryId);
        return "index";
    }

    @PostMapping("search")
    public String searchAnnouncements(@RequestParam String queryString, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("principal", authentication.getPrincipal());
        model.addAttribute("homeFragment", true);
        model.addAttribute("showAnnouncements", true);
        model.addAttribute("announcements", announcementService.searchAnnouncements(queryString));
        model.addAttribute("categories", announcementService.getAnnouncementCategories());
        model.addAttribute("activeCategory", "all");
        return "index";
    }

    @GetMapping("image/{announcementId}")
    public ResponseEntity<byte[]> announcementImage(@PathVariable int announcementId) {
        return announcementService.getAnnouncementImage(announcementId);
    }

    @PostMapping("announcement/report")
    public String reportAnnouncement(
            @RequestParam() int announcementId,
            @RequestParam() String reportMessage,
            RedirectAttributes redirectAttributes
    ) {
        AnnouncementDetails details = new AnnouncementDetails();
        details.setAnnouncementId(announcementId);
        details.setMessage(reportMessage);
        AnnouncementReport report = announcementService.reportAnnouncement(details);
        if (report != null) {
            redirectAttributes.addFlashAttribute("announcementReported", true);
        } else {
            redirectAttributes.addFlashAttribute("errorReport", true);
        }
        return "redirect:/announcement/" + announcementId;
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("loginFragment", true);
        return "index";
    }

    @GetMapping("loginError")
    public String loginError(RedirectAttributes attributes) {
        attributes.addFlashAttribute("loginError", true);
        return "redirect:login";
    }
}