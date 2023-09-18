package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
