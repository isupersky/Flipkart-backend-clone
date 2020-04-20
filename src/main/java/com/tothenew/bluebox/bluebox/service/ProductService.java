package com.tothenew.bluebox.bluebox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tothenew.bluebox.bluebox.co.ProductCO;
import com.tothenew.bluebox.bluebox.co.ProductUpdateCO;
import com.tothenew.bluebox.bluebox.co.ProductVariationCO;
import com.tothenew.bluebox.bluebox.configuration.MessageResponseEntity;
import com.tothenew.bluebox.bluebox.dto.ProductDTO;
import com.tothenew.bluebox.bluebox.dto.ProductVariationDTO;
import com.tothenew.bluebox.bluebox.enitity.product.Category;
import com.tothenew.bluebox.bluebox.enitity.product.Product;
import com.tothenew.bluebox.bluebox.enitity.product.ProductVariation;
import com.tothenew.bluebox.bluebox.enitity.user.Seller;
import com.tothenew.bluebox.bluebox.exception.CategoryNotFoundException;
import com.tothenew.bluebox.bluebox.exception.MailSendFailedException;
import com.tothenew.bluebox.bluebox.exception.ProductAlreadyExistsException;
import com.tothenew.bluebox.bluebox.exception.ProductNotFoundException;
import com.tothenew.bluebox.bluebox.exception.ProductVariationNotFoundException;
import com.tothenew.bluebox.bluebox.repository.CategoryRepository;
import com.tothenew.bluebox.bluebox.repository.ProductRepository;
import com.tothenew.bluebox.bluebox.repository.ProductVariationRepository;
import com.tothenew.bluebox.bluebox.repository.SellerRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SellerRepository sellerRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductVariationRepository productVariationRepository;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  private EmailSenderService emailSenderService;

  @Autowired
  private ImageUploaderService imageUploaderService;


  //-----------------------------------------SELLER API-----------------------------------------------
  /*
    Adds new product to the product List
   */
  public ResponseEntity<MessageResponseEntity> addProduct(String email, ProductCO productCO) {

    boolean exists = categoryRepository.existsById(productCO.getCategoryId());
    if (!exists) {
      throw new CategoryNotFoundException("Category does not exists");
    }

    Category category = categoryRepository.findById(productCO.getCategoryId()).get();
    if (!category.isLeafNode()) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.BAD_REQUEST,
              "Please enter a leaf category id".toUpperCase())
          , HttpStatus.BAD_REQUEST);
    }

    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Product product = new Product();
    ModelMapper modelMapper = new ModelMapper();

    List<Product> productList = productRepository.findAllBySellerUserId(seller);

    if (productList != null) {
      productList.forEach(e -> {
        if (e.getBrand().equalsIgnoreCase(productCO.getBrand())
            && e.getCategoryId().getId() == productCO.getCategoryId()
            && e.getBrand().equalsIgnoreCase(productCO.getBrand())) {

          throw new ProductAlreadyExistsException("Product Alread Exists");
        }

      });

    }

    modelMapper.map(productCO, product);
    product.setCategoryId(category);
    product.setSellerUserId(seller);

    productRepository.save(product);

    return new ResponseEntity<MessageResponseEntity>(
        new MessageResponseEntity(productCO, HttpStatus.CREATED, "Product Added".toUpperCase()),
        HttpStatus.CREATED);
  }

  /*
    Get Details Of One Product by Id
   */
  public ResponseEntity<Object> getProductByIdSeller(String email, Long productId) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Optional<Product> optionalProduct = productRepository.findById(productId);

    //if product does not exists
    if (!optionalProduct.isPresent()) {
      throw new ProductNotFoundException("Invalid Product Id");
    }

    Product product = optionalProduct.get();
    //if product is not added by the seller
    if (product.getSellerUserId().getId() != seller.getId()) {
      throw new ProductNotFoundException("Invalid Product Id");
    }
    //if product was deleted
    if (product.isDeleted()) {
      throw new ProductNotFoundException("Product Deleted");
    }

    ProductDTO productDTO = new ProductDTO();
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.map(product, productDTO);
    productDTO.setCategoryDetail(product.getCategoryId());

    return new ResponseEntity<>(new MessageResponseEntity<>(productDTO, HttpStatus.OK),
        HttpStatus.OK);

  }


  /* @Untested
    Fetch Details Of All Products
   */
  public ResponseEntity<Object> listAllProduct(String email, Integer pageNo, Integer pageSize,
      String sortBy) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
    List<Product> productList = productRepository.listAllProduct(paging, seller.getId());
    if (!productList.isEmpty()) {
      return new ResponseEntity<>("The Product Does Not Exist!", HttpStatus.BAD_REQUEST);
    }

    List<ProductDTO> responseProductDTO = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();
    productList.forEach(e -> {

      if (!e.isDeleted()) {
        ProductDTO productDTO = new ProductDTO();
        modelMapper.map(e, productDTO);
        productDTO.setCategoryDetail(e.getCategoryId());
        responseProductDTO.add(productDTO);

      }
    });
    return new ResponseEntity<>(new MessageResponseEntity<>(responseProductDTO, HttpStatus.OK),
        HttpStatus.OK);
  }

  /*@Untested
    METHOD to Delete One Product
   */
  public ResponseEntity<Object> deleteProduct(String email, Long id) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotFoundException("Invalid product Id");
    }
    Product product = optionalProduct.get();

    if (product.isDeleted()) {
      return new ResponseEntity<>(
          new MessageResponseEntity<>("Product Successfully Deleted!", HttpStatus.OK)
          , HttpStatus.OK);
    }

    if (product.getSellerUserId().getId() != seller.getId()) {
      throw new ProductNotFoundException("No such product available");
    }

    product.setDeleted(true);
    productRepository.save(product);

    return new ResponseEntity<>(
        new MessageResponseEntity<>("Product Successfully Deleted!", HttpStatus.OK)
        , HttpStatus.OK);
  }

  /*@Untested
    METHOD to Update a Product
   */
  public ResponseEntity<Object> updateOneProduct(String email, Long id,
      ProductUpdateCO productUpdateCO) {
    Seller seller = sellerRepository.findByEmailIgnoreCase(email);
    Optional<Product> optionalProduct = productRepository.findById(id);

    if (!optionalProduct.isPresent()) {      //product ID exist or not
      throw new ProductNotFoundException("Invalid Product Id");
    }
    Product product = optionalProduct.get();
    if (product.getSellerUserId().getId() != seller.getId()) {
      throw new ProductNotFoundException("No such product exists");
    }
    ModelMapper mapper = new ModelMapper();
    mapper.map(productUpdateCO, product);
    productRepository.save(product);

    return new ResponseEntity<>(
        new MessageResponseEntity<>("Product Successfully Updated!", HttpStatus.OK)
        , HttpStatus.OK);
  }


  /*
    Adds new product Variation
   */
  public ResponseEntity<MessageResponseEntity> addProductVariation(
      ProductVariationCO productVariationCO) throws JsonProcessingException {

    Long productId = productVariationCO.getProductId();
    Optional<Product> optionalProduct = productRepository.findById(productId);

    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
    } else {
      throw new ProductNotFoundException("Enter a valid Product Id");
    }

    ProductVariation productVariation = new ProductVariation();
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.map(productVariationCO, productVariation);

    productVariation.jsonMetadataStringSerialize();

    productVariationRepository.save(productVariation);

    return new ResponseEntity<>(
        new MessageResponseEntity(productVariationCO, HttpStatus.CREATED,
            "Product Variation Added Successfully".toUpperCase())
        , HttpStatus.CREATED);
  }

  /*
    Method to Add primary images to product variation
   */
  public ResponseEntity<MessageResponseEntity> addProductVariationPrimaryImages(Long id,
      MultipartFile imageFiles)
      throws IOException {
    Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(id);

    if (!optionalProductVariation.isPresent()) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.NOT_FOUND,
              "Invalid Product Variation Id".toUpperCase())
          , HttpStatus.NOT_FOUND);
    }

    ProductVariation productVariation = optionalProductVariation.get();

    String imageApi = imageUploaderService.uploadProductVariationImage(imageFiles);
    productVariation.setPrimaryImageName(imageApi);
    productVariationRepository.save(productVariation);

    return new ResponseEntity<>(
        new MessageResponseEntity(imageApi, HttpStatus.OK,
            "Image uploaded Successfully".toUpperCase())
        , HttpStatus.OK);

  }

  /*
    Method to Add secondary images to product variation
   */
  public ResponseEntity<MessageResponseEntity> addProductVariationSecondaryImages(Long id,
      MultipartFile imageFiles)
      throws IOException {
    Optional<ProductVariation> optionalProductVariation = productVariationRepository.findById(id);

    if (!optionalProductVariation.isPresent()) {
      return new ResponseEntity<>(
          new MessageResponseEntity(HttpStatus.NOT_FOUND,
              "Invalid Product Variation Id".toUpperCase())
          , HttpStatus.NOT_FOUND);
    }

    ProductVariation productVariation = optionalProductVariation.get();

    String imageApi = imageUploaderService.uploadProductVariationImage(imageFiles);
    productVariation.setSecondaryImageName(imageApi);
    productVariationRepository.save(productVariation);

    return new ResponseEntity<>(
        new MessageResponseEntity(imageApi, HttpStatus.OK,
            "Image uploaded Successfully".toUpperCase())
        , HttpStatus.OK);

  }

  /*
    Method to get details of one product variation by id
   */
  public ResponseEntity<MessageResponseEntity> getProductVariationById(String email,
      Long productVationId)
      throws IOException {

    Long sellerId = sellerRepository.findByEmailIgnoreCase(email).getId();
    Optional<ProductVariation> optionalProductVariation = productVariationRepository
        .findById(productVationId);
    ModelMapper modelMapper = new ModelMapper();

    if (!optionalProductVariation.isPresent()) {
      throw new ProductVariationNotFoundException("Invalid Product Variation Id");
    }
    ProductVariation productVariation = optionalProductVariation.get();

    if (!productVariation.getProductId().getSellerUserId().getId().equals(sellerId)) {
      throw new ProductVariationNotFoundException("Invalid Product Variation Id");
    }

    ProductVariationDTO productVariationDTO = new ProductVariationDTO();
    ProductDTO productDTO = new ProductDTO();

    productVariation.jsonMetadataStringDeserialize();
    modelMapper.map(productVariation, productVariationDTO);
    modelMapper.map(productVariation.getProductId(), productDTO);
    productVariationDTO.setProductId(productDTO);

    return new ResponseEntity<>(
        new MessageResponseEntity(productVariationDTO, HttpStatus.OK)
        , HttpStatus.OK);
  }

  /* @Untested
   view all product Variations
  */
  public ResponseEntity<MessageResponseEntity> listAllProductVariation(String email, Long productId,
      Integer pageNo, Integer pageSize,
      String sortBy) {

    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
    Long sellerId = sellerRepository.findByEmailIgnoreCase(email).getId();

    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotFoundException("enter a valid product id");
    }

    Long productsellerId = optionalProduct.get().getSellerUserId().getId();
    if (sellerId != productsellerId) {
      throw new ProductNotFoundException("No such product exists");
    }

    List responseList = productVariationRepository.findByProductId(optionalProduct.get());

    return new ResponseEntity<>(
        new MessageResponseEntity(responseList, HttpStatus.OK)
        , HttpStatus.OK);
  }

