package com.trishul.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ExpenseGroup {
    private Set<User> groupMembers;
    private Map<String, UserShare> userContributions;
    private String groupId;

    public ExpenseGroup(){
        groupId = UUID.randomUUID().toString();
        groupMembers = new HashSet<>();
        userContributions = new HashMap<>();

    }
}
