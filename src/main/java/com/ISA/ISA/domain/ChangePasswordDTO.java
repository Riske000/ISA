package com.ISA.ISA.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDTO {
    String email;
    String oldPassword;
    String newPassword;
}