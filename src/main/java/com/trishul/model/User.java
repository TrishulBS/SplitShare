package com.trishul.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;

    public User(@NonNull String email, String name, String phoneNumber){
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this. phoneNumber = phoneNumber;
        this.name = name;
    }

}
