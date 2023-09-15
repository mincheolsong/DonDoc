package com.dondoc.backend.moim.repository;

import com.dondoc.backend.moim.entity.Moim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimRepository extends JpaRepository<Moim, Long> {
}
