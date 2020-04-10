package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.CustomerCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.dto.AddressDTO;
import com.tothenew.bluebox.bluebox.dto.CustomerDTO;
import com.tothenew.bluebox.bluebox.enitity.user.Address;
import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.UserAlreadyExistsException;
import com.tothenew.bluebox.bluebox.exception.UserNotFoundException;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
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

//---------------------------------------------------CREATE------------------------------------------------------------

  /*
    Method to register the validated customer
   */
  public ResponseEntity<MessageResponseEntity> registerCustomer(CustomerCO customerCO) {

    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findByAuthority("ROLE_CUSTOMER");
    defaultRole.add(role);

    User existingUser = userRepository.findByEmailIgnoreCase(customerCO.getEmail());
    if (existingUser != null) {
      throw new UserAlreadyExistsException("User Already Registered !!!");
    } else {
      Customer customer = new Customer();

      customer.setFirstName(customerCO.getFirstName());
      customer.setMiddleName(customerCO.getMiddleName());
      customer.setLastName(customerCO.getLastName());
      customer.setEmail(customerCO.getEmail());
      customer.setPassword(passwordEncoder.encode(customerCO.getPassword()));
      customer.setRoles(defaultRole);
      customer.setCreatedDate(new Date());
      customer.setUpdatedDate(new Date());
      customer.setContact(customerCO.getContact());

      customerRepository.save(customer);

      generateToken(customerRepository.findByEmailIgnoreCase(customerCO.getEmail()));
      return new ResponseEntity<>(
          new MessageResponseEntity<>(customerCO, HttpStatus.CREATED,
              "successful Registration".toUpperCase())
          , HttpStatus.CREATED);
    }
  }

  /*
    Method to generate a token to be mailed to customer's email id
   */
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

  /*
    Method to validate customer's email and activate it's account
   */
  public ResponseEntity<MessageResponseEntity> activateCustomer(String confirmationToken) {

    ConfirmationToken token = confirmationTokenRepository
        .findByConfirmationToken(confirmationToken);

    Date date = new Date();

    if (token != null && (date.getHours() - token.getCreatedDate().getHours()) < 3) {

      Customer customer = (Customer) customerRepository
          .findByEmailIgnoreCase(token.getUser().getEmail());
      if (customer.isActive()) {
        return new ResponseEntity<>(
            new MessageResponseEntity(HttpStatus.ACCEPTED, "Account Already Active".toUpperCase())
            , HttpStatus.ACCEPTED);
      }

      customer.setActive(true);
      customerRepository.save(customer);
      confirmationTokenRepository.delete(token);

      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(customer.getEmail());
          mailMessage.setSubject("Account activated");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage.setText("Congratulations!!! Your account has been activated");

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println(
              "Failed to send email to: " + customer.getEmail() + " reason: " + e.getMessage());
        }
      });

      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.ACCEPTED, "Account Verified".toUpperCase())
          , HttpStatus.ACCEPTED);
    } else if (token != null && (date.getHours() - token.getCreatedDate().getHours()) > 3) {
      Customer customer = customerRepository
          .findById(
              token
                  .getUser()
                  .getId())
          .get();

      confirmationTokenRepository.delete(token);
      generateToken(customer);
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST, "Token time Expired!! "
              + " New Token sent to registered email Id".toUpperCase())
          , HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST,
              "The link is invalid or broken!".toUpperCase()),
          HttpStatus.BAD_REQUEST);
    }
  }

  /*
    Method to resend activation Link with new Activation Token and deletes previously created token.
   */
  public ResponseEntity<MessageResponseEntity> resendActivationToken(String email) {

    User user = userRepository.findByEmailIgnoreCase(email);
    if (user == null) {
      throw new UserNotFoundException("User does not exists");
    } else if (user != null) {
      if (confirmationTokenRepository.findAllByUserId(user.getId()) != null) {
        confirmationTokenRepository
            .findAllByUserId(user.getId())
            .forEach(e -> confirmationTokenRepository.deleteById(e.getTokenid()));
      }
      generateToken(user);

      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.OK, "Activation Link Resent".toUpperCase())
          , HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.BAD_REQUEST,
            "Some error occurred, Please contact the Admin".toUpperCase())
        , HttpStatus.BAD_REQUEST);

  }

  /*
    Method to add Address
   */
  public ResponseEntity<MessageResponseEntity> addAddress(String email, AddressCO addressCO) {

    ModelMapper modelMapper = new ModelMapper();
    Address address = new Address();

    Customer customer = customerRepository.findByEmailIgnoreCase(email);
    address.setUser(customer);
    modelMapper.map(addressCO, address);
    customer.getAddress().add(address);
    customerRepository.save(customer);

    return new ResponseEntity<>(
        new MessageResponseEntity(addressCO, HttpStatus.OK)
        , HttpStatus.OK);
  }

//---------------------------------------------------READ------------------------------------------------------------

  /*
    Fetches customer details and sends back.
   */
  public ResponseEntity<MessageResponseEntity> showProfile(String email) {
    Customer customer = customerRepository.findByEmailIgnoreCase(email);
    CustomerDTO customerDTO = new CustomerDTO();
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(customer, customerDTO);

    return new ResponseEntity<>(
        new MessageResponseEntity<>(customerDTO, HttpStatus.OK)
        , HttpStatus.OK);
  }

  /*
    Fetches List of Addresses of the customer
   */
  public ResponseEntity<MessageResponseEntity> showAddresses(String email) {
    ModelMapper modelMapper = new ModelMapper();
    List<AddressDTO> addressDTOList = new ArrayList<>();
    Customer customer = customerRepository.findByEmailIgnoreCase(email);

    customer.getAddress().forEach(
        address -> {
          AddressDTO addressDTO = new AddressDTO();
          modelMapper.map(address, addressDTO);
          addressDTOList.add(addressDTO);
        }
    );

    return new ResponseEntity<>(
        new MessageResponseEntity(addressDTOList, HttpStatus.OK)
        , HttpStatus.OK);
  }
}
