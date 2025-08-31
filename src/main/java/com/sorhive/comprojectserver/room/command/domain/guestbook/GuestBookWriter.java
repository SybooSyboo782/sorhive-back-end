package com.sorhive.comprojectserver.room.command.domain.guestbook;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * <pre>
 * Class : GuestBookWriter
 * Comment: 방명록 작성자 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Getter
@AllArgsConstructor
public class GuestBookWriter {

    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "guestbook_writer_code"))
    )
    private MemberCode memberCode;

    @Column(name = "guestbook_writer_name")
    private String name;

    @Column(name = "guestbook_writer_id")
    private String id;

    protected GuestBookWriter() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestBookWriter that = (GuestBookWriter) o;
        return Objects.equals(memberCode, that.memberCode) && Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, name, id);
    }
}