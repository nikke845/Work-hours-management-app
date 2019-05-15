package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Workday;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.WorkdayRepository;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Paycycle;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.PaycycleRepository;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.User;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.UserRepository;

@SpringBootApplication
public class WorkHoursManagementAppApplication {
	private static final Logger log = LoggerFactory.getLogger(WorkHoursManagementAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WorkHoursManagementAppApplication.class, args);
	}@Bean
	public CommandLineRunner workHoursManagementApp(WorkdayRepository wRepository, PaycycleRepository pRepository, UserRepository userRepository) {
		return (args) -> {
			
			// Create users: admin/admin, user1/user1, user2/user2
			log.info("Add users");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userRepository.save(new User("user1", encoder.encode("user1"), "user1@user.com","USER"));
			userRepository.save(new User("user2", encoder.encode("user2"), "user2@user.com","USER"));
			userRepository.save(new User("admin", encoder.encode("admin"), "admin@admin.com","ADMIN"));
			
			log.info("Fetch all users");
			for (User user : userRepository.findAll()) {
				log.info(user.toString());
			}
			
			
			log.info("save workdays");
			
			pRepository.save(new Paycycle("Tammikuu", "user1"));
			pRepository.save(new Paycycle("Helmikuu", "user1"));
			pRepository.save(new Paycycle("1.1-2.1", "user2"));
			pRepository.save(new Paycycle("testi", "admin"));
			
			wRepository.save(new Workday("1.1.2019", 6.5, "8.00", "15.25", "user1", pRepository.findByName("Tammikuu").get(0)));
			wRepository.save(new Workday("2.1.2019", 5.5, "7.00", "17.00", "user1", pRepository.findByName("Tammikuu").get(0)));	
			wRepository.save(new Workday("1.2.2019", 8.0, "6.00", "14.00", "user2", pRepository.findByName("1.1-2.1").get(0)));	
			
			log.info("fetch all workdays");
			for (Workday workday : wRepository.findAll()) {
				log.info(workday.toString());
			}

		};
	}
}