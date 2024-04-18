package in.sita.bonuseligibilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBonusListDTO {
    private String errorMessage;
    private List<CurrencyDTO> data;

}
