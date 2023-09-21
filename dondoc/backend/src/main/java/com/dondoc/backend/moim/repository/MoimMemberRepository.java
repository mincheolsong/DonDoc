package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.MoimMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoimMemberRepository extends JpaRepository<MoimMember, Long> {

    Optional<MoimMember> findByUser_Id(Long userId);

    Optional<MoimMember> findByUser_IdAndMoim_Id(Long userId, Long moimId);
}
