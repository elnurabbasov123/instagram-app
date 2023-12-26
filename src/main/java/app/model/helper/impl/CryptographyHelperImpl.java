package app.model.helper.impl;

import app.model.helper.CryptographyHelper;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptographyHelperImpl implements CryptographyHelper {

    @Override
    public KeyPair getKeyPair() {
        try {
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            return rsa.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
