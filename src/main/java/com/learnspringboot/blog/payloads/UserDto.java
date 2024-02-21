package com.learnspringboot.blog.payloads;


import com.learnspringboot.blog.entities.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min= 4, message = "UserName should be min of 4 charcters")
    private String name;

    @Email(message = "Email is not valid!")
    private String email;

    @NotEmpty
    @Size(min = 3, max=10, message = "password should be of min 3 characters and maximum 10 characters")
    private String password;

    @NotEmpty
    private String about;


}
