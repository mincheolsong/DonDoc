package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByMoimMemberAndMoimMember_MoimAndStatusNotOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim, int status);

    List<Mission> findByMoimMember_MoimAndStatusNotOrderByStatusAscCreatedAtDesc(Moim moim, int status);

    List<Mission> findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim);
}
