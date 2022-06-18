package pl.simple.finance.apiserver.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.simple.finance.apiserver.model.goal.SavingGoal;
import pl.simple.finance.apiserver.model.goal.SavingGoalDto;
import pl.simple.finance.apiserver.model.goal.SavingGoalForm;
import pl.simple.finance.apiserver.model.user.User;
import pl.simple.finance.apiserver.repository.goal.SavingGoalRepository;
import pl.simple.finance.apiserver.repository.user.UserRepository;
import pl.simple.finance.apiserver.service.contract.GoalService;
import pl.simple.finance.apiserver.service.contract.SavingsService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GoalServiceImp implements GoalService {

    private final UserRepository userRepository;
    private final SavingGoalRepository savingGoalRepository;
    private final SavingsService savingsService;

    @Autowired
    public GoalServiceImp(UserRepository userRepository,
                          SavingGoalRepository savingGoalRepository,
                          SavingsService savingsService) {
        this.userRepository = userRepository;
        this.savingGoalRepository = savingGoalRepository;
        this.savingsService = savingsService;
    }

    @Override
    public SavingGoalDto addSavingGoal(SavingGoalForm goalForm, long userId) {

        User user = getUserWithId(userId);
        SavingGoal goalToAdd = new SavingGoal(goalForm.getAmountToSave(),
                goalForm.getDescription(), user);

        SavingGoal addedGoal = savingGoalRepository.save(goalToAdd);
        return castSavingGoal(addedGoal, userId);
    }

    @Override
    public boolean deleteSavingGoal(long id) {

        if(!savingGoalRepository.existsById(id)) {
            throw new RuntimeException("No such goal in db");
        }
        savingGoalRepository.deleteById(id);
        return !savingGoalRepository.existsById(id);
    }

    @Override
    public List<SavingGoalDto> getAllUserSavingGoals(long userId) {

        Iterable<SavingGoal> userSavingGoals = savingGoalRepository.findAllByUserId(userId);
        List<SavingGoalDto> savingGoalsDto = new ArrayList<>();
        for(SavingGoal goal : userSavingGoals) {
            savingGoalsDto.add(castSavingGoal(goal, userId));
        }
        return savingGoalsDto;
    }

    @Override
    public Optional<SavingGoalDto> getUserMostCompletedGoal(long userId) {

        List<SavingGoalDto> userGoals = getAllUserSavingGoals(userId);

        return userGoals.stream().max(
                Comparator.comparing(SavingGoalDto::getPercentCompleted));
    }

    private User getUserWithId(long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("user with id " + userId + " does not exist."));
    }

    private SavingGoalDto castSavingGoal(SavingGoal savingGoal, long userId) {

        double userMoney = savingsService.getUserTotalMoney(userId);
        double amountToSave = savingGoal.getAmountToSave();
        double percentCompleted = userMoney >= amountToSave ?
                100 : Math.round((userMoney * 100 / amountToSave) * 100.0) / 100.0;

        SavingGoalDto savingGoalDto = new SavingGoalDto(savingGoal.getId(),
                savingGoal.getAmountToSave(),
                userMoney, percentCompleted,
                savingGoal.getDescription());
        return savingGoalDto;
    }
}
