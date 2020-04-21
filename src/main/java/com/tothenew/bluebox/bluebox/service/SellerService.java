package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.SellerCO;
import com.tothenew.bluebox.bluebox.co.SellerProfileUpdate;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.dto.SellerDTO;
import com.tothenew.bluebox.bluebox.enitity.product.Category;
import com.tothenew.bluebox.bluebox.enitity.user.Address;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.UserAlreadyExistsException;
import com.tothenew.bluebox.bluebox.repository.CategoryMetadataFieldValuesRespository;
import com.tothenew.bluebox.bluebox.repository.CategoryRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

  @Autowired
  private SellerRepository sellerRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryMetadataFieldValuesRespository categoryMetadataFieldValuesRespository;

  //---------------------------------------------------CREATE------------------------------------------------------------
  /*
    Method for seller's registration
   */
  public ResponseEntity<MessageResponseEntity> registerSeller(SellerCO sellerCO) {

    List<Role> defaultRole = new ArrayList<>();
    Role role = roleRepository.findByAuthority("ROLE_SELLER");
    defaultRole.add(role);
    Date date = new Date();

    User existingUser = userRepository.findByEmailIgnoreCase(sellerCO.getEmail());

    if (existingUser != null) {

      throw new UserAlreadyExistsException("This email already exists!".toUpperCase());
    } else {
      Address address = new Address();
      BeanUtils.copyProperties(sellerCO.getCompanyAddress(), address);
      HashSet<Address> addresses = new HashSet<>();
      addresses.add(address);

      Seller seller = new Seller();
      BeanUtils.copyProperties(sellerCO, seller);
      seller.setCreatedDate(date);
      seller.setUpdatedDate(date);
      seller.setPassword(passwordEncoder.encode(sellerCO.getPassword()));
      seller.setRoles(defaultRole);
      seller.setAddress(addresses);
      sellerRepository.save(seller);

      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.CREATED,
              "Account Created. Please wait for the Admin to Activate the acoount".toUpperCase())
          , HttpStatus.CREATED);
    }
  }

//---------------------------------------------------READ------------------------------------------------------------

  /*
    Fetches Seller details and sends back.
   */
  public ResponseEntity<MessageResponseEntity> showProfile(String email) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    SellerDTO sellerDTO = new SellerDTO();
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(seller, sellerDTO);
    sellerDTO.setAddress(seller.getAddress());

    return new ResponseEntity<>(
        new MessageResponseEntity<>(sellerDTO, HttpStatus.OK)
        , HttpStatus.OK);
  }

//---------------------------------------------------UPDATE------------------------------------------------------------

  /*
    Update Seller profile
   */
  public ResponseEntity<MessageResponseEntity> updateProfile(String email,
      SellerProfileUpdate sellerProfileUpdate) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(sellerProfileUpdate, seller);
    sellerRepository.save(seller);

    return new ResponseEntity<>(
        new MessageResponseEntity(sellerProfileUpdate, HttpStatus.OK)
        , HttpStatus.OK
    );
  }

//---------------------------------------------------SELLER CATEGORY------------------------------------------------------------

  /*
   Method to list All Category
  */
  public ResponseEntity<MessageResponseEntity> listAllCategory(Integer pageNo, Integer pageSize,
      String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

    Map<Category, Object> response = new HashMap<>();

    List<Category> categoryList = categoryRepository.findAllCategory(paging);
    categoryList.forEach(category -> {
      List<Map<Object, Object>> metadatavalueList = categoryMetadataFieldValuesRespository
          .findByCategoryId(category.getId());
      response.put(category, metadatavalueList);
    });

    return new ResponseEntity<>(
        new MessageResponseEntity(response, HttpStatus.OK)
        , HttpStatus.OK);
  }

}
