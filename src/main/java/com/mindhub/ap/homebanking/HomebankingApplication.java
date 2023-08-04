package com.mindhub.ap.homebanking;

import com.mindhub.ap.homebanking.models.Client;
import com.mindhub.ap.homebanking.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melbam@mail.com");
			Client client2 = new Client("Fernando", "Maciel", "ferm@mail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
		});
	}

}
