package com.tech.examportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.examportal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value = "delete from User  u where u.username=:username") void
	 * deleteByUsername(@Param("username") String username);
	 */

	Optional<User> findOneByUsername(String username);
	
}
