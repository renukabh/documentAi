package com.visible.thred.documentAi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visible.thred.documentAi.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
