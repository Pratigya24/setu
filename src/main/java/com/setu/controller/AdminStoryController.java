// com/setu/controller/AdminStoryController.java
package com.setu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.setu.entity.Story;
import com.setu.repository.StoryRepository;

@Controller
public class AdminStoryController {
    @Autowired private StoryRepository storyRepository;

    @GetMapping("/admin/stories")
    public String page(Model model) {
        model.addAttribute("storyList", storyRepository.findAllByOrderByCreatedAtDesc());
        return "admin-stories";
    }

    @PostMapping("/admin/stories")
    public String add(@RequestParam String title, @RequestParam String content,
                      @RequestParam String authorName, @RequestParam String imageUrl) {
        storyRepository.save(new Story(title.trim(), content.trim(), imageUrl.trim(), authorName.trim()));
        return "redirect:/admin/stories";
    }

    @PostMapping("/admin/stories/delete")
    public String delete(@RequestParam Long id) {
        storyRepository.deleteById(id);
        return "redirect:/admin/stories";
    }
}