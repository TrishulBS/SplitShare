package com.trishul.repository;

import com.trishul.model.User;

import java.util.HashMap;
import java.util.Map;

import lombok.*;

@Getter
@Setter
public class UserRepository {
    public static Map<String, User> userHashMap = new HashMap<>();
}
