package com.tothenew.bluebox.bluebox.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploaderService {

  @Autowired
  @Qualifier("com.cloudinary.cloud_name")
  String mCloudName;

  @Autowired
  @Qualifier("com.cloudinary.api_key")
  String mApiKey;

  @Autowired
  @Qualifier("com.cloudinary.api_secret")
  String mApiSecret;

  /*
    Method for uploading user image
   */
  public String uploadUserImage(MultipartFile multiPartFile, String ImagePublicId)
      throws IOException {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", mCloudName,
        "api_key", mApiKey,
        "api_secret", mApiSecret));

    File file = Files.createTempFile("temp", multiPartFile.getOriginalFilename()).toFile();
    multiPartFile.transferTo(file);

    try {
      Map response = cloudinary.uploader().upload(file, ObjectUtils.asMap(
          "public_id", ImagePublicId,
          "folder", "/bluebox/userdp"));

      String imageApi = (String) response.get("url");
      return imageApi;
    } catch (Exception e) {
      throw new IOException("Please try again Later");
    }
  }


  /*
   Method to upload productVariation
   */
  public HashSet<String> uploadProductVariationImage(List<MultipartFile> multipartFileList)
      throws IOException {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", mCloudName,
        "api_key", mApiKey,
        "api_secret", mApiSecret));

    List<Map> responseList = new ArrayList<>();

    multipartFileList.forEach(multipartFile -> {

      File file;
      try {
        file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(file);
        Map response = cloudinary.uploader().upload(file, ObjectUtils.asMap(
            "public_id", multipartFile.getOriginalFilename(),
            "folder", "/bluebox/productdp"));

        responseList.add(response);

      } catch (IOException e) {
        e.printStackTrace();
      }

    });

    HashSet<String> imageApis = new HashSet<>();

    responseList.forEach(response -> {
      String imageApi = (String) response.get("url");
      imageApis.add(imageApi);
    });

    return imageApis;

  }

}
