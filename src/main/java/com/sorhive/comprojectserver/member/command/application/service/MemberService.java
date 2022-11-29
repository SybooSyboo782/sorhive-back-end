package com.sorhive.comprojectserver.member.command.application.service;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.application.dto.MemberModifyRequestDto;
import com.sorhive.comprojectserver.member.command.application.dto.MemberModifyResponseDto;
import com.sorhive.comprojectserver.member.command.application.dto.PasswordModifyRequestDto;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.FailedModifyPasswordException;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <pre>
 * Class : MemberService
 * Comment: 회원 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       부시연           최초 생성
 * 2022-11-21       부시연           회원 수정 추가
 * 2022-11-21       부시연           비밀번호 수정 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(TokenProvider tokenProvider, PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    /** 회원 수정 */
    public MemberModifyResponseDto modifyMember(String accessToken, MemberModifyRequestDto memberModifyRequestDto) {

        log.info("[MemberService] modifyMember Start ===============");
        log.info("[MemberService] memberModifyRequestDto : " + memberModifyRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        Optional<Member> memberData =  memberRepository.findByMemberCode(memberCode);

        Member member = memberData.get();

        member.updateMember(memberModifyRequestDto.getName());

        memberRepository.save(member);

        MemberModifyResponseDto memberModifyResponseDto = new MemberModifyResponseDto(
                member.getMemberCode(),
                member.getMemberId(),
                member.getMemberName(),
                member.getUploadTime()
        );

        return memberModifyResponseDto;

    }

    /** 비밀번호 수정 */
    public Object modifyPassword(String accessToken, PasswordModifyRequestDto passwordModifyRequestDto) {

        log.info("[MemberService] modifyPassword Start ===============");
        log.info("[MemberService] passwordModifyRequestDto : " + passwordModifyRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(memberRepository.findByMemberCode(memberCode) == null) {
            log.warn("[NoMemberException]");
            throw new NoMemberException("해당 회원은 존재하지 않습니다.");
        }

        Optional<Member> memberData =  memberRepository.findByMemberCode(memberCode);

        Member member = memberData.get();

        if(!passwordEncoder.matches(passwordModifyRequestDto.getPassword(), member.getPassword())) {
            throw new FailedModifyPasswordException("비밀번호가 일치하지 않습니다.");
        }

        String newPassword = passwordEncoder.encode(passwordModifyRequestDto.getNewPassword());

        member.changePassword(newPassword);
        memberRepository.save(member);

        return "비밀번호 변경에 성공했습니다.";

    }
}
