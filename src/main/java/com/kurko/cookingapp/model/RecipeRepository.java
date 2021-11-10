/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Rautis
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    
    @Query("select id from Recipe r where r.name = ?1")
    public Long findByName(String name);
    
}
