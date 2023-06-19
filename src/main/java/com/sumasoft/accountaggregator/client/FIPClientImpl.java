package com.sumasoft.accountaggregator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumasoft.accountaggregator.dao.FIPDao;
import com.sumasoft.accountaggregator.entity.AccountDiscoveryResponse;
import com.sumasoft.accountaggregator.entity.AccountLinkResponse;
import com.sumasoft.accountaggregator.entity.FetchAccountsLinkResponse;
import com.sumasoft.accountaggregator.util.CommonUtil;
import com.sumasoft.accountaggregator.util.GenerateJWSSignatureUtil;
import com.sumasoft.accountaggregator.util.HeaderGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.LOCAL_URL;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
@Component
public class FIPClientImpl implements FIPClient {
    private static final Logger logger = LogManager.getLogger(FIPClientImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    // Generate content signature in the compact serialization format.
    private final GenerateJWSSignatureUtil util = new GenerateJWSSignatureUtil();
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private FIPDao fipDao;
    @Autowired
    private HeaderGenerator headerGenerator;

    public FIPClientImpl() throws Exception {
    }

    /**
     * @param jsonObject
     * @return
     */
    @Override
    public ResponseEntity<?> accountDiscover(JSONObject jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Map map = new HashMap();
        AccountDiscoveryResponse accountDiscoveryResponse = new AccountDiscoveryResponse();
        try {
            // Generate consent string for signing.
            String accountDiscoverToSign = mapper.writeValueAsString(jsonObject);
            logger.info("generateAccountDiscover requestBody: " + accountDiscoverToSign);
            String signature = util.doSign(accountDiscoverToSign, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, accountDiscoverToSign);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            response = restTemplate.exchange(LOCAL_URL + "Accounts/discover", HttpMethod.POST, headerGenerator.generatePost(accountDiscoverToSign, signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                accountDiscoveryResponse = CommonUtil.json2Java(response.getBody(), AccountDiscoveryResponse.class);
                Map<String, List<String>> header = response.getHeaders();
                List<String> jws = header.get("x-jws-signature");
                fipDao.saveAccountDiscoverResponse(accountDiscoveryResponse);
            }
            /*} else {
                map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
                map.put("errorMessage", "Invalid Jws signature");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    /**
     * @param jsonObject
     * @return
     */
    @Override
    public ResponseEntity<?> accountsLink(JSONObject jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Map map = new HashMap();
        AccountLinkResponse accountLinkResponse = new AccountLinkResponse();
        try {
            // Generate consent string for signing.
            String accountsLinkToSign = mapper.writeValueAsString(jsonObject);
            logger.info("generateAccountsLink requestBody: " + accountsLinkToSign);
            String signature = util.doSign(accountsLinkToSign, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, accountsLinkToSign);
            // now validate the signature.
            // logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            response = restTemplate.exchange(LOCAL_URL + "Accounts/link", HttpMethod.POST, headerGenerator.generatePost(accountsLinkToSign, signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                accountLinkResponse = CommonUtil.json2Java(response.getBody(), AccountLinkResponse.class);
                Map<String, List<String>> header = response.getHeaders();
                List<String> jws = header.get("x-jws-signature");
                fipDao.saveLinkedAccounts(accountLinkResponse);
            }
            /*} else {
                map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
                map.put("errorMessage", "Invalid Jws signature");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> fetchAccountDetails(Map request) {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        FetchAccountsLinkResponse fetchAccountsLinkResponse = null;
        try {
            // Generate consent string for signing.
            String fetchAccountDetailsToSign = mapper.writeValueAsString(new JSONObject(request));
            String signature = util.doSign(fetchAccountDetailsToSign, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, fetchAccountDetailsToSign);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            String consent_url = LOCAL_URL + "Accounts/link/" + request.get("refNumber" + "/" + request.get("token"));
            //response = restTemplate.exchange(consent_url, HttpMethod.GET, headerGenerator.generateGet(signature), String.class);
            response = restTemplate.exchange(consent_url, HttpMethod.GET, null, String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                fetchAccountsLinkResponse = new FetchAccountsLinkResponse();
                fetchAccountsLinkResponse = CommonUtil.json2Java(response.getBody(), FetchAccountsLinkResponse.class);
                fipDao.saveAccountDetails(fetchAccountsLinkResponse);
            }
            /*} else {
                return new ResponseEntity<>("Invalid Jws signature", HttpStatus.BAD_REQUEST);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    /**
     * @param jsonObject
     * @return
     */
    @Override
    public ResponseEntity<?> deleteLinkedAccount(JSONObject jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Map map = new HashMap();
        AccountLinkResponse accountLinkResponse = new AccountLinkResponse();
        try {
            // Generate consent string for signing.
            String deleteLinkedAccountToSign = mapper.writeValueAsString(jsonObject);
            logger.info("generateAccountsLink requestBody: " + deleteLinkedAccountToSign);
            String signature = util.doSign(deleteLinkedAccountToSign, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, deleteLinkedAccountToSign);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            response = restTemplate.exchange(LOCAL_URL + "Accounts/link", HttpMethod.DELETE, headerGenerator.generatePost(deleteLinkedAccountToSign, signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                accountLinkResponse = CommonUtil.json2Java(response.getBody(), AccountLinkResponse.class);
                Map<String, List<String>> header = response.getHeaders();
                List<String> jws = header.get("x-jws-signature");
                fipDao.saveLinkedAccounts(accountLinkResponse);
            }
            /*} else {
                map.put("errorCode", HttpStatus.UNAUTHORIZED.value());
                map.put("errorMessage", "Invalid Jws signature");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }
}
