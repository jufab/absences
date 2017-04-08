package fr.jufab.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataSourceProperties.class)
public class AbsencesApplication {
	public static void main(String[] args) {
		SpringApplication.run(AbsencesApplication.class, args);
	}
}
