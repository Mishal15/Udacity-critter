package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomersService;
import com.udacity.jdnd.course3.critter.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Handles web requests related to Users.
 * Includes requests for both customers and employees.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CustomersService customerService;
    @Autowired
    private EmployeesService employeeService;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = createCustomerEntity(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        return convertCustomerToDTO(savedCustomer);

    }
    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> response = new ArrayList<>();

        for (Customer customer : customers) {
            response.add(convertCustomerToDTO(customer));
        }

        return response;
    }
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer owner = customerService.getOwnerByPetId(petId);
        return convertCustomerToDTO(owner);
    }
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = createEmployeeEntity(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return convertEmployeeToDTO(savedEmployee);
    }
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToDTO(employee);
    }
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeesForService(
                employeeDTO.getDate(),
                employeeDTO.getSkills()
        );

        List<EmployeeDTO> response = new ArrayList<>();

        for (Employee employee : employees) {
            response.add(convertEmployeeToDTO(employee));
        }

        return response;


    }
    private Employee createEmployeeEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
    private Customer createCustomerEntity(CustomerDTO customerDTO){
        Customer cController = new Customer();
        cController.setName(customerDTO.getName());
        cController.setPhoneNumber(customerDTO.getPhoneNumber());
        cController.setNotes(customerDTO.getNotes());
        return cController;
    }
    private CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO cControllerDTO = new CustomerDTO();
        cControllerDTO.setId(customer.getId());
        cControllerDTO.setName(customer.getName());
        cControllerDTO.setPhoneNumber(customer.getPhoneNumber());
        cControllerDTO.setNotes(customer.getNotes());
        if (customer.getPets() != null) {
            cControllerDTO.setPetIds(customer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList()));
        } else {
            cControllerDTO.setPetIds(new ArrayList<>());
        }
        return cControllerDTO;
    }
    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO eControllerDTO = new EmployeeDTO();
        eControllerDTO.setId(employee.getId());
        eControllerDTO.setName(employee.getName());
        eControllerDTO.setSkills(employee.getSkills());
        eControllerDTO.setDaysAvailable(employee.getDaysAvailable());
        return eControllerDTO;
    }
}
