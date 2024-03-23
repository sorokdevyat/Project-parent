package com.project.java.pp.repository.specifications;

import com.project.java.pp.common.enums.MemberStatus;
import com.project.java.pp.repository.filters.MemberFilter;
import com.project.java.pp.model.Member;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class MemberSpecification {
    public static Specification< Member> getSpec(MemberFilter filter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.notEqual(root.get("status"), MemberStatus.DELETED));

            if (!ObjectUtils.isEmpty(filter.getFirstname())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("firstname")),
                        String.format("%%%s%%",filter.getFirstname().toLowerCase())));
            }
            if (!ObjectUtils.isEmpty(filter.getPosition())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("position")),
                        String.format("%%%s%%",filter.getPosition().toLowerCase())));
            }
            if (!ObjectUtils.isEmpty(filter.getEmail())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")),
                        String.format("%%%s%%",filter.getEmail().toLowerCase())));
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }

}
