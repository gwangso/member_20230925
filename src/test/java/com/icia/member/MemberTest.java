package com.icia.member;

import com.icia.member.dto.MemberDTO;
import com.icia.member.repository.MemberRepository;
import com.icia.member.service.MemberService;
// Assertions에 속한 모든 static 메서드를 사용할 때 메서드 이름만 작성해서 사용 가능하도록 만듬
// import 다음 static을 해주고, 클래스명 뒤에 .*을 해주면 된다.
//import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; // 자바에서 제공하는 테스트를 위한 라이브러리
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {
    // 테스트 클래스에서 사용할 클래스 객체 Autowired 주입

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    // 회원가입용 메서드 정의
    private MemberDTO newMember(int i){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test_email"+i);
        memberDTO.setMemberPassword("test_pass"+i);
        memberDTO.setMemberName("test_name"+i);
        memberDTO.setMemberBirth("2023-09-27");
        memberDTO.setMemberMobile("01044444444");
        return memberDTO;
    }

    @Test
    @DisplayName("회원 데이터 붓기")
    public void dataInsert() {
//        for (int i = 1; i<=20; i++){
//            MemberDTO memberDTO = newMember(i);
//            memberService.save(memberDTO);
//        }
        IntStream.rangeClosed(1,20).forEach(i ->{
            MemberDTO memberDTO = newMember(i);
            memberService.save(memberDTO);
        });
    }

    // 회원가입 기능 테스트
    /**
     *
     * 1. 새로운 회원을 하나 가입시킴
     * 2. 가입된 회원의 id값을 받아옴
     * 3. 그 id로 조회 기능을 실행
     * 4. 1번에서 만든 회원의 이메일 값과 3번에서 조회한 이메일 값이 일치하면 테스트 성공
    */
    @Test // 테스트를 하는 메서드임을 선언
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트") // 테스트의 이름(없어도 됨)
    public void memberSaveTest(){
        // 1.
        MemberDTO newMember = new MemberDTO();
        newMember.setMemberEmail("test_email");
        newMember.setMemberPassword("test_pass");
        newMember.setMemberName("test_name");
        newMember.setMemberBirth("2023-09-27");
        newMember.setMemberMobile("010444444444");
        // 2.
        Long savedId = memberService.save(newMember);
        // 3.
        MemberDTO findMember = memberService.findById(savedId);
        // 4.
        // Assertions :
        assertThat(newMember.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
//        Assertions.assertThat(newMember.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }

    /** 설명을 위한 주석 표시
     * 1. 신규회원 가입
     * 2. 로그인을 위한 DTO 객체 생성 후 로그인 수행
     * 3. 로그인 결과값이 있는지를 통해서 성공여부 판단
     * */
    @Test
    @Transactional // springframework import
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest(){
        // 1.
        MemberDTO memberDTO = newMember(999);
        memberService.save(memberDTO);
        // 2.
        MemberDTO loginMember = new MemberDTO();
        loginMember.setMemberEmail(memberDTO.getMemberEmail());
        loginMember.setMemberPassword(memberDTO.getMemberPassword());
        MemberDTO findMember = memberService.login(loginMember.getMemberEmail(), loginMember.getMemberPassword());
        // 3.
        assertThat(loginMember.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }

    /**
     * 삭제기능 테스트
     * 1. 회원가입 수행
     * 2. 회원가입 후 id 받아옴
     * 3. 삭제 기능 수행
     * 4. 삭제 후 해당 id로 조회했을 때 NoSuchElementException 발생 체크
     */
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 삭제 테스트")
    public void deleteTest(){
        // 1.
        MemberDTO memberDTO = newMember(1111);
        // 2.
        Long savedId = memberService.save(memberDTO);
        // 3.
        memberService.delete(savedId);
        // 4.
        // assertThatThrownBy() : 던져진 것이 무엇인지
        // isInstanceOf() : 어떤 종류의 Instance인지
        assertThatThrownBy(() -> memberService.findById(savedId)).isInstanceOf(NoSuchElementException.class);
    }
}
