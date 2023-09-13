package com.dondoc.backend.group.repository;

import com.dondoc.backend.group.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
