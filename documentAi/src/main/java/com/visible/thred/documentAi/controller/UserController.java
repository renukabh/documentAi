package com.visible.thred.documentAi.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.visible.thred.documentAi.model.User;
import com.visible.thred.documentAi.model.UserDTO;
import com.visible.thred.documentAi.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/without-doc-uploads")
	public ResponseEntity<Map<String, Object>> getUsersWithoutUploads(@RequestParam String fromdate,
			@RequestParam String todate) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {

			DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			LocalDateTime fromDate = LocalDateTime.parse(fromdate, parser);
			LocalDateTime toDate = LocalDateTime.parse(todate, parser);

			if (fromDate.isAfter(toDate)) {
				response.put("message", "Start date must be before end date");
				response.put("code", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				Optional<List<User>> userListOptional = userService.getUsersWithoutDocUploads(fromDate, toDate);
				if (userListOptional.isPresent()) {
					response.put("message", "successful");
					response.put("data", userListOptional.get());
					response.put("code", HttpStatus.OK);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.put("message", "data not available..");
					response.put("code", HttpStatus.NOT_FOUND);
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			}
		} catch (DateTimeParseException e) {
			response.put("message", "Invalid date format. Please use 'dd-MM-yyyy HH:mm'");
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			response.put("message", "Exception: " + e.getStackTrace());
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

		}
	}

	@PostMapping("/createUser")
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userdto) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {

			User savedUser = userService.createUser(userMapper(userdto), userdto.getTeamIds());
			response.put("message", "user created..");
			response.put("data", savedUser);
			response.put("code", HttpStatus.CREATED);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			response.put("message", "Exception: " + e.getStackTrace());
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/updateUser")
	public ResponseEntity<Map<String, Object>> update(@RequestBody User user, Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			User savedUser = userService.updateUser(user, id);
			response.put("message", "user updated.");
			response.put("data", savedUser);
			response.put("code", HttpStatus.OK);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			response.put("message", "Exception: " + e.getStackTrace());
			response.put("code", HttpStatus.EXPECTATION_FAILED);
			response.put("exception", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
	}

	private User userMapper(UserDTO dto) {
		User u = new User();
		u.setEmail(dto.getEmail());
		u.setDateAdded(LocalDateTime.now());
		return u;
	}
}