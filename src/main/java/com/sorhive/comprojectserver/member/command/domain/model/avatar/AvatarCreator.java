package com.sorhive.comprojectserver.member.command.domain.model.avatar;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * <pre>
 * Class : AvatarCreator
 * Comment: 아바타 생성자 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-05       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Embeddable
public class AvatarCreator {

    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "avatar_creator_code"))
    )
    private MemberCode memberCode;

    @Column(name = "avatar_creator_name")
    private String name;

    protected AvatarCreator() {}

    public AvatarCreator(MemberCode memberCode, String name) {
        this.memberCode = memberCode;
        this.name = name;
    }

    public MemberCode getMemberCode() {
        return memberCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarCreator avatarCreator = (AvatarCreator) o;
        return Objects.equals(memberCode, avatarCreator.memberCode) && Objects.equals(name, avatarCreator.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberCode, name);
    }
}
