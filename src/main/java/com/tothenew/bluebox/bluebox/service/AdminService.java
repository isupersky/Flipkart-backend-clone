package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  SellerRepository sellerRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  EmailSenderService emailSenderService;


  /*
    Method to get the list of all Customers present in the system.
   */
  public List<Object> getAllCustomers() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    return customerRepository.findAllCustomers(pageable);
  }

  /*
    Method to get the list of all Sellers present in the system.
   */
  public List<Object> getAllSellers() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    return sellerRepository.findAllSellers(pageable);
  }

  /*
    Method activates the user account for provide user id and triggers a mail to user about the activation.
   */
  public ResponseEntity<Object> activateUser(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isActive()) {
        return new ResponseEntity<>("user activated", HttpStatus.OK);
      }

      user.setActive(true);
      userRepository.save(user);

      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(user.getEmail());
          mailMessage.setSubject("Account Activated by Admin");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage.setText("Your account has been activated by the Admin");

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println(
              "Failed to send email to: " + user.getEmail() + " reason: " + e.getMessage());
        }
      });

      return new ResponseEntity<>("user activated", HttpStatus.OK);
    }
    return new ResponseEntity<>("User not present", HttpStatus.NOT_FOUND);
  }


  /*
   Method deactivates the user account for provide user id and triggers a mail to user about the activation.
  */
  public ResponseEntity<Object> deactivateUser(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isActive()) {
        return new ResponseEntity<>("user deactivated", HttpStatus.OK);
      }

      user.setActive(false);
      userRepository.save(user);

      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(user.getEmail());
          mailMessage.setSubject("Account DeActivated by Admin");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage.setText("Your account has been deactivated by the Admin");

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println(
              "Failed to send email to: " + user.getEmail() + " reason: " + e.getMessage());
        }
      });

      return new ResponseEntity<>("user deactivated", HttpStatus.OK);
    }
    return new ResponseEntity<>("User not present", HttpStatus.NOT_FOUND);
  }
}
