package in.sita.bonuseligibilityservice.repository;

import in.sita.bonuseligibilityservice.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor {
    @Query("SELECT e FROM EmployeeEntity e WHERE :date BETWEEN e.joiningDate AND e.exitDate")
    List<EmployeeEntity> findEligibleEmployees(Date date);
}
