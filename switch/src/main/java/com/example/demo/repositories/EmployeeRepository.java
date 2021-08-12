package com.example.demo.repositories;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByIsActive(Boolean isActive);
    List<Employee> findByLastNameAndFirstNameAllIgnoreCase(String lastname, String firstname);
    List<Employee> findByLastNameAndFirstNameAndDepartmentAllIgnoreCase(String lastname, String firstname, Department department);
    List<Employee> findByLastNameAllIgnoreCase(String lastname);
    List<Employee> findByFirstNameAllIgnoreCase(String firstname);
    List<Employee> findByDepartmentAllIgnoreCase(Department department);
    List<Employee> findByLastNameAndAndDepartmentAllIgnoreCase(String lastname, Department department);
    List<Employee> findByFirstNameAndDepartmentAllIgnoreCase(String firstname, Department department);
    List<Employee> findBySalaryGreaterThanEqual(float salary);

    @Override
    void deleteById(Integer integer);
}
