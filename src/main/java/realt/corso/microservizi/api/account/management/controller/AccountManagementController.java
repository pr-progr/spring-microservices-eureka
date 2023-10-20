package realt.corso.microservizi.api.account.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountManagementController {

	@GetMapping("/management")
	public String accountManager() {
		return "Account Mnager";
	}
}
