package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.MoimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface MoimMemberRepository extends JpaRepository<MoimMember,Long> {

    @Query("select mm from MoimMember mm join Moim m on mm.moim.id=m.id where m.id = :moimId and mm.user.id = :userId")
    List<MoimMember> findMoimMember(@Param("userId")String userId, @Param("moimId")Long moimId);
    
    Optional<MoimMember> findByUser_Id(Long userId);

    Optional<MoimMember> findByUser_IdAndMoim_Id(Long userId, Long moimId);
}


