package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Repository.CustomersRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeesRepository;
import com.udacity.jdnd.course3.critter.Repository.PetsRepository;
import com.udacity.jdnd.course3.critter.Repository.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
@Service
@Transactional
public class SchedulesService {
    @Autowired
    private SchedulesRepository scheduleRepository;
    @Autowired
    private EmployeesRepository employeeRepository;
    @Autowired
    private PetsRepository petRepository;
    @Autowired
    private CustomersRepository customerRepository;
    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleForPet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Pet with id " + petId + " not found"));
        return scheduleRepository.findByPetsContaining(pet);
    }
    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        return scheduleRepository.findByEmployeesContaining(employee);
    }
    public List<Schedule> getScheduleForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + customerId + " not found"));
        List<Pet> pets = customer.getPets();
        if (pets == null || pets.isEmpty()) {
            return Collections.emptyList();
        }
        return scheduleRepository.findByPetsIn(pets);
    }
}
