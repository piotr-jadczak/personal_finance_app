package pl.simple.finance.apiserver.repository.goal;

import org.springframework.data.repository.CrudRepository;
import pl.simple.finance.apiserver.model.goal.SavingGoal;

public interface SavingGoalRepository extends CrudRepository<SavingGoal, Long> {

    boolean existsById(long goalId);
    Iterable<SavingGoal> findAllByUserId(long userId);
}
