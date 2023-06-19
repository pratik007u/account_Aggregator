package com.sumasoft.accountaggregator.util;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.VerificationJwkSelector;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.lang.JoseException;

public class VerifyJWSSignatureUtil {
    String publicKey = "{\"keys\":[{\"alg\":\"RS256\",\"e\":\"AQAB\",\"kid\":\"b78390c9-188f-41bd-851b-5df889c23a57\",\"kty\":\"RSA\",\"n\":\"tIHPVRZi1-pxcDfkv6jxEADS7Rs8UUbrXB5BlaB7W5ROhPqh2pttZCCA-2GwYvlJLpNNulj5PXXX6Jqs4F7rB57n6mH2KYP5M2DDKn7EEs_rm1rjG8H-x6SigYiv2YjrYcZYjDBcjqonmLEwpRquuuWGofKWDiM89dVPbhJ4rl1cYLjrkBI3Cyadcyrazb5AYcmD6Hhb0kHlSmdku8y0mEF1-Mu_gClIAmqtjpAVWABWUjEk-wwRnafXgLGLWOrdxhQ7tjsCj6dR0Mrq92p8bqokmqA8KeCCrpgqTGlagfbDTIooTdLOzCRAyAozVR1UyOlv4M0jwWASoFnaUvv73Q\",\"use\":\"sig\"}]}";
    private final JsonWebKeySet rsaJsonWebKeySet;

    public VerifyJWSSignatureUtil() throws Exception {
        rsaJsonWebKeySet = new JsonWebKeySet(publicKey);
    }

    public String signEmbedded(String payload) throws Exception {
        return doSign(payload, false);
    }

    public String doSign(String payload, boolean detached) throws Exception {
        // Create a new JsonWebSignature object for the signing
        JsonWebSignature signerJws = new JsonWebSignature();

        // The content is the payload of the JWS
        signerJws.setPayload(payload);

        // Set the signature algorithm on the JWS
        signerJws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        RsaJsonWebKey jwk = getJsonWebKey();

        // The private key is used to sign
        signerJws.setKey(jwk.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        signerJws.setKeyIdHeaderValue(jwk.getKeyId());

        // Set the "b64" header to false, which indicates that the payload is not
        // encoded
        // when calculating the signature (per RFC 7797)
        signerJws.getHeaders().setObjectHeaderValue(HeaderParameterNames.BASE64URL_ENCODE_PAYLOAD, !detached);

        // Produce the compact serialization with an empty/detached payload,
        // which is the encoded header + ".." + the encoded signature
        if (detached) {
            return signerJws.getDetachedContentCompactSerialization();
        } else {
            // RFC 7797 requires that the "b64" header be listed as critical
            signerJws.setCriticalHeaderNames(HeaderParameterNames.BASE64URL_ENCODE_PAYLOAD);
            return signerJws.getCompactSerialization();
        }
    }

    /**
     * This method generates a detached json web signature,
     * Using the RFC 7797 JWS Unencoded Payload Option
     *
     * @param payload
     * @return signature without the payload (i.e. detached signature)
     * @throws Exception
     */
    public String sign(String payload) throws Exception {
        return doSign(payload, true);
    }

    /**
     * This method validates the detached json web signature with the supplied payload.
     *
     * @param detachedSignature
     * @param payload
     * @throws Exception - if signature validation fails.
     */
    public JsonWebSignature parseSign(String detachedSignature, String payload) throws Exception {

        // Use a JsonWebSignature object to verify the signature
        JsonWebSignature verifierJws = new JsonWebSignature();

        // Set the algorithm constraints based on what is agreed upon or expected from
        // the sender
        verifierJws.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                AlgorithmIdentifiers.RSA_USING_SHA256));

        if (payload == null) {
            // The JWS with embedded content is the compact serialization
            verifierJws.setCompactSerialization(detachedSignature);
        } else {
            // The JWS with detached content is the compact serialization
            verifierJws.setCompactSerialization(detachedSignature);

            // The unencoded detached content is the payload
            verifierJws.setPayload(payload);
        }

        VerificationJwkSelector jwkSelector = new VerificationJwkSelector();
        RsaJsonWebKey jwk = (RsaJsonWebKey) jwkSelector.select(verifierJws, rsaJsonWebKeySet.getJsonWebKeys());

        // The public key is used to verify the signature
        // This should be the public key of the sender.
        verifierJws.setKey(jwk.getPublicKey());

        if (!verifierJws.verifySignature()) {
            throw new JoseException("Signature verification failed.");
        }

        // return the jws
        return verifierJws;
    }

    private RsaJsonWebKey getJsonWebKey() {
        return (RsaJsonWebKey) rsaJsonWebKeySet.getJsonWebKeys().get(0);
    }
}
