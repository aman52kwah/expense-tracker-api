package com.example.expensetracker.repository;

import com.example.expensetracker.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long> {
    Page<ActivityLog> findAll(Pageable pageable);
}
