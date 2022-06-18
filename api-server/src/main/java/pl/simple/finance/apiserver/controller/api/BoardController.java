package pl.simple.finance.apiserver.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.simple.finance.apiserver.controller.helper.SecurityContextHandler;
import pl.simple.finance.apiserver.controller.helper.TimeFrame;
import pl.simple.finance.apiserver.model.expense.Expense;
import pl.simple.finance.apiserver.model.goal.SavingGoalDto;
import pl.simple.finance.apiserver.service.contract.ExpenseService;
import pl.simple.finance.apiserver.service.contract.GoalService;
import pl.simple.finance.apiserver.service.contract.SavingsService;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/board")
public class BoardController {

    private final ExpenseService expenseService;
    private final SavingsService savingsService;
    private final GoalService goalService;

    @Autowired
    public BoardController(ExpenseService expenseService, SavingsService savingsService, GoalService goalService) {
        this.expenseService = expenseService;
        this.savingsService = savingsService;
        this.goalService = goalService;
    }

    @GetMapping("/total-money")
    public double getUserTotalMoney() {

        long id = SecurityContextHandler.getUserId();
        return savingsService.getUserTotalMoney(id);
    }

    @GetMapping("/total-expenses/month={month}&year={year}")
    public double getUserSumExpensesInMonth(
            @PathVariable("month") String month, @PathVariable("year") String year) {

        long userId = SecurityContextHandler.getUserId();
        int monthNumber = Integer.parseInt(month);
        int yearNumber = Integer.parseInt(year);
        TimeFrame timeFrame = TimeFrame.getTimeFrameOfMonthAndYear(monthNumber, yearNumber);
        return expenseService.sumUserExpensesBetweenDates(
                userId, timeFrame.getStart(), timeFrame.getEnd());
    }

    @GetMapping("/last-expense")
    public Expense getUserLastExpense() {

        long userId = SecurityContextHandler.getUserId();
        Optional<Expense> lastExpense = expenseService.getUserLastExpense(userId);
        if(lastExpense.isPresent()) {
            return lastExpense.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have any expenses");
    }

    @GetMapping("/max-profit-investment")
    public double getUserBestInvestmentProfit() {

        long userId = SecurityContextHandler.getUserId();
        double maxProfit = savingsService.getUserBestInvestmentProfit(userId);
        if(maxProfit <= 0.0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have any profit investment");
        return maxProfit;
    }

    @GetMapping("/max-loss-investment")
    public double getUserBiggestLoss() {

        long userId = SecurityContextHandler.getUserId();
        double maxLoss = savingsService.getUserWorstInvestmentLoss(userId);
        if(maxLoss >= 0.0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have any loss investment");
        return maxLoss;
    }

    @GetMapping("/most-completed-goal")
    public SavingGoalDto getMostCompletedGoal() {

        long userId = SecurityContextHandler.getUserId();
        Optional<SavingGoalDto> savingGoal = goalService.getUserMostCompletedGoal(userId);
        if(savingGoal.isPresent()) {
            return savingGoal.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You don't have any saving goals");
    }
}
