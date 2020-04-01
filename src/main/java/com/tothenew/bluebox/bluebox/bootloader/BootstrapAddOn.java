package com.tothenew.bluebox.bluebox.bootloader;

import com.tothenew.bluebox.bluebox.enitity.product.Category;
import com.tothenew.bluebox.bluebox.enitity.user.Role;
import com.tothenew.bluebox.bluebox.repository.CategoryRepository;
import com.tothenew.bluebox.bluebox.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BootstrapAddOn {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  RoleRepository roleRepository;


  public void createRoles() {

    Role role = new Role();
    role.setAuthority("ROLE_ADMIN");
    Role role2 = new Role();
    role2.setAuthority("ROLE_CUSTOMER");
    Role role3 = new Role();
    role3.setAuthority("ROLE_SELLER");
    roleRepository.save(role);
    roleRepository.save(role2);
    roleRepository.save(role3);
  }


  public void createCategory() {
//------------ROOT CATEGORY------------------
    Category fashion = new Category();
    fashion.setName("FASHION");
    categoryRepository.save(fashion);

    Category electronics = new Category();
    electronics.setName("ELECTRONICS");
    categoryRepository.save(electronics);

    Category books = new Category();
    books.setName("BOOKS");
    categoryRepository.save(books);

//------------SUB CATEGORY LEVEL-1------------------
//-------------SUBCATEGORY:FASHION------------------
    Category men = new Category();
    men.setName("MEN");
    men.setParentId(fashion);
    categoryRepository.save(men);

    Category women = new Category();
    women.setName("WOMEN");
    women.setParentId(fashion);
    categoryRepository.save(women);

    Category kids = new Category();
    kids.setName("KIDS");
    kids.setParentId(fashion);
    categoryRepository.save(kids);

//-------------SUBCATEGORY:ELECTRONICS------------------
    Category mobileAndAccessory = new Category();
    mobileAndAccessory.setName("MOBILE AND ACCESSORY");
    mobileAndAccessory.setParentId(electronics);
    categoryRepository.save(mobileAndAccessory);

    Category cameraAndAccessory = new Category();
    cameraAndAccessory.setName("CAMERA AND ACCESSORY");
    cameraAndAccessory.setParentId(electronics);
    categoryRepository.save(cameraAndAccessory);

//-------------SUBCATEGORY:BOOKS------------------
    Category actionAndAdventure = new Category();
    actionAndAdventure.setName("ACTION AND ADVENTURE");
    actionAndAdventure.setParentId(books);
    categoryRepository.save(actionAndAdventure);

    Category biography = new Category();
    biography.setName("BIOGRAPHY");
    biography.setParentId(books);
    biography.setLeafNode(true);
    categoryRepository.save(biography);

//------------SUB CATEGORY LEVEL-2------------------
//-------------SUBCATEGORY:MEN------------------
    Category menCloth = new Category();
    menCloth.setName("MEN CLOTH");
    menCloth.setParentId(men);
    menCloth.setLeafNode(true);
    categoryRepository.save(menCloth);

    Category menShoe = new Category();
    menShoe.setName("MEN SHOE");
    menShoe.setParentId(men);
    menShoe.setLeafNode(true);
    categoryRepository.save(menShoe);

    Category menWatch = new Category();
    menWatch.setName("MEN WATCH");
    menWatch.setParentId(men);
    menWatch.setLeafNode(true);
    categoryRepository.save(menWatch);

//-------------SUBCATEGORY:WOMEN------------------
    Category womenCloth = new Category();
    womenCloth.setName("WOMEN CLOTH");
    womenCloth.setParentId(women);
    womenCloth.setLeafNode(true);
    categoryRepository.save(womenCloth);

    Category womenShoe = new Category();
    womenShoe.setName("WOMEN SHOE");
    womenShoe.setParentId(women);
    womenShoe.setLeafNode(true);
    categoryRepository.save(womenShoe);

    Category womenWatch = new Category();
    womenWatch.setName("WOMEN WATCH");
    womenWatch.setParentId(women);
    womenWatch.setLeafNode(true);
    categoryRepository.save(womenWatch);

//-------------SUBCATEGORY:KIDS------------------
    Category kidsCloth = new Category();
    kidsCloth.setName("KIDS CLOTH");
    kidsCloth.setParentId(kids);
    kidsCloth.setLeafNode(true);
    categoryRepository.save(kidsCloth);

    Category kidsShoe = new Category();
    kidsShoe.setName("KIDS SHOE");
    kidsShoe.setParentId(kids);
    kidsShoe.setLeafNode(true);
    categoryRepository.save(kidsShoe);

    Category kidsWatch = new Category();
    kidsWatch.setName("KIDS WATCH");
    kidsWatch.setParentId(kids);
    kidsWatch.setLeafNode(true);
    categoryRepository.save(kidsWatch);

//-------------SUBCATEGORY:MOBILE AND ACCESSORY------------------
    Category mobile = new Category();
    mobile.setName("MOBILE");
    mobile.setParentId(mobileAndAccessory);
    mobile.setLeafNode(true);
    categoryRepository.save(mobile);

    Category mobileAccessory = new Category();
    mobile.setName("MOBILE ACCESSORY");
    mobile.setParentId(mobileAndAccessory);
    mobile.setLeafNode(true);
    categoryRepository.save(mobile);

//-------------SUBCATEGORY:CAMERA AND ACCESSORY------------------
    Category camera = new Category();
    camera.setName("CAMERA");
    camera.setParentId(cameraAndAccessory);
    camera.setLeafNode(true);
    categoryRepository.save(camera);

    Category cameraAccessory = new Category();
    camera.setName("CAMERA ACCESSORY");
    camera.setParentId(mobileAndAccessory);
    camera.setLeafNode(true);
    categoryRepository.save(camera);

//-------------SUBCATEGORY:ACTION AND ADVENTURE------------------
    Category actionBook = new Category();
    actionBook.setName("ACTION BOOK");
    actionBook.setParentId(actionAndAdventure);
    actionBook.setLeafNode(true);
    categoryRepository.save(actionBook);

    Category adventureBook = new Category();
    adventureBook.setName("ADVENTURE BOOK");
    adventureBook.setParentId(actionAndAdventure);
    adventureBook.setLeafNode(true);
    categoryRepository.save(adventureBook);
  }

}
