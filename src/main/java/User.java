import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class User {
    private String username;
    private String firstname;
    private String lastname;
}
