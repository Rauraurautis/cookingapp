/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.web;

import com.kurko.cookingapp.model.Ingredient;
import com.kurko.cookingapp.model.IngredientRepository;
import com.kurko.cookingapp.model.Recipe;
import com.kurko.cookingapp.model.RecipeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @Autowired
    IngredientRepository ir;

    @Autowired
    RecipeRepository rr;

    @GetMapping("/api/recipes")
    public List<Recipe> recipesRest() {
        return (List<Recipe>) rr.findAll();
    }

    @GetMapping("/api/ingredients")
    public List<Ingredient> ingredientsRest() {
        return (List<Ingredient>) ir.findAll();
    }

    @GetMapping("/api/recipes/{id}")
    public Recipe singleRecipeRest(@PathVariable("id") Long id) {
        return rr.findById(id).get();
    }
}
