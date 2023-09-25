package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.id = memberEntity.getId();
        memberDTO.memberEmail = memberEntity.getMemberEmail();
        memberDTO.memberPassword = memberEntity.getMemberPassword();
        memberDTO.memberName = memberEntity.getMemberName();
        memberDTO.memberBirth = memberEntity.getMemberBirth();
        memberDTO.memberMobile = memberEntity.getMemberMobile();
        return memberDTO;
    }
}
