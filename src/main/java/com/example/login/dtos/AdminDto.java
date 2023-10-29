package com.example.login.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminDto {

    private String username;

    private String password;

    private String email;

}
