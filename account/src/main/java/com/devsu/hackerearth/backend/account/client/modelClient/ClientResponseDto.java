package com.devsu.hackerearth.backend.account.client.modelClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    private Long id;
    private String dni;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private boolean active;
}
