package com.bank.backend.repository;

import com.bank.backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("select distinct o from Owner o join fetch o.accountList al where o.identificationNumber = :inumber")
    public Optional<Owner> findByIdentificationNumberWithAccount(@Param("inumber") String inumber);
    Optional<Owner> findByIdentificationNumber(String identificationNumber);
}
