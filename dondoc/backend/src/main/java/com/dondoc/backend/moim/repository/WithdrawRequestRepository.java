package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {

    @Query("select w from WithdrawRequest w where w.status=1 and w.moimMember.id = :moimMemberId")
    List<WithdrawRequest> findSpendingAmount(@Param("moimMemberId")Long moimMemberId);
    List<WithdrawRequest> findByMoimMember_IdAndMoimMember_Moim_Id(Long moimMemberId, Long moimId);

    List<WithdrawRequest> findByMoimMemberAndMoimMember_MoimAndStatusOrderByCreatedAtDesc(MoimMember moimMember, Moim moim, int status);
    List<WithdrawRequest> findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim);
    List<WithdrawRequest> findByMoimMember_MoimAndStatusOrderByCreatedAtDesc(Moim moim, int status);

    Optional<WithdrawRequest> findByMoimMemberAndMoimMember_MoimAndId(MoimMember moimMember, Moim moim, Long id);
    Optional<WithdrawRequest> findByMoimMember_MoimAndId(Moim moim, Long id);

}
