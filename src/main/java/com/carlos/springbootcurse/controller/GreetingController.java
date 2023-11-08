package com.carlos.springbootcurse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GreetingController {
    @GetMapping("/greeting")
    public String greet(@RequestParam (name = "wizardName", required = false, defaultValue = "World")
                            String name){
        return "Hello "+name+"!";
    }

}
