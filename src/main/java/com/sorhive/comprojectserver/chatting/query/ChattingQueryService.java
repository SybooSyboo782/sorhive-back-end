package com.sorhive.comprojectserver.chatting.query;

import com.sorhive.comprojectserver.chatting.exception.NoMongoChattingException;
import com.sorhive.comprojectserver.chatting.query.dto.ChattingDetailResponseDto;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : ChattingQueryService
 * Comment: 채팅 조회 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-18       부시연           최초 생성
 * 2022-11-18       부시연           자신의 채팅 목록 불러오기
 * 2022-11-19       부시연           자신과 채팅한 한 사람과의 내역 불러오기
 * 2022-11-21       부시연           채팅 목록 조회 기능 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class ChattingQueryService {

    private static final Logger log = LoggerFactory.getLogger(ChattingQueryService.class);
    private final TokenProvider tokenProvider;
    private final ChattingMapper chattingMapper;
    private final ChattingQueryRepository chattingQueryRepository;
    private final MongoChattingQueryRepository mongoChattingQueryRepository;

    public ChattingQueryService(TokenProvider tokenProvider, ChattingMapper chattingMapper, ChattingQueryRepository chattingQueryRepository, MongoChattingQueryRepository mongoChattingQueryRepository) {
        this.tokenProvider = tokenProvider;
        this.chattingMapper = chattingMapper;
        this.chattingQueryRepository = chattingQueryRepository;
        this.mongoChattingQueryRepository = mongoChattingQueryRepository;
    }


    /** 자신의 채팅 목록 불러오기 */
    public Object findChattingList(String accessToken) {

        log.info("[ChattingQueryService] Start ==============================");

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /** 회원이 있는 채팅 내역들을 불러온다. */
        List<ChattingData> chattingData = chattingMapper.findChattingList(memberCode);


        List<MongoChattingData> mongoChattingData = new ArrayList<>();

        /** 채팅 데이터에 있는
         * chattingData.get(i).getMemberCode1() 회원 1
         * chattingData.get(i).getMemberCode2() 회원 2
         * 조회를 요청한 회원 값은 둘 중 하나에 들어있다.
         *
         * 회원1과 회원2를 통해 조회를 하는데 가장 최근에 저장된 1건들에 대해서 반복문을 돌려서 조회를 한다.
         * */
        for (int i = 0; i < chattingData.size(); i++) {

            mongoChattingData.add(mongoChattingQueryRepository.findFirstByMemberCode1AndMemberCode2OrderByUploadTimeDesc(chattingData.get(i).getMemberCode1(), chattingData.get(i).getMemberCode2()));

        }

        List<ChattingDetailResponseDto> chattingListResponseDtos = new ArrayList<>();

        for (int i = 0; i < mongoChattingData.size(); i++) {

            ChattingDetailResponseDto chattingDetailResponseDto = new ChattingDetailResponseDto(
                    mongoChattingData.get(i).getMemberCode1(),
                    mongoChattingData.get(i).getMemberName1(),
                    mongoChattingData.get(i).getMemberRoomImage1(),
                    mongoChattingData.get(i).getMemberCode2(),
                    mongoChattingData.get(i).getMemberName2(),
                    mongoChattingData.get(i).getMemberRoomImage2(),
                    mongoChattingData.get(i).getMessages().get((mongoChattingData.get(i).getMessages().size()-1)),
                    mongoChattingData.get(i).getUploadTime()

            );

            chattingListResponseDtos.add(chattingDetailResponseDto);

        }

        return chattingListResponseDtos;
    }

    /** 자신과 채팅한 한 사람과의 내역 불러오기 */
    public MongoChattingData findChattingDetail(String accessToken, Long memberCode) {

        log.info("[ChattingQueryService] Start ==============================");

        Long memberCode2 = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* membercode1은 작은값 membercode 2는 큰값으로 맞춘다. */
        if(memberCode > memberCode2) {
            Long temp = 0L;
            temp = memberCode;
            memberCode = memberCode2;
            memberCode2 = temp;
        }

        MongoChattingData mongoChattingData = mongoChattingQueryRepository.findFirstByMemberCode1AndMemberCode2(memberCode, memberCode2);

        if (mongoChattingData == null) {
            throw new NoMongoChattingException("해당 채팅은 존재 하지 않습니다.");
        }

        return mongoChattingData;
        
    }
}
