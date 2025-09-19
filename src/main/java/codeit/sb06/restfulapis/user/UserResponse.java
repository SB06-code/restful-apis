package codeit.sb06.restfulapis.user;

public record UserResponse(
    Long id,
    String name,
    String email
) {
}
