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
		Role role = roleRepository.findById(1).get();
//		role.setAuthority("ADMIN");
		Role role2 = roleRepository.findById(2).get();
//		role2.setAuthority("CUSTOMER");
		Role role3 = roleRepository.findById(3).get();
//		role3.setAuthority("SELLER");
		tempRole.add(role);
		tempRole.add(role2);
		tempRole.add(role3);
		customer.setRoles(tempRole);
		userRepository.save(customer);


		Customer customer2 = new Customer();
		customer2.setContact(92l);
		customer2.setActive(true);
		customer2.setEmail("mto");
		customer2.setFirstName("Ab");
		customer2.setLastName("S");
		customer2.setMiddleName("");
		customer2.setPassword("Pa");
		customer2.setDeleted(true);


		ArrayList<Role> tempRole2 = new ArrayList<>();

		Role role4 = roleRepository.findById(1).get();
//		role.setAuthority("ADMIN");
		Role role5 = roleRepository.findById(2).get();
		tempRole2.add(role4);
		tempRole2.add(role5);
		customer2.setRoles(tempRole2);
		userRepository.save(customer2);
	}

}
