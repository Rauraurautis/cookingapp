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
import com.kurko.cookingapp.model.User;
import com.kurko.cookingapp.service.IngredientService;
import com.kurko.cookingapp.service.RecipeService;
import com.kurko.cookingapp.service.UserDetailsServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @Autowired
    IngredientRepository ir;

    @Autowired
    RecipeRepository rr;

    @Autowired
    RecipeService rs;

    @Autowired
    IngredientService is;

    @Autowired
    UserDetailsServiceImpl us;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup(Model model) {

        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.toString());
            return "signup";
        }
        return us.postNewUser(user);
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("users", us.getUsers());
        return "admin";
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("recipes", rr.findAll());
        return "main";
    }

    //Getting image of the recipe
    @GetMapping("/recipes/image/{id}")
    public void getImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Recipe recipe = rr.findById(id).get();
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(recipe.getImage());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("/recipes/{id}")
    public String getRecipe(@PathVariable("id") Long id, Model model) {
        Recipe recipe = rr.findById(id).get();
        LinkedHashMap<Ingredient, String> ingredientMap = is.mapIngredients(recipe);
        model.addAttribute("newIngredient", new Ingredient());
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredientMap);
        model.addAttribute("ingredientlist", ir.findAll());
        return "recipeinfo";
    }

    @PostMapping("/recipes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String addIngredient(@RequestParam("ingredient") Long ingredientId, @RequestParam("amount") String amount, @PathVariable("id") Long recipeId) {
        return rs.addIngredient(ingredientId, amount, recipeId);
    }
    
     @PostMapping("/recipes/description/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editDescription(@RequestParam("description")@Valid String newDescription, @PathVariable("id") long recipeId) {
      
        return rs.editDescription(recipeId, newDescription);
    }

    @GetMapping("/newingredient")
    public String newIngredient(Model model) {
        model.addAttribute("newIngredient", new Ingredient());
        model.addAttribute("ingredients", ir.findAll());
        return "newingredient";
    }

    @GetMapping("/ingredient/{id}")
    public String viewIngredient(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingredient", ir.findById(id).get());
        return "ingredientinfo";
    }

    @PostMapping("/saveIngredient")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveIngredient(@Valid Ingredient ingredient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newingredient";
        }
        return is.saveIngredient(ingredient);
    }

    @GetMapping("/newrecipe")
    public String newRecipe(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        model.addAttribute("ingredients", ir.findAll());
        return "newrecipe";
    }

    @PostMapping("/newrecipe")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveRecipe(@Valid Recipe recipe, @RequestParam("file") MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newrecipe";
        }
        return rs.saveRecipe(recipe, file);
    }

    @GetMapping("/deleterecipe/{id}")
    public String deleteRecipe(@PathVariable("id") Long id) {
        return rs.deleteRecipe(id);
    }
}
