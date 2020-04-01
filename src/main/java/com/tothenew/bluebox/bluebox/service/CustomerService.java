package com.tothenew.bluebox.bluebox.service;

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
import org.springframework.mail.SimpleMailMessage;
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

  //  This is a test method and to be deleted
  public Iterable<Customer> getTestUser() {
    return customerRepository.findAll();
  }

//  public void createCustomer(Customer customer) {
//    List<Role> defaultRole = new ArrayList<>();
//    Role role = roleRepository.findById(2).get();
//    defaultRole.add(role);
//    customer.setRoles(defaultRole);
//    userRepository.save(customer);
//  }


  public Object registerCustomer(Customer customer) {
    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findByAuthority("ROLE_CUSTOMER");
    defaultRole.add(role);
    User existingUser = customerRepository.findByEmailIgnoreCase(customer.getEmail());
    if (existingUser != null) {
      throw new UserAlreadyExistsException("User Already Registered !!!");
    } else {
      customer.setRoles(defaultRole);
      customerRepository.save(customer);
      generateToken(customer);
      return "successfull Registeration";
    }
  }

  public void generateToken(Customer customer) {
    ConfirmationToken confirmationToken = new ConfirmationToken(customer);

    confirmationTokenRepository.save(confirmationToken);

    taskExecutor.execute(() -> {
      try {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customer.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("ecommerce476@gmail.com ");
        mailMessage.setText("To confirm your account, please click here : "
            + "http://localhost:8080/confirm-account?token=" + confirmationToken
            .getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println(
            "Failed to send email to: " + customer.getEmail() + " reason: " + e.getMessage());
      }
    });
  }


  public String validateCustomer(String confirmationToken) {

    ConfirmationToken token = confirmationTokenRepository
        .findByConfirmationToken(confirmationToken);

    Date date = new Date();

    if (token != null && (date.getHours() - token.getCreatedDate().getHours()) < 3) {

      Customer customer = (Customer) customerRepository
          .findByEmailIgnoreCase(token.getUser().getEmail());
      customer.setActive(true);
      customerRepository.save(customer);
      confirmationTokenRepository.delete(token);
      return "accountVerified";
    } else if (token != null && (date.getHours() - token.getCreatedDate().getHours()) > 3) {
      Customer customer = customerRepository
          .findById(
              token
                  .getUser()
                  .getId())
          .get();

      confirmationTokenRepository.delete(token);
      generateToken(customer);
      return "Token time Expired!!  New Token sent to registered email Id";
    } else {
      return "message: The link is invalid or broken!";
    }
  }
}
