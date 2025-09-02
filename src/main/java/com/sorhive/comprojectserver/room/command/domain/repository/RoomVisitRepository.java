package com.sorhive.comprojectserver.room.command.domain.repository;

import com.sorhive.comprojectserver.room.command.domain.model.RoomVisit;
import org.springframework.data.repository.Repository;

/**
 * <pre>
 * Class : RoomVisitRepository
 * Comment: 방 조회수 레포지토리
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
public interface RoomVisitRepository extends Repository<RoomVisit, Long> {

    void save(RoomVisit roomVisit);

}
