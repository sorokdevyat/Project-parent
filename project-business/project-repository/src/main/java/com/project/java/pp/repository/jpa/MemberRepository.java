package com.project.java.pp.repository.jpa;

import com.project.java.pp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, JpaSpecificationExecutor<Member> {
    Optional<Member> findMemberById(Long id);
    Optional<Member> findMemberByUserAccountId(Long id);
    Optional<Member> findMemberByIdNotAndUserAccount_Id(Long memberId, Long accountId);
    Optional<Member> findMemberByUserAccount_Username(String username);


}
