package com.interview.controller;

import java.util.List;

import com.interview.model.EmployeeStatus;
import com.interview.model.EmployeeTax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.interview.model.Employee;
import com.interview.service.EmployeeService;


import javax.validation.Valid;

//creating RestController
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    //creating a get mapping that retrieves all the employee details from the database
    @GetMapping("/employee")
    private List<EmployeeTax> getAllEmployees() {
        return employeeService.getAllEmployeesTax();
    }

    //creating a get mapping that retrieves the detail of a specific employee
    @GetMapping("/employee/{id}")
    private Employee getEmployee(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    //creating post mapping that post the employee detail in the database
    @PostMapping("/employee")
    private EmployeeStatus saveEmployee(@Valid @RequestBody Employee employee) {
        EmployeeStatus employeeStatus = new EmployeeStatus();

        if (employeeService.saveOrUpdate(employee) != null) {
            employeeStatus.setStatusCode(200);
            employeeStatus.setStatusMessage("Inserted Successfully");
        } else {
            employeeStatus.setStatusMessage("Please check your inputs");
            employeeStatus.setStatusCode(500);
        }
        return employeeStatus;
    }
}
