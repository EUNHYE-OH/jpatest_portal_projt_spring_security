package springboot.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.jpatest.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.stID=:stID and s.password=:password")
    Student findStudent(@Param("stID") String stID, @Param("password") String password);

    Student findByStID(String stID);

}
