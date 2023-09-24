package com.trishul.service;

import com.trishul.exception.ExpenseDoesNotExistsException;
import com.trishul.model.*;
import com.trishul.repository.ExpenseRepository;
import com.trishul.repository.UserRepository;

import java.util.Map;
import java.util.UUID;

public class ExpenseService {

    NotificationService notificationService = new NotificationServiceImpl();
    public Expense createExpense(String expName, String desc,double amt, String userid){
        Expense expense = Expense.builder()
                .expenseId(UUID.randomUUID().toString())
                .expenseName(expName)
                .userId(userid)
                .expenseAmount(amt)
                .expenseDescription(desc)
                .expenseGroup(new ExpenseGroup())
                .build();

        ExpenseRepository.expenseHashMap.putIfAbsent(expense.getExpenseId(), expense);
        return expense;
    }

    public void addUserToExpense(String email, String expenseId)
            throws ExpenseDoesNotExistsException{
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);
        if(expense==null){
            throw new ExpenseDoesNotExistsException("Expense does not exist");
        }

        expense.getExpenseGroup().getGroupMembers().add(UserRepository.userHashMap.get(email));

        if(notificationService!=null){
            notificationService.notifyUser(UserRepository.userHashMap.get(email), ExpenseRepository.expenseHashMap.get(expenseId));

        }
    }

    public void assignExpenseShare(String email, String expenseId, double share){
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);

        ExpenseGroup expenseGroup = expense.getExpenseGroup();
        expenseGroup.getUserContributions().putIfAbsent(email, new UserShare(email, share));
    }

    public void setExpenseStatus(String expenseId, ExpenseStatus expenseStatus) {
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);
        expense.setExpenseStatus(expenseStatus);
    }

    public boolean isExpenseSettled(String expenseId){
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);

        double expenseAmount = expense.getExpenseAmount();

        Map<String, UserShare> userShares = expense.getExpenseGroup().getUserContributions();

        for (Map.Entry<String, UserShare> entry : userShares.entrySet()) {
            UserShare userShare = entry.getValue();
            for (Contribution contribution : userShare.getContributions()) {
                expenseAmount -= contribution.getContributionValue();
            }
        }
        if (expenseAmount <= 1) {
            return true;
        }
        return false;
    }

}
