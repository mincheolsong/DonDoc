package com.dondoc.backend.notify.repository;

import com.dondoc.backend.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifyRepository extends JpaRepository<Notify, Long> {
    @Query("select n from Notify n where n.user.id = :userId")
    List<Notify> findByUserId(@Param("userId") Long userId);
}
