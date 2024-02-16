package com.hb.neobank.icici.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

public final class IciciBankEncryption {
	private static Cipher encCipher = null;
	private static Cipher decCipher = null;

	public static String encrypt(String input) throws Exception {
		Cipher cipher = getEncCipher();
		byte[] body = cipher.doFinal(input.getBytes());
		return Base64.getEncoder().encodeToString(body);
	}

	public static String decrypt(String input) throws Exception {
		Cipher cipher = getDecCipher();
		input = input.replaceAll("\\r\\n", "");
		byte[] bytes = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
		return new String(cipher.doFinal(bytes));
	}

	public static String decryptStatement(String input) throws Exception {
		JSONObject inputJson = new JSONObject(input);
		if (inputJson != null && inputJson.has("encryptedKey") && inputJson.has("encryptedData")) {
			return decryptByAES(inputJson.getString("encryptedKey"), inputJson.getString("encryptedData"));
		}
		return null;
	}

	private static Cipher getEncCipher() throws CertificateException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException {

		if (IciciBankEncryption.encCipher != null) {
			return IciciBankEncryption.encCipher;
		}

		RSAPublicKey pubkey = getRSAPublicKey();

		// Obtain a RSA Cipher Object
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// The source of randomness
		SecureRandom secureRandom = new SecureRandom();

		// Initialize the cipher for encryption
		cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

		IciciBankEncryption.encCipher = cipher;

		return IciciBankEncryption.encCipher;
	}

	private static Cipher getDecCipher() throws Exception {

		if (IciciBankEncryption.decCipher != null) {
			return IciciBankEncryption.decCipher;
		}

		// The source of randomness
		SecureRandom secureRandom = new SecureRandom();

		// Obtain a RSA Cipher Object
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		String pvtKey = "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";

		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvtKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey priv = keyFactory.generatePrivate(privKeySpec);

		// Initialize the cipher for decryption
		cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom);
		IciciBankEncryption.decCipher = cipher;

