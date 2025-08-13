package com.mapcycle.mapcycle.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;
}