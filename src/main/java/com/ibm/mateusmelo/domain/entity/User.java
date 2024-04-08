package com.ibm.mateusmelo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "{field.username.required}")
    private String username;

    @Column
    @NotEmpty(message = "{field.password.required}")
    private String password;

    @Column
    private boolean admin;
}
