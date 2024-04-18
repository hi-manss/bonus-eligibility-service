package in.sita.bonuseligibilityservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesBonusDTO {
    private String empName;
    private BigDecimal amount;


}
