package com.example.khushiproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class ControllerDemo {

    @Autowired 
    HelloWorld hw;

    @GetMapping("/")
    public String getHelloWorld(){
        return hw.display();
    }
    
}
