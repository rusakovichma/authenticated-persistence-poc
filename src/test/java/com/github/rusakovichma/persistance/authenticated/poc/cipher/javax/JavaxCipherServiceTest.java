package com.github.rusakovichma.persistance.authenticated.poc.cipher.javax;

import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherContext;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherKeyGenerator;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherService;
import com.github.rusakovichma.persistance.authenticated.poc.model.AuthenticationTag;
import com.github.rusakovichma.persistance.authenticated.poc.model.EncryptedData;
import com.github.rusakovichma.persistance.authenticated.poc.model.PlainText;
import com.github.rusakovichma.persistance.authenticated.poc.nonce.NonceGenerator;
import com.github.rusakovichma.persistance.authenticated.poc.util.BytesUtil;
import org.junit.jupiter.api.Test;

import javax.crypto.AEADBadTagException;
import javax.crypto.SecretKey;

import java.security.AlgorithmConstraints;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class JavaxCipherServiceTest {

    private CipherService generateCipherService() throws Exception {
        final CipherKeyGenerator keyGenerator = new JavaxKeyGenerator();
        final SecretKey secretKey = keyGenerator.generateKey();

        final NonceGenerator nonceGenerator = new NonceGenerator();
        final Clock clock = Clock.systemUTC();
        final Instant expirationDate = clock.instant().plus(Duration.ofDays(10));
        final AlgorithmConstraints constraints = new DecentAlgorithmConstraints();

        final CipherContext context = new JavaxCipherContext(secretKey, constraints, expirationDate, nonceGenerator, clock);
        return new JavaxCipherService(context);
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        final CipherService cipherService = generateCipherService();

        final PlainText plainText = new PlainText("Secret message");
        final AuthenticationTag authTag = new AuthenticationTag(BytesUtil.intToByteArray(plainText.hashCode()));

        final EncryptedData data = cipherService.encrypt(plainText, authTag);
        final PlainText plainTextUnencrypted = cipherService.decrypt(data, authTag);

        assertEquals(plainText, plainTextUnencrypted);
    }

    @Test
    public void testEncryptDecryptWrongAuthTag() {
        Exception exception = assertThrows(AEADBadTagException.class, () -> {
            final CipherService cipherService = generateCipherService();

            final PlainText plainText = new PlainText("Secret message");
            final AuthenticationTag authTag = new AuthenticationTag(BytesUtil.intToByteArray(plainText.hashCode()));

            final EncryptedData data = cipherService.encrypt(plainText, authTag);

            final AuthenticationTag wrongAuthTag = new AuthenticationTag(BytesUtil.intToByteArray("Another string".hashCode()));
            final PlainText plainTextUnencrypted = cipherService.decrypt(data, wrongAuthTag);
        });
        assertEquals("Tag mismatch!", exception.getMessage());

    }

}