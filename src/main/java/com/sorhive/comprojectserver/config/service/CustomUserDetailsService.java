package com.sorhive.comprojectserver.config.service;

import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

/**
 * <pre>
 * Class : CustomUserDetailsService
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 토큰 이용하여 유저 정보 가져오기 */
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        log.info("[CustomUserDetailsService] START ===================================");
        log.info("[CustomUserDetailsService] loadUserByUsername {}", memberId);

        Member member = memberRepository.findByMemberId(memberId);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberRole().toString());

        Collection<? extends GrantedAuthority> collection = Arrays.asList(grantedAuthority);

        member.setAuthorities(collection);

        if (member == null) {
            throw new UsernameNotFoundException("해당 회원 정보는 존재하지 않습니다.");
        }
        log.info("[CustomUserDetailsService] END ===================================");

        return member;
    }
}
