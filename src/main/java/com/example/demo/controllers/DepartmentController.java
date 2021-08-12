package com.example.demo.controllers;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins= "*")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    // API for adding new department
    @PostMapping("/department")
    public ResponseEntity add(@RequestBody Department department) {
        try{
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can add department.
                if(userDetails.isAdmin()){
                    // save the department.
                    departmentRepository.save(department);
                    // return response with ok status if there is no crash.
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // get all departments
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAll() {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can get all departments.
                if(userDetails.isAdmin()){
                    List<Department> allDepartments = StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
                    // return all departments.
                    return new ResponseEntity(allDepartments, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
