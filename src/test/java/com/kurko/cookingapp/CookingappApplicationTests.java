package com.kurko.cookingapp;

import com.kurko.cookingapp.web.MainController;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CookingappApplicationTests {

    @Autowired
    private MainController controller;
    
    
    
	@Test
	void contextLoads() throws Exception {
            assertThat(controller).isNotNull();
	}

}
