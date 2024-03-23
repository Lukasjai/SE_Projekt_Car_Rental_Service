package org.example.dto;

import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerDto {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String email;


}
