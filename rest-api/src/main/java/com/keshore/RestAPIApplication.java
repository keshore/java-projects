package com.keshore;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.keshore.dao.Role;
import com.keshore.dao.User;
import com.keshore.service.UserService;

@SpringBootApplication
public class RestAPIApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	private static final Logger logger = LogManager.getLogger(RestAPIApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestAPIApplication.class, args);
	}

	@Override
	public void run(String... params) throws Exception {
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("Oops! We have an Error. OK");
		logger.fatal("Damn! Fatal error. Please fix me.");
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setFirstname("Admin");
		admin.setMobile_1(new Long("9791887954"));
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_CLIENT)));

		userService.signup(admin);

		User client = new User();
		client.setUsername("client");
		client.setPassword("client");
		client.setEmail("client@email.com");
		client.setFirstname("Client");
		client.setMobile_1(new Long("9791887954"));
		client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

		userService.signup(client);
	}

}
