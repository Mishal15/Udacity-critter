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
        Schedule schedule = buildScheduleEntity(scheduleDTO);

        Schedule savedSchedule = scheduleService.createSchedule(
                schedule,
                scheduleDTO.getEmployeeIds(),
                scheduleDTO.getPetIds()
        );

        return convertScheduleToDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> response = new ArrayList<>();

        for (Schedule schedule : schedules) {
            response.add(convertScheduleToDTO(schedule));
        }

        return response;
    }
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> response = new ArrayList<>();

        for (Schedule schedule : schedules) {
            response.add(convertScheduleToDTO(schedule));
        }

        return response;
    }
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> response = new ArrayList<>();

        for (Schedule schedule : schedules) {
            response.add(convertScheduleToDTO(schedule));
        }

        return response;
    }
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> response = new ArrayList<>();

        for (Schedule schedule : schedules) {
            response.add(convertScheduleToDTO(schedule));
        }

        return response;
    }
    private Schedule buildScheduleEntity(ScheduleDTO scheduleDTO) {
        Schedule sController = new Schedule();
        sController.setDate(scheduleDTO.getDate());
        sController.setActivities(scheduleDTO.getActivities());
        return sController;
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
