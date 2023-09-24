package com.trishul;

import com.trishul.exception.ContributionExceededException;
import com.trishul.exception.ExpenseDoesNotExistsException;
import com.trishul.exception.ExpensesSettledException;
import com.trishul.exception.InvalidExpenseState;
import com.trishul.model.*;
import com.trishul.repository.ExpenseRepository;
import com.trishul.service.ExpenseService;
import com.trishul.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

public class Main {

    static UserService userService;
    static ExpenseService expenseService;
    public static void main(String[] args)
            throws ContributionExceededException, ExpenseDoesNotExistsException, ExpensesSettledException, InvalidExpenseState {
        userService = new UserService();
        expenseService = new ExpenseService();
        createTestUsers();

        Expense expense = createLunchExpense();

        try {
            bifurcateExpense(expense.getExpenseId());
        } catch (ExpenseDoesNotExistsException expenseDoesNotExistsException) {
            System.out.println(expenseDoesNotExistsException.getMessage());
        }
        expense.setExpenseStatus(ExpenseStatus.PENDING);

        Set<User> users = expense.getExpenseGroup().getGroupMembers();
        for (User user : users) {
            contributeToExpense(expense.getExpenseId(), user.getEmail());
        }
        if (expenseService.isExpenseSettled(expense.getExpenseId())) {
            System.out.println("Expense Settled....");
            expenseService.setExpenseStatus(expense.getExpenseId(), ExpenseStatus.CLOSED);
        }
    }

        private static void createTestUsers() {
            User user1 = userService.userCreate("bagesh@gmail.com", "bagesh", "3486199635");
            User user2 = userService.userCreate("ajay@gmail.com", "ajay", "6112482630");
            User user3 = userService.userCreate("amit@gmail.com", "amit", "2509699232");
            User user4 = userService.userCreate("kamal@gmail.com", "kamal", "5816355154");
            User user5 = userService.userCreate("neha@gmail.com", "neha", "7737316054");
            User user6 = userService.userCreate("kajal@gmail.com", "kajal", "4813053349");
            User user7 = userService.userCreate("jyothi@gmail.com", "jyothi", "3974178644");
            User user8 = userService.userCreate("subin@gmail.com", "subin", "4768463294");
            User user9 = userService.userCreate("deepak@gmail.com", "deepak", "4829338803");
            User user10 = userService.userCreate("vishnu@gmail.com", "vishnu", "3384071602");
            User user11 = userService.userCreate("mayank@gmail.com", "mayank", "2376951206");
            User user12 = userService.userCreate("anu@gmail.com", "anu", "8478577491");
        }

    public static Expense createLunchExpense() {
        Expense expense = expenseService.createExpense(
                "Team Lunch",
                "Friday 19Th June Lunch in Briyani zone",
                2000.00,
                "vishnu@gmail.com"
                );
        return expense;
    }

    private static void bifurcateExpense(String expenseId) throws ExpenseDoesNotExistsException {
        expenseService.addUserToExpense(expenseId, "bagesh@gmail.com");
        expenseService.addUserToExpense(expenseId, "ajay@gmail.com");
        expenseService.addUserToExpense(expenseId, "amit@gmail.com");
        expenseService.addUserToExpense(expenseId, "kamal@gmail.com");

        expenseService.assignExpenseShare(expenseId, ExpenseRepository.expenseHashMap.get(expenseId).getUserId(), 400);
        expenseService.assignExpenseShare(expenseId, "bagesh@gmail.com", 400);
        expenseService.assignExpenseShare(expenseId, "ajay@gmail.com", 400);
        expenseService.assignExpenseShare(expenseId, "amit@gmail.com", 400);
        expenseService.assignExpenseShare(expenseId, "kamal@gmail.com", 400);
    }

    private static void contributeToExpense(String expenseId, String userId)
            throws ContributionExceededException, ExpenseDoesNotExistsException, ExpensesSettledException {
        Contribution contribution = new Contribution();
        Expense expense = ExpenseRepository.expenseHashMap.get(expenseId);
        ExpenseGroup expenseGroup = expense.getExpenseGroup();
        UserShare userShare = expenseGroup.getUserContributions().get(userId);
        contribution.setContributionValue(userShare.getShare());
        contribution.setContributionDate(LocalDateTime.now());
        contribution.setTransactionId("T" + Instant.EPOCH);
        contribution.setTransactionDescription("Transferred from UPI");
        userService.contributeToExpense(expenseId, userId, contribution);
    }


    }