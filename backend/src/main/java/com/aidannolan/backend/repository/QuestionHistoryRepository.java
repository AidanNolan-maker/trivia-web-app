package com.aidannolan.backend.repository;

import com.aidannolan.backend.entity.QuestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory,Long> {
}
