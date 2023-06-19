package com.sumasoft.accountaggregator.service;

import com.sumasoft.accountaggregator.dto.DHPublicKey;
import com.sumasoft.accountaggregator.dto.ErrorInfo;
import com.sumasoft.accountaggregator.dto.KeyMaterial;
import com.sumasoft.accountaggregator.dto.SerializedKeyPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class ECCService {

    private static final Logger logger = LogManager.getLogger(ECCService.class);
    @Value("${accountaggregator.ecc.curve:Curve25519}")
    String curve;
    @Value("${accountaggregator.ecc.algorithm:EC}")
    String algorithm;
    @Value("${accountaggregator.ecc.keyDerivationAlgorithm:ECDH}")
    String keyDerivationAlgorithm;
    @Value("${accountaggregator.ecc.provider:BC}")
    String provider;
    @Value("${accountaggregator.ecc.keyExpiryHrs:24}")
    int keyExpiry;

    @Autowired
    AccountAggregatorService accountAggregatorService;


    private KeyPair generateKey()
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance(algorithm, provider);

        X9ECParameters ecP = CustomNamedCurves.getByName(curve);
        ECParameterSpec ecSpec = EC5Util.convertToSpec(ecP);
        kpg.initialize(ecSpec);

        final KeyPair kp = kpg.genKeyPair();
        //log.info("Key pair generated " + kp.getPublic().getAlgorithm());
        return kp;
    }

    public SerializedKeyPair getKeyPair() {
        //final SerializedKeyPair serializedKeyPair = new SerializedKeyPair(privateKey, keyMaterial);
        SerializedKeyPair serializedKeyPair = new SerializedKeyPair();
        try {
            final KeyPair kp = this.generateKey();
            final String privateKey = this.getPEMEncodedStream(kp.getPrivate(), true);
            final String publicKey = this.getPEMEncodedStream(kp.getPublic(), false);
            Date date = new Date();
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.HOUR, keyExpiry);
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);
            final String nonce = accountAggregatorService.generateNance();
            String expiryAsISO = df.format(cl.getTime());
            //final DHPublicKey dhPublicKey = new DHPublicKey(expiryAsISO, "", publicKey);
            DHPublicKey dhPublicKey = new DHPublicKey();
            dhPublicKey.setExpiry(expiryAsISO);
            dhPublicKey.setParameters("params");
            dhPublicKey.setKeyValue(publicKey);
            //final KeyMaterial keyMaterial = new KeyMaterial(keyDerivationAlgorithm, curve, "", dhPublicKey, nonce);
            KeyMaterial keyMaterial = new KeyMaterial();

            keyMaterial.setCryptoAlg(keyDerivationAlgorithm);
            keyMaterial.setCurve(curve);
            keyMaterial.setParams("params");
            keyMaterial.setDhPublicKey(dhPublicKey);
            keyMaterial.setNonce(nonce);

            serializedKeyPair.setPrivateKey(privateKey);
            serializedKeyPair.setKeyMaterial(keyMaterial);
            serializedKeyPair.setErrorInfo(null);
        } catch (Exception exception) {
            logger.error(exception);
            String errorMessage = exception.getMessage();
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorMessage(exception.getMessage());
            serializedKeyPair.setErrorInfo(errorInfo);
        }
        return serializedKeyPair;
    }

    private String getPEMEncodedStream(final Key key, boolean privateKey) {

        final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key.getEncoded());
        final StringBuilder sb = new StringBuilder();
        final String keyType = privateKey ? "PRIVATE" : "PUBLIC";
        sb.append("-----BEGIN " + keyType + " KEY-----");
        sb.append(new String(Base64.getEncoder().encode(pkcs8KeySpec.getEncoded())));
        sb.append("-----END " + keyType + " KEY-----");
        return sb.toString();
    }

    public Key getPEMDecodedStream(final String pemEncodedKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {

        boolean privateKey = false;
        String encodedKey = "";

        if (pemEncodedKey.startsWith("-----BEGIN PRIVATE KEY-----")) {
            privateKey = true;
            encodedKey = pemEncodedKey.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "");
        } else {
            encodedKey = pemEncodedKey.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "");
        }

        final byte[] pkcs8EncodedKey = Base64.getDecoder().decode(encodedKey);

        KeyFactory factory = KeyFactory.getInstance(algorithm, provider);
//        log.log(Level.FINE, "Successfully initialised the key factory");

        if (privateKey) {
//            log.log(Level.FINE, "Its a private key");
            KeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedKey);
            //This does not mean the key is in correct format. If you receive invalid key spec error then the encoding is not correct.
            //log.log(Level.FINE, "PKCS8 decoded");
            return factory.generatePrivate(keySpec);
        }
        //log.log(Level.FINE, "Its a public key");
        KeySpec keySpec = new X509EncodedKeySpec(pkcs8EncodedKey);
        //This does not mean the key is in correct format. If you receive invalid key spec error then the encoding is not correct.
        //log.log(Level.FINE, "X509 decoded");
        return factory.generatePublic(keySpec);
    }

}

