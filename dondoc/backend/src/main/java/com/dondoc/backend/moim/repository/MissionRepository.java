package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByMoimMemberAndMoimMember_MoimAndStatusOrStatusOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim, int status1, int status2);
    List<Mission> findByMoimMember_MoimAndStatusOrStatusOrderByStatusAscCreatedAtDesc(Moim moim, int status1, int status);
    List<Mission> findByMoimMemberAndMoimMember_MoimOrderByStatusAscCreatedAtDesc(MoimMember moimMember, Moim moim);
    List<Mission> findByMoimMemberAndStatus(MoimMember moimMember, int status);
    List<Mission> findByStatusOrStatus(int status1, int status2);

    Optional<Mission> findByMoimMemberAndMoimMember_MoimAndId(MoimMember moimMember, Moim moim, Long id);
    Optional<Mission> findByMoimMember_MoimAndId(Moim moim, Long id);

}
