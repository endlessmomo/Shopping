package com.yuki.Shopping.entity;

import com.yuki.Shopping.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "soohyuk", roles = "USER")
    void auditingTest(){
        //given
        Member newMember = new Member();
        memberRepository.save(newMember);

        //when
        em.flush();
        em.clear();

        //then
        Member member = memberRepository.findById(newMember.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("register time : " + member.getRegisteredTime());
        System.out.println("update time : " + member.getUpdatedTime());
        System.out.println("create time : " + member.getCreatedBy());
        System.out.println("modify member : " + member.getModifiedBy());

    }
}