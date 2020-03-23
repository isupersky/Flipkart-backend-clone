package com.tothenew.bluebox.bluebox;

import com.tothenew.bluebox.bluebox.enitity.Role;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {


  @Autowired
  RoleRepository roleRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Role role = new Role();
    role.setAuthority("ADMIN");
    Role role2 = new Role();
    role2.setAuthority("CUSTOMER");
    Role role3 = new Role();
    role3.setAuthority("SELLER");
    roleRepository.save(role);
    roleRepository.save(role2);
    roleRepository.save(role3);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//      user1.setPassword(passwordEncoder.encode("pass"));

  }
}
