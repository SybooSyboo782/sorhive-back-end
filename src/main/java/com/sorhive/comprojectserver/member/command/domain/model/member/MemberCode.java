package com.sorhive.comprojectserver.member.command.domain.model.member;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * Class : MemberCode
 * Comment: 멤버 코드 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-03       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
@Access(AccessType.FIELD)
public class MemberCode implements Serializable {

    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long value;

    protected MemberCode() {
    }

    public MemberCode(Long value) { this.value = value; }

    public Long getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberCode memberNo = (MemberCode) o;
        return Objects.equals(value, memberNo.value);
    }

    @Override
    public int hashCode() { return value != null ? value.hashCode() : 0; }

    public static MemberCode of(Long value) { return new MemberCode(value); }

}