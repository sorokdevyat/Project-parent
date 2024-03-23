package com.project.java.pp.service;

import com.project.java.pp.dto.MemberDto;
import com.project.java.pp.repository.filters.MemberFilter;

import java.util.List;

public interface MemberService {

    List<MemberDto> search(MemberFilter filter);
    MemberDto create(MemberDto memberDto);
    List<MemberDto> getAll();
    MemberDto delete(Long id);
    MemberDto update(Long id,MemberDto memberDto);
    boolean isExist(Long id);
    MemberDto findByAccountUsername(String username);
    MemberDto getById(Long id);

}
