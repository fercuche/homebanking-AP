package com.mindhub.ap.homebanking;

import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melbam@mail.com");
			Client client2 = new Client("Fernando", "Maciel", "ferm@mail.com");

			Account account1 = new Account("VIN001", LocalDate.now(),5000D, client1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500D, client1);
			Account account3 = new Account("VIN003", LocalDate.now(),8500D, client2);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1),9500D, client2);

			clientRepository.save(client1);
			clientRepository.save(client2);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

		});
	}

}
