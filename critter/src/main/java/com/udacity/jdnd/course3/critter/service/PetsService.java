package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomersRepository;
import com.udacity.jdnd.course3.critter.Repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class PetsService {
    @Autowired
    private PetsRepository petRepository;
    @Autowired
    private CustomersRepository customerRepository;
    public Pet savePet(Pet pet, Long ownerId) {
        Customer owner = customerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + ownerId + " not found"));

        pet.setCustomer(owner);
        Pet savedPet = petRepository.save(pet);

        // Synchronize owner's pets collection
        if (owner.getPets() == null) {
            owner.setPets(new ArrayList<>());
        }
        if (!owner.getPets().contains(savedPet)) {
            owner.getPets().add(savedPet);
            customerRepository.save(owner);
        }

        return savedPet;
    }
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet with id " + id + " not found"));
    }
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByCustomerId(ownerId);
    }
}
