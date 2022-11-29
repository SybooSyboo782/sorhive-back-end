package com.sorhive.comprojectserver.chatting.infra;

import com.sorhive.comprojectserver.chatting.command.application.dto.ChattingCreateRequestDto;
import com.sorhive.comprojectserver.chatting.command.application.dto.ChattingCreateResponseDto;
import com.sorhive.comprojectserver.chatting.command.domain.model.Chatting;
import com.sorhive.comprojectserver.chatting.command.domain.model.MongoChatting;
import com.sorhive.comprojectserver.chatting.command.domain.repository.ChattingRepository;
import com.sorhive.comprojectserver.chatting.command.domain.repository.MongoChattingRepository;
import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <pre>
 * Class : ChattingInfraService
 * Comment: 채팅 인프라 서비스(몯고 DB와 통신)
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * 2022-11-14       부시연           채팅 생성 기능 추가
 * 2022-11-17       부시연           채팅 생성 기능 수정
 * 2022-11-21       부시연           채팅 목록 조회 기능 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class ChattingInfraService {
    private static final Logger log = LoggerFactory.getLogger(ChattingInfraService.class);
    private final MongoChattingRepository mongoChattingRepository;
    private final MemberRepository memberRepository;
    private final ChattingRepository chattingRepository;
    private final TokenProvider tokenProvider;

    public ChattingInfraService(MongoChattingRepository mongoChattingRepository, MemberRepository memberRepository, ChattingRepository chattingRepository, TokenProvider tokenProvider) {
        this.mongoChattingRepository = mongoChattingRepository;
        this.memberRepository = memberRepository;
        this.chattingRepository = chattingRepository;
        this.tokenProvider = tokenProvider;
    }

    /** 채팅 생성 */
    @Transactional
    public ChattingCreateResponseDto createChatting(String accessToken, ChattingCreateRequestDto chattingCreateRequestDto) {

        log.info("[ChattingInfraService] createChatting Start ===================");
        log.info("[ChattingInfraService] chattingCreateDto " + chattingCreateRequestDto);

        if((chattingCreateRequestDto.getMessages() ==null ) || (chattingCreateRequestDto.getMemberCode1() == null) || (chattingCreateRequestDto.getMemberCode2() == null)) {
            throw new NoContentException("채팅 정보나 방번호가 존재하질 않습니다.");
        }

        Long memberCode1 = chattingCreateRequestDto.getMemberCode1();
        Long memberCode2 = chattingCreateRequestDto.getMemberCode2();

        Optional<Member> memberData1 = memberRepository.findByMemberCode(memberCode1);
        Member member1 = memberData1.get();
        String memberName1 = member1.getMemberName();
        String memberRoomImage1 = member1.getRoomImagePath();

        Optional<Member> memberData2 = memberRepository.findByMemberCode(memberCode2);
        Member member2 = memberData2.get();
        String memberName2 = member2.getMemberName();
        String memberRoomImage2 = member1.getRoomImagePath();

        /* membercode1은 작은값 membercode 2는 큰값으로 맞춘다. */
        if(memberCode1 > memberCode2) {
            Long temp = 0L;
            temp = memberCode1;
            memberCode1 = memberCode2;
            memberCode2 = temp;
        }

        MongoChatting mongoChatting = null;

        MongoChatting oldMongoChatting = mongoChattingRepository.findFirstByMemberCode1AndMemberCode2OrderByCounterDesc(memberCode1, memberCode2);

        if(oldMongoChatting != null) {

            /* 키값 중복때문에 기존에 있던 채팅 이력은 삭제한다. */
            mongoChattingRepository.deleteById(oldMongoChatting.getId());

            /* 이전 채팅에다가 새로운 채팅내역을 추가시킨다. */
            for (int i = 0; i < chattingCreateRequestDto.getMessages().size(); i++) {
                oldMongoChatting.getMessages().add(chattingCreateRequestDto.getMessages().get(i));
            }

            mongoChatting = new MongoChatting(
                    memberCode1,
                    memberName1,
                    memberRoomImage1,
                    memberCode2,
                    memberName2,
                    memberRoomImage2,
                    oldMongoChatting.getMessages()
            );

        } else {

            /* 몽고 DB 채팅 생성 */
            mongoChatting = new MongoChatting(
                    memberCode1,
                    memberName1,
                    memberRoomImage1,
                    memberCode2,
                    memberName2,
                    memberRoomImage2,
                    chattingCreateRequestDto.getMessages()
            );
        }

        /* 몽고 DB 채팅 저장하기 */
        mongoChattingRepository.insert(mongoChatting);

        /* 채팅 생성하기 */
        Chatting chatting = new Chatting(
                memberCode1,
                memberCode2,
                mongoChatting.getId()
        );

        /* 채팅에 몽고 DB의 아이디 포함하여 저장하기 */
        chattingRepository.save(chatting);

        ChattingCreateResponseDto chattingCreateResponseDto = new ChattingCreateResponseDto(
                chatting.getChattingNo(),
                chatting.getMemberCode1(),
                chatting.getMemberCode2(),
                chatting.getChattingId(),
                chatting.getUploadTime()
        );

        log.info("[ChattingInfraService] createChatting End ====================");
        
        return chattingCreateResponseDto;

    }
}
