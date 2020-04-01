package com.tothenew.bluebox.bluebox;

import com.tothenew.bluebox.bluebox.enitity.user.Address;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import com.tothenew.bluebox.bluebox.repository.AddressRepository;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.ProductVariationRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlueboxApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductVariationRepository productVariationRepository;

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

	@Test
	void createTestUser(){
		User user = new User();
		Role role = roleRepository.findById(1).get();
		List<Role> tempRoleList = new ArrayList<>();
		tempRoleList.add(role);
		user.setRoles(tempRoleList);

		Address address = new Address();
		address.setAddressLine("ye hai mera pata");
		List<Address> tempAddressList = new ArrayList<>();
		tempAddressList.add(address);
		user.setAddress(tempAddressList);

		user.setActive(true);
		userRepository.save(user);

		Customer customer = new Customer();

		Address address1 = new Address();
		address1.setAddressLine("new Address 1");
		Address address2 = new Address();
		address2.setAddressLine("new Address 2");

		List<Address> addressList = new ArrayList<>();
		addressList.add(address1);
		addressList.add(address2);
		customer.setAddress(addressList);

		Role role1 = roleRepository.findById(2).get();
		List<Role> roleList = new ArrayList<>();
		roleList.add(role);
		roleList.add(role1);
		customer.setRoles(roleList);

		userRepository.save(customer);


	}

	@Test
	void createTestSeller(){
		Seller testSeller = new Seller();
		testSeller.setCompanyName("Blubox");
		testSeller.setFirstName("Aakash");
		testSeller.setEmail("mailtomeaakash@gmail.com");
		sellerRepository.save(testSeller);
	}

	@Test
	void createTestProduct(){
		createTestSeller();
		Product testProduct = new Product();
		testProduct.setSellerUserId((Seller) sellerRepository.findByEmailIgnoreCase("mailtomeaakash@gmail.com"));
		productRepository.save(testProduct);
	}

	@Test
	void createProductVariation() throws IOException {
		ProductVariation productVariation = new ProductVariation();
		HashMap<String, String> variationTest = new HashMap<>();
		variationTest.put("size", "123 ");
		variationTest.put("color", "red");
		productVariation.setMetadataHashmap(variationTest);
		productVariation.jsonMetadataStringSerialize();
		String serialisedTest = productVariation.getMetadata();
		productVariation.setMetadata(serialisedTest);
		productVariation.jsonMetadataStringDeserialize();
		productVariationRepository.save(productVariation);
	}

}
