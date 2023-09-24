package com.trishul.model;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class UserShare {
    private String userId;
    private double share;
    private List<Contribution> contributions;

    public UserShare(String userId, double share){
        this.userId = userId;
        this.share = share;
        contributions = new ArrayList<>();
    }
}
