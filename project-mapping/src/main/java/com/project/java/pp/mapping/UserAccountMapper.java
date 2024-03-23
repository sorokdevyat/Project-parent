package com.project.java.pp.mapping;

import com.project.java.pp.dto.UserAccountDto;
import com.project.java.pp.model.UserAccount;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
@Component
public class UserAccountMapper {
    public UserAccount fromDtoToAccount(UserAccountDto dto){
        if(!ObjectUtils.isEmpty(dto)){
            return  UserAccount.builder()
                    .id(dto.getId())
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .build();
        }
        return null;
    }
    public UserAccountDto fromAccoutnToDto(UserAccount account){
        if (!ObjectUtils.isEmpty(account)){
            return UserAccountDto.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .build();
        }
        return new UserAccountDto();
    }
}
