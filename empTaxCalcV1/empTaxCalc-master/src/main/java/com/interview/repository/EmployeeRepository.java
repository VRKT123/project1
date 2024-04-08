package com.interview.repository;
import org.springframework.data.repository.CrudRepository;
import com.interview.model.Employee;
public interface EmployeeRepository extends CrudRepository<Employee, Integer>
{
	
}
