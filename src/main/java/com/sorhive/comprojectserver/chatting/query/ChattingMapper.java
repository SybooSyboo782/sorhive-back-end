package com.sorhive.comprojectserver.chatting.query;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * Class : ChattingMapper
 * Comment: 채팅 매퍼
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-18       부시연           최초 생성
 * 2022-11-21       부시연           회원 번호1로 채팅 목록 조회
 * 2022-11-21       부시연           회원 번호2로 채팅 목록 조회
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Mapper
public interface ChattingMapper {
    List<ChattingData> findChattingList(Long memberCode);
    List<ChattingData> findChattingList2(Long memberCode);
}
