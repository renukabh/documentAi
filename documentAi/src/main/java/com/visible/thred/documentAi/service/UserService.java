package com.visible.thred.documentAi.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visible.thred.documentAi.model.Team;
import com.visible.thred.documentAi.model.User;
import com.visible.thred.documentAi.repository.TeamRepository;
import com.visible.thred.documentAi.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeamRepository teamRepository;

	// Users not uploading documents within a given period
	public Optional<List<User>> getUsersWithoutDocUploads(LocalDateTime from, LocalDateTime to) {

		return Optional.of(userRepository.getUsersWithoutDocumentUploads(from, to));
	}

	public User createUser(User user, Set<Long> teamIds) {
		Set<Team> userteam = new HashSet<>();
		for (Long teamid : teamIds) {
			Optional<Team> teams = teamRepository.findById(teamid);
			if (teams.isPresent())
				userteam.add(teams.get());
		}
		user.setTeams(userteam);
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	public User updateUser(User user, Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		User existingUser = null;
		if (Objects.nonNull(optionalUser.get())) {
			existingUser = optionalUser.get();
			if (Objects.nonNull(user.getEmail()) && !user.getEmail().isBlank() && !user.getEmail().isEmpty()) {
				existingUser.setEmail(user.getEmail());
			}
			if (Objects.nonNull(user.getTeams())) {
				existingUser.setTeams(user.getTeams());
			}
			return userRepository.save(existingUser);
			// need to add coloum  updatedtimestamp in database.
		}

		return user;
	}

	public Boolean deleteUser(Long id) {
		boolean isdelated = false;
		Optional<User> optionalUser = userRepository.findById(id);
		if (Objects.nonNull(optionalUser.get())) {
			userRepository.deleteById(id); // need to modify user entity for soft delete
			isdelated = true;
		}
		return isdelated;
	}
}