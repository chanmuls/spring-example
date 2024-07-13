package com.chanmul.domain.user.service;

import com.chanmul.core.exception.ApiException;
import com.chanmul.core.type.ServiceErrorType;
import com.chanmul.domain.user.entity.Account;
import com.chanmul.domain.user.repository.AccountRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ApiException(ServiceErrorType.NOT_FOUND));
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Page<Account> getSearchByPageable(String name, String email, Pageable pageable) {
        Page<Account> account;

        if (Objects.nonNull(name) && Objects.nonNull(email)) {
            account = accountRepository.findByNameContainingOrUsernameContaining(name, email, pageable);
        } else if (Objects.nonNull(email)) {
            account = accountRepository.findByUsernameContaining(email, pageable);
        } else if (Objects.nonNull(name)) {
            account = accountRepository.findByNameContaining(name, pageable);
        } else {
            account = accountRepository.findAll(pageable);
        }

        return account;
    }
}
