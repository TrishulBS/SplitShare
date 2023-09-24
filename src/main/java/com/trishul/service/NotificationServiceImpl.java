package com.trishul.service;


import com.trishul.model.Expense;
import com.trishul.model.User;

public class NotificationServiceImpl implements NotificationService{
    @Override
    public void notifyUser(User user, Expense expense) {
        System.out.println("Notified user with email" + user.getEmail());
    }
}
