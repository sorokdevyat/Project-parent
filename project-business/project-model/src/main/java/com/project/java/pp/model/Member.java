package com.project.java.pp.model;


import com.project.java.pp.common.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String firstname;

    @Column(name = "position")
    private String position;

    @OneToOne
    @JoinColumn(name = "account")
    private UserAccount userAccount;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}
