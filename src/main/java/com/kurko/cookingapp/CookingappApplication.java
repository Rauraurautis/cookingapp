package com.kurko.cookingapp;

import com.kurko.cookingapp.model.Ingredient;
import com.kurko.cookingapp.model.IngredientRepository;
import com.kurko.cookingapp.model.Recipe;
import com.kurko.cookingapp.model.RecipeRepository;
import com.kurko.cookingapp.model.User;
import com.kurko.cookingapp.model.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CookingappApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(CookingappApplication.class, args);

    }

    @Bean
    public CommandLineRunner saveAdmin(UserRepository ur) {
        return (args) -> {
            User admin = new User("admin", "admin");
            admin.setRoles("ROLE_ADMIN");
            admin.setActive(true);
            ur.save(admin);

        };
    }
}
