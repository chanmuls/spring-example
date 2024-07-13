package com.chanmul.domain.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chanmul.domain.user.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Page<Account> findByNameContainingOrUsernameContaining(String name, String username, Pageable pageable);

    Page<Account> findByUsernameContaining(String username, Pageable pageable);

    Page<Account> findByNameContaining(String name, Pageable pageable);
}
