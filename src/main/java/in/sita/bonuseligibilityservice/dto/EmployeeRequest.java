package in.sita.bonuseligibilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String empName;
    private String department;
    private BigDecimal amount;
    private String currency;
    private String joiningDate;
    private String exitDate;
}
