package com.sorhive.comprojectserver.room.query;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * <pre>
 * Class : GuestBookDataDao
 * Comment: 방명록 데이터 레포지토리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * 2022-11-22       부시연           변수명 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public interface GuestBookDataDao extends Repository<GuestBookData, Long> {
    List<GuestBookData> findByRoomIdAndGuestBookDeleteYnEquals(Long roomId, char n);
}
