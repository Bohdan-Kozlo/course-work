package ua.lviv.iot.algo.coursework.runapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("ua.lviv.iot.algo.coursework")
public class CourseworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseworkApplication.class, args);

	}

}
