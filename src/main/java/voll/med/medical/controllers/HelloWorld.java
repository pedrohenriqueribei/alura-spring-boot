package voll.med.medical.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ola")
public class HelloWorld {

    @GetMapping
    public String hello(){
        return "Olá Mundo com Spring Boot";
    }
}
