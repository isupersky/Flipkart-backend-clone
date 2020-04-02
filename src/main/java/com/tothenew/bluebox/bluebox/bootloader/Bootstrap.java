package com.tothenew.bluebox.bluebox.bootloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

  @Autowired
  BootstrapAddOn bootstrapAddOn;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    bootstrapAddOn.createRoles();
    bootstrapAddOn.createCategory();
    bootstrapAddOn.createAdmin();

  }


}
