package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {

    List<WithdrawRequest> findByMoimMember_IdAndMoimMember_Moim_Id(Long moimMemberId, Long moimId);

    List<WithdrawRequest> findByMoimMember_MoimAndStatusOrderByCreatedAtDesc(Moim moim, int status);


    
}
