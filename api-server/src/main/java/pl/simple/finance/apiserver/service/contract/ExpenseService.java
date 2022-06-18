package pl.simple.finance.apiserver.service.contract;

import pl.simple.finance.apiserver.model.expense.Expense;
import pl.simple.finance.apiserver.model.expense.ExpenseCategory;
import pl.simple.finance.apiserver.model.expense.ExpenseForm;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    Expense addExpense(ExpenseForm expenseForm, long userId);
    List<Expense> getUserExpensesBetweenDates(long userId, LocalDate startDate, LocalDate endDate);
    List<Expense> getUserExpenses(long userId);
    boolean deleteExpense(long expenseId);
    List<ExpenseCategory> getAllCategories();
    double sumUserExpensesBetweenDates(long userId, LocalDate startDate, LocalDate endDate);
    Optional<Expense> getUserLastExpense(long userId);

}
