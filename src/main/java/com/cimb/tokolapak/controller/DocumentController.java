package com.cimb.tokolapak.controller;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/documents")
@CrossOrigin
public class DocumentController {
																// "/src/main/resources/static/images/"
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
//	private Date date = new Date();
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/testing")
	public void testing() {
		System.out.println(uploadPath);
	}
	
	@PostMapping
	public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("userData")String userString) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
		User user = new ObjectMapper().readValue(userString, User.class);
		
		System.out.println("USERNAME: "+ user.getUsername());
		
		String fileExtension = file.getContentType().split("/")[1];
		System.out.println(fileExtension);
		String newFileName = "PROD-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);
		
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/download/").path(fileName).toUriString();
	
		user.setProfilePicture(fileDownloadUri);

		userRepo.save(user);
//		return fileName + " has been uploaded";
		
		return fileDownloadUri;
	}
	@PostMapping("/login")
	public User loginWithProfilePicture(@RequestBody User user) {
		return userRepo.findByUsername(user.getUsername()).get();
	}

	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		System.out.println("DOWNLOAD");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename()+ "\"").body(resource);
	}
}
