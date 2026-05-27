package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.service.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetsService petService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet controller = createPetEntity(petDTO);
        Pet savedPet = petService.savePet(controller, petDTO.getOwnerId());
        return convertPetToDTO(savedPet);
    }
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToDTO(pet);
    }
    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> response = new ArrayList<>();

        for (Pet pet : pets) {
            response.add(convertPetToDTO(pet));
        }

        return response;
    }
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<Pet> ownerPets = petService.getPetsByOwnerId(ownerId);
    List<PetDTO> response = new ArrayList<>();

        for (Pet pet : ownerPets) {
            response.add(convertPetToDTO(pet));
    }

        return response;
    }
    private Pet createPetEntity(PetDTO petDTO){
        Pet controller = new Pet();
        controller.setType(petDTO.getType());
        controller.setName(petDTO.getName());
        controller.setBirthDate(petDTO.getBirthDate());
        controller.setNotes(petDTO.getNotes());
        return controller;
    }

    private PetDTO convertPetToDTO(Pet pet) {
        PetDTO controllerDTO = new PetDTO();
        controllerDTO.setId(pet.getId());
        controllerDTO.setType(pet.getType());
        controllerDTO.setName(pet.getName());
        controllerDTO.setBirthDate(pet.getBirthDate());
        controllerDTO.setNotes(pet.getNotes());
        if (pet.getCustomer() != null) {
            controllerDTO.setOwnerId(pet.getCustomer().getId());
        }
        return controllerDTO;
    }
}