package pl.simple.finance.apiserver.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.simple.finance.apiserver.controller.helper.DateConverter;
import pl.simple.finance.apiserver.controller.helper.SecurityContextHandler;
import pl.simple.finance.apiserver.model.expense.Expense;
import pl.simple.finance.apiserver.model.expense.ExpenseCategory;
import pl.simple.finance.apiserver.model.expense.ExpenseForm;
import pl.simple.finance.apiserver.service.contract.ExpenseService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/all-expense-categories")
    public List<ExpenseCategory> getAllExpenseCategories() {

        return expenseService.getAllCategories();
    }

    @GetMapping("/expenses")
    public List<Expense> getAllUserExpenses() {

        long userId = SecurityContextHandler.getUserId();
        return expenseService.getUserExpenses(userId);
    }

    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody @Valid ExpenseForm expenseForm) {

        long userId = SecurityContextHandler.getUserId();
        return expenseService.addExpense(expenseForm, userId);
    }

    @DeleteMapping("/expenses/{id}")
    public boolean deleteExpense(@PathVariable("id") Long id) {

        return expenseService.deleteExpense(id);
    }

    @GetMapping("/expenses/from={startDate}/to={endDate}")
    public List<Expense> getAllUserExpensesBetweenDates(@PathVariable("startDate") String start,
                                                        @PathVariable("endDate") String end) {

        long userId = SecurityContextHandler.getUserId();
        LocalDate startDate = DateConverter.convertDate(start);
        LocalDate endDate = DateConverter.convertDate(end);
        if(startDate.isAfter(endDate)) {
            throw new RuntimeException("Invalid dates provided");
        }
        return expenseService.getUserExpensesBetweenDates(userId, startDate, endDate);
    }
}