		return IciciBankEncryption.decCipher;
	}

	private static RSAPublicKey getRSAPublicKey() throws CertificateException, IOException {
		String certData = "-----BEGIN CERTIFICATE-----\n"
				+ "MIIFhDCCA2wCCQCIqfcsomoC1jANBgkqhkiG9w0BAQUFADCBgzELMAkGA1UEBhMCSU4xFDASBgNV\n"
				+ "BAgMC01BSEFSQVNIVFJBMQ8wDQYDVQQHDAZNVU1CQUkxGDAWBgNVBAoMD0lDSUNJIEJhbmsgTHRk\n"
				+ "LjEMMAoGA1UECwwDQ0lCMSUwIwYDVQQDDBx3d3cuY2libmV4dGFwaS5pY2ljaWJhbmsuY29tMB4X\n"
				+ "DTE3MDQxMzA3MTkyOVoXDTIyMDQxMjA3MTkyOVowgYMxCzAJBgNVBAYTAklOMRQwEgYDVQQIDAtN\n"
				+ "QUhBUkFTSFRSQTEPMA0GA1UEBwwGTVVNQkFJMRgwFgYDVQQKDA9JQ0lDSSBCYW5rIEx0ZC4xDDAK\n"
				+ "BgNVBAsMA0NJQjElMCMGA1UEAwwcd3d3LmNpYm5leHRhcGkuaWNpY2liYW5rLmNvbTCCAiIwDQYJ\n"
				+ "KoZIhvcNAQEBBQADggIPADCCAgoCggIBAM+en2ErEsETmfoZJjf3I5DIc8KAt6dv/ZkKYHcpli1g\n"
				+ "yLFjJNbZnyk4Um7UaKvU0fpqnsLboapXKR0iHFp4/7SR9kTh4FfvFrrp2pKmQd8f/Rf6OPk2/48i\n"
				+ "X7sCs0nl8IZYMqe1Tt1YAMFPJjPIH/ERx3vnWYhgHUmRGJqjHfo4NeNR0IarF3HAYX4hh6K0LaAQ\n"
				+ "hUoq6SuWyWf9m9qzHRHpWq4eJRsbhPYLaTtt8XS+vPpBjFjfQreDtgWdIXwKuHq8EOS/KxBifThC\n"
				+ "tEBMGZUSYZBoldq1kdaakkt5FaXhe+g0FWrLcxalaSS4bHK0QCv1Lbh3tcPetCO3XyR1Jj28SL+5\n"
				+ "gYm464jmjMGURJwocWUhuNd0qAKt8bMv9NCDgKiWSmAlzeznRYeNaay2ckg5aB5tNO5l/8pUh8Ew\n"
				+ "qLyKECFnCoNvBlcaoJIvZ0sprQO+dHzggT/Q9wl0XRFUkPh4SFGHIiqldy6VgA6I7uVWb7ve1Y3P\n"
				+ "4yhlfTDV/Hr4ZL4gTFVrorS1a4Tqap38iqHnfM3djwgwbnzv0TJZCywZ5ED8MRDmub6W4jNYMVar\n"
				+ "uG1gLVf7gE2sUY/dgTRu1Hdw3/YlOY9XpQebBP37RD3+Up+oEYxjPe04Cy4rTFx9/8SluuBPvwNH\n"
				+ "WVmHkv1ULNHum0VQ3kej17jbEeO+FftJAgMBAAEwDQYJKoZIhvcNAQEFBQADggIBAMGX2dKuXsGj\n"
				+ "ujhKxZOFzo8A0QKu+nsw+pFtiJ5KjyOR1vW9pOdG7roJJGr6cU5fUDlUpYDDVIvPiVbPYgWLkVe3\n"
				+ "7+tpM8T77ZYSXdO7G9hhU8uw2pcRHiQMlDotV/RcTGZHyVVaw7TJty3xMH2j0/FIHejcFaYXZYQB\n"
				+ "A5+zKc7PBsvwn/KQgJ9R4BTqmdWeca1r0+iBXGq1iRg4IGePf0lIc+80AUneC1ceC07RfvI0PJpk\n"
				+ "LVTkDCXdNK7QtG/cIqjdZ1jtB+ne7cwtksw1ewu5dE3BFNmqdT3DmKHAupTc2ILSup2w/JEEepMI\n"
				+ "DHO8GvqR0dUXS5xCcXNKwXUMiLPYA56mRKoST5+e2RO5WtVQMHiizEF5iID+WjyXNlVtqMarEjih\n"
				+ "Z0+/vkABp/Q3AfKs3rtaXxU4crt+RLaaldG/dBXOoUDTpaNR+ktUkNmEPTe9zc7pwwRDC2zNylt4\n"
				+ "FnhNP2b2t+RLuP+smAROVaXA1owpte3zeh7aiUe02Y6udEzVrKCAvRUiCKoCDH9N101k3lzCFy80\n"
				+ "rRquHZ7ZZmUrX4DksuPnSuLILR5ss6UkQTZbg7HXtMN2lDTgPjO2UMCjqI+5gPGTqdld4XWDTEW0\n"
				+ "xdyhJiEgATeqQllbn47B7C7603ltWFpoInafn2NwxBW89wv938bMKpxFxmQcseGH\n" + "-----END CERTIFICATE-----";

		certData = "-----BEGIN CERTIFICATE-----\n"
				+ "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\n"
				+ "-----END CERTIFICATE-----";
		InputStream inStream = new ByteArrayInputStream(certData.getBytes());
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		inStream.close();
		return (RSAPublicKey) cert.getPublicKey();
	}

	public static String decryptByAES(String secretKey, String cipherText) throws Exception {
		int INIT_VECTOR_LENGTH = 16;

		// Get raw encoded data
		String decryptKey = decrypt(secretKey);
		byte[] encrypted = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));

		// Slice initialization vector
		IvParameterSpec ivParameterSpec = new IvParameterSpec(encrypted, 0, INIT_VECTOR_LENGTH);

		// Set secret password
		SecretKeySpec secretKeySpec = new SecretKeySpec(decryptKey.getBytes(StandardCharsets.UTF_8), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

		// Trying to get decrypted text

		return new String(cipher.doFinal(encrypted, INIT_VECTOR_LENGTH, encrypted.length - INIT_VECTOR_LENGTH));
	}
}