package likelion.underdog.songgotmae.web.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;

    public boolean match(BCryptPasswordEncoder passwordEncoder, String encodedPassword) {
        return passwordEncoder.matches(this.password, encodedPassword);
    }

}
