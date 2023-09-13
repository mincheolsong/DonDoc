package com.dondoc.backend.group.repository;

import com.dondoc.backend.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
