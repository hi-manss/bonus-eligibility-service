package in.sita.bonuseligibilityservice.controller;

import in.sita.bonuseligibilityservice.dto.EmployeeBonusListDTO;
import in.sita.bonuseligibilityservice.dto.EmployeeDTO;
import in.sita.bonuseligibilityservice.dto.RestResponse;
import in.sita.bonuseligibilityservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/tci/employee-bonus")
    ResponseEntity<RestResponse<EmployeeDTO>> processData(@RequestBody EmployeeDTO employeeDTO) {
        RestResponse<EmployeeDTO> response = new RestResponse<EmployeeDTO>();
        try {
            employeeService.saveProcessData(employeeDTO);
            response.setMessage("Data Saved Successfully");
            response.setStatus(true);
            response.setData(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ParseException e) {
            response.setStatus(false);
            response.setMessage("Date parsing error: " + e.getMessage());
            response.setData(employeeDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Error: " + e.getMessage());
            response.setData(employeeDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/tci/employee-bonus")
    public ResponseEntity<RestResponse<List<EmployeeBonusListDTO>>> getEligibleEmployees(
            @RequestParam("date") String dateStr) {

        RestResponse<List<EmployeeBonusListDTO>> response = new RestResponse<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
            Date date = dateFormat.parse(dateStr);
            List<EmployeeBonusListDTO> bonusDTOs = employeeService.getEligibleEmployees(date);
            response.setData(bonusDTOs);
            response.setStatus(true);
            response.setMessage("Data Retrieved Successfully");
            return ResponseEntity.ok(response);
        } catch (ParseException e) {
            response.setData(new ArrayList<>());
            response.setStatus(false);
            response.setMessage("Error parsing date: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
