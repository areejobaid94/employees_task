package com.example.demo.controllers;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.models.SaveAndUpdateEmployeeRequest;
import com.example.demo.models.SearchEmployeeRequest;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins= "*")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    // API for adding new Employee
    @PostMapping("/employee")
    public ResponseEntity<AppUser> add( @RequestBody SaveAndUpdateEmployeeRequest saveEmployeeRequest) {
        try{
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can add employee.
                if(userDetails.isAdmin()){
                    // get the department from the database using the saveEmployeeRequest.departmentId.
                    Optional<Department> department = departmentRepository.findById(saveEmployeeRequest.getDepartmentId());
                    // create new employee using the request data.
                    Employee employee = new Employee(saveEmployeeRequest.getFirstName(),saveEmployeeRequest.getLastName(),saveEmployeeRequest.getSalary(), saveEmployeeRequest.isActive(),department.get());
                    // save the employee.
                    Employee savedEmployee = employeeRepository.save(employee);
                    // generate username using firstname and lastname.
                    String userName = savedEmployee.getFirstName() + savedEmployee.getLastName();
                    // generate password using firstname, lastname and random number.
                    String password =userName + (int)(Math.random() * 1000);
                    // create new user.
                    AppUser user = new AppUser(userName, password,savedEmployee);
                    // save the user.
                    user = userRepository.save(user);
                    // return the user
                    return new ResponseEntity(user, HttpStatus.OK);
                }
            }
            // return bad request is anything isn't good.
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employee/{id}")
    // delete employee.
    public ResponseEntity delete(@PathVariable Integer id) {
        try {
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can delete employee.
                if(userDetails.isAdmin()){
                    // delete the employee using the PathVariable id
                    employeeRepository.deleteById(id);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    };

    // update the employee.
    @PutMapping("/employee")
    public ResponseEntity<Employee> update( @RequestBody SaveAndUpdateEmployeeRequest updateEmployeeRequest) {
        try {
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can update employee.
                if(userDetails.isAdmin()){
                    // get the department from the database using the saveEmployeeRequest.departmentId id.
                    Optional<Department> department = departmentRepository.findById(updateEmployeeRequest.getDepartmentId());
                    // get the employee from the database using the saveEmployeeRequest.getId id.
                    Employee employeeToUpdate = employeeRepository.findById(userDetails.getEmployee().getId()).get();
                    // update the employeeToUpdate's data.
                    employeeToUpdate.setDepartment(department.get());
                    employeeToUpdate.setFirstName(updateEmployeeRequest.getFirstName());
                    employeeToUpdate.setLastName(updateEmployeeRequest.getLastName());
                    employeeToUpdate.setSalary(updateEmployeeRequest.getSalary());
                    employeeToUpdate.setActive(updateEmployeeRequest.isActive());
                    // save the updated employeeToUpdate.
                    employeeToUpdate = employeeRepository.save(employeeToUpdate);
                    return new ResponseEntity(employeeToUpdate, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    };

    // search by active state.
    @GetMapping("/active_employee")
    public ResponseEntity getAllIsActive(@RequestParam(value = "isActive") Boolean isActive ) {
        try{
            // check the authentication
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can search by active state.
                if(userDetails.isAdmin()){
                // find employees by using isActive RequestParam.
                List<Employee> allEmployee = employeeRepository.findByIsActive(isActive);
                // return all employees.
                return new ResponseEntity(allEmployee, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // get all employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAll() {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can get all employees.
                if(userDetails.isAdmin()){
                    List<Employee> allEmployees = StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
                    // return all employees.
                    return new ResponseEntity(allEmployees, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // search for employees per department and/or by name.
    @GetMapping("/search_employees")
    public ResponseEntity<List<Employee>> search(@RequestBody SearchEmployeeRequest employee) {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can search for employees.
                if(userDetails.isAdmin()){
                List<Employee> searchEmployees = new ArrayList<Employee>();
                if( employee.getFirstName() != null && employee.getLastName() != null &&  employee.getDepId() != null){
                    Optional<Department> department = departmentRepository.findById(employee.getDepId());
                    searchEmployees = employeeRepository.findByLastNameAndFirstNameAndDepartmentAllIgnoreCase(employee.getLastName(), employee.getFirstName(), department.get());
                } else if (employee.getFirstName() != null && employee.getLastName() != null ){
                    searchEmployees = employeeRepository.findByLastNameAndFirstNameAllIgnoreCase(employee.getLastName(), employee.getFirstName());
                }else if (employee.getFirstName() != null && employee.getDepId() != null){
                    Optional<Department> department = departmentRepository.findById(employee.getDepId());
                    searchEmployees = employeeRepository.findByFirstNameAndDepartmentAllIgnoreCase(employee.getFirstName(), department.get());
                }
                else if (employee.getLastName() != null && employee.getDepId() != null){
                    Optional<Department> department = departmentRepository.findById(employee.getDepId());
                    searchEmployees = employeeRepository.findByLastNameAndAndDepartmentAllIgnoreCase(employee.getLastName(), department.get());
                }
                else if (employee.getFirstName() != null){
                    searchEmployees = employeeRepository.findByFirstNameAllIgnoreCase(employee.getFirstName());
                } else if (employee.getLastName() != null){
                    searchEmployees = employeeRepository.findByLastNameAllIgnoreCase(employee.getLastName());
                } else if (employee.getDepId() != null){
                    Optional<Department> department = departmentRepository.findById(employee.getDepId());
                    searchEmployees = employeeRepository.findByDepartmentAllIgnoreCase(department.get());
                }
                return new ResponseEntity(searchEmployees, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // get employees by salary => get all employees with salary more than the RequestParam salary.
    @GetMapping("/salary_employee")
    public ResponseEntity<List<Employee>> getAllBySalary(@RequestParam(value = "salary") float salary ) {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can search for employees.
                if(userDetails.isAdmin()){
                    List<Employee> allEmployee = employeeRepository.findBySalaryGreaterThanEqual(salary);
                    return new ResponseEntity(allEmployee, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // API to raise employee salary (add ratio from current salary)
    @PutMapping("/salary_employee")
    public ResponseEntity<List<Employee>> update(@RequestParam(value = "ratio") float ratio,  @RequestParam(value = "employee_id") Integer employee_id ) {
        try{
            if ((SecurityContextHolder.getContext().getAuthentication()) != null ) {
                AppUser userDetails = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
                // check if the user is an admin => only the admin can raise employee salary.
                if(userDetails.isAdmin()){
                    // get employee by employee_id to update the salary
                    Employee employeeToUpdate = employeeRepository.findById(employee_id).get();
                    // calculate the new salary
                    float newSalary = employeeToUpdate.getSalary() + employeeToUpdate.getSalary() * ratio;
                    // update the employee data.
                    employeeToUpdate.setSalary(newSalary);
                    return new ResponseEntity(employeeToUpdate, HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}