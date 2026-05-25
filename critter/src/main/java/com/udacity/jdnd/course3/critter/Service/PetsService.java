package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.CustomersRepository;
import com.udacity.jdnd.course3.critter.Repository.PetsRepository;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PetsService {
    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    public Pet getPetById(Long petId){
        return petsRepository.getOne(petId);
    }

    public List<Pet> getAllPets(){
        return petsRepository.findAll();
    }

    public List<Pet> getPetsByCustomerId(Long CustomerId){
        return petsRepository.getAllByCustomerId(CustomerId);
    }

    public Pet savePet(Pet pet,long ownerId){
        Customer customer = customersRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petsRepository.save(pet);
        customer.insertPet(pet);
        customersRepository.save(customer);
        return pet;
    }
}
