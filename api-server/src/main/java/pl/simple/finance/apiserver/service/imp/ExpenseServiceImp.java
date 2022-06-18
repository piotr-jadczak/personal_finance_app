package pl.simple.finance.apiserver.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.expense.Expense;
import pl.simple.finance.apiserver.model.expense.ExpenseCategory;
import pl.simple.finance.apiserver.model.expense.ExpenseForm;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.repository.expense.ExpenseCategoryRepository;
import pl.simple.finance.apiserver.repository.expense.ExpenseRepository;
import pl.simple.finance.apiserver.repository.user.UserRepository;
import pl.simple.finance.apiserver.service.contract.ExpenseService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ExpenseServiceImp implements ExpenseService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;

    @Autowired
    public ExpenseServiceImp(UserRepository userRepository,
                             ExpenseRepository expenseRepository,
                             ExpenseCategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Expense addExpense(ExpenseForm expenseForm, long userId) {

        User user = getUserWithId(userId);
        Expense expenseToAdd = new Expense(
                expenseForm.getDate(),
                expenseForm.getAmount(),
                expenseForm.getNote(),
                new HashSet<>(expenseForm.getCategories()),
                user);
        return expenseRepository.save(expenseToAdd);
    }

    @Override
    public List<Expense> getUserExpensesBetweenDates(long userId, LocalDate startDate,
                                                     LocalDate endDate) {
        return StreamSupport.stream(
                expenseRepository.findAllByUserIdAndDateIsGreaterThanEqualAndDateIsLessThanEqual(
                        userId, startDate, endDate).spliterator(), false)
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
    public List<Expense> getUserExpenses(long userId) {

        return StreamSupport.stream(
                expenseRepository.findAllByUserId(userId).spliterator(), false)
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
    public boolean deleteExpense(long expenseId) {

        if(!expenseRepository.existsById(expenseId)) {
            throw new RuntimeException("No such expense in db");
        }
        expenseRepository.deleteById(expenseId);
        return !expenseRepository.existsById(expenseId);
    }

    @Override
    public List<ExpenseCategory> getAllCategories() {

        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparingLong(ExpenseCategory::getId))
                .collect(Collectors.toList());
    }

    @Override
    public double sumUserExpensesBetweenDates(long userId, LocalDate startDate, LocalDate endDate) {

        List<Expense> expenses = getUserExpensesBetweenDates(userId ,startDate, endDate);
        int sum = 0;
        for(Expense exp : expenses) {
            sum += exp.getAmount();
        }
        return sum;
    }

    @Override
    public Optional<Expense> getUserLastExpense(long userId) {

        List<Expense> expenses = getUserExpenses(userId);
        Optional<Expense> lastExpense = expenses.stream().
                max(Comparator.naturalOrder());
        return lastExpense;
    }


    private User getUserWithId(long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("user with id " + userId + " does not exist."));
    }
}
