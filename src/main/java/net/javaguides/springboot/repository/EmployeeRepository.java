package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL(Java Persistence Query Language : 엔티티 객체를 조회하는 객체지향 쿼리) with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    // define custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQLNamedParams(@Param("firstName")String firstName, @Param("lastName") String lastName);

    // define custom query using Native SQL with index params
    // nativeQuery = true 설정해야 기본 SQL 쿼리 작성 가능(e.g. select * from temp)
    @Query(value = "select * from employees e where e.first_Name = ?1 and e.last_Name = ?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    // define custom query using Native SQL with named params
    @Query(value = "select * from employees e where e.first_Name = :firstName and e.last_Name = :lastName", nativeQuery = true)
    Employee findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
