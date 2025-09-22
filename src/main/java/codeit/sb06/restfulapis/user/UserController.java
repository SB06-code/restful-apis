package codeit.sb06.restfulapis.user;

import codeit.sb06.restfulapis.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping(value = "/{id}", produces = "application/vnd.sb06.v3+json")
    public ResponseEntity<UserResponseV3> getUserV3(
            @PathVariable("id") String userId
    ) {
        UserResponseV3 response = new UserResponseV3(userId, "Gyutae Ha", "gted221@gmail.com");

        response.add(linkTo(methodOn(UserController.class).getUserV3(userId)).withSelfRel());
        response.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserResponseV3>>> getAllUsers() {
        List<UserResponseV3> users = List.of(
            new UserResponseV3("1000", "john.doe", "john.doe@example.com"),
            new UserResponseV3("1001", "jane.doe", "jane.doe@example.com")
        );

        List<EntityModel<UserResponseV3>> userModels = users.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserV3(user.getId())).withSelfRel()
                ))
                .toList();

        CollectionModel<EntityModel<UserResponseV3>> collectionModel = CollectionModel.of(userModels,
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
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