//  /*
//    Updates product Variation given its id
//   */
//  public ResponseEntity<MessageResponseEntity> updateProductVariation(String email, Long productId, ProductVariationUpdateCo productVariationUpdateCo){
//
//  }

//-----------------------------------------CUSTOMER API---------------------------------------------


  public ResponseEntity<MessageResponseEntity> getProductDetailsById(Long productId) {

    boolean exists = productRepository.existsById(productId);
    if (!exists) {
      throw new ProductNotFoundException("Invalid product Id");
    }

    Product product = productRepository.findById(productId).get();
    if (product.isDeleted() || !product.isActive()) {
      throw new ProductNotFoundException("product Not available ");
    }

    List productVariationList = productVariationRepository.findByProductId(product);
    System.out.println(productVariationList);
    if (productVariationList == null) {
      throw new ProductNotFoundException("No product Variation available ");
    }

    ModelMapper modelMapper = new ModelMapper();
    ProductDTO productDTO = new ProductDTO();
    modelMapper.map(product, productDTO);

    List<ProductVariationDTO> productVariationDTOList = new ArrayList<>();
    productVariationList.forEach(variation -> {
      ProductVariationDTO productVariationDTO = new ProductVariationDTO();
      modelMapper.map(variation, productVariationDTO);
      productVariationDTOList.add(productVariationDTO);
    });

    Map<String, Object> responseSet = new HashMap<>();
    responseSet.put("Product details :", productDTO);
    responseSet.put("Variations available", productVariationDTOList);

    return new ResponseEntity<>(
        new MessageResponseEntity(responseSet, HttpStatus.OK)
        , HttpStatus.OK);
  }

