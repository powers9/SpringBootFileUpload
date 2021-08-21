package com.hackerrank.files;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RequestController {
    public static final String UPLOAD_DIR = "uploads/";
    private final Path fileRoot=Paths.get(UPLOAD_DIR);

    @PostMapping("/uploader")
    public ResponseEntity uploader(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
    try{ 

      Files.copy(file.getInputStream(), this.fileRoot.resolve(file.getOriginalFilename()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }catch(Exception e){
System.out.println(e.getLocalizedMessage());
    }
        return null;
    }

    @GetMapping("/downloader")
    public ResponseEntity downloader(@RequestParam String fileName) {
        try{ 
      Path fileTobeRead=fileRoot.resolve(fileName);
      Resource resource=new UrlResource(fileTobeRead.toUri());
     if(resource.exists() || resource.isReadable()){
return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
     } 
    }catch(Exception e){
System.out.println(e.getLocalizedMessage());
     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}