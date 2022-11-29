package com.sorhive.comprojectserver.lifing.command.domain.repository;

import com.sorhive.comprojectserver.lifing.command.domain.model.lifing.Lifing;
import com.sorhive.comprojectserver.lifing.command.domain.model.lifingimage.LifingImage;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * Class : LifingImageRepository
 * Comment: 라이핑 이미지 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-11       부시연           최초 생성
 * 2022-11-17       부시연           라이핑을 통한 라이핑 이미지 리스트 찾기 기능 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface LifingImageRepository extends Repository<LifingImage, Long> {
    void save(LifingImage lifingImage);
    Optional<LifingImage> findById(Long memberCode);
    List<LifingImage> findByLifing(Lifing lifing);

    List<LifingImage> findAllByLifingLearningYn(char N);
}
