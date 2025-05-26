package com.service;

import com.model.User;
import com.repository.UserRepository;
import com.validation.EmailValidator;
import com.validation.PasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PasswordValidator passwordValidator = new PasswordValidator();
    private final EmailValidator emailValidator = new EmailValidator();

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /** Get all users */
    public List<User> findAll() {
        return userRepo.findAll();
    }

    /** Get one user or throw 404 */
    public User findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }

    /** Create a new user */
    public User create(User user) {
        String raw = user.getPassword();
        if (!passwordValidator.isValid(raw)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must be at least 8 characters, include upper/lowercase, a digit and a special character"
            );
        }
        if (!emailValidator.isValid(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid email format: " + user.getEmail()
            );
        }
        String hashed = passwordEncoder.encode(raw);
        user.setPassword(hashed);
        return userRepo.save(user);
    }

    /** Update existing user */
    public User update(Long id, User updated) {
        User existing = findById(id);
        existing.setFirst_name(updated.getFirst_name());
        existing.setLast_name(updated.getLast_name());
        existing.setEmail(updated.getEmail());
        existing.setPassword(updated.getPassword());
        existing.setRole(updated.getRole());
        existing.setStudent(updated.isStudent());
        existing.setStudentVerified(updated.isStudentVerified());
        existing.setStudentProofImageUrl(updated.getStudentProofImageUrl());
        return userRepo.save(existing);
    }

    /** Delete a user */
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id);
        }
        userRepo.deleteById(id);
    }
}
