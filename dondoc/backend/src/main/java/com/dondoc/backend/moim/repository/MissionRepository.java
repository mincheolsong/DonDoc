package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByMoimMember_MoimAndStatusNotOrderByStatusAscCreatedAtDesc(Moim moim, int status);

}
