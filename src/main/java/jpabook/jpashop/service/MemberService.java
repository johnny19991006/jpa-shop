package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import java.lang.reflect.Member;

@Service
@Transactional(readOnly = true)//readOnly=true를 넣으면 데이터 변경이 안됨.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional//데이터 변경(기본 값은 readOnly=false임)이나 lazy로딩을 할려면 transctional안에서 실행 되어야 한다.(javax보다 Spring에서 쓰는거 쓰자.)
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
      return memberRepository.findAll();
    }
    //한건 조회
    public Member findOne(Long memberId){
      return memberRepository.findOne(memberId);
    }
}
