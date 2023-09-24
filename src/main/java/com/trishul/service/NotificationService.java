package com.trishul.service;

import com.trishul.model.Expense;
import com.trishul.model.User;

public interface NotificationService {
    void notifyUser(User user, Expense expense);
}
