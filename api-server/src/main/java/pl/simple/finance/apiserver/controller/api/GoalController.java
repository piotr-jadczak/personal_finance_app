package pl.simple.finance.apiserver.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.simple.finance.apiserver.controller.helper.SecurityContextHandler;
import pl.simple.finance.apiserver.model.goal.SavingGoalDto;
import pl.simple.finance.apiserver.model.goal.SavingGoalForm;
import pl.simple.finance.apiserver.service.contract.GoalService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/saving-goals")
    List<SavingGoalDto> getUserSavingGoals() {

        long userId = SecurityContextHandler.getUserId();
        return goalService.getAllUserSavingGoals(userId);
    }

    @PostMapping("/saving-goals")
    SavingGoalDto addSavingGoal(@RequestBody @Valid SavingGoalForm goalForm) {

        long userId = SecurityContextHandler.getUserId();
        return goalService.addSavingGoal(goalForm, userId);
    }

    @DeleteMapping("/saving-goals/{id}")
    boolean deleteUserSavingGoal(@PathVariable("id") Long id) {

        return goalService.deleteSavingGoal(id);
    }
}
