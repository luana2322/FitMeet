package com.FitMeet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoacheDto {
    @Email(message = "Email không hợp lệ")
    String emailcoach;
    @NotEmpty(message = "Thiếu password")
    @Min(value = 8, message = "Password phải từ 8 kí tự trở lên")
    String passworddto;
    @Min(value = 1, message = "userName không được để trống")
    String coachName;
}
