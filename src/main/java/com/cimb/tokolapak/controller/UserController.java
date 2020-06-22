package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.cimb.tokolapak.util.EmailUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	// User1 Password = 123321 -> abc123abc1212
	// User2 Password = 123321 -> cba1212ab1232
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping
	public User registerUser(@RequestBody User user) {
//		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
		
//		if(findUser.toString() != "Optional.empty") {
//			throw new RuntimeException("Username exists!");
//		}
		
		System.out.println(user);
		
		String encodedPassword = pwEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
//		String teks = user.getUsername()+",<h1>Selamat Anda bergabung dengan ID</h1>"+user.getId();
		this.emailUtil.sendEmail("cimbxpwd@gmail.com", "Verifikasi Registrasi Email", "<h1>Selamat ,"+user.getUsername()+"</h1> \nAnda telah bergabung bersama kami! Klik disini <a href=\"http://localhost:8080/users/edit/"+user.getId()+"\">link</a> ini untuk verifikasi email anda");
		
		return savedUser;
	}
	
	@GetMapping("/edit/{userId}")
	public User verifikasiEmail(@PathVariable int userId) {
		User findUser = userRepo.findById(userId).get();
		if(findUser == null) {
			throw new RuntimeException("User not found");
		}
		findUser.setVerified(true);
		return userRepo.save(findUser);
	}
	
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();
							// Password raw/sblm encode  |  password sdh encode
		if(pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}else {
			throw new RuntimeException("Wrong password!");
//			return null;
		}
	}
	
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String username,@RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();
		
		if(pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}
		
		throw new RuntimeException("Wrong password!");
//		return null;
	}
	@PostMapping("/sendEmail")
	public String sendEmailTesting() {
		this.emailUtil.sendEmail("cimbxpwd@gmail.com", "Testing Spring Mail", "<h1>Hello World!</h1>  \nApa Kabar? \nSehat?");
		return "Email Sent!";
	}
	
//	@PostMapping("/verifikasi")
//	public String verifikasiEmail() {
//		this.emailUtil.sendEmail("cimbxpwd@gmail.com", "Verifikasi Registrasi Email", "<h1>Selamat</h1> \nAnda telah bergabung bersama kami! \nApa Kabar? \nSehat?");
//		
//	}

}
