package com.example.task.repository.custom;

import com.example.task.model.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskCustomRepository {
    private final EntityManager em;

    public TaskCustomRepository(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    public List<Task> search(Task taskSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> task = cq.from(Task.class);
        List<Predicate> predicates = new ArrayList<>();

        if (taskSearch.getId() != null)
            predicates.add(cb.equal(task.get("id"), taskSearch.getId()));

        if (taskSearch.getTitle() != null)
            predicates.add(cb.or(
                    cb.like(cb.lower(task.get("title")), "%" + taskSearch.getTitle().toLowerCase() + "%"),
                    cb.like(cb.lower(task.get("description")), "%" + taskSearch.getTitle().toLowerCase() + "%")
            ));

        if (taskSearch.getEmployeeId() != null)
            predicates.add(cb.equal(task.get("employeeId"), taskSearch.getEmployeeId()));

        if (taskSearch.getCompleted() != null)
            predicates.add(cb.equal(task.get("completed"), taskSearch.getCompleted()));

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();

    }
}
