package springboot.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.jpatest.domain.Manager;

@Repository
public interface ManagerInterfaceRepository extends JpaRepository<Manager, Long> {

    @Query("select m from Manager m where m.mgID=:mgID and m.password=:password")
    Manager findManager(String mgID, String password);
}
