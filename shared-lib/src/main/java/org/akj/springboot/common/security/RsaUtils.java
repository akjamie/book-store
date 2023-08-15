package org.akj.springboot.common.security;

import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RsaUtils {
	public static final String CHARSET_NAME = "UTF-8";
	public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
	public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
	public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
	public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
	private PrivateKey privateKey;

	private PublicKey publicKey;

	public String encrypt(String str) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(CHARSET_NAME)));
	}

	public String decrypt(String str) throws Exception {
		byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return new String(cipher.doFinal(inputByte));
	}

	@SneakyThrows
	public static RSAPrivateKey loadRsaPrivateKeyFromFile(@org.springframework.lang.NonNull String path) {
		RSAPrivateKey key = null;
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		try (InputStream ins = new ClassPathResource(path).getInputStream()) {
			String base64Keys = new String(ins.readAllBytes())
					.replaceAll("\\n", "")
					.replace(BEGIN_PRIVATE_KEY, "")
					.replace(END_PRIVATE_KEY, "")
					.trim();

			byte[] bytes = Base64.getDecoder().decode(base64Keys.getBytes(Charset.defaultCharset()));
			PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytes);
			key = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);
		}

		return key;
	}

	@SneakyThrows
	public static RSAPublicKey loadRsaPublicKeyFromFile(@NonNull String path) {
		RSAPublicKey key = null;
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		try (InputStream ins = new ClassPathResource(path).getInputStream()) {
			String base64Keys = new String(ins.readAllBytes())
					.replaceAll("\\n", "")
					.replace(BEGIN_PUBLIC_KEY, "")
					.replace(END_PUBLIC_KEY, "")
					.trim();

			byte[] bytes = Base64.getDecoder().decode(base64Keys.getBytes(Charset.defaultCharset()));
			X509EncodedKeySpec pubvSpec = new X509EncodedKeySpec(bytes);
			key = (RSAPublicKey) keyFactory.generatePublic(pubvSpec);
		}

		return key;
	}
}
