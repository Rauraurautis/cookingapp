/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min=4, max=30)
    @NotNull
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients  = new ArrayList<>();
    @ElementCollection
    private List<String> amount = new ArrayList<>();
    @Lob
    @JsonIgnore
    private byte[] image;
    
    public Recipe(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient i, String amount) {
        this.ingredients.add(i);
        this.amount.add(amount);
    }


    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", description=" + description + ", ingredients=" + ingredients + ", image=" + image + '}';
    }
    
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }


}
