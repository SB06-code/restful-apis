package codeit.sb06.restfulapis;

public record ErrorResponse(
        String code,
        String message
) {
}
