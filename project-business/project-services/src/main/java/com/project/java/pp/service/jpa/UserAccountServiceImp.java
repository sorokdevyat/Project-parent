package com.project.java.pp.service.jpa;


import com.project.java.pp.dto.UserAccountDto;
import com.project.java.pp.mapping.UserAccountMapper;
import com.project.java.pp.model.UserAccount;
import com.project.java.pp.repository.jpa.UserAccountRepository;
import com.project.java.pp.service.UserAccountService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountServiceImp implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserAccountDto> getAll(){
        List<UserAccount> all = userAccountRepository.findAll();
        return all.stream().map(u -> mapper.fromAccoutnToDto(u)).toList();
    }

    @Override
    public UserAccountDto findById(Long accountId) {
        Optional<UserAccount> byId = userAccountRepository.findById(accountId);
        if (byId.isPresent()){
            return mapper.fromAccoutnToDto(byId.get());
        } else {
            throw new NoSuchElementException
                    (String.format("Us.acc with id = %d not found",accountId));
        }
    }

    @Transactional
    public UserAccountDto create(UserAccountDto account){
//        account.setPassword(passwordEncoder.encode(account.getPassword()));
        UserAccount userAccount = mapper.fromDtoToAccount(account);
        UserAccount save = userAccountRepository.save(userAccount);
        log.info("Create new account with username = {}",save.getUsername());
        return mapper.fromAccoutnToDto(save);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(username, userAccount.getPassword(), Collections.emptyList());
    }
    @PostConstruct
    public void initAdmin(){
        UserAccount admin = userAccountRepository.findByUsername("root")
                .orElseGet(() -> userAccountRepository.save(UserAccount.builder()
                        .username("root")
                        .password(passwordEncoder.encode("root"))
                        .build()));
    }
}
