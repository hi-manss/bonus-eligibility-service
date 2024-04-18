package in.sita.bonuseligibilityservice.service;

import in.sita.bonuseligibilityservice.entity.DepartmentEntity;
import in.sita.bonuseligibilityservice.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    public DepartmentEntity findOrCreateDepartment(String departmentName) {
        List<DepartmentEntity> departments = departmentRepository.findByDepartmentName(departmentName.toLowerCase());
        if (departments.isEmpty()) {
            DepartmentEntity newDepartment = new DepartmentEntity();
            newDepartment.setDepartmentName(departmentName.toLowerCase());
            return departmentRepository.save(newDepartment);
        } else {
            return departments.get(0);
        }
    }

}