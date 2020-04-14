package com.tothenew.bluebox.bluebox.co;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryMetadataFieldCO {

    @NotNull(message = "Please provide Metadata Name")
    @NotBlank(message = "Please provide valid Metadata Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
