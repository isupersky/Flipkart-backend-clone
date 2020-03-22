package com.tothenew.bluebox.bluebox;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.enitity.Role;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlueboxApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;


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

		ArrayList<Role> tempRole = new ArrayList<>();
		Role role = new Role();
		role.setAuthority("ADMIN");
		Role role2 = new Role();
		role2.setAuthority("CUSTOMER");
		Role role3 = new Role();
		role3.setAuthority("SELLER");
		tempRole.add(role);
		tempRole.add(role2);
		tempRole.add(role3);
//		roleRepository.save(role);
		customer.setRoles(tempRole);
		userRepository.save(customer);
//		roleRepository.saveAll(tempRole);
	}

}
