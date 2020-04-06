package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.dto.CustomerDto;
import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.UserAlreadyExistsException;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  EmailSenderService emailSenderService;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  PasswordEncoder passwordEncoder;


  public ResponseEntity<Object> registerCustomer(CustomerDto customerDto) {

    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findByAuthority("ROLE_CUSTOMER");
    defaultRole.add(role);

    User existingUser = userRepository.findByEmailIgnoreCase(customerDto.getEmail());
    if (existingUser != null) {
      throw new UserAlreadyExistsException("User Already Registered !!!");
    } else {
      Customer customer = new Customer();

      customer.setFirstName(customerDto.getFirstName());
      customer.setMiddleName(customerDto.getMiddleName());
      customer.setLastName(customerDto.getLastName());
      customer.setEmail(customerDto.getEmail());
      customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
      customer.setRoles(defaultRole);
      customer.setCreatedDate(new Date());
      customer.setUpdatedDate(new Date());
      customer.setContact(customerDto.getContact());

      customerRepository.save(customer);

      generateToken(customerRepository.findByEmailIgnoreCase(customerDto.getEmail()));
      return new ResponseEntity<Object>("successful Registration", HttpStatus.CREATED);
    }
  }

  public void generateToken(User customer) {
    ConfirmationToken confirmationToken = new ConfirmationToken(customer);

    confirmationTokenRepository.save(confirmationToken);

    taskExecutor.execute(() -> {
      try {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customer.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("ecommerce476@gmail.com ");
        mailMessage.setText("To confirm your account, please click here : "
            + "http://localhost:8080/customer/confirm-account?token=" + confirmationToken
            .getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println(
            "Failed to send email to: " + customer.getEmail() + " reason: " + e.getMessage());
      }
    });
  }


  public ResponseEntity<Object> validateCustomer(String confirmationToken) {

    ConfirmationToken token = confirmationTokenRepository
        .findByConfirmationToken(confirmationToken);

    Date date = new Date();

    if (token != null && (date.getHours() - token.getCreatedDate().getHours()) < 3) {

      Customer customer = (Customer) customerRepository
          .findByEmailIgnoreCase(token.getUser().getEmail());
      customer.setActive(true);
      customerRepository.save(customer);
      confirmationTokenRepository.delete(token);
      return new ResponseEntity<Object>("accountVerified", HttpStatus.ACCEPTED);
    } else if (token != null && (date.getHours() - token.getCreatedDate().getHours()) > 3) {
      Customer customer = customerRepository
          .findById(
              token
                  .getUser()
                  .getId())
          .get();

      confirmationTokenRepository.delete(token);
      generateToken(customer);
      return new ResponseEntity<Object>(
          "Token time Expired!!  New Token sent to registered email Id", HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<Object>("message: The link is invalid or broken!",
          HttpStatus.BAD_REQUEST);
    }
  }
}
