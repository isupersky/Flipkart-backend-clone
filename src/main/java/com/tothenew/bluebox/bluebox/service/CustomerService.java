package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.AddressCO;
import com.tothenew.bluebox.bluebox.co.CustomerCO;
import com.tothenew.bluebox.bluebox.co.CustomerProfileUpdateCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.dto.AddressDTO;
import com.tothenew.bluebox.bluebox.dto.CustomerDTO;
import com.tothenew.bluebox.bluebox.enitity.user.Address;
import com.tothenew.bluebox.bluebox.enitity.user.ConfirmationToken;
import com.tothenew.bluebox.bluebox.enitity.user.Customer;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.AccessNotAllowedExeption;
import com.tothenew.bluebox.bluebox.exception.CategoryNotFoundException;
import com.tothenew.bluebox.bluebox.exception.UserAlreadyExistsException;
import com.tothenew.bluebox.bluebox.exception.UserNotFoundException;
import com.tothenew.bluebox.bluebox.repository.AddressRepository;
import com.tothenew.bluebox.bluebox.repository.CategoryMetadataFieldValuesRespository;
import com.tothenew.bluebox.bluebox.repository.CategoryRepository;
import com.tothenew.bluebox.bluebox.repository.ConfirmationTokenRepository;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  @Autowired
  UserService userService;

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  CategoryMetadataFieldValuesRespository categoryMetadataFieldValuesRespository;

  @Autowired
  ProductRepository productRepository;

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
    customer.setUpdatedDate(new Date());
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

//---------------------------------------------------UPDATE------------------------------------------------------------


  /*
    Update Customer profile
   */
  public ResponseEntity<MessageResponseEntity> updateProfile(String email,
      CustomerProfileUpdateCO customerProfileUpdateCO) {
    Customer customer = customerRepository.findByEmailIgnoreCase(email);
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(customerProfileUpdateCO, customer);
    customerRepository.save(customer);

    return new ResponseEntity<>(
        new MessageResponseEntity(customerProfileUpdateCO, HttpStatus.OK)
        , HttpStatus.OK
    );
  }

//---------------------------------------------------DELETE------------------------------------------------------------

  /*
    Deletes an address corresponding to given id.
   */
  public ResponseEntity<MessageResponseEntity> deleteAddress(String email, Long addressId) {

    Customer customer = customerRepository.findByEmailIgnoreCase(email);
    Optional<Address> optionalAddress = addressRepository.findById(addressId);

    if (optionalAddress.isPresent()) {
      Address address = optionalAddress.get();
      if (address.getUser().getId() == customer.getId()) {

        addressRepository.deleteByAddressID(addressId);

        return new ResponseEntity<>(
            new MessageResponseEntity(HttpStatus.OK, "Address deleted successfully".toUpperCase())
            , HttpStatus.OK
        );
      } else if (address.getUser().getId() != customer.getId()) {
        throw new AccessNotAllowedExeption("Invalid Request");
      }
    } else {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.NOT_FOUND, "invalid address id".toUpperCase())
          , HttpStatus.NOT_FOUND
      );
    }

    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.OK)
        , HttpStatus.OK
    );
  }

//---------------------------------------------------CUSTOMER-CATEGORY------------------------------------------------------------

  /*
     Return List all root level categroies if no ID is passed,
      else list of all immediate child nodes of passed category ID
   */
  public ResponseEntity<MessageResponseEntity> listAllCustomerCategories(Long id) {

    boolean exists;

    if (id != null) {
      exists = categoryRepository.existsById(id);

      if (exists) {
        List<Map<Object, Object>> resposeList = categoryRepository.findByParentCategory(id);

        return new ResponseEntity<>(
            new MessageResponseEntity(resposeList, HttpStatus.OK)
            , HttpStatus.OK);
      } else {
        throw new CategoryNotFoundException("Invalid category Id");
      }
    } else {
      List<Map<Object, Object>> resposeList = categoryRepository.findByCategoryIsNull();

      return new ResponseEntity<>(
          new MessageResponseEntity(resposeList, HttpStatus.OK)
          , HttpStatus.OK);
    }
  }

  /*@UNTESTED
  Returns-
    1. All metadata field along with possible values for that category
    2. Brands list compiled from associated products of that node
    3. Minimum and Maximum price possible from lowest and highest valued product under that category"
   */
  public ResponseEntity<MessageResponseEntity> getFilterDetails(Long id) {

    boolean exists = categoryRepository.existsById(id);

    if (!exists) {
      throw new CategoryNotFoundException("invalid Category Id");
    }

    List<Object> responseList = new ArrayList();
//    All metadata field along with possible values for that category
    responseList.add(categoryMetadataFieldValuesRespository.findByCategoryId(id));
    responseList.add(productRepository.findAllBrandsByCategoryId(id));
    responseList.add(productRepository.findMinimum(id));
    responseList.add(productRepository.findMaximum(id));

    return new ResponseEntity<>(
        new MessageResponseEntity<>(responseList, HttpStatus.OK)
        , HttpStatus.OK);

  }
}
