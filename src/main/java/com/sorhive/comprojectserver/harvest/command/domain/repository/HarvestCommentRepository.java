package com.sorhive.comprojectserver.harvest.command.domain.repository;

import com.sorhive.comprojectserver.harvest.command.domain.model.harvestcomment.HarvestComment;
import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : HarvestCommentRepository
 * Comment: 클래스에 대한 간단 설명
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
public interface HarvestCommentRepository extends Repository<HarvestComment, Long> {

    void save(HarvestComment harvestComment);
}