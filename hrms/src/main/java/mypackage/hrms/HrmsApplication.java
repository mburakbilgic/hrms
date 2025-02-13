package mypackage.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "HRMS API",
				version = "1.0",
				description = "HRMS API Dokümantasyonu"
		)
)
public class HrmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmsApplication.class, args);
	}

	// TODO update that at night of 7/21 and followed 7/22
	// mernis and system verification needed, under the core create the utilities
	// and command all update-delete-verify operations
}
