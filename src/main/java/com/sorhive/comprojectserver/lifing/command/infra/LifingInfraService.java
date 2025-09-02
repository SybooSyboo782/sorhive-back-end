package com.sorhive.comprojectserver.lifing.command.infra;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.common.exception.NoContentException;
import com.sorhive.comprojectserver.common.exception.NotSameWriterException;
import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.file.S3LifingImageFile;
import com.sorhive.comprojectserver.lifing.command.application.dto.*;
import com.sorhive.comprojectserver.lifing.command.application.exception.NoLifingException;
import com.sorhive.comprojectserver.lifing.command.domain.model.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.LifingComment;
import com.sorhive.comprojectserver.lifing.command.domain.model.LifingImage;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingCommentRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingImageRepository;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingRepository;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * <pre>
 * Class : LifingInfraService
 * Comment: 라이핑 인프라 서비스(AI, S3 통신)
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * 2022-11-11       부시연           라이핑 이미지 생성 추가
 * 2022-11-14       부시연           라이핑 AI 분석 없는 이미지 생성 추가
 * 2022-11-16       부시연           라이핑 이미지 리스트로 변경
 * 2022-11-17       부시연           라이핑 삭제 기능 추가
 * 2022-11-19       부시연           라이핑 이미지 다시 1개로 변경
 * 2022-11-22       부시연           라이핑 AI 분석 로직 변경 대응
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LifingInfraService {
    public static final String IMAGES = "images";
    private final S3LifingImageFile s3LifingImageFile;
    private final LifingImageRepository lifingImageRepository;
    private final LifingRepository lifingRepository;
    private final LifingCommentRepository lifingCommentRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Value("${url.lifing}")
    private String lifingUrl;

    /** 라이핑 AI 이미지 저장 */
    @Transactional
    public LifingImageCreateAiResponseDto insertLifingAiImage(String accessToken, LifingAIImageRequestDto lifingAIImageRequestDto) {

        log.info("[LifingImageService] insertLifingImage Start ===============");
        log.info("[LifingImageService] lifingImageDto : " + lifingAIImageRequestDto);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));
        /* 라이핑 작성자 생성하기(미리 라이핑 작성자를 생성해서 라이핑 번호를 만든다.) */
        Member lifingWriter = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        /* 라이핑 생성 */
        Lifing lifing = new Lifing(
                lifingWriter
        );

        lifingRepository.save(lifing);

        LifingImageCreateAiResponseDto lifingImageCreateAiResponseDto = null;

        try {

            /* 라이핑 이미지가 존재 한다면 */
            if (lifingAIImageRequestDto.getLifingImage() != null) {

                /* 이미지 변수명들 지정하기 */
                byte[] lifingByteImage = lifingAIImageRequestDto.getLifingImage();
                String originalName = lifingAIImageRequestDto.getLifingImageName();
                String changeName = UUID.randomUUID() + " - lifing_" + memberCode + ".png";

                /* 라이핑 이미지를 S3에 저장하고 URL 값을 반환 받는다. */
                String lifingImagePath = s3LifingImageFile.upload(lifingByteImage, changeName, "lifing");

                /* AI 라이핑 이미지 요청 전송 객체 생성 */
                LifingImageAiRequestDto lifingImageAiRequestDto = new LifingImageAiRequestDto(
                        lifingImagePath
                );

                /* AI서버에 전송하기 위해 차셋 등 헤더 설정하기 */
                HttpHeaders headers = new HttpHeaders();
                Charset utf8 = Charset.forName("UTF-8");
                MediaType mediaType = new MediaType("application", "json", utf8);
                headers.setContentType(mediaType);
                final String url = lifingUrl;

                /* 바디에 넣기 위한 값을 MAP에 넣어주기 */
                Map<String, Object> map = new HashMap<>();
                map.put("url", lifingImageAiRequestDto.getUrl());

                /* JSON으로 파싱하기 */
                JSONObject params = new JSONObject(map);

                log.info("params :  " + params);

                /* 헤더와 바디를 하나의 JSON으로 만들기 */
                HttpEntity<JSONObject> requestEntity = new HttpEntity<>(params, headers);

                /* 스프링 3.0부터 지원하는 Spring의 HTTP 통신 템플릿 */
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<?> response = restTemplate.exchange(url,HttpMethod.PUT, requestEntity, String.class);

                log.info("[LifingInfraService]");
                log.info("[insertLifingAiImage] response.getBody() " + response.getBody());

                /* body의 값을 스트링으로 바꿔주기 */
                String jsonData = (String) response.getBody();

                /* JSON parser를 이용하여 방금 받은 응답값 파싱하기 */
                JSONParser parser = new JSONParser();
                Object object= null;
                try {
                    object = parser.parse(jsonData);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                /* Json 객체를 만들고 파싱한 데이터를 넣어주기 */
                JSONObject jsonObject = (JSONObject) object;

                /* list 값을 각각 꺼내주기 */
                // lifing 리스트
                JSONArray lifingArray = (JSONArray) jsonObject.get("lifing");
                List<Long> lifingList = new ArrayList<>();
                for (Object o : lifingArray) {
                    lifingList.add(Long.valueOf(String.valueOf(o)));
                }

                // score 리스트
                JSONArray scoreArray = (JSONArray) jsonObject.get("score");
                List<Double> scoreList = new ArrayList<>();
                for (Object o : scoreArray) {
                    scoreList.add(Double.valueOf(String.valueOf(o)));
                }

                log.info("[LifingInfraService]");
                log.info("[insertLifingAiImage] lifingList " + lifingList);
                log.info("[insertLifingAiImage] scoreList " + scoreList);

                /* 분석된 결과값 Long에 담기 */
                Long analyzedLifingNo1 = Long.valueOf(String.valueOf(lifingList.get(0)));
                Long analyzedLifingNo2 = Long.valueOf(String.valueOf(lifingList.get(1)));
                Long analyzedLifingNo3 = Long.valueOf(String.valueOf(lifingList.get(2)));
                Double analyzedLifingScore1 = Double.valueOf(String.valueOf(scoreList.get(0)));
                Double analyzedLifingScore2 = Double.valueOf(String.valueOf(scoreList.get(1)));
                Double analyzedLifingScore3 = Double.valueOf(String.valueOf(scoreList.get(2)));

                /* 라이핑 이미지에 값 넣어주기 */
                Optional<Lifing> lifingData = lifingRepository.findByLifingIdAndDeletedEquals(lifing.getLifingId(), false);
                lifing = lifingData.get();

                /* 라이핑 이미지 저장하기 */
                LifingImage lifingImage = new LifingImage(
                        lifingImagePath,
                        originalName,
                        changeName,
                        analyzedLifingNo1,
                        analyzedLifingScore1,
                        analyzedLifingNo2,
                        analyzedLifingScore2,
                        analyzedLifingNo3,
                        analyzedLifingScore3,
                        lifing
                );

                lifingImageRepository.save(lifingImage);

                lifingImageCreateAiResponseDto = new LifingImageCreateAiResponseDto(
                        lifing.getLifingId(),
                        lifingImage.getAnalyzedLifingNo1(),
                        lifing.getLifingWriter().getMemberCode(),
                        lifing.getLifingWriter().getMemberName(),
                        lifing.getLifingWriter().getMemberId(),
                        lifing.getCreatedDate(),
                        lifingImage.getLifingImagePath()
                );

                log.info("[LifingImageService] insertImage End ===============");

                return lifingImageCreateAiResponseDto;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /** AI 분석 없이 라이핑 생성하기 */
    @Transactional
    public LifingResponseDto insertLifingImage(String accessToken, LifingImageRequestDto lifingImageRequestDto) {

        log.info("[LifingInfraService] insertLifingImage Start ===================");
        log.info("[LifingInfraService] lifingImageRequestDto {}", lifingImageRequestDto);

        /* 토큰에서 멤버 코드 값 꺼내오기 */
        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 라이핑 이미지가 없을 경우 예외처리 */
        if (lifingImageRequestDto.getLifingImage() == null) {
            throw new NoContentException("라이핑에 이미지가 없습니다.");
        }

        /* 라이핑 작성자 가져오기 */
        Member lifingWriter = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new NoMemberException("Member not found with code: " + memberCode));

        /* 라이핑 생성하기 */
        Lifing lifing = new Lifing(
                lifingWriter,
                lifingImageRequestDto.getLifingContent()
        );

        /* 라이핑 저장하기 */
        lifingRepository.save(lifing);

        String lifingImagePath;
        /* 이미지 변수명들 지정하기 */
        byte[] lifingByteImage = lifingImageRequestDto.getLifingImage();
        String originalName = lifingImageRequestDto.getLifingImageName();
        String changeName = UUID.randomUUID() + " - lifing_" + memberCode + ".png";

        try {
            /* 라이핑 이미지를 S3에 저장하고 URL 값을 반환 받는다. */
            lifingImagePath = s3LifingImageFile.upload(
                    lifingByteImage,
                    changeName,
                    IMAGES);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* 라이핑 이미지 생성 */
        LifingImage lifingImage = new LifingImage(
                lifingImagePath,
                originalName,
                changeName,
                -1L,
                -1.0,
                -1L,
                -1.0,
                -1L,
                -1.0,
                lifing
        );

        lifingImageRepository.save(lifingImage);

        /* 라이핑 생성 반환 전송 객체 만들기 */
        return new LifingResponseDto(
                lifing.getLifingId(),
                lifing.getLifingConetent(),
                lifing.getLifingNo(),
                lifing.getLifingCategoryNo(),
                lifing.getCreatedDate(),
                lifing.getLifingWriter().getMemberCode(),
                lifing.getLifingWriter().getMemberName(),
                lifing.getLifingWriter().getMemberId(),
                lifingImagePath
        );
    }

    /** 라이핑 삭제 */
    @Transactional
    public Long deleteLifing(String accessToken, Long lifingId) {

        log.info("[LifingService] deleteLifing Start =========================================================");
        log.info("[LifingService] lifingId : " + lifingId);

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        // 라이핑 조회 (삭제 여부까지 한번에 확인)
        Lifing lifing = lifingRepository.findByLifingIdAndDeletedEquals(lifingId, false)
                .orElseThrow(() -> new NoLifingException("해당 라이핑은 존재하지 않거나 이미 삭제되었습니다."));

        // 작성자 검증
        if (!lifing.getLifingWriter().getMemberCode().equals(memberCode)) {
            throw new NotSameWriterException("해당 라이핑 작성자와 요청자가 다릅니다.");
        }

        lifing.softDelete();
        lifingRepository.save(lifing);

        /* 해당 라이핑에 대한 댓글이 있다면 */
        // 댓글 soft delete
        List<LifingComment> lifingComments = lifingCommentRepository.findByLifing(lifing);
        lifingComments.forEach(SoftDeleteEntity::softDelete);
        lifingCommentRepository.saveAll(lifingComments);

        /* 해당 라이핑에 대한 이미지가 있다면 */
        List<LifingImage> lifingImages = lifingImageRepository.findByLifing(lifing);
        lifingImages.forEach(SoftDeleteEntity::softDelete);
        lifingImageRepository.saveAll(lifingImages);

        return lifing.getLifingId();

    }

}
