package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.MoimMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface MoimMemberRepository extends JpaRepository<MoimMember,Long> {

    @Query("select distinct mm from MoimMember mm join fetch mm.moim m where m.id = :moimId and mm.user.id = :userId")
    List<MoimMember> findMoimMember(@Param("userId")Long userId, @Param("moimId")Long moimId);
    @Query("select distinct mm from MoimMember mm join fetch mm.moim m where mm.user.id = :userId and mm.status=0")
    List<MoimMember> findInviteList(@Param("userId")Long userId);
    @Query("select distinct mm from MoimMember mm join fetch mm.moim m join fetch mm.user u where mm.id = :moimMemberId")
    List<MoimMember> findWithMoimAndUser(@Param("moimMemberId")Long moimMemberId);
    Optional<MoimMember> findTop1ByUser_Id(Long userId);
    Optional<MoimMember> findByUser_IdAndMoim_Id(Long userId, Long moimId);
}


