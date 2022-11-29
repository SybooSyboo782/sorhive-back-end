package com.sorhive.comprojectserver.room.query;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * <pre>
 * Class : FurnitureImageDataDao
 * Comment: 가구이미지 데이터 DAO
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface FurnitureImageDataDao extends Repository<FurnitureImageData, Long> {
    List<FurnitureImageData> findByRoomIdAndDeleteYnEquals(Long roomId, char n);
}
