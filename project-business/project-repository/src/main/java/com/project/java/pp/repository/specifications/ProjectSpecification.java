package com.project.java.pp.repository.specifications;


import com.project.java.pp.repository.filters.ProjectFilter;
import com.project.java.pp.model.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {
    public static Specification<Project> getSpec(ProjectFilter filter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (!ObjectUtils.isEmpty(filter.getId())){
                predicates.add(criteriaBuilder.equal(root.get("id"),filter.getId()));
            }

            if (!ObjectUtils.isEmpty(filter.getTitle())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        String.format("%%%s%%",filter.getTitle().toLowerCase())));
            }


            if (!ObjectUtils.isEmpty(filter.getStatuses())){
                predicates.add(criteriaBuilder.or(
                        filter.getStatuses()
                                .stream()
                                .map(s -> criteriaBuilder.equal(root.get("status").as(String.class), s.name()))
                                .toArray(Predicate[]::new))
                );
            }

            if (CollectionUtils.isEmpty(predicates)) {
                return query.where().getRestriction();
            }

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
