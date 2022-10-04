package com.example.alishev_spring_security.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
  //  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_')"), если прописываем для нескольких ролей
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
