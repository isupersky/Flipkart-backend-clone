package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.enitity.Role;
import com.tothenew.bluebox.bluebox.enitity.User;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  public void createCustomer(Customer customer) {
    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findById(2).get();
    defaultRole.add(role);
    customer.setRoles(defaultRole);
    userRepository.save(customer);
  }


  public Iterable<User> getTestUser() {
//    List<User> tempList = new ArrayList<>();
    return userRepository.findAll();

//    return tempList;
  }
}
