package com.dondoc.backend.notify.repository;

import com.dondoc.backend.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
