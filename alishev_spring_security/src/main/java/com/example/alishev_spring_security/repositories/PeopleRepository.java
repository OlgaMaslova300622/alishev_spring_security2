package com.example.alishev_spring_security.repositories;

import com.example.alishev_spring_security.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Persistence;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername (String username);

}
