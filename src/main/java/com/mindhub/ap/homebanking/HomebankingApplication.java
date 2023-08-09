package com.mindhub.ap.homebanking;

import com.mindhub.ap.homebanking.models.Account;
import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.models.Transaction;
import com.mindhub.ap.homebanking.models.TransactionType;
import com.mindhub.ap.homebanking.repository.AccountRepository;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import com.mindhub.ap.homebanking.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melbam@mail.com");
			Client client2 = new Client("Fernando", "Maciel", "ferm@mail.com");

			Account account1 = new Account("VIN001", LocalDate.now(),5000D, client1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500D, client1);
			Account account3 = new Account("VIN003", LocalDate.now(),8500D, client2);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1),9500D, client2);

			Transaction transaction1 = new Transaction("Store", LocalDateTime.now(),-5000D, TransactionType.DEBIT, account1);
			Transaction transaction2 = new Transaction("Income", LocalDateTime.now(),5000D, TransactionType.CREDIT, account1);
			Transaction transaction3 = new Transaction("Groceries", LocalDateTime.now(),-7500D, TransactionType.DEBIT, account2);
			Transaction transaction4 = new Transaction("Mark", LocalDateTime.now(),7500D, TransactionType.CREDIT, account2);
			Transaction transaction5 = new Transaction("Gas", LocalDateTime.now(),-8500D, TransactionType.DEBIT, account3);
			Transaction transaction6 = new Transaction("Melba", LocalDateTime.now(),8500D, TransactionType.CREDIT, account3);
			Transaction transaction7 = new Transaction("Starbucks", LocalDateTime.now(),-6000D, TransactionType.DEBIT, account4);
			Transaction transaction8 = new Transaction("Fernando", LocalDateTime.now(),6000D, TransactionType.CREDIT, account4);

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

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account4.addTransaction(transaction7);
			account4.addTransaction(transaction8);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);

		});
	}

}
