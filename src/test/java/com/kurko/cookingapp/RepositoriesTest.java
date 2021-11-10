/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp;

import com.kurko.cookingapp.model.Ingredient;
import com.kurko.cookingapp.model.IngredientRepository;
import com.kurko.cookingapp.model.Recipe;
import com.kurko.cookingapp.model.RecipeRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {
    
    @Autowired
    private RecipeRepository rr;

    @Autowired
    private IngredientRepository ir;
        
    @Test
    public void findByNameShouldReturnId() {
        rr.save(new Recipe("testrecipe"));
        Long id = rr.findByName("testrecipe");
        
        assertThat(id).isNotNull();
    }
    
    @Test
    public void createNewRecipe() {
        Recipe recipe = new Recipe("testRecipe");
        rr.save(recipe);
        assertThat(recipe.getId()).isNotNull();
    }
    
    @Test
    public void createNewIngredient() {
        Ingredient ingredient = new Ingredient("testIngredient");
        ir.save(ingredient);
        assertThat(ingredient.getId()).isNotNull();
        
    }
    
}
