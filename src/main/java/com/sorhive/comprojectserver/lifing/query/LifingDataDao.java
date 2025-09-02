package com.sorhive.comprojectserver.lifing.query;

import com.sorhive.comprojectserver.lifing.query.dto.LifingData;
import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : LifingDataDao
 * Comment: 라이핑 데이터 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface LifingDataDao extends Repository<LifingData, Long> {
}
