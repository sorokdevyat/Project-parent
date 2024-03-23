package com.project.java.pp.mapping;

import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.model.Member;
import com.project.java.pp.model.UserAccount;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MemberMapper {
    public Member fromDtoToMember(MemberDto memberDto){
        Member member = Member.builder().id(memberDto.getId())
                .firstname(memberDto.getFirstname())
                .position(memberDto.getPosition())
                .email(memberDto.getEmail())
                .status(memberDto.getStatus())
                .build();
        if (!ObjectUtils.isEmpty(memberDto.getAccount_id())){
            member.setUserAccount(UserAccount.builder()
                    .id(memberDto.getAccount_id()).build());
        }
        return member;
    }
    public MemberDto fromMemberToDto(Member member){
        MemberDto dto = MemberDto.builder()
                .id(member.getId())
                .firstname(member.getFirstname())
                .position(member.getPosition())
                .email(member.getEmail())
                .status(member.getStatus())
                .build();
        if (!ObjectUtils.isEmpty(member.getUserAccount())){
            dto.setAccount_id(member.getUserAccount().getId());
        }
        return dto;
    }

}
