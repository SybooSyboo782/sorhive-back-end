package com.sorhive.comprojectserver.member.query.avatar;

import com.sorhive.comprojectserver.config.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Class : AvatarQueryService
 * Comment: 아바타 조회용 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class AvatarQueryService {

    private static final Logger log = LoggerFactory.getLogger(AvatarQueryService.class);
    private final AvatarDataDao avatarDataDao;
    private final TokenProvider tokenProvider;

    public AvatarQueryService(AvatarDataDao avatarDataDao, TokenProvider tokenProvider) {
        this.avatarDataDao = avatarDataDao;
        this.tokenProvider = tokenProvider;
    }

    public AvatarData selectAvatar(String accessToken) {

        log.info("[ChattingQueryService] Start ==============================");

        Long memberCode = Long.valueOf(tokenProvider.getUserCode(accessToken));

        AvatarData avatarData = avatarDataDao.findByMemberCode(memberCode);

        return avatarData;

    }
}
