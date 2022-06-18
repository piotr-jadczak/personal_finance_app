package pl.simple.finance.apiserver.repository.expense;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.simple.finance.apiserver.model.expense.Expense;

import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    Iterable<Expense> findAllByUserId(long userId);
    boolean existsById(long expenseId);
    Iterable<Expense> findAllByUserIdAndDateIsGreaterThanEqualAndDateIsLessThanEqual
            (long userId, LocalDate start, LocalDate end);
}
