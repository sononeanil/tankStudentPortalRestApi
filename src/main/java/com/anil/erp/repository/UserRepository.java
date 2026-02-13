package com.anil.erp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.UserEntity;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> getByEmail(String userId);
	
	@Modifying
	@Query("Update UserEntity u SET u.role = :rolesList where u.id = :id")
	int updateRoleById(@Param("id") Long id, @Param("rolesList") String rolesList	);

}