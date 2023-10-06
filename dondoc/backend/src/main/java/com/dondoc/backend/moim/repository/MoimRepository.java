package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Moim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoimRepository extends JpaRepository<Moim, Long> {
    List<Moim> findByIdentificationNumber(String identificationNumber);
    @Query("select distinct m from Moim m join fetch m.moimMemberList mm where m.isActive=1 and mm.status=1 and mm.user.id = :userId")
    List<Moim> getMoimList(@Param("userId")Long userId);

    @Query("select distinct m from Moim m join fetch m.moimMemberList mm join fetch mm.account a join fetch mm.user u where m.id = :moimId")
    List<Moim> findWithMember(@Param("moimId")Long moimId);
}
