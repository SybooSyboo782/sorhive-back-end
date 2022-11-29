package com.sorhive.comprojectserver.member.command.infra;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import com.sorhive.comprojectserver.file.S3AvatarImageFile;
import com.sorhive.comprojectserver.member.command.application.dto.AvatarImageDto;
import com.sorhive.comprojectserver.member.command.application.dto.ResponseAvatarImageAiDto;
import com.sorhive.comprojectserver.member.command.domain.model.avatarimage.AvatarImage;
import com.sorhive.comprojectserver.member.command.domain.repository.AvatarImageRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <pre>
 * Class : AvatarImageInfraService
 * Comment: 아바타 이미지 인프라 서비스(외부 서버와 연동 : 파일서버, AI 서버)
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-11       부시연           바이트배열로 대응
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Service
public class AvatarImageInfraService {

    private static final Logger log = LoggerFactory.getLogger(AvatarImageInfraService.class);
    private final S3AvatarImageFile s3AvatarImageFile;
    private final AvatarImageRepository avatarImageRepository;
    private final TokenProvider tokenProvider;
    @Value("${url.avatar}")
    private String avatarUrl;

    public AvatarImageInfraService(S3AvatarImageFile s3AvatarImageFile, AvatarImageRepository avatarImageRepository, TokenProvider tokenProvider) {
        this.s3AvatarImageFile = s3AvatarImageFile;
        this.avatarImageRepository = avatarImageRepository;
        this.tokenProvider = tokenProvider;
    }

    /** 아바타 이미지 생성 */
    @Transactional
    public ResponseAvatarImageAiDto insertAvatarImage(String accessToken, AvatarImageDto avatarImageDto) {

        log.info("[AvatarImageInfraService] insertAvatarImage Start ===============");
        log.info("[AvatarImageInfraService] avatarImageDto : " + avatarImageDto);

        final String url = avatarUrl;
        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        /* 중복되지 않게 아바타 이미지 이름 생성 */
        String changeName = avatarImageDto.getAvatarImageName() + "avatar_" + memberCode + ".png";

        try {
            
            /* 아바타이미지가 있다면 */
            if (avatarImageDto.getAvatarImage() != null) {
                
                /* 아바타 이미지 생성 */
                AvatarImage avatarImage = new AvatarImage(
                        memberCode,
                        s3AvatarImageFile.upload(avatarImageDto.getAvatarImage(), changeName, "images"),
                        avatarImageDto.getAvatarImageName(),
                        changeName
                );
                
                /* 아바타 이미지 저장하기 */
                avatarImageRepository.save(avatarImage);

                /* 아바타 이미지 URL 뽑아오기 */
                Optional<AvatarImage> imagePath = avatarImageRepository.findById(memberCode);

                /* 헤더 설정하기 */
                HttpHeaders headers = new HttpHeaders();
                Charset utf8 = Charset.forName("UTF-8");
                MediaType mediaType = new MediaType("application", "json", utf8);
                headers.setContentType(mediaType);

                /* 바디 설정하기 */
                Map<String, Object> map = new HashMap<>();
                String path = imagePath.get().getPath();
                map.put("url", path);

                /* JSON 파싱하기 */
                JSONObject params = new JSONObject(map);

                log.info("params : " + params);

                /* 헤더와 바디 담기 */
                HttpEntity<JSONObject> requestEntity
                        = new HttpEntity<>(params, headers);

                RestTemplate restTemplate = new RestTemplate();

                /* PUT 방식으로 AI 서버에 요청하기 */
                ResponseEntity<ResponseAvatarImageAiDto> res = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ResponseAvatarImageAiDto.class);

                log.info("[AvatarImageInfraService] insertImage End ===============");

                return res.getBody();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseAvatarImageAiDto();
    }

}
