package com.setu.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.setu.entity.Story;
import com.setu.repository.StoryRepository;

@Component
public class StorySeeder implements CommandLineRunner {
    private final StoryRepository repo;
    public StorySeeder(StoryRepository repo) { this.repo = repo; }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) return;
        repo.save(new Story("Winter warmth for 40 children",
            "A donor offered 40 blankets on SETU. Within a day a nearby home claimed them and a volunteer delivered them before the cold wave hit.",
            "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop", "Asha Home"));
        repo.save(new Story("A birthday they will never forget",
            "Riya booked an occasion at a charitable home, brought a cake and games, and spent the afternoon with the residents.",
            "https://images.unsplash.com/photo-1574722772633-e401c33eb317?q=80&w=800&auto=format&fit=crop", "Riya, Donor"));
        repo.save(new Story("From idle weekends to real impact",
            "Imran signed up as a volunteer and now delivers donations every Saturday, with live tracking so donors can follow along.",
            "https://images.unsplash.com/photo-1758599668408-dfb4a7ed22c0?q=80&w=800&auto=format&fit=crop", "Imran, Volunteer"));
    }
}