package com.trishul.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
    private String expenseId;
    private String expenseName;
    private String userId;
    private double expenseAmount;
    private String expenseDescription;
    private ExpenseStatus expenseStatus;
    private ExpenseGroup expenseGroup;
}
