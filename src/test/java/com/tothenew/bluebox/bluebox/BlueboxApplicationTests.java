package com.tothenew.bluebox.bluebox;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlueboxApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void insertUserTest(){
		Customer customer = new Customer();
		customer.setContact(92l);
		customer.setActive(true);
		customer.setEmail("mto");
		customer.setFirstName("Ab");
		customer.setLastName("S");
		customer.setMiddleName("");
		customer.setPassword("Pa");
		customer.setDeleted(true);
		userRepository.save(customer);
	}

}
