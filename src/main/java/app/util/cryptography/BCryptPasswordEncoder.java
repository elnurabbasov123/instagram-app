package app.util.cryptography;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BCryptPasswordEncoder{
    public String encode(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public boolean matches(String currentPass, String hashedPassword) {
        return BCrypt.checkpw(currentPass,hashedPassword);
    }
}
