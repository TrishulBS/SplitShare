package com.trishul.service;

import com.trishul.exception.ContributionExceededException;
import com.trishul.exception.ExpenseDoesNotExistsException;
import com.trishul.exception.ExpensesSettledException;
import com.trishul.model.Contribution;
import com.trishul.model.Expense;
import com.trishul.model.User;
import com.trishul.model.UserShare;
import com.trishul.repository.ExpenseRepository;
import com.trishul.repository.UserRepository;
import lombok.*;

@Getter
@Setter
public class UserService {
    public User userCreate(String email, String name, String phoneNumber){
        User user = new User(email, name, phoneNumber);
        UserRepository.userHashMap.putIfAbsent(user.getEmail(), user);
        return user;
    }

    public void contributeToExpense(String expenseId, String email, Contribution contribution)
            throws ExpenseDoesNotExistsException, ContributionExceededException, ExpensesSettledException {
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);
        if (expense == null){
            throw new ExpenseDoesNotExistsException("create an expense first");
        }

        UserShare userShare = expense.getExpenseGroup().getUserContributions().get(email);
        double cost = userShare.getShare();

        if(contribution.getContributionValue()>cost){
            throw new ContributionExceededException("Already paid");
        }

        userShare.getContributions().add(contribution);
    }
}
