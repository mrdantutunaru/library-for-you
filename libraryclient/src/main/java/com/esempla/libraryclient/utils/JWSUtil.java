package com.esempla.libraryclient.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Slf4j
@Component
public class JWSUtil {

    @Value("${server.alias}")
    private String serverAlias;

    @Value("${client.alias}")
    private String clientAlias;

    @Value("${client.key.store}")
    private Resource clientKeyStore;

    @Value("${key.password}")
    private String password;


    //For Client
    public String signClient(JSONObject object) throws Exception {
        RSAPrivateKey privateKey = getClientRSAPrivatKey();
        RSAPublicKey publicKey   = getClientRSADefaultPublicKey();
        JWSSigner signer = new RSASSASigner(privateKey);
        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(clientAlias).build(),
                new Payload(object));
        jwsObject.sign(signer);
        String s = jwsObject.serialize();
        jwsObject = JWSObject.parse(s);
        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        if (jwsObject.verify(verifier)){
            return s;
        }
        throw new JWSException("Could not verificate object signature");
    }


    public JSONObject serializeJWSObjectClient(String json) throws JWSException {
        try {
            RSAPublicKey publicKey = getServerPublicKey();
            JWSObject parse = JWSObject.parse(json);
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (parse.verify(verifier)){
                return parse.getPayload().toJSONObject();
            }else
                throw new JWSException(
                        String.format("Could not validate certificate signature with alias %s", clientAlias));
        }catch (Exception e){
            throw new JWSException(
                    String.format("Could not find certificate with alias %s", clientAlias));
        }
    }

    private RSAPublicKey getClientRSADefaultPublicKey(){
        try {
            KeyStore keystore = getClientKeyStore();
            return (RSAPublicKey)keystore.getCertificate(clientAlias).getPublicKey();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    private RSAPublicKey getServerPublicKey(){
        try {
            KeyStore keystore = getClientKeyStore();
            return (RSAPublicKey)keystore.getCertificate(serverAlias).getPublicKey();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    private RSAPrivateKey getClientRSAPrivatKey() {
        try {
            KeyStore keystore = getClientKeyStore();
            return (RSAPrivateKey) keystore.getKey(clientAlias, password.toCharArray());
        }catch (Exception e){
            throw new JWSException(e.getMessage());
        }
    }

    private KeyStore getClientKeyStore() {
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(clientKeyStore.getInputStream(), password.toCharArray());
            return keystore;
        }catch (Exception e){
            throw new JWSException(e.getMessage());
        }
    }
}
