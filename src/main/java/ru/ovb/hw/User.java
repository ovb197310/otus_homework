package ru.ovb.hw;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@ToString
@Accessors()
public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
