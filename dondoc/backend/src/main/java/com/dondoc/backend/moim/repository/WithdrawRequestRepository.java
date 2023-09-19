package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {

    List<WithdrawRequest> findByMoimMember_IdAndMoimMember_Moim_Id(Long moimMemberId, Long moimId);

    List<WithdrawRequest> findByMoimMemberAndMoimMember_MoimAndStatusOrderByCreatedAtDesc(MoimMember moimMember, Moim moim, int status);
    List<WithdrawRequest> findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim);
    List<WithdrawRequest> findByMoimMember_MoimAndStatusOrderByCreatedAtDesc(Moim moim, int status);

    Optional<WithdrawRequest> findByMoimMemberAndMoimMember_MoimAndId(MoimMember moimMember, Moim moim, Long id);

}
