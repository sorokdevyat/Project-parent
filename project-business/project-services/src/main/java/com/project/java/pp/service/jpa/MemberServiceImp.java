package com.project.java.pp.service.jpa;


import com.project.java.pp.common.enums.MemberStatus;
import com.project.java.pp.common.exceptions.AlreadyAttachedException;
import com.project.java.pp.common.exceptions.NotUniqueAccountException;
import com.project.java.pp.dto.MemberDto;

import com.project.java.pp.mapping.MemberMapper;
import com.project.java.pp.model.Member;
import com.project.java.pp.repository.jpa.MemberRepository;
import com.project.java.pp.repository.filters.MemberFilter;
import com.project.java.pp.repository.specifications.MemberSpecification;
import com.project.java.pp.service.MemberService;
import com.project.java.pp.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImp implements MemberService {
    private final MemberRepository memberRepository;
    private final UserAccountService userAccountService;
    private final MemberMapper memberMapper;

    public List<MemberDto> search(MemberFilter filter){
        List<Member> all = memberRepository.findAll(MemberSpecification.getSpec(filter));
        return all.stream().map(m->memberMapper.fromMemberToDto(m)).toList();
    }
    public MemberDto getById(Long id){
        Member member = memberRepository.findById(id).orElse(new Member());
        return memberMapper.fromMemberToDto(member);
    }

    @Transactional
    public MemberDto create(MemberDto dto) {

        // Check that such an account has already been created,
        // because account is created before Member

        if (!ObjectUtils.isEmpty(dto.getAccount_id())) {
            if (ObjectUtils.isEmpty(userAccountService.findById(dto.getAccount_id()).getId())) {
                throw new NoSuchElementException
                        (String.format("Accout with id %d, does not exist", dto.getAccount_id()));
            }
            // Check that account that we want to link is unique
            Optional<Member> memberWithThatAccountId =
                    memberRepository.findMemberByUserAccountId(dto.getAccount_id());
            if (memberWithThatAccountId.isPresent()) {
                throw new AlreadyAttachedException(
                        String.format("Account with id = %d is already attached with Member with id = %d"
                                , dto.getAccount_id()
                                , memberWithThatAccountId.get().getId())
                );
            }
        }
        Member member = memberMapper.fromDtoToMember(dto);
        member.setStatus(MemberStatus.ACTIVE);
        Member createdMember = memberRepository.save(member);
        log.info("Create member with id = {}",createdMember.getId());
        return memberMapper.fromMemberToDto(createdMember);
    }

    @Override
    public List<MemberDto> getAll() {
        List<Member> all = memberRepository.findAll();
        log.info("Showed all members");
        return all.stream().map(m -> memberMapper.fromMemberToDto(m)).toList();
    }

    @Override
    public MemberDto delete(Long id) {
        Optional<Member> memberById = memberRepository.findMemberById(id);
        if (memberById.isEmpty()){
            return new MemberDto();
        }
        Member member = memberById.get();
        member.setStatus(MemberStatus.DELETED);
        Member deleted = memberRepository.save(member);
        log.info("Delete member with id = {}",deleted.getId());
        return memberMapper.fromMemberToDto(deleted);
    }

    @Transactional
    public MemberDto update(Long id, MemberDto dto) {
        if(!isMemberExist(id)) {
            throw new NoSuchElementException(String.format("Member with id = %d not exists", id));
        }
        if (!ObjectUtils.isEmpty(dto.getAccount_id()) && ObjectUtils.isEmpty(userAccountService.findById(dto.getAccount_id()).getId())) {
            throw new NoSuchElementException(String.format("Account with id = %d not exist", dto.getAccount_id()));
        }
        Optional<Member> memberWithSuchAccount = memberRepository.findMemberByIdNotAndUserAccount_Id(id, dto.getAccount_id());
        if (memberWithSuchAccount.isPresent()) {
            throw new NotUniqueAccountException(
                    String.format("Account with id = %d already attached with Member with id = %d",
                            dto.getAccount_id(),
                            memberWithSuchAccount.get().getId()
                    )
            );
        }
        Member member = memberMapper.fromDtoToMember(dto);
        member.setId(id);
        Member updatedMember = memberRepository.save(member);
        log.info("Update member with id = {}",updatedMember.getId());
        return memberMapper.fromMemberToDto(updatedMember);
    }

    @Override
    public boolean isExist(Long id) {
        return memberRepository.findMemberById(id).isPresent();
    }

    @Override
    public MemberDto findByAccountUsername(String username) {
        Member member = memberRepository.findMemberByUserAccount_Username(username).orElse(new Member());
        return memberMapper.fromMemberToDto(member);
    }

    public boolean isMemberExist(Long id) {
        return memberRepository.findMemberById(id).isPresent();
    }
}