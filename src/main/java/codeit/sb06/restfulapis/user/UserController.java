package codeit.sb06.restfulapis.user;

import codeit.sb06.restfulapis.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable("id") Long userId
    ) {
        if (userId == 9999L) {
            throw new UserNotFoundException("User not found for id: " + userId);
        }
        UserResponse response = new UserResponse(userId, "John Doe", "john@example.com");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.sb06.v2+json")
    public ResponseEntity<UserResponseV2> getUserV2(
            @PathVariable("id") String userId
    ) {
        UserResponseV2 response = new UserResponseV2(userId, "Gyutae Ha", "gted221@gmail.com");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        UserResponse created = new UserResponse(1001L, request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/_search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        List<UserResponse> results = new ArrayList<>();
        results.add(new UserResponse(10L, "JohnSmith", "smith@example.com"));
        results.add(new UserResponse(11L, "Johnny", "johnny@example.com"));
        return ResponseEntity.ok(results);
    }
}
