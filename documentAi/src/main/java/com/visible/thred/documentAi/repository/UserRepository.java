package com.visible.thred.documentAi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.visible.thred.documentAi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(" SELECT user FROM User user LEFT JOIN Document doc "
			+ "  ON user.id=doc.user.id "
			+ "  AND doc.uploadTime BETWEEN :from AND :to "
			+ "  where user.dateAdded BETWEEN :from AND :to  "
			+ " AND doc.id is NULL")
	public List<User> getUsersWithoutDocumentUploads(LocalDateTime from, LocalDateTime to) ;
}
