package com.sorhive.comprojectserver.common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * Class : RangeableRepositoryImpl
 * Comment: : 스프링 데이터 JPA의 기본 구현체를 확장. RangeableRepository 인터페이스의 구현을 제공.
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-04       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public class RangeableRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements RangeableRepository<T, ID> {

    public RangeableRepositoryImpl(
            JpaEntityInformation<T, ?> entityInformation,
            EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<T> getRange(Specification<T> spec, Rangeable rangeable) {
        TypedQuery<T> query = getQuery(
                spec, getDomainClass(), rangeable.getSort());

        query.setFirstResult(rangeable.getStart());
        query.setMaxResults(rangeable.getLimit());

        return query.getResultList();
    }
}