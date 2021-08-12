package com.example.demo.repositories;

import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository  extends CrudRepository<Department, Integer> {
}
