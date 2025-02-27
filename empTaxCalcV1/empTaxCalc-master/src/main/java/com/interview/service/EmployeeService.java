package com.interview.service;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.interview.model.EmployeeTax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.interview.model.Employee;
import com.interview.repository.EmployeeRepository;

//defining the business logic
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    //getting all employee records
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employee -> employees.add(employee));
        return employees;
    }

    public List<EmployeeTax> getAllEmployeesTax() {
        List<Employee> employees = new ArrayList<Employee>();
        List<EmployeeTax> employeeTaxes = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employees.add(employee));
        Double totalSalary = 0.0;
        Double taxSalary = 0.0;
        double totalTax = 0.0;
        double temp = 0.0;
        double montlySalary = 0.0;
        Date currentDate = Date.from(Instant.now());
        Date date = new Date(2022, 4, 1);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 04);

        // set Date
        c.set(Calendar.DATE, 01);

        // set Year
        c.set(Calendar.YEAR, 2020);

        Date dateOne = c.getTime();

        for (int i = 0; i < employees.size(); i++) {
            EmployeeTax employeeTax = new EmployeeTax();

            taxSalary = employees.get(i).getSalary();

            if ((taxSalary > 0) && (taxSalary > 250000) && (taxSalary <= 500000)) {
                totalTax += taxSalary * 0.05;
            }

            if ((taxSalary > 0) && (taxSalary > 500000) && (taxSalary <= 1000000)) {
                totalTax += taxSalary * 0.1;
            }

            if ((taxSalary > 0) && (taxSalary > 1000000)) {
                totalTax += taxSalary * 0.2;
            }

            employeeTax.setYearlySalary((employees.get(i).getSalary() * 12) - totalTax);

            employeeTax.setTaxAmount(totalTax);
            employeeTax.setEmployeeCode(employees.get(i).getEmployeeId());
            employeeTax.setFirstName(employees.get(i).getFirstName());
            employeeTax.setLastName(employees.get(i).getLastName());
            employeeTax.setCessAmount(0.0);//default

            //calculate
            totalSalary = employees.get(i).getSalary();
            montlySalary = totalSalary / 12;
            if (taxSalary > 2500000) {
                employeeTax.setCessAmount((300000) * 0.02);
            }
            if (employees.get(i).getDoj().before(dateOne)) {
                int da = employees.get(i).getDoj().getDate();
                int mo = employees.get(i).getDoj().getMonth();
                System.out.println("da" + da);
                for (int j = mo + 1; j <= 12; j++) {
                    temp += montlySalary;
                }
                for (int j = mo; j <= 3; j++) {
                    temp += montlySalary;
                }
                temp += montlySalary - da;

                employeeTax.setYearlySalary(temp);
            }

            employeeTaxes.add(employeeTax);
        }

        return employeeTaxes;
    }


    //getting a specific record
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).get();
    }

    public Employee saveOrUpdate(Employee employee) {
        if ((employee.getEmployeeId() != null) &&
                (employee.getEmail() != null) &&
                (employee.getDoj() != null) &&
                (employee.getFirstName() != null) &&
                (employee.getLastName() != null) &&
                (employee.getSalary() != null) &&
                (employee.getPhoneNumber() != null)
        )
            return employeeRepository.save(employee);

        return null;
    }

    //deleting a specific record
    public void delete(int id) {
        employeeRepository.deleteById(id);
    }
}