package pl.simple.finance.apiserver.repository.expense;

import org.springframework.data.repository.CrudRepository;
import pl.simple.finance.apiserver.model.expense.ExpenseCategory;

public interface ExpenseCategoryRepository extends CrudRepository<ExpenseCategory, Long> {
}
