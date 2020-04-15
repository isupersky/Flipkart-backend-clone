package com.tothenew.bluebox.bluebox.service;

import com.tothenew.bluebox.bluebox.co.CategoryCO;
import com.tothenew.bluebox.bluebox.co.CategoryMetadataFieldCO;
import com.tothenew.bluebox.bluebox.co.CategoryMetadataFieldValueCO;
import com.tothenew.bluebox.bluebox.co.CategoryUpdateCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.enitity.product.Category;
import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataField;
import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValues;
import com.tothenew.bluebox.bluebox.enitity.product.CategoryMetadataFieldValuesId;
import com.tothenew.bluebox.bluebox.enitity.user.User;
import com.tothenew.bluebox.bluebox.exception.CategoryAlreadyExistsException;
import com.tothenew.bluebox.bluebox.exception.CategoryMetadataFieldValueExistsException;
import com.tothenew.bluebox.bluebox.exception.CategoryNotFoundException;
import com.tothenew.bluebox.bluebox.exception.MetadataFieldExistsException;
import com.tothenew.bluebox.bluebox.exception.MetadataFieldNotFoundException;
import com.tothenew.bluebox.bluebox.repository.CategoryMetadataFieldRepository;
import com.tothenew.bluebox.bluebox.repository.CategoryMetadataFieldValuesRespository;
import com.tothenew.bluebox.bluebox.repository.CategoryRepository;
import com.tothenew.bluebox.bluebox.repository.CustomerRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import com.tothenew.bluebox.bluebox.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
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

  @Autowired
  CategoryMetadataFieldRepository categoryMetadataFieldRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  CategoryMetadataFieldValuesRespository categoryMetadataFieldValuesRespository;

  //-------------------------------------------GET LIST OF USER---------------------------------------
  /*
    Method to get the list of all Customers present in the system.
   */
  public ResponseEntity<MessageResponseEntity> getAllCustomers() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    List<Object> customerList = customerRepository.findAllCustomers(pageable);

    return new ResponseEntity<>(
        new MessageResponseEntity<>(customerList, HttpStatus.OK),
        HttpStatus.OK);
  }

  /*
    Method to get the list of all Sellers present in the system.
   */
  public ResponseEntity<MessageResponseEntity> getAllSellers() {

    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    List<Object> sellerList = sellerRepository.findAllSellers(pageable);

    return new ResponseEntity<>(
        new MessageResponseEntity<>(sellerList, HttpStatus.OK)
        , HttpStatus.OK);
  }


  //------------------------------------------ACTIVATE/DEACTIVATE-------------------------------------
  /*
    Method activates the user account for provide user id and triggers a mail to user about the activation.
   */
  public ResponseEntity<MessageResponseEntity> activateUser(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isActive()) {

        return new ResponseEntity<>(
            new MessageResponseEntity<>(HttpStatus.OK, "user activated")
            , HttpStatus.OK);
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

      return new ResponseEntity<>(
          new MessageResponseEntity<>(HttpStatus.OK, "user activated")
          , HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new MessageResponseEntity<>(HttpStatus.NOT_FOUND, "User not present")
        , HttpStatus.NOT_FOUND);
  }


  /*
   Method deactivates the user account for provide user id and triggers a mail to user about the activation.
  */
  public ResponseEntity<MessageResponseEntity> deactivateUser(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isActive()) {

        return new ResponseEntity<>(
            new MessageResponseEntity<>(HttpStatus.OK, "user deactivated")
            , HttpStatus.OK);
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

      return new ResponseEntity<>(
          new MessageResponseEntity<>(HttpStatus.OK, "user deactivated")
          , HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new MessageResponseEntity<>(HttpStatus.NOT_FOUND, "User not present")
        , HttpStatus.NOT_FOUND);
  }


  //---------------------------------------------CATEGORY API-----------------------------------------
  /*
    Method to Add Category-Metadata-Field
   */
  public ResponseEntity<MessageResponseEntity> addMetadataField(
      CategoryMetadataFieldCO categoryMetadataFieldCO) {

    ModelMapper modelMapper = new ModelMapper();
    CategoryMetadataField categoryMetadataField = new CategoryMetadataField();

    modelMapper.map(categoryMetadataFieldCO, categoryMetadataField);
    String name = categoryMetadataField.getName();

    CategoryMetadataField savedCategoryMetadataField = categoryMetadataFieldRepository
        .findByNameIgnoreCase(name);
    if (savedCategoryMetadataField == null) {
      categoryMetadataFieldRepository.save(categoryMetadataField);

      return new ResponseEntity<>(
          new MessageResponseEntity<>(categoryMetadataFieldCO, HttpStatus.CREATED)
          , HttpStatus.CREATED);
    } else {
      throw new MetadataFieldExistsException(
          "The Metadata Field Already Exists: " + categoryMetadataField.getName());
    }
  }


  /*
    Method to List All Category-Metadata-Field
   */
  public ResponseEntity<MessageResponseEntity> listAllMetadata(Integer pageNo, Integer pageSize,
      String sortBy) {

    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
    Page<CategoryMetadataField> pagedResult = categoryMetadataFieldRepository.findAll(paging);

    return new ResponseEntity<>(new MessageResponseEntity(pagedResult.getContent(), HttpStatus.OK),
        HttpStatus.OK);
  }

  /*
    Method to Add New Category
   */
  public ResponseEntity<Object> addCategory(CategoryCO categoryCO) {

    Category oldCategory = categoryRepository
        .findByNameAndParent(categoryCO.getName(), categoryCO.getParentId());
    if (oldCategory != null) {

      throw new CategoryAlreadyExistsException("Category already exists ".toUpperCase());
    }

    if (categoryCO.getParentId() == null) {
      Category category = new Category();
      category.setLeafNode(false);
      category.setName(categoryCO.getName());
      categoryRepository.save(category);

      return new ResponseEntity<>(
          new MessageResponseEntity<>(categoryCO, HttpStatus.CREATED)
          , HttpStatus.CREATED);
    } else {
      Optional<Category> optionalParentCategory = categoryRepository
          .findById(categoryCO.getParentId());
      if (optionalParentCategory.isPresent()) {
        Category parentCategory = optionalParentCategory.get();
        parentCategory.setLeafNode(false);
        Category category = new Category();
        category.setLeafNode(true);
        category.setName(categoryCO.getName());
        category.setParentId(parentCategory);

        categoryRepository.save(parentCategory);
        categoryRepository.save(category);

        return new ResponseEntity<>(
            new MessageResponseEntity<>(categoryCO, HttpStatus.CREATED)
            , HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(
            new MessageResponseEntity<>(HttpStatus.NOT_FOUND,
                "Parent Category Not Found!".toUpperCase())
            , HttpStatus.NOT_FOUND);
      }
    }
  }

  /*
    Method to list All Category
   */
  public ResponseEntity<MessageResponseEntity> listAllCategory(Integer pageNo, Integer pageSize,
      String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
    List<Category> categoryList = categoryRepository.findAllCategory(paging);
    return new ResponseEntity<>(
        new MessageResponseEntity(categoryList, HttpStatus.OK)
        , HttpStatus.OK);
  }

  /*
    method to get details of a given category by Id
   */
  public ResponseEntity<MessageResponseEntity> getCategoryDetails(Long id) {

    Map<String, Object> response = new HashMap<>();
    Optional<Category> optionalCategory = categoryRepository.findById(id);

    if (optionalCategory.isPresent()) {
      Category category = optionalCategory.get();
      List<Object> subCategory = categoryRepository.findAllChildren(id);

      response.put("Sub-categories".toUpperCase(), subCategory);
      response.put("Category Datail".toUpperCase(), category);

      return new ResponseEntity<>(
          new MessageResponseEntity(response, HttpStatus.OK)
          , HttpStatus.OK);
    }

    throw new CategoryNotFoundException("Invalid Category Id".toUpperCase());

  }

  /*
    Method to update a Category
   */
  public ResponseEntity<MessageResponseEntity> updateCategory(CategoryUpdateCO categoryUpdateCO) {
    Optional<Category> optionalCategory = categoryRepository.findById(categoryUpdateCO.getId());

    if (!optionalCategory.isPresent()) {
      throw new CategoryNotFoundException("No Such Category Exists".toUpperCase());
    } else {
      Category savedCategory = optionalCategory.get();

      Category oldCategory = categoryRepository
          .findByNameAndParent(categoryUpdateCO.getName(),
              savedCategory
                  .getParentId()
                  .getId());
      if (oldCategory != null) {
        throw new CategoryAlreadyExistsException(
            "Category with similar name already exists".toUpperCase());
      }

      ModelMapper mapper = new ModelMapper();
      mapper.map(categoryUpdateCO, savedCategory);
      categoryRepository.save(savedCategory);

      return new ResponseEntity<>(
          new MessageResponseEntity<>(categoryUpdateCO, HttpStatus.OK)
          , HttpStatus.OK);
    }

  }

  /*
    Method To add category-metadata-field-values
   */
  public ResponseEntity<MessageResponseEntity> addCategoryMetadataFieldValues(
      CategoryMetadataFieldValueCO categoryMetadataFieldValueCO) {

    Optional<Category> optionalCategory = categoryRepository
        .findById(categoryMetadataFieldValueCO.getCategoryId());
    if (!optionalCategory.isPresent()) {
      throw new CategoryNotFoundException("Category does not exists");
    }

    Optional<CategoryMetadataField> optionalCategoryMetadataField = categoryMetadataFieldRepository
        .findById(categoryMetadataFieldValueCO.getCategoryMetadataFieldId());
    if (!optionalCategoryMetadataField.isPresent()) {
      throw new MetadataFieldNotFoundException("Metadata field does not exists");
    }

    CategoryMetadataFieldValuesId categoryMetadataFieldValuesId =
        new CategoryMetadataFieldValuesId(optionalCategoryMetadataField.get().getId(),
            optionalCategory.get().getId());

    Optional<CategoryMetadataFieldValues> optionalCategoryMetadataFieldValues =
        categoryMetadataFieldValuesRespository.findById(categoryMetadataFieldValuesId);

    if (optionalCategoryMetadataFieldValues.isPresent()) {

      throw new CategoryMetadataFieldValueExistsException("Category meta data already exists ");
    }

    if (!optionalCategoryMetadataFieldValues.isPresent()) {
      CategoryMetadataFieldValues categoryMetadataFieldValues =
          new CategoryMetadataFieldValues();
      categoryMetadataFieldValues.setValue(categoryMetadataFieldValueCO.getValue());
      categoryMetadataFieldValues.setCategory(optionalCategory.get());
      categoryMetadataFieldValues.setCategoryMetadataField(optionalCategoryMetadataField.get());
      categoryMetadataFieldValuesRespository.save(categoryMetadataFieldValues);

      return new ResponseEntity<>(
          new MessageResponseEntity(categoryMetadataFieldValueCO, HttpStatus.OK)
          , HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new MessageResponseEntity(HttpStatus.OK, "Something Went Wrong")
        , HttpStatus.OK);
  }

  /*
    Method to update c
   */
  public ResponseEntity<MessageResponseEntity> updateCategoryMetadataFieldvalues(
      CategoryMetadataFieldValueCO categoryMetadataFieldValueCO) {

    Optional<Category> optionalCategory = categoryRepository
        .findById(categoryMetadataFieldValueCO.getCategoryId());
    if (!optionalCategory.isPresent()) {
      throw new CategoryNotFoundException("Category does not exists");
    }

    Optional<CategoryMetadataField> optionalCategoryMetadataField = categoryMetadataFieldRepository
        .findById(categoryMetadataFieldValueCO.getCategoryMetadataFieldId());
    if (!optionalCategoryMetadataField.isPresent()) {
      throw new MetadataFieldNotFoundException("Metadata field does not exists");
    }

    CategoryMetadataFieldValuesId categoryMetadataFieldValuesId =
        new CategoryMetadataFieldValuesId(optionalCategoryMetadataField.get().getId(),
            optionalCategory.get().getId());

    Optional<CategoryMetadataFieldValues> optionalCategoryMetadataFieldValues =
        categoryMetadataFieldValuesRespository.findById(categoryMetadataFieldValuesId);

    CategoryMetadataFieldValues categoryMetadataFieldValues = optionalCategoryMetadataFieldValues
        .get();

    if (categoryMetadataFieldValues.getValue()
        .contains(categoryMetadataFieldValueCO.getValue())) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.OK, "Already in the System")
          , HttpStatus.OK);
    }

    String newValue = categoryMetadataFieldValues.getValue()
        .concat("," + categoryMetadataFieldValueCO.getValue());
    categoryMetadataFieldValues.setValue(newValue);
    categoryMetadataFieldValuesRespository.save(categoryMetadataFieldValues);

    categoryMetadataFieldValueCO.setValue(newValue);
    return new ResponseEntity<>(
        new MessageResponseEntity(categoryMetadataFieldValueCO, HttpStatus.OK, "Updated")
        , HttpStatus.OK);
  }


}
