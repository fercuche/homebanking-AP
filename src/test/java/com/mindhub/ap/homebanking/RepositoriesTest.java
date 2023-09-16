package com.mindhub.ap.homebanking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.mindhub.ap.homebanking.models.*;
import com.mindhub.ap.homebanking.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    //Loan Tests
    @Test
    void existLoans(){
        List<Loan> loans = loanRepository.findAll();
         assertThat(loans,is(not(empty())));
    }

    @Test
    void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    void maxAmountPersonalLoan(){
        Loan personalLoan = loanRepository.getLoanByName("Personal");
        assertThat(personalLoan, hasProperty("maxAmount", is(10000D)));
    }

    @Test
    void loanPaymentsIncludesPayment(){
        Loan hipoLoan = loanRepository.getLoanByName("Hipotecario");
        for (Integer payment : hipoLoan.getPayments()){
            assertThat(payment, isOneOf(12,24,36,48,60));
        }
    }

    //Account Tests
    @Test
    void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    void existAccountNumber(){
        Account account = accountRepository.findByNumber("VIN-001");
        assertThat(account, is(notNullValue()));
    }

    @Test
    void checkAccountTypeFromAccount(){
        Account account = accountRepository.findByNumber("VIN-001");
        assertThat(account, hasProperty("accountType", is(AccountType.CHECKING)));
    }

    //Client Tests
    @Test
    void findAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    void checkClientFirstName(){
        Client client = clientRepository.findByEmail("melbam@mail.com");
        assertThat(client, hasProperty("firstName", is("Melba")));
    }

    @Test
    void checkClientHasAccounts(){
        Client client = clientRepository.findByEmail("melbam@mail.com");
        assertThat(client.getAccounts(), is(not(empty())));
    }

    //Card Tests
    @Test
    void findAllCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    void ccvNumbersLength(){
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            Integer cvv = card.getCvv();
            String cvvString = String.valueOf(cvv);
            assertThat(cvvString, hasLength(3));
        }
    }

    //Transaction tests
    @Test
    void findAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    void checkTransactionsTypes(){
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions){
            assertThat(transaction.getType(), anyOf(is(TransactionType.CREDIT), is(TransactionType.DEBIT)));        }
    }

    @Test
    void checkAmountInDebitType(){
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions){
            if (transaction.getType() == TransactionType.DEBIT){
                assertThat(transaction.getAmount().intValue(), is(lessThan(0)));
            }
        }

    }


}