package com.sorhive.comprojectserver.member.query.recommend;

import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * Class : RecommendMapper
 * Comment: 친구추천 매퍼
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Mapper
public interface RecommendMapper {
    String findRecommendId();
}
