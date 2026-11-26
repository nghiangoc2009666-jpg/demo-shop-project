package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("select u from User u where lower(u.username) like lower(concat('%', :k, '%')) "
         + "or lower(u.fullName) like lower(concat('%', :k, '%')) "
         + "or lower(u.email) like lower(concat('%', :k, '%'))")
    Page<User> search(@Param("k") String keyword, Pageable pageable);
}
