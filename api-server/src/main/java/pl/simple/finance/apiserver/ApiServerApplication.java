package pl.simple.finance.apiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.simple.finance.apiserver.model.expense.ExpenseCategory;
import pl.simple.finance.apiserver.model.saving.currency.CurrencyDataInputReader;
import pl.simple.finance.apiserver.model.saving.stock.StockDataInputReader;
import pl.simple.finance.apiserver.model.user.ERole;
import pl.simple.finance.apiserver.model.user.Role;
import pl.simple.finance.apiserver.model.user.request.SignUpForm;
import pl.simple.finance.apiserver.repository.expense.ExpenseCategoryRepository;
import pl.simple.finance.apiserver.repository.user.RoleRepository;
import pl.simple.finance.apiserver.service.contract.AuthService;
import pl.simple.finance.apiserver.service.contract.CurrencyDataService;
import pl.simple.finance.apiserver.service.contract.StockDataService;

import javax.swing.*;


@SpringBootApplication
public class ApiServerApplication {

	private final int REFRESH_PERIOD = 900000; //15 minutes

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner setupApp(RoleRepository roleRepository,
									  CurrencyDataInputReader currencyReader,
									  CurrencyDataService currencyDataService,
									  AuthService authService,
									  StockDataService stockDataService,
									  StockDataInputReader stockReader,
									  ExpenseCategoryRepository categoryRepository
	) {
		return args -> {
			roleRepository.save(new Role(ERole.ROLE_USER));
			categoryRepository.save(new ExpenseCategory("rent"));
			categoryRepository.save(new ExpenseCategory("utilities"));
			categoryRepository.save(new ExpenseCategory("groceries"));
			categoryRepository.save(new ExpenseCategory("clothes"));
			categoryRepository.save(new ExpenseCategory("mortgage"));
			categoryRepository.save(new ExpenseCategory("entertainment"));
			categoryRepository.save(new ExpenseCategory("hobby"));
			categoryRepository.save(new ExpenseCategory("car"));
			categoryRepository.save(new ExpenseCategory("transport"));
			categoryRepository.save(new ExpenseCategory("holidays"));
			categoryRepository.save(new ExpenseCategory("medicines"));
			authService.registerUser(new SignUpForm("tester",
					"test@email.pl", "password"));
			stockReader.initialSetUp();
			stockDataService.updateStocksBufferPrices();
			stockDataService.copyStockDataFromBuffer();
			Timer timer = new Timer(REFRESH_PERIOD, stockDataService);
			timer.start();
			currencyReader.initialSetUp();
			currencyDataService.updateCurrencyDataBufferPrices();
			currencyDataService.copyCurrencyDataFromBuffer();
			Timer timer2 = new Timer(REFRESH_PERIOD, currencyDataService);
			timer2.start();

		};
	}

}
