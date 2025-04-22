package pfe.project.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import pfe.project.back.auth.AuthenticationService;
import pfe.project.back.auth.RegisterRequest;

import static pfe.project.back.Entity.Role.ADMIN;
import static pfe.project.back.Entity.Role.EMPLOYEE;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}


	/*@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService ser) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("admin")
					.lastname("admin")
					.email("admi@gmail.com").password("password")
					.role(ADMIN)
					.build();
			System.out.println("ADMIN token : "+ser.register(admin).getAccessToken());

			var employee = RegisterRequest.builder()
					.firstname("employee")
					.lastname("employee")
					.email("employe2@gmail.com").password("password")
					.role(EMPLOYEE)
					.build();
			System.out.println("EMPLOYEE token : "+ser.register(employee).getAccessToken());

		};
	}*/

}
