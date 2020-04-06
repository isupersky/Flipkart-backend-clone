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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


  public ResponseEntity<Object> registerSeller(SellerDto sellerDto) {
    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findByAuthority("ROLE_SELLER");
    defaultRole.add(role);

    User existingUser = sellerRepository.findByEmailIgnoreCase(sellerDto.getEmail());
    if (existingUser != null) {
      throw new UserAlreadyExistsException("This email already exists!");
    } else {
      Seller seller = new Seller();
      BeanUtils.copyProperties(sellerDto, seller);
      seller.setRoles(defaultRole);
      sellerRepository.save(seller);
      return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }
  }

}
