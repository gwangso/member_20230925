package com.icia.member.entity;

import com.icia.member.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String memberEmail;

    @Column(length = 20, nullable = false)
    private String memberPassword;
    @Column(length = 20, nullable = false)
    private String memberName;
    @Column(length = 20)
    private String memberBirth;
    @Column(length = 30)
    private String memberMobile;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.memberEmail = memberDTO.getMemberEmail();
        memberEntity.memberPassword = memberDTO.getMemberPassword();
        memberEntity.memberName = memberDTO.getMemberName();
        memberEntity.memberBirth = memberDTO.getMemberBirth();
        memberEntity.memberMobile = memberDTO.getMemberMobile();
        return memberEntity;
    }

    public static MemberEntity toUpdateEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = memberDTO.getId();
        memberEntity.memberEmail = memberDTO.getMemberEmail();
        memberEntity.memberPassword = memberDTO.getMemberPassword();
        memberEntity.memberName = memberDTO.getMemberName();
        memberEntity.memberBirth = memberDTO.getMemberBirth();
        memberEntity.memberMobile = memberDTO.getMemberMobile();
        return memberEntity;
    }

}
