package hello.finalterm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 안에 @componentScan이 포함되어 있어서 application context 역할을 한다
@SpringBootApplication
public class FinaltermApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinaltermApplication.class, args);
	}

}
