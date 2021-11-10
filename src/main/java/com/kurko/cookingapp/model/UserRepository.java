/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.model;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Rautis
 */
public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUserName(String userName);
}
