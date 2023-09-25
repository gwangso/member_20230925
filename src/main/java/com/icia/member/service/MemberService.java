package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        memberEntityList.forEach(memberEntity -> {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        });
        return memberDTOList;
    }

    public MemberDTO login(String memberEmail, String memberPassword) {
        /*
            DB에서 로그인하는 사용자의 이메일로 조회한 결과를 가져와서
            비밀번호가 일치하는지 비교한 뒤 로그인 성공 여부를 판단

            findByMemberEmail()
            select * from member_table where member_email =?

            findById()
            select * from member_table where id = ?
        */
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberEmail,memberPassword);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toMemberDTO(memberEntity);
        }else{
            return null;
        }
    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toMemberDTO(memberEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
