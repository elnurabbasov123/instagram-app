package app.model.helper;

import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
public interface CryptographyHelper {
    KeyPair getKeyPair();
}
