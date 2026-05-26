package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPetsContaining(Pet pet);

    List<Schedule> findByEmployeesContaining(Employee employee);

    List<Schedule> findByPetsIn(List<Pet> pets);
}

