package com.example.alishev_spring_security.services;

import com.example.alishev_spring_security.models.Person;
import com.example.alishev_spring_security.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder; // бин, который нужен для шифрования паролей

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional // т.к. происходят изменения в БД
    public void register(Person person) {  // метод регистрации пользователя
        String encodedPassword = passwordEncoder.encode(person.getPassword()); // метод encode зашифрует пароль
        person.setPassword(encodedPassword); // обновляем пароль после шифрования

        person.setRole("ROLE_USER"); // для каждого зарегистрированного пользователя назначаем роль Юзер

       // person.setPassword(passwordEncoder.encode(person.getPassword())); // можно так, вместо двух строк выше

        peopleRepository.save(person);

    }

}
