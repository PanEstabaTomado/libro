package dfs1103.maq.bibiliotecaam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BibiliotecaamApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibiliotecaamApplication.class, args);
	}

}
