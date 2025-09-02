package com.sorhive.comprojectserver.lifing.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.lifing.command.application.dto.*;
import com.sorhive.comprojectserver.lifing.command.application.exception.AlreadyHoneyException;
import com.sorhive.comprojectserver.lifing.command.application.exception.NoLifingException;
import com.sorhive.comprojectserver.lifing.command.domain.model.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.LifingComment;
import com.sorhive.comprojectserver.lifing.command.domain.model.LifingHoney;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingCommentRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingHoneyRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingRepository;
import com.sorhive.comprojectserver.lifing.exception.NoLifingCommentException;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Class : LifingService
 * Comment: 라이핑 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-12       부시연           라이핑 저장
 * 2022-11-15       부시연           허니 추가
 * 2022-11-15       부시연           허니 제거
 * 2022-11-15       부시연           라이핑 댓글 저장
 * 2022-11-16       부시연           라이핑 이미지 리스트로 변경
 * 2022-11-16       부시연           라이핑 댓글 삭제
 * 2022-11-17       부시연           라이핑 댓글 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LifingService {

    private final LifingRepository lifingRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final LifingHoneyRepository lifingHoneyRepository;
    private final LifingCommentRepository lifingCommentRepository;

    /** 라이핑 생성하기 */
    public LifingResponseDto createLifing(String accessToken, LifingCreateDto lifingCreateDto) {

        log.info("[LifingService] createLifing Start =====================");
        log.info("[LifingService] lifingCreateDto " + lifingCreateDto);

        /* 토큰에서 멤버 코드 값 꺼내오기 */
        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 라이핑 작성자 찾기 */
        Member lifingWriter = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("해당 회원이 없습니다."));

        /* 라이핑 생성하기 */
        Lifing lifing = new Lifing(
                lifingWriter,
                lifingCreateDto.getLifingNo(),
                lifingCreateDto.getLifingCategoryNo(),
                lifingCreateDto.getLifingContent()
        );

        /* 라이핑 저장하기 */
        lifingRepository.save(lifing);

        /* 라이핑 번호를 저장하기 위해 멤버 데이터 조회하기 */
        lifingWriter.changeLifingWithAI(lifingCreateDto.getLifingNo(), lifingCreateDto.getLifingCategoryNo());

        /* 멤버에 라이핑번호 정보 저장하기 */
        memberRepository.save(lifingWriter);

        /* 라이핑 생성 전송 객체 반환하기 */
        return new LifingResponseDto(
                lifing.getLifingId(),
                lifing.getLifingConetent(),
                lifing.getLifingNo(),
                lifing.getLifingCategoryNo(),
                lifing.getCreatedDate(),
                lifing.getLifingWriter().getMemberCode(),
                lifing.getLifingWriter().getMemberName(),
                lifing.getLifingWriter().getMemberId()
        );
    }


    /** 허니 추가 */
    @Transactional
    public LifingHoneyCreateResponseDto createLifingHoney(String accessToken, Long lifingId) {

        log.info("[LifingService] createLifingHoney Start =========================================================");
        log.info("[LifingService] lifingId : " + lifingId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        Member member = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("해당하는 회원이 없습니다."));
        
        Lifing lifing = lifingRepository.findByLifingIdAndDeletedEquals(lifingId, false)
                .orElseThrow(() -> new NoLifingException("해당하는 라이핑이 없습니다."));

        if (!lifingHoneyRepository.findByMemberMemberCodeAndLifingLifingIdAndDeletedEquals(memberCode, lifingId, false).isEmpty() ) {
            throw new AlreadyHoneyException("이미 허니를 했습니다.");
        }

        lifing.countingHoney(1);
        lifingRepository.save(lifing);

        LifingHoney lifingHoney = new LifingHoney(
                member,
                lifing
        );

        lifingHoneyRepository.save(lifingHoney);

        return new LifingHoneyCreateResponseDto(
                lifingHoney.getId(),
                lifingHoney.getMember().getMemberCode(),
                lifingHoney.getLifing().getLifingId(),
                lifingHoney.getCreatedDate()
        );
    }

    /** 허니 취소 */
    @Transactional
    public LifingHoneyDeleteResponseDto deleteLifingHoney(String accessToken, Long lifingId) {

        log.info("[LifingService] deleteLifingHoney Start =========================================================");
        log.info("[LifingService] lifingId : " + lifingId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));


        Lifing lifing = lifingRepository.findByLifingIdAndDeletedEquals(lifingId, false)
                .orElseThrow(() -> new NoLifingException("해당 라이핑이 존재하지 않습니다."));

        lifing.countingHoney(-1);
        lifingRepository.save(lifing);

        LifingHoney lifingHoney = lifingHoneyRepository.findByMemberMemberCodeAndLifingLifingIdAndDeletedEquals(memberCode, lifingId, false)
                .orElseThrow(() -> new AlreadyDeleteException("이미 허니가 취소 되었습니다."));

        lifingHoney.softDelete();
        lifingHoneyRepository.save(lifingHoney);

        return new LifingHoneyDeleteResponseDto(
                lifingHoney.getId(),
                lifingHoney.getLifing().getLifingId(),
                lifingHoney.getMember().getMemberCode(),
                lifingHoney.getDeletedAt()
        );

    }

    /** 라이핑 댓글 작성 */
    @Transactional
    public LifingCommentResponseDto createLifingComment(String accessToken, Long lifingId, LifingCommentCreateDto lifingCommentCreateDto) {

        log.info("[LifingService] createLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentCreateDto : " + lifingCommentCreateDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        Member lifingCommentWriter = memberRepository.findByMemberCode(memberCode)
                        .orElseThrow(() -> new NoMemberException("해당하는 회원이 없습니다. " + memberCode));

        String lifingCommentContent = lifingCommentCreateDto.getLifingCommentContent();

        Lifing lifing = lifingRepository.findById(lifingId)
                .orElseThrow(() -> new NoLifingException("해당 라이핑이 존재하지 않습니다."));

        LifingComment lifingComment = new LifingComment(
                lifingCommentWriter,
                lifingCommentContent,
                lifing
        );

        lifingCommentRepository.save(lifingComment);

        return new LifingCommentResponseDto(
                lifingComment.getLifingCommentId(),
                lifingComment.getLifingCommentContent(),
                lifingComment.getCreatedDate()
        );

    }

    /** 라이핑 댓글 삭제 */
    public Long deleteLifingComment(String accessToken, Long lifingCommentId) {

        log.info("[LifingService] deleteLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentId : " + lifingCommentId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        // 삭제 되지 않은 댓글만 조회
        LifingComment lifingComment = getCommentIfWriterMatches(lifingCommentId, memberCode);

        lifingComment.softDelete();
        lifingCommentRepository.save(lifingComment);

        return lifingComment.getLifingCommentId();
    }

    /** 라이핑 댓글 수정 */
    public LifingCommentUpdateResponseDto updateLifingComment(String accessToken, LifingCommentUpdateRequestDto lifingCommentUpdateRequestDto) {

        log.info("[LifingService] updateLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentUpdateDto : " + lifingCommentUpdateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));
        String lifingCommentContent = lifingCommentUpdateRequestDto.getLifingCommentContent();
        Long lifingCommentId = lifingCommentUpdateRequestDto.getLifingCommentId();

        LifingComment lifingComment = getCommentIfWriterMatches(lifingCommentId, memberCode);

        lifingComment.updateComment(lifingCommentContent);
        lifingCommentRepository.save(lifingComment);

        /* 라이핑 댓글 수정 응답 전송객체에 값을 답아서 반환한다. */
        return new LifingCommentUpdateResponseDto(
                lifingComment.getLifingCommentId(),
                lifingComment.getLifingCommentContent(),
                lifingComment.getLifingCommentWriter().getMemberCode(),
                lifingComment.getLifingCommentWriter().getMemberName(),
                lifingComment.getLifingCommentWriter().getMemberId(),
                lifingComment.getUpdateDate()
        );
    }

    private LifingComment getCommentIfWriterMatches(Long lifingCommentId, Long memberCode) {
        // 삭제 되지 않은 댓글만 조회
        LifingComment lifingComment = lifingCommentRepository
                .findByLifingCommentIdAndDeletedEquals(lifingCommentId, false)
                .orElseThrow(() -> new NoLifingCommentException("해당 댓글은 존재하지 않거나 이미 삭제되었습니다."));

        // 작성자 검증
        if (!lifingComment.getLifingCommentWriter().getMemberCode()
                .equals(memberCode)) {
            throw new NotSameWriterException("해당 댓글 작성자와 요청자가 다릅니다.");
        }
        return lifingComment;
    }
}
