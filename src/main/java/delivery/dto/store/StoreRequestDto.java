package delivery.dto.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreRequestDto {

    @NotBlank(message = "가게명은 필수 입력 항목입니다.")
    private String storeName;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "오픈 시간은 필수 입력 항목입니다.")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "마감 시간은 필수 입력 항목입니다.")
    private LocalTime closeTime;
}
