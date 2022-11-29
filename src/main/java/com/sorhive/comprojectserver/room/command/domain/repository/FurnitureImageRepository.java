package com.sorhive.comprojectserver.room.command.domain.repository;

import com.sorhive.comprojectserver.room.command.domain.furnitureimage.FurnitureImage;
import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : FurnitureImageRepository
 * Comment: 가구 이미지 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * 2022-11-20       부시연           가구 이미지 번호로 가구이미지 찾기
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface FurnitureImageRepository extends Repository<FurnitureImage, Long> {

    void save(FurnitureImage furnitureImage);

    FurnitureImage findById(Long furnitureImageId);
    
}
