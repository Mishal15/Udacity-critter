package com.udacity.jdnd.course3.critter.schedule;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private SchedulesService scheduleService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule sController = new Schedule();
        sController.setDate(scheduleDTO.getDate());
        sController.setActivities(scheduleDTO.getActivities());

        Schedule savedSchedule = scheduleService.createSchedule(sController,
                scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return convertScheduleToDTO(savedSchedule);
    }
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
    private ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO sControllerDTO = new ScheduleDTO();
        sControllerDTO.setId(schedule.getId());
        sControllerDTO.setDate(schedule.getDate());
        sControllerDTO.setActivities(schedule.getActivities());

        if (schedule.getEmployees() != null) {
            sControllerDTO.setEmployeeIds(schedule.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList()));
        } else {
            sControllerDTO.setEmployeeIds(new ArrayList<>());
        }

        if (schedule.getPets() != null) {
            sControllerDTO.setPetIds(schedule.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList()));
        } else {
            sControllerDTO.setPetIds(new ArrayList<>());
        }

        return sControllerDTO;
    }
}
