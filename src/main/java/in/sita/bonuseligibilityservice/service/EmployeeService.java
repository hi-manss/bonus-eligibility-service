package in.sita.bonuseligibilityservice.service;

import in.sita.bonuseligibilityservice.dto.*;
import in.sita.bonuseligibilityservice.entity.DepartmentEntity;
import in.sita.bonuseligibilityservice.entity.EmployeeEntity;
import in.sita.bonuseligibilityservice.repository.DepartmentRepository;
import in.sita.bonuseligibilityservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    //    @Autowired
//    private EmployeeEntity employeeEntity;
    @Transactional
    public void saveProcessData(EmployeeDTO employeeData) throws ParseException {

        List<EmployeeRequest> employeeDTO = employeeData.getEmployees();

        if (!CollectionUtils.isEmpty(employeeDTO)) {
            List<EmployeeEntity> addEntities = new ArrayList<>();
            for (EmployeeRequest request : employeeDTO) {
                departmentService.findOrCreateDepartment(request.getDepartment().toLowerCase());
                Map<String, Integer> getDeptMap = mapCode(request.getDepartment().toLowerCase());
                Integer dept = getDeptMap.get(request.getDepartment().toLowerCase());
                EmployeeEntity employeeEntity = new EmployeeEntity();
                SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy");
                Date exitDate = formatter.parse(request.getExitDate());
                Date joinDate = formatter.parse(request.getJoiningDate());

                employeeEntity.setEmpName(request.getEmpName());
                employeeEntity.setDepartment(dept);
                employeeEntity.setAmount(request.getAmount());
                employeeEntity.setCurrency(request.getCurrency());
                employeeEntity.setExitDate(exitDate);
                employeeEntity.setJoiningDate(joinDate);
                addEntities.add(employeeEntity);

            }
            employeeRepository.saveAll(addEntities);
        }

    }


    private Map<String, Integer> getDeptMap(List<DepartmentEntity> list) {
        return list.stream().collect(Collectors.toMap(DepartmentEntity::getDepartmentName, DepartmentEntity::getId));
    }

    private Map<String, Integer> mapCode(String dept) {
        List<String> codes = List.of(dept);
        List<DepartmentEntity> deptData = departmentRepository.findByDepartmentName(dept);

        if (CollectionUtils.isEmpty(deptData)) {
            throw new RuntimeException(" DEPT Code not available in our system");
        } else if (deptData.size() > 1) {
            throw new RuntimeException("Multiple departments found with the same name");
        }
        return getDeptMap(deptData);
    }


    public List<EmployeeBonusListDTO> getEligibleEmployees(Date date) {
        // Fetch employees eligible for bonus based on the given date from the database
        List<EmployeeEntity> eligibleEmployees = employeeRepository.findEligibleEmployees(date);

        // Organize the eligible employees by currency
        Map<String, List<EmployeeEntity>> employeesByCurrency = new HashMap<>();
        for (EmployeeEntity employee : eligibleEmployees) {
            employeesByCurrency
                    .computeIfAbsent(employee.getCurrency(), key -> new ArrayList<>())
                    .add(employee);
        }

        // Convert the organized data to DTOs
        List<EmployeeBonusListDTO> bonusDTOs = new ArrayList<>();
        for (Map.Entry<String, List<EmployeeEntity>> entry : employeesByCurrency.entrySet()) {
            EmployeeBonusListDTO bonusDTO = new EmployeeBonusListDTO();
            bonusDTO.setErrorMessage(""); // Set error message to empty string

            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setCurrency(entry.getKey());
            currencyDTO.setEmployees(mapToEmployeesBonusDTO(entry.getValue()));

            bonusDTO.setData(List.of(currencyDTO)); // Set data as a list containing a single CurrencyDTO
            bonusDTOs.add(bonusDTO);
        }

        return bonusDTOs;
    }

    private List<EmployeesBonusDTO> mapToEmployeesBonusDTO(List<EmployeeEntity> employees) {
        return employees.stream()
                .map(employee -> {
                    EmployeesBonusDTO employeeDTO = new EmployeesBonusDTO();
                    employeeDTO.setEmpName(employee.getEmpName());
                    employeeDTO.setAmount(employee.getAmount());
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }

}
