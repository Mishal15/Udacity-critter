package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.CustomersRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeesRepository;
import com.udacity.jdnd.course3.critter.Repository.PetsRepository;
import com.udacity.jdnd.course3.critter.Repository.SchedulesRepository;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class SchedulesService {
    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private SchedulesRepository schedulesRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<Schedule> getAllSchedule(){
        return schedulesRepository.findAll();
    }

    public List<Schedule> getAllForPet(Long petId){
        Pet pet = petsRepository.getOne(petId);
        return schedulesRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getAllForEmployee(Long employeeId){
        Employee employee = employeesRepository.getOne(employeeId);
        return schedulesRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> getAllScheduleForCustomer(Long customerId){
        Customer customer = customersRepository.getOne(customerId);
        return schedulesRepository.getAllByPetsIn(customer.getPets());
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeesRepository.findAllById(employeeIds);
        List<Pet> pets = petsRepository.findAllById(petIds);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return schedulesRepository.save(schedule);
    }
}
