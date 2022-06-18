package pl.simple.finance.apiserver.service.contract;

import pl.simple.finance.apiserver.model.goal.SavingGoalDto;
import pl.simple.finance.apiserver.model.goal.SavingGoalForm;

import java.util.List;
import java.util.Optional;

public interface GoalService {

    SavingGoalDto addSavingGoal(SavingGoalForm goalForm, long userId);
    boolean deleteSavingGoal(long id);
    List<SavingGoalDto> getAllUserSavingGoals(long userId);
    Optional<SavingGoalDto> getUserMostCompletedGoal(long userId);
}
