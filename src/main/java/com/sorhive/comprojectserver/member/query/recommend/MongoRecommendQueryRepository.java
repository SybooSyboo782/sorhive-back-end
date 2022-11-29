package com.sorhive.comprojectserver.member.query.recommend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <pre>
 * Class : MongoRecommendQueryRepository
 * Comment: 회원 추천 조회용 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface MongoRecommendQueryRepository extends MongoRepository<MongoRecommendData, String> {
    MongoRecommendData findOneByMemberCodesOrderByUploadTimeDesc(Long memberCode);
}
