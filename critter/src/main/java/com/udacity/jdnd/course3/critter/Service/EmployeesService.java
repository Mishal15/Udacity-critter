package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.EmployeesRepository;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public Employee getEmployeeById(Long employeeId){
        return employeesRepository.getOne(employeeId);
    }

    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> employees = employeesRepository.getAllByDaysAvailableContains(date.getDayOfWeek()).stream()
                .filter(employee->employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
        return employees;
    }
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeesRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeesRepository.save(employee);
    }

    public Employee saveEmployee(Employee employee){
        return employeesRepository.save(employee);
    }

}
