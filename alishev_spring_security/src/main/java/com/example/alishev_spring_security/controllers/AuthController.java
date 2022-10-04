package com.example.alishev_spring_security.controllers;


import com.example.alishev_spring_security.models.Person;
import com.example.alishev_spring_security.services.RegistrationService;
import com.example.alishev_spring_security.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }


    @GetMapping("/login")   // http://localhost:8080/auth/login  // метод аутентификации
    public String loginPage() {
        return "auth/login"; // папка auth, файл login

    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) { // метод для регистрации нового пользователя
        return "auth/registration"; //папка auth, файл registration

    }

    @PostMapping("/registration") //нельзя зарегистрироваться с именем пользователя, который уже существует
    public String performRegistration (@ModelAttribute("person") @Valid  Person person, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) // если ошибка при регистрации, заново возвращаемся на страницу регистрации
            return "/auth/registration";


        registrationService.register(person);

        return "redirect:/auth/login";

    }
}
