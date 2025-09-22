package codeit.sb06.restfulapis.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseV3 extends RepresentationModel<UserResponseV3> {
    private String id;
    private String name;
    private String email;
}
