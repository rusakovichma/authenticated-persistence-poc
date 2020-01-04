package com.github.rusakovichma.persistance.authenticated.poc.cipher.javax;

import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherContext;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherService;
import com.github.rusakovichma.persistance.authenticated.poc.model.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

/**
 * A type safe front end to an authenticated encryption service.
 */
public class AesGcmCipherService implements CipherService {

    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_NONCE_LENGTH = 12; // in bytes
    private static final int GCM_TAG_LENGTH = 16; // in bytes

    private final CipherContext context;

    public AesGcmCipherService(CipherContext context) throws NoSuchAlgorithmException {
        this.context = context;
    }

    /**
     * Encrypts some plaintext data and passes in some data to be authenticated later.
     *
     * @param plainText plaintext
     * @param authTag auth data
     * @return encrypted data
     * @throws GeneralSecurityException
     */
    public EncryptedData encrypt(PlainText plainText, AuthenticationTag authTag) throws GeneralSecurityException {
        if (plainText == null) {
            throw new IllegalArgumentException("Null plainText");
        }
        // You can encrypt about 68 GB (2**39 − 256 in bits) for a single nonce, but JCE
        // buffers plaintext during decryption, so you really wouldn't want to.
        final Nonce nonce = context.generateNonce(GCM_NONCE_LENGTH);
        final Cipher cipher = initEncryptCipher(nonce, authTag);

        final CipherText cipherText = new CipherText(cipher.doFinal(plainText.getBytes()));
        return new EncryptedData(nonce, cipherText);
    }

    /**
     * Decrypts encrypted encryptedData to plaintext.
     *
     * @param encryptedData encrypted encryptedData.
     * @param authTag encryptedData to authenticate.
     * @return the plain text.
     * @throws GeneralSecurityException
     */
    public PlainText decrypt(EncryptedData encryptedData, AuthenticationTag authTag) throws GeneralSecurityException {
        final Cipher cipher = initDecryptCipher(encryptedData, authTag);
        final byte[] plainText = cipher.doFinal(encryptedData.getCipherText().getBytes());
        return new PlainText(plainText);
    }

    private Cipher initEncryptCipher(Nonce nonce, AuthenticationTag authTag) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(AES_GCM);
        final AlgorithmParameters params = initAlgorithmParameters(nonce);
        final SecretKey secretKey = context.getSecretKey(cipher, params);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);
        cipher.updateAAD(authTag.getData());
        return cipher;
    }

    private Cipher initDecryptCipher(EncryptedData encryptedData, AuthenticationTag authTag) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(AES_GCM);
        final AlgorithmParameters params = initAlgorithmParameters(encryptedData.getNonce());
        final SecretKey secretKey = context.getSecretKey(cipher, params);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        cipher.updateAAD(authTag.getData());
        return cipher;
    }

    private AlgorithmParameters initAlgorithmParameters(Nonce nonce) throws GeneralSecurityException {
        final GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce.getBytes());
        final AlgorithmParameters params = AlgorithmParameters.getInstance("GCM");
        params.init(spec);
        return params;
    }

}
