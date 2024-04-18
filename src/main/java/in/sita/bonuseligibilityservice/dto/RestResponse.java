package in.sita.bonuseligibilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RestResponse<T> {

    private String message;

    private boolean status;

    private T data;

}
