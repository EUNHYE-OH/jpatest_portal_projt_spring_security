package springboot.jpatest.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springboot.jpatest.domain.Manager;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {

    private final EntityManager em;

    public void save(Manager manager){
        em.persist(manager);
    }

    public Manager findByMgID(String mgID){
        return em.find(Manager.class, mgID);
    }
}
