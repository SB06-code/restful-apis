package codeit.sb06.restfulapis.user;

import jakarta.validation.constraints.Email;

public record UserRequest(
        String name,
        @Email String email
) {
}
