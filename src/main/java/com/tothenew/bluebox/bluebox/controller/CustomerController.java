package com.tothenew.bluebox.bluebox.controller;

import com.tothenew.bluebox.bluebox.enitity.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.Customer;
import com.tothenew.bluebox.bluebox.enitity.User;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.service.CustomerService;
import com.tothenew.bluebox.bluebox.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  @Autowired
  CustomerService customerService;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  EmailSenderService emailSenderService;

  @PostMapping(path = "/customer-register")
  public String registerCustomer(@RequestBody Customer customer) {
    customerService.createCustomer(customer);
    return "Success".toUpperCase();
  }

  @GetMapping(path = "/customer")
  public Iterable<Customer> getTestUser() {
    return customerService.getTestUser();
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String registerUser(@RequestBody Customer customer) {

    User existingUser = customerRepository.findByEmailIgnoreCase(customer.getEmail());
    if (existingUser != null) {
      return "message : This email already exists!";
//      modelAndView.setViewName("error");
    } else {
      customerRepository.save(customer);

      ConfirmationToken confirmationToken = new ConfirmationToken(customer);

      confirmationTokenRepository.save(confirmationToken);

      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(customer.getEmail());
      mailMessage.setSubject("Complete Registration!");
      mailMessage.setFrom("ecommerce476@gmail.com ");
      mailMessage.setText("To confirm your account, please click here : "
          + "http://localhost:8080/confirm-account?token=" + confirmationToken
          .getConfirmationToken());

      emailSenderService.sendEmail(mailMessage);

//      modelAndView.addObject("emailId", customer.getEmailId());

      return "successfulRegisteration";
    }

//    return modelAndView;
  }

  @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public String confirmUserAccount(@RequestParam("token") String confirmationToken) {
    ConfirmationToken token = confirmationTokenRepository
        .findByConfirmationToken(confirmationToken);

    if (token != null) {
      Customer customer = (Customer) customerRepository
          .findByEmailIgnoreCase(token.getUser().getEmail());
      customer.setActive(true);
      customerRepository.save(customer);
      confirmationTokenRepository.delete(token);
      return "accountVerified";
    } else {
      return "message: The link is invalid or broken!";
//      modelAndView.setViewName("error");
    }

//    return modelAndView;
  }

}
