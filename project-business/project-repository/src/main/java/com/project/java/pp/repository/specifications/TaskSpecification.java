package com.project.java.pp.repository.specifications;


import com.project.java.pp.repository.filters.TaskFilter;
import jakarta.persistence.criteria.Predicate;
import com.project.java.pp.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpec(TaskFilter taskFilter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(taskFilter.getTitle())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        String.format("%%%s%%",taskFilter.getTitle().toLowerCase())
                ));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getAuthorId())){
                predicates.add(criteriaBuilder.equal(root.get("author").get("id"), taskFilter.getAuthorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getExecutorId())){
                predicates.add(criteriaBuilder.equal(root.get("executor").get("id"), taskFilter.getExecutorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineMin())){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), taskFilter.getDeadlineMin()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineMax())){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), taskFilter.getDeadlineMax()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getCreationDateMin())){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), taskFilter.getCreationDateMin()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getCreationDateMax())){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), taskFilter.getCreationDateMax()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getStatuses())){
                predicates.add(criteriaBuilder.or(
                        taskFilter.getStatuses()
                        .stream()
                        .map(s -> criteriaBuilder.equal(root.get("status").as(String.class),s.name()))
                        .toArray(Predicate[]::new)
                ));
            }
            query.orderBy(criteriaBuilder.desc(root.get("creationDate")));
            if (CollectionUtils.isEmpty(predicates)){
                return query.where().getRestriction();
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
