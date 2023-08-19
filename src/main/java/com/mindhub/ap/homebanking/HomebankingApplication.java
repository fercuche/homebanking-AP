package com.mindhub.ap.homebanking;

import com.mindhub.ap.homebanking.models.*;
import com.mindhub.ap.homebanking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melbam@mail.com","asd1234");
			Client client2 = new Client("Fernando", "Maciel", "ferm@mail.com", "qwe4567");

			clientRepository.save(client1);
			clientRepository.save(client2);

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

			Loan loan1 = new Loan("Hipotecario", 500000D, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000D, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 300000D, List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000D, 60);
			ClientLoan clientLoan2 = new ClientLoan(5000D, 12);
			ClientLoan clientLoan3 = new ClientLoan(100000D, 24);
			ClientLoan clientLoan4 = new ClientLoan(200000D, 36);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			client1.addLoan(clientLoan1);
			client1.addLoan(clientLoan2);
			client2.addLoan(clientLoan3);
			client2.addLoan(clientLoan4);

			loan1.addLoan(clientLoan1);
			loan2.addLoan(clientLoan2);
			loan2.addLoan(clientLoan3);
			loan3.addLoan(clientLoan4);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card("4555-5555-5555-1234", 123, CardType.DEBIT, CardColor.GOLD, LocalDate.now(), LocalDate.now().plusYears(5), client1 );
			Card card2 = new Card("4555-5555-4444-1234", 456, CardType.CREDIT, CardColor.TITANIUM, LocalDate.now(), LocalDate.now().plusYears(5), client1 );
			Card card3 = new Card("4555-5555-3333-1234", 789, CardType.CREDIT, CardColor.SILVER, LocalDate.now(), LocalDate.now().plusYears(5), client2 );

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);

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

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		});
	}

}
