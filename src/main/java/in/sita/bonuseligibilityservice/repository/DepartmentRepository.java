package in.sita.bonuseligibilityservice.repository;

import in.sita.bonuseligibilityservice.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor {
    List<DepartmentEntity> findByDepartmentName(String departmentName);


}
