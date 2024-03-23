package com.project.java.pp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String username;
    private String password;
}
