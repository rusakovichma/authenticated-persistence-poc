package com.github.rusakovichma.persistance.authenticated.poc.config;

import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherKeyGenerator;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherService;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.javax.DecentAlgorithmConstraints;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.javax.JavaxCipherContext;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.javax.AesGcmCipherService;
import com.github.rusakovichma.persistance.authenticated.poc.nonce.NonceGenerator;
import com.github.rusakovichma.persistance.authenticated.poc.cipher.javax.ConstantCipherKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.security.AlgorithmConstraints;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Configuration
public class AesGcmCipherConfig {

    @Value("${antitampering.encryption.poc.private.key}")
    private String privateKey;

    @Value("${antitampering.encryption.poc.private.key.alg}")
    private String privateKeyAlg;

    @Bean
    public CipherService cipherService() {
        try {
            final CipherKeyGenerator keyGenerator = new ConstantCipherKey(privateKey.getBytes(), privateKeyAlg);
            final SecretKey secretKey = keyGenerator.generateKey();

            final NonceGenerator nonceGenerator = new NonceGenerator();
            final Clock clock = Clock.systemUTC();
            final Instant expirationDate = clock.instant().plus(Duration.ofDays(10));
            final AlgorithmConstraints constraints = new DecentAlgorithmConstraints();

            return new AesGcmCipherService(
                    new JavaxCipherContext(secretKey, constraints, expirationDate, nonceGenerator, clock)
            );
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

}
