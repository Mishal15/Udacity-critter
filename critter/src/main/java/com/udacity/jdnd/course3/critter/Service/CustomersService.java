package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.CustomersRepository;
import com.udacity.jdnd.course3.critter.Repository.PetsRepository;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
@Transactional
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PetsRepository petsRepository;

    public List<Customer> getAllCustomers(){
        return customersRepository.findAll();
    }

    public Customer getCustomerByPetId(Long petId){
        return petsRepository.getOne(petId).getCustomer();
    }

    public Customer saveCustomer(Customer customer,List<Long> petIds){
        List<Pet> pets = new ArrayList<>();
        if(petIds != null && !petIds.isEmpty()){
            pets = petIds.stream()
                    .map(petId -> petsRepository.getOne(petId))
                    .collect(Collectors.toList());
        }
        customer.setPets(pets);
        return customersRepository.save(customer);
    }
}
