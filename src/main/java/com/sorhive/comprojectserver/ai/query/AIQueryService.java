package com.sorhive.comprojectserver.ai.query;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifingimage.LifingImage;
import com.sorhive.comprojectserver.lifing.command.domain.repository.LifingImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Class : AIQueryController
 * Comment: AI 조회용 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       부시연           최초 생성
 * 2022-11-27       부시연           AI 학습용 데이터 제공
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class AIQueryService {

    private static final Logger log = LoggerFactory.getLogger(AIQueryService.class);
    private final AIMapper aiMapper;
    private final LifingImageRepository lifingImageRepository;

    public AIQueryService(AIMapper aiMapper, LifingImageRepository lifingImageRepository) {
        this.aiMapper = aiMapper;
        this.lifingImageRepository = lifingImageRepository;
    }

    public Object aiLearningData() {

        log.info("[AIQueryService] aiLearningData Start ===================================");

        List<AILearningLifingResponseDto> aiLearningLifingResponseDtos = aiMapper.getAiLearningData();

        if(!lifingImageRepository.findAllByLifingLearningYn('N').isEmpty()) {

            /* 학습에 제공한 라이핑 이미지들은 이미 배웠다고 표시한다. */
            List<LifingImage> lifingImages = lifingImageRepository.findAllByLifingLearningYn('N');
            for (int i = 0; i < lifingImages.size(); i++) {
                lifingImages.get(i).learningYn('Y');
                lifingImageRepository.save(lifingImages.get(i));
            }
        }

        return aiLearningLifingResponseDtos;

    }
}
