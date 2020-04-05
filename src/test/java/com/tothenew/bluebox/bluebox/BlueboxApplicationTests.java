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
import java.util.HashSet;
import java.util.List;
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


}
