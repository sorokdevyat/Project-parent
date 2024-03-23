package com.project.java.pp.service;


import com.project.java.pp.dto.UserAccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserAccountService extends UserDetailsService {
    public UserAccountDto create(UserAccountDto dto);
    public List<UserAccountDto> getAll();

    UserAccountDto findById(Long accountId);
}
