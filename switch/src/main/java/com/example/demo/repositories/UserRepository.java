package com.example.demo.repositories;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByUserName(String username);
}
