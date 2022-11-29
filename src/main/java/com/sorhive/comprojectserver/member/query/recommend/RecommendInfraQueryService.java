package com.sorhive.comprojectserver.member.query.recommend;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import com.sorhive.comprojectserver.member.query.avatar.AvatarData;
import com.sorhive.comprojectserver.member.query.avatar.AvatarDataDao;
import com.sorhive.comprojectserver.member.query.member.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Class : RecommendInfraQueryService
 * Comment: 회원 추천 조회용 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       부시연           최초 생성
 * 2022-11-26       부시연           친구 추천 배열 불러오기 기능 추가
 * 2022-11-27       부시연           전체 친구 목록 리스트 조회 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class RecommendInfraQueryService {

    private static final Logger log = LoggerFactory.getLogger(RecommendInfraQueryService.class);
    private final TokenProvider tokenProvider;
    private final MongoRecommendQueryRepository mongoRecommendQueryRepository;
    private final RecommendMapper recommendMapper;
    private final MemberMapper memberMapper;
    private final MemberDataDao memberDataDao;
    private final AvatarDataDao avatarDataDao;

    public RecommendInfraQueryService(TokenProvider tokenProvider, MongoRecommendQueryRepository mongoRecommendQueryRepository, RecommendMapper recommendMapper, MemberMapper memberMapper, MemberDataDao memberDataDao, AvatarDataDao avatarDataDao) {
        this.tokenProvider = tokenProvider;
        this.mongoRecommendQueryRepository = mongoRecommendQueryRepository;
        this.recommendMapper = recommendMapper;
        this.memberMapper = memberMapper;
        this.memberDataDao = memberDataDao;
        this.avatarDataDao = avatarDataDao;
    }

    /** 전체 회원 조회 */
    public FindAllMemberResponseDto findAllByMemberCode(String accessToken, Long offset) {

        log.info("[RecommendInfraQueryService] findAllByMemberCode Start ================");
        log.info("[RecommendInfraQueryService] offset : " + offset);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 자신 조회하기 */
        MemberSummary memberSummary = memberMapper.findOneByMemberCode(memberCode);

        List<MemberSummary> memberSummaryList = new ArrayList<>();
        List<MemberSummary> tempLifingMemberSummaryList = new ArrayList<>();
        List<MemberSummary> tempLifingMemberSummaryList2 = new ArrayList<>();

        /* 응답 전송 객체 만들기 */
        FindAllMemberResponseDto findAllMemberResponseDto = new FindAllMemberResponseDto();

        if (memberSummary == null) {

            log.warn("[RecommendInfraQueryService] NoMemberException ================");
            throw new NoMemberException("해당 회원은 존재 하지 않습니다.");
        }

        /* 응답 전송 객체에 담기 위해 리스트에 자신의 정보 넣기 */
        memberSummaryList.add(memberSummary);

        /* 회원 번호의 손님들의 추천리스트 가져오기 */
        List<String> recommendList = findGuestList(memberCode);

        /* 자신이 팔로우 하고 있는 인원들의 목록 */
        List<MemberSummary> tempMemberSummaryList = memberMapper.findAllFollowerByMemberCode(memberCode, offset * 30);

        /* 임시 리스트 2개 선언 및 초기화 */
        List<Integer> tempList = new ArrayList<>();
        List<Integer> tempList2 = new ArrayList<>();

        /* 팔로우 하고 있는 목록이 있다면 */
        if(!tempMemberSummaryList.isEmpty()) {

            for (int i = 0; i < tempMemberSummaryList.size(); i++) {

                /* 만약 추천 리스트에 임시 회원 요약 리스트의 회원 코드가 존재한다면 */
                if (recommendList.contains(tempMemberSummaryList.get(i).getMemberCode().toString())) {

                    /* 임시 리스트에 해당 추천리스트 인덱스 번호를 추가한다. */
                    tempList.add(recommendList.indexOf(tempMemberSummaryList.get(i).getMemberCode().toString()));
                }

            }

            /* 추천 순위에 맞게 정렬하기 */
            Collections.sort(tempList);

            /* 정렬된 값을 통해 추천 리스트의 순서에 맞게 값 넣어주기 */
            for (int i = 0; i < tempList.size(); i++) {
                tempList2.add(Integer.valueOf(recommendList.get(tempList.get(i))));
            }

            /* 추천 정렬 시스템이 동작하기 전에 팔로우 추가 된 회원들 추가해주기 */
            for (int i = 0; i < tempMemberSummaryList.size(); i++) {
                if (!recommendList.contains(tempMemberSummaryList.get(i).getMemberCode().toString())) {
                    tempList2.add(tempMemberSummaryList.get(i).getMemberCode().intValue());
                }
            }

            /* 임시 리스트2의 사이즈만큼 반복 */
            for (int i = 0; i < tempList2.size(); i++) {

                /* 임시 회원요약리스트의 사이즈만큼 반복 */
                for (int j = 0; j < tempMemberSummaryList.size(); j++) {

                    /* 만약 임시 리스트2의 값과 임시회원요약리스트의 회원코드가 같다면 */
                    if(tempList2.get(i) == tempMemberSummaryList.get(j).getMemberCode().intValue()) {

                        /* 라이핑 YN의 여부에 따라서 따로 임시 저장하기 */
                        if(tempMemberSummaryList.get(j).getLifingYn().equals("'Y'")) {
                            tempLifingMemberSummaryList.add(tempMemberSummaryList.get(j));
                        } else {
                            tempLifingMemberSummaryList2.add(tempMemberSummaryList.get(j));
                        }
                    }
                }
            }
        }
        
        /* 회원요약리스트에 임시회원라이핑여부리스트 추가해주기 */
        for (int i = 0; i < tempLifingMemberSummaryList.size(); i++) {
            memberSummaryList.add(tempLifingMemberSummaryList.get(i));
        }

        /* 회원요약리스트에 임시회원라이핑여부리스트 추가해주기 */
        for (int i = 0; i < tempLifingMemberSummaryList2.size(); i++) {
            memberSummaryList.add(tempLifingMemberSummaryList2.get(i));
        }

        /* 전송객체에 값을 담아서 반환하기 */
        findAllMemberResponseDto.setMemberSummary(memberSummaryList);

        log.info("[RecommendInfraQueryService] findAllByMemberCode End ================");

        return findAllMemberResponseDto;
    }

    /** 친구추천 배열 불러오기 */
    public List<String> findGuestList(Long memberCode) {

        log.info("[MongoRecommendQueryService] Start ==============================");

        String recommendId = recommendMapper.findRecommendId();

        Optional<MongoRecommendData> mongoRecommendData = mongoRecommendQueryRepository.findById(recommendId);

        MongoRecommendData mongoRecommend = mongoRecommendData.get();

        List<String> guestCodes = new ArrayList<>();

        for (int i = 0; i < mongoRecommend.getMemberCodes().size(); i++) {

            if(mongoRecommend.getMemberCodes().get(i).equals(memberCode.toString())) {

                guestCodes = mongoRecommend.getGuestCodes().get(i);

                log.info("[MongoRecommendQueryService] End ==============================");

                return guestCodes;
            }
        }

        log.info("[MongoRecommendQueryService] End ==============================");

        return guestCodes;

    }


    /** 룸인 회원 조회 */
    public FindRoomInMemberResponseDto findRoomInMember(String accessToken, Long roomId) {

        log.info("[RecommendInfraQueryService] findRoomInMember Start ================");

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        FindRoomInMemberResponseDto findRoomInMemberResponseDto = new FindRoomInMemberResponseDto();

        List<MemberData> memberDataList = new ArrayList<>();
        List<MemberData> tempMemberDataList = new ArrayList<>();
        List<MemberData> resultMemberDataList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        List<Integer> tempList2 = new ArrayList<>();

        /* 회원번호로 조회하기 */
        MemberData followerMemberData = memberDataDao.findByMemberCodeAndMemberRole(roomId, MemberRole.valueOf("ROLE_MEMBER"));

        if(followerMemberData == null) {
            throw new NoMemberException("회원 정보가 없습니다");
        }


        /* 회원 번호의 손님들의 추천리스트 가져오기 */
        List<String> recommendList = findGuestList(roomId);

        tempMemberDataList.add(followerMemberData);

        /* 팔로워의 팔로잉 조회 */
        List<MemberData> followerMemberDataList = memberMapper.findAllFollowerByFollowerCode(roomId);

        int followerCount = 0;
        followerCount = followerMemberDataList.size();

        /* 팔로워의 팔로잉 목록이 있다면 하나씩 꺼내서 tempMemberDataList에 넣어준다. */
        if(followerMemberDataList != null ){

            for (int i = 0; i < followerCount; i++) {

                /* 만약 추천 리스트에 임시 회원 요약 리스트의 회원 코드가 존재한다면 */
                if (recommendList.contains(followerMemberDataList.get(i).getMemberCode().toString())) {

                    /* 임시 리스트에 해당 추천리스트 인덱스 번호를 추가한다. */
                    tempList.add(recommendList.indexOf(followerMemberDataList.get(i).getMemberCode().toString()));

                }

                /* 추천 순위에 맞게 정렬하기 */
                Collections.sort(tempList);

                /* 정렬된 값을 통해 추천 리스트의 순서에 맞게 값 넣어주기 */
                for (int j = 0; j < tempList.size(); j++) {
                    tempList2.add(Integer.valueOf(recommendList.get(tempList.get(j))));
                }

                /* 임시 리스트2의 사이즈만큼 반복 */
                for (int j = 0;j < tempList2.size(); j++) {

                    /* 임시 회원요약리스트의 사이즈만큼 반복 */
                    for (int k = 0; k < followerMemberDataList.size(); k++) {

                        /* 만약 임시 리스트2의 값과 임시회원요약리스트의 회원코드가 같다면 */
                        if(tempList2.get(j) == followerMemberDataList.get(k).getMemberCode().intValue()) {

                            /* 회원요약리스트에 임시회원요약리스트 추가해주기 */
                            tempMemberDataList.add(followerMemberDataList.get(k));
                        }
                    }
                }
            }
        }

        int count = 0;

        int maxMemberCode = memberMapper.findMaxMemberCode();

        /* 무한루프 */
        while(true) {

            /* 멤버코드의 최대값만큼 회원이 존재한다고 가정한다. */
            int memberSize[] = new int[maxMemberCode];

            /* 랜덤 난수값을 만든다. */
            Random randomNo = new Random();

            for (int i = 0; i < maxMemberCode; i++) {

                /* 랜덤 정수값을 만들어서 부여한다. */
                memberSize[i] = randomNo.nextInt(maxMemberCode) + 1;

                for (int j = 0; j < i; j++) {

                    /* 만약 중복이 발생한다면 다시 조정을 한다. */
                    if (memberSize[i] == memberSize[j]) {
                        i--;
                    }
                }
            }

            /* 멤버코드의 최대값만큼 반복을 시킨다. */
            for (int k = 0; k < maxMemberCode; k++) {

                /* 해당 멤버코드를 제외한 회원들을 조회한다. */
                MemberData tempMemberData = memberDataDao.findByMemberCodeAndMemberCodeIsNotAndMemberRole((long) memberSize[k], memberCode, MemberRole.valueOf("ROLE_MEMBER"));

                if (tempMemberData != null) {

                    memberDataList.add(tempMemberData);
                    count++;

                    /* 6명이 되거나 최대회원수가 6명도 채 안되면 그 회원 수만큼만 조회하고 무한루프를 탈출한다. */
                    if(count == (6) || count == maxMemberCode)  {
                        break;
                    }
                }

            }


            for (int i = 0; i < memberDataList.size(); i++) {
                tempMemberDataList.add(memberDataList.get(i));
            }

            break;

        }

        /* 자신의 아바타 정보 불러오기 */
        if(avatarDataDao.findByMemberCode(memberCode) != null) {

            AvatarData ownAvatarData = avatarDataDao.findByMemberCode(memberCode);
            findRoomInMemberResponseDto.setOwnAvatarData(ownAvatarData);

        }

        resultMemberDataList = tempMemberDataList.stream().distinct().collect(Collectors.toList());

        /* 조회했던 회원 정보를 전송객체에 담고 반환한다. */
        findRoomInMemberResponseDto.setMemberDtoList(resultMemberDataList.subList(0,7));

        return findRoomInMemberResponseDto;

    }
}
