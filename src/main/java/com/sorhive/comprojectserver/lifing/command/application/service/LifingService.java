package com.sorhive.comprojectserver.lifing.command.application.service;

import com.sorhive.comprojectserver.common.exception.AlreadyDeleteException;
import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.lifing.command.application.dto.*;
import com.sorhive.comprojectserver.lifing.command.application.exception.AlreadyHoneyException;
import com.sorhive.comprojectserver.lifing.command.application.exception.NoLifingException;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.LifingId;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.LifingWriter;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.LifingWriterService;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment.LifingComment;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment.LifingCommentWriter;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingcomment.LifingCommentWriterService;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifinghoney.LifingHoney;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingCommentRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingHoneyRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingRepository;
import com.sorhive.comprojectserver.lifing.command.infra.NoLifingNoException;
import com.sorhive.comprojectserver.lifing.exception.NoLifingCommentException;
import com.sorhive.comprojectserver.lifing.query.LifingMapper;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class LifingService {

    private static final Logger log = LoggerFactory.getLogger(LifingService.class);
    private final LifingRepository lifingRepository;

    private final LifingWriterService lifingWriterService;
    private final TokenProvider tokenProvider;
    private final LifingMapper lifingMapper;
    private final MemberRepository memberRepository;
    private final LifingHoneyRepository lifingHoneyRepository;
    private final LifingCommentWriterService lifingCommentWriterService;
    private final LifingCommentRepository lifingCommentRepository;

    public LifingService(LifingRepository lifingRepository, LifingWriterService lifingWriterService, TokenProvider tokenProvider, LifingMapper lifingMapper, MemberRepository memberRepository, LifingHoneyRepository lifingHoneyRepository, LifingCommentWriterService lifingCommentWriterService, LifingCommentRepository lifingCommentRepository) {

        this.lifingRepository = lifingRepository;
        this.lifingWriterService = lifingWriterService;
        this.tokenProvider = tokenProvider;
        this.lifingMapper = lifingMapper;
        this.memberRepository = memberRepository;
        this.lifingHoneyRepository = lifingHoneyRepository;

        this.lifingCommentWriterService = lifingCommentWriterService;
        this.lifingCommentRepository = lifingCommentRepository;
    }


    /** 라이핑 생성하기 */
    public LifingResponseDto createLifing(String accessToken, LifingCreateDto lifingCreateDto) {

        log.info("[LifingService] createLifing Start =====================");
        log.info("[LifingService] lifingCreateDto " + lifingCreateDto);

        /* 토큰에서 멤버 코드 값 꺼내오기 */
        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 라이핑 내용 없을 경우 예외처리 */
        if(lifingCreateDto.getLifingContent() == null) {
            throw new NoContentException("라이핑에 내용이 없습니다.");
        }

        /* 라이핑 번호가 없을 경우 예외처리 */
        if(lifingCreateDto.getLifingNo() == null) {
            throw new NoLifingNoException("라이핑 번호가 없습니다.");
        }
        
        /* 라이핑 아이디가 없을 경우 예외처리 */
        if(lifingCreateDto.getLifingId() == null) {
            throw new NoLifingNoException("라이핑 아이디가 없습니다.");
        }

        /* 라이핑 카테고리 번호가 없을 경우 예외처리 */
        if(lifingCreateDto.getLifingCategoryNo() == null) {
            throw new NoLifingNoException("라이핑 카테고리 번호가 없습니다.");
        }

        /* 라이핑 작성자 만들기 */
        LifingWriter lifingWriter = lifingWriterService.createLifingWriter(new MemberCode(memberCode));

        Long lifingNo = lifingCreateDto.getLifingNo();
        Long lifingCategoryNo = lifingCreateDto.getLifingCategoryNo();
        String lifingConetent = lifingCreateDto.getLifingContent();

        Optional<Lifing> lifingData = lifingRepository.findByLifingIdAndDeleteYnEquals(lifingCreateDto.getLifingId(), 'N');

        Lifing lifing = lifingData.get();

        /* 라이핑 생성하기 */
        lifing.createNewAiLifing(
                lifingWriter,
                lifingNo,
                lifingCategoryNo,
                lifingConetent
        );

        /* 라이핑 저장하기 */
        lifingRepository.save(lifing);

        /* 라이핑 번호를 저장하기 위해 멤버 데이터 조회하기 */
        Optional<Member> memberData = memberRepository.findByMemberCode(memberCode);
        Member member = memberData.get();
        member.changeLifingWithAI(lifingNo, lifingCategoryNo);

        /* 멤버에 라이핑번호 정보 저장하기 */
        memberRepository.save(member);

        /* 라이핑 생성 반환 전송 객체 만들기 */
        LifingResponseDto lifingResponseDto = new LifingResponseDto(
                lifing.getLifingId(),
                lifing.getLifingConetent(),
                lifing.getLifingNo(),
                lifing.getLifingCategoryNo(),
                lifing.getCreateTime(),
                lifing.getLifingWriter()
        );

        return lifingResponseDto;
    }


    /** 허니 추가 */
    @Transactional
    public LifingHoneyCreateResponseDto createLifingHoney(String accessToken, Long lifingId) {

        log.info("[LifingService] createLifingHoney Start =========================================================");
        log.info("[LifingService] lifingId : " + lifingId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(lifingRepository.findByLifingId(lifingId) == null) {
            throw new NoLifingException("해당 라이핑이 존재하지 않습니다.");
        }

        Optional<Lifing> lifingData = lifingRepository.findByLifingIdAndDeleteYnEquals(lifingId, 'N');

        if(!lifingHoneyRepository.findByMemberCodeAndLifingIdAndDeleteYnEquals(new MemberCode(memberCode), new LifingId(lifingId), 'N').isEmpty() ) {
            throw new AlreadyHoneyException("이미 허니를 했습니다.");
        }

        Lifing lifing = lifingData.get();
        lifing.countingHoney(1);
        lifingRepository.save(lifing);

        LifingHoney lifingHoney = new LifingHoney(
                new MemberCode(memberCode),
                new LifingId(lifingId)
        );

        lifingHoneyRepository.save(lifingHoney);

        LifingHoneyCreateResponseDto lifingHoneyCreateResponseDto = new LifingHoneyCreateResponseDto(
                lifingHoney.getId(),
                lifingHoney.getMemberCode().getValue(),
                lifingHoney.getLifingId().getValue(),
                lifingHoney.getCreateTime()
        );

        return lifingHoneyCreateResponseDto;

    }

    /** 허니 취소 */
    @Transactional
    public LifingHoneyDeleteResponseDto deleteLifingHoney(String accessToken, Long lifingId) {

        log.info("[LifingService] deleteLifingHoney Start =========================================================");
        log.info("[LifingService] lifingId : " + lifingId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(lifingRepository.findByLifingId(lifingId) == null) {
            throw new NoLifingException("해당 라이핑이 존재하지 않습니다.");
        }

        Optional<Lifing> lifingData = lifingRepository.findByLifingIdAndDeleteYnEquals(lifingId, 'N');

        Lifing lifing = lifingData.get();
        lifing.countingHoney(-1);
        lifingRepository.save(lifing);

        if(lifingHoneyRepository.findByMemberCodeAndLifingIdAndDeleteYnEquals(new MemberCode(memberCode), new LifingId(lifingId), 'N').isEmpty()) {
            throw new AlreadyDeleteException("이미 허니가 취소 되었습니다.");
        }

        Optional<LifingHoney> honeyData = lifingHoneyRepository.findByMemberCodeAndLifingIdAndDeleteYnEquals(new MemberCode(memberCode), new LifingId(lifingId), 'N');

        LifingHoney lifingHoney = honeyData.get();
        lifingHoney.setDeleteYn('Y');
        lifingHoneyRepository.save(lifingHoney);

        LifingHoneyDeleteResponseDto lifingHoneyDeleteResponseDto = new LifingHoneyDeleteResponseDto(
                lifingHoney.getId(),
                lifingHoney.getLifingId().getValue(),
                lifingHoney.getMemberCode().getValue(),
                lifingHoney.getDeleteTime()
        );

        return lifingHoneyDeleteResponseDto;

    }

    /** 라이핑 댓글 작성 */
    @Transactional
    public LifingCommentResponseDto createLifingComment(String accessToken, Long lifingId, LifingCommentCreateDto lifingCommentCreateDto) {

        log.info("[LifingService] createLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentCreateDto : " + lifingCommentCreateDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        LifingCommentWriter lifingCommentWriter = lifingCommentWriterService.createLifingCommentWriter(new MemberCode(memberCode));

        String lifingCommentContent = lifingCommentCreateDto.getLifingCommentContent();

        Lifing lifing = lifingRepository.findByLifingId(lifingId);

        if(lifing == null) {
            throw new NoLifingException("해당 라이핑이 존재하지 않습니다.");
        }

        LifingComment lifingComment = new LifingComment(
                lifingCommentWriter,
                lifingCommentContent,
                lifing
        );

        lifingCommentRepository.save(lifingComment);

        LifingCommentResponseDto lifingCommentResponseDto = new LifingCommentResponseDto(
                lifingComment.getLifingCommentId(),
                lifingComment.getLifingCommentContent(),
                lifingComment.getLifingCommentCreateTime()
        );

        return lifingCommentResponseDto;

    }

    /** 라이핑 댓글 삭제 */
    public Long deleteLifingComment(String accessToken, Long lifingCommentId) {

        log.info("[LifingService] deleteLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentId : " + lifingCommentId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if(lifingCommentRepository.findByLifingCommentId(lifingCommentId) == null) {
            throw new NoLifingCommentException("해당 댓글은 존재 하지 않습니다");
        }

        if(lifingCommentRepository.findByLifingCommentIdAndLifingCommentDeleteYnEquals(lifingCommentId, 'Y') != null) {
            throw new NoLifingCommentException("해당 댓글은 이미 삭제가 되었습니다.");
        }

        Optional<LifingComment> lifingCommentData = Optional.ofNullable(lifingCommentRepository.findByLifingCommentIdAndLifingCommentDeleteYnEquals(lifingCommentId, 'N'));
        LifingComment lifingComment = lifingCommentData.get() ;

        if(lifingComment.getLifingCommentWriter().getLifingCommentWriterCode().getValue() != memberCode) {
            throw new NotSameWriterException("해당 댓글 작성자와 요청자가 다릅니다.");
        }

        lifingComment.deleteComment('Y');
        lifingCommentRepository.save(lifingComment);

        return lifingComment.getLifingCommentId();

    }

    /** 라이핑 댓글 수정 */
    public LifingCommentUpdateResponseDto updateLifingComment(String accessToken, LifingCommentUpdateRequestDto lifingCommentUpdateRequestDto) {

        log.info("[LifingService] updateLifingComment Start =========================================================");
        log.info("[LifingService] lifingCommentUpdateDto : " + lifingCommentUpdateRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        if((lifingCommentUpdateRequestDto.getLifingCommentId() == null) || (lifingCommentUpdateRequestDto.getLifingCommentContent() == null)) {
            throw new NoContentException("해당 댓글 수정에 필요한 내용이 없습니다.");
        }

        String lifingCommentContent = lifingCommentUpdateRequestDto.getLifingCommentContent();
        Long lifingCommentId = lifingCommentUpdateRequestDto.getLifingCommentId();

        if(lifingCommentRepository.findByLifingCommentId(lifingCommentId) == null) {
            throw new NoLifingCommentException("해당 댓글은 존재 하지 않습니다");
        }

        if(lifingCommentRepository.findByLifingCommentIdAndLifingCommentDeleteYnEquals(lifingCommentId, 'Y') != null) {
            throw new NoLifingCommentException("해당 댓글은 이미 삭제가 되었습니다.");
        }

        Optional<LifingComment> lifingCommentData = Optional.ofNullable(lifingCommentRepository.findByLifingCommentIdAndLifingCommentDeleteYnEquals(lifingCommentId, 'N'));
        LifingComment lifingComment = lifingCommentData.get() ;

        if(lifingComment.getLifingCommentWriter().getLifingCommentWriterCode().getValue() != memberCode) {
            throw new NotSameWriterException("해당 댓글 작성자와 요청자가 다릅니다.");
        }

        lifingComment.updateComment(lifingCommentContent);
        lifingCommentRepository.save(lifingComment);

        /* 라이핑 댓글 수정 응답 전송객체에 값을 답아서 반환한다. */
        LifingCommentUpdateResponseDto lifingCommentUpdateResponseDto = new LifingCommentUpdateResponseDto(
                lifingComment.getLifingCommentId(),
                lifingComment.getLifingCommentContent(),
                lifingComment.getLifingCommentWriter(),
                lifingComment.getLifingCommentUploadTime()
        );

        return lifingCommentUpdateResponseDto;

    }
}
