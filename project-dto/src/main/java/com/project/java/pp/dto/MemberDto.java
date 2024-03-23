package com.project.java.pp.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.java.pp.common.enums.MemberStatus;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String firstname;
    private String position;
    private Long account_id;
    private String email;
    private MemberStatus status;
}

