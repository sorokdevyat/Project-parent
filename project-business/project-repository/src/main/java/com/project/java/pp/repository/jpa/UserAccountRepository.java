package com.project.java.pp.repository.jpa;

import com.project.java.pp.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    Optional<UserAccount> findById(Long id);
    Optional<UserAccount> findByUsername(String username);
}
