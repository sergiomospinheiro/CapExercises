package virtuallibrary.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import virtuallibrary.models.User;
import virtuallibrary.repositories.UserJpaRepository;

@RestController
@RequestMapping("/virtual-library/users")
public class UsersControllers {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@GetMapping("/getallusers")
	public List<User> users() {
		return userJpaRepository.findAll();
	}

	@PostMapping("/getuser")
	public User getUser(@RequestParam Long userId) {
		return userJpaRepository.getById(userId);
	}

	@PostMapping("/saveuser")
	public User saveUser(@RequestBody User user) {
		return userJpaRepository.save(user);
	}

	@PutMapping("/updateuser")
	public User updateUser(@RequestParam Long userId, @RequestBody User user) {
		return userJpaRepository.save(user);

	}

	@DeleteMapping("/deleteuser")
	public void deleteUser(@RequestParam Long userId) {
		userJpaRepository.deleteById(userId);
	}

}
