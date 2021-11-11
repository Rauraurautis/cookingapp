/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.service;

import com.kurko.cookingapp.model.Ingredient;
import com.kurko.cookingapp.model.IngredientRepository;
import com.kurko.cookingapp.model.Recipe;
import com.kurko.cookingapp.model.RecipeRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rautis
 */
@Component
public class RecipeService {

    @Autowired
    IngredientRepository ir;

    @Autowired
    RecipeRepository rr;

    public String deleteRecipe(Long id) {
        rr.deleteById(id);
        return "main";
    }

    public String saveRecipe(Recipe recipe, MultipartFile file) {

        // Checks whether a recipe with the same name already exists in the db
        Boolean exists = false;
        for (Recipe r : rr.findAll()) {
            if (r.getName().toLowerCase().equals(recipe.getName().toLowerCase())) {
                exists = true;
            }
        };
        if (recipe.getName().isEmpty()) {
            return "main";
        } else if (exists) {
            return "main";
        } else if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                recipe.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        rr.save(recipe);
        return "redirect:/recipes/" + rr.findByName(recipe.getName());
    }

    public String addIngredient(Long ingredientId, String amount, Long recipeId) {
        Ingredient ingredient = ir.findById(ingredientId).get();
        Recipe recipe = rr.findById(recipeId).get();
        recipe.addIngredient(ingredient, amount);
        rr.save(recipe);

        return "redirect:/recipes/" + recipeId;
    }

    public String editDescription(Long recipeId, String newDescription) {
        Recipe recipe = rr.findById(recipeId).get();
        recipe.editDescription(newDescription);
        rr.save(recipe);

        return "redirect:/recipes/" + recipeId;
    }

}
