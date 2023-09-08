package com.bank.backend.repository;

import com.bank.backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByIdentificationNumber(String identificationNumber);

}
