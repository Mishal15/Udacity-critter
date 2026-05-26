package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetsRepository extends JpaRepository<Pet,Long> {
<<<<<<< HEAD
    List<Pet> findByCustomerId(Long ownerId);
=======
    List<Pet> getAllByCustomerId(Long customerId);
>>>>>>> 80e702d7f26bd541a8be3beb2b9131d29ab23c86
}