//-----------------------------------------ADMIN API------------------------------------------------

  /*
    Method to Activate A Product
   */
  public ResponseEntity<MessageResponseEntity> activateProduct(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotFoundException("Product Id Not Found!");
    }

    Product product = optionalProduct.get();
    Seller seller = product.getSellerUserId();
    boolean flag = product.isActive();
    if (!flag) {
      product.setActive(true);
      productRepository.save(product);
      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(seller.getEmail());
          mailMessage.setSubject("Product Activated");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage
              .setText("Your product " + product.getId() + " has been activated by the Admin");

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception ex) {
          throw new MailSendFailedException("Failed to Send Mail: " + seller.getEmail());
        }
      });
      return new ResponseEntity<>(
          new MessageResponseEntity<>("Product Activated Successfully!", HttpStatus.OK)
          , HttpStatus.OK);


    } else {
      return new ResponseEntity<>(
          new MessageResponseEntity<>("Product Activated", HttpStatus.OK)
          , HttpStatus.OK);
    }
  }

  /*
    Method to Activate A Product
   */
  public ResponseEntity<MessageResponseEntity> deActivateProduct(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotFoundException("Product Id Not Found!");
    }

    Product product = optionalProduct.get();
    Seller seller = product.getSellerUserId();
    boolean flag = product.isActive();
    if (flag) {
      product.setActive(false);
      productRepository.save(product);
      taskExecutor.execute(() -> {
        try {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(seller.getEmail());
          mailMessage.setSubject("Product Deactivated");
          mailMessage.setFrom("ecommerce476@gmail.com ");
          mailMessage
              .setText("Your product " + product.getId() + " has been deactivated by the Admin");

          emailSenderService.sendEmail(mailMessage);
        } catch (Exception ex) {
          throw new MailSendFailedException("Failed to Send Mail: " + seller.getEmail());
        }
      });
      return new ResponseEntity<>(
          new MessageResponseEntity<>("Product DeActivated Successfully!", HttpStatus.OK)
          , HttpStatus.OK);


    } else {
      return new ResponseEntity<>(
          new MessageResponseEntity<>("Product Activated", HttpStatus.OK)
          , HttpStatus.OK);
    }
  }
}
