/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.service;

import com.kurko.cookingapp.model.Ingredient;
import com.kurko.cookingapp.model.IngredientRepository;
import com.kurko.cookingapp.model.Recipe;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class IngredientService {

    @Autowired
    IngredientRepository ir;

    public String saveIngredient(Ingredient ingredient) {   
        // Checks whether the ingredient already exists
        Boolean exists = false;
        for (Ingredient i : ir.findAll()) {
            if (i.getName().toLowerCase().equals(ingredient.getName().toLowerCase())) {
                exists = true;
            }
        }
        if ((ingredient.getName().isEmpty())) {
            return "redirect:/main";
        } else if (exists) {
            return "redirect:/main";
        }
        ir.save(ingredient);
        return "redirect:/newingredient";
    
    }
    
    // Takes both Ingredient and Amount lists from the Recipe object and uses them to create a LinkedHashMap for iterating with Thymeleaf
    public LinkedHashMap<Ingredient, String> mapIngredients(Recipe recipe) {

        List<Ingredient> ingredientList = recipe.getIngredients();
        List<String> amountList = recipe.getAmount();
        LinkedHashMap<Ingredient, String> ingredientMap = new LinkedHashMap<>();
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredientMap.put(ingredientList.get(i), amountList.get(i));
        }
        return ingredientMap;
    }

}
