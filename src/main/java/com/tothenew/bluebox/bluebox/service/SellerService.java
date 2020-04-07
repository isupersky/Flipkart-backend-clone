package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.dto.SellerDto;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.UserAlreadyExistsException;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
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
    Role role = roleRepository.findByAuthority("ROLE_SELLER");

    User existingUser = sellerRepository.findByEmailIgnoreCase(seller.getEmail());
    if (existingUser != null) {
      throw new UserAlreadyExistsException("This email already exists!");
    } else {
      defaultRole.add(role);
      seller.setRoles(defaultRole);
      sellerRepository.save(seller);
      return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }
  }

}
