package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 인터페이스는 실행문이 없는 추상메서드(abstract method)만 정의할 수 있다.
    // select * from member_table where member_email = ?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, @Param("password") String memberPassword);
}
