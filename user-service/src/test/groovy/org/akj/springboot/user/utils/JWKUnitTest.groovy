package org.akj.springboot.user.utils

import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyType
import com.nimbusds.jose.util.IOUtils
import com.nimbusds.jwt.SignedJWT
import org.akj.springboot.common.security.RsaUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

class JWKUnitTest extends Specification {
    @Shared
    RSAPrivateKey privKey;
    @Shared
    RSAPublicKey pubKey;
    @Shared
    RsaUtils rsaUtils;
    static Logger log = LoggerFactory.getLogger(JWKUnitTest.class)

    def setup() {
        String privateKeyPath = "rsa/rsa-20221017.key"
        String publicKeyPath = "rsa/rsa-20221017.pem"
        privKey = RsaUtils.loadRsaPrivateKeyFromFile(privateKeyPath);
        pubKey = RsaUtils.loadRsaPublicKeyFromFile(publicKeyPath);
        rsaUtils = new RsaUtils(privKey, pubKey);
    }

    def "Verify RSA key encode and decode"() {
        given:
        String plainText = "123456";

        when:
        String encodedValue = rsaUtils.encrypt(plainText);
        log.info("encoded string: {}", encodedValue)
        String decodedValue = rsaUtils.decrypt(encodedValue);

        then:
        plainText.equals(decodedValue);
    }

    @Ignore
    def "Verify FilePathResource"() {
        given:
        def ins = new FileSystemResource("keystore.p12").getInputStream()

        expect:
        ins.available() > 0
    }

    def "Construct JWK from private key"() {
        given:
        String path = "rsa/rsa-20221017.key"
        String key;
        try (InputStream ins = new ClassPathResource(path).getInputStream()) {
            key = new String(ins.readAllBytes()).trim();
        }
        JWK jwk = JWK.parseFromPEMEncodedObjects(key);

        expect:
        verifyAll {
            jwk.getKeyType() == KeyType.RSA
            jwk.isPrivate() == Boolean.TRUE
        }
    }

    def "Verify token"(){
        given:
        String privKeyPath = "rsa/rsa-20221017.key"
        String pubKeyPath = "rsa/rsa-20221017.pem"
        String privKey = IOUtils.readInputStreamToString(new ClassPathResource(privKeyPath).getInputStream());
        String pubKey = IOUtils.readInputStreamToString(new ClassPathResource(pubKeyPath).getInputStream());
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqYW1pZS0wMiIsInVpZCI6MywibmJmIjoxNjY2MTc3MTc2LCJzY29wZSI6WyJSb2xlX3VzZXItZ2VuZXJhbC1yZWFkb25seSIsInVzZXI6bGlzdCJdLCJleHAiOjE2NjYxNzc0NzYsImlhdCI6MTY2NjE3NzE3NiwianRpIjoiZWViNjAyZjUtNDBhYi00NTNkLThkMDctNjM2YjNjMDdjMjI4In0.BPMkIT_kqgUOlObOc1dL4Btg_GL4Va_HRo3_SV7nI2m4cWZzJd6Wlm9CzzwbgRlQO_Ykt8AzQUgLybvZALGo7wCd9E5jcZxPGu7hHTdw1V5y69EBfWC37lZA_SMPZ5zL8U46MOPGSM_GFVKBP3jcr6agyxxy4yj5rHiQNmK4msulXhRqy492ZS4uNn9j5P0a9fl8ZHXQ48gPfL4Rx9xUMTS2srFG9XACuSpWaqf9ZAjDPUtjS592DSV5ebO1-FkTxOGYP6U1XeFZgICLzVqeNwXqJDwIGtXoSlqpUx76qlsC0bRiRV8yUPj-tDA-9NCcDnXhV_kMcabX4kTMlNocceQctVI9X3Q_bTSgHoKQWFX21Kdc18QLtbgebvCjgasQ-_cT-RWOGKP_1tBTWKPt-QgGyh8pEUT0gANSoNhR4pY9ur7uEOzwt_HVxkmA6o_sAmyuK2ztt86EIWOviKZ_S0MaGnrfgg5SC5g9s5p7b8bXAWa220JWwbc_1QWasCAEWYxC0_UoTlhmVC-mFTFOdlKk9YOznhwwKuHX1SqVHzhVibmrxq3HasIkhmVBYtcViWnGokle1LxE5369eIWto_577MC_p3AvaWabHZFf7jBHM2fObdcMzKo8jvWHh27USZDknoaeY23Hl9GCCxZMBC5l9RQ24LTMgEeJGkTs738";

        RSASSAVerifier verifier = new RSASSAVerifier(JWK.parseFromPEMEncodedObjects(pubKey));

        when:
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean result = signedJWT.verify(verifier);

        then:
        verifyAll {
            result == true
        }

    }

}
