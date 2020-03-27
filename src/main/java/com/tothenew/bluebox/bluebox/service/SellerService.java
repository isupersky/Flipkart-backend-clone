package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.enitity.Role;
import com.tothenew.bluebox.bluebox.enitity.Seller;
import com.tothenew.bluebox.bluebox.enitity.User;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

  @Autowired
  SellerRepository sellerRepository;

  @Autowired
  RoleRepository roleRepository;

  public Iterable<Seller> getTestUser() {
    return sellerRepository.findAll();
  }


  public String registerSeller(Seller seller) {
    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findById(2).get();

    User existingUser = sellerRepository.findByEmailIgnoreCase(seller.getEmail());
    if (existingUser != null) {
      return "message : This email already exists!";
    } else {
      defaultRole.add(role);
      seller.setRoles(defaultRole);
      sellerRepository.save(seller);
      return "SUCCESS";
    }
  }

}
