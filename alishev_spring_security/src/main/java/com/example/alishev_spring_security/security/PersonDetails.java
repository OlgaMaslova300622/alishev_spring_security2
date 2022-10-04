package com.example.alishev_spring_security.security;

import com.example.alishev_spring_security.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //метод нужен для авторизации-права доступа для ролей

        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // показывает что данная сущность активна
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // показывает что данный аккаунт не заблокирован
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // пароль не просрочен
        return true;
    }

    @Override
    public boolean isEnabled() { // аккаунт включен и работает
        return true;
    }

    public Person getPerson() { // нужен чтобы получать данные аутентифицированного  пользователя
        return this.person;
    }
}
