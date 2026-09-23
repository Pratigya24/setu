package com.setu.config;

import com.setu.entity.Category;
import com.setu.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        // name -> description. Order here is the order they'll appear in the dropdown.
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put("Fruits & Vegetables", "Fresh fruits and vegetables");
        defaults.put("Food & Groceries", "Non-perishable food items, groceries, ration kits");
        defaults.put("Medicine", "Medicines, first-aid supplies, medical equipment");
        defaults.put("Clothes", "New or gently used clothing for all age groups");
        defaults.put("Books", "School books, storybooks, educational material");
        defaults.put("Toys", "Toys and games for children");
        defaults.put("Furniture", "Beds, chairs, tables, and other furniture");
        defaults.put("Electronics", "Fans, heaters, and other useful appliances");
        defaults.put("Others", "Anything that doesn't fit the categories above");

        Set<String> existingNames = categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        int added = 0;
        for (Map.Entry<String, String> entry : defaults.entrySet()) {
            if (!existingNames.contains(entry.getKey())) {
                categoryRepository.save(new Category(entry.getKey(), entry.getValue()));
                added++;
            }
        }

        if (added > 0) {
            System.out.println("=========================================");
            System.out.println("Added " + added + " missing donation categories.");
            System.out.println("=========================================");
        }
    }
}