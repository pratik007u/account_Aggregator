package com.sumasoft.accountaggregator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumasoft.accountaggregator.dao.AccountAggregatorDao;
import com.sumasoft.accountaggregator.dto.AccountLinkNotificationDto;
import com.sumasoft.accountaggregator.dto.GenerateConsentRequest;
import com.sumasoft.accountaggregator.entity.*;
import com.sumasoft.accountaggregator.util.CommonUtil;
import com.sumasoft.accountaggregator.util.GenerateJWSSignatureUtil;
import com.sumasoft.accountaggregator.util.HeaderGenerator;
import com.sumasoft.accountaggregator.util.VerifyJWSSignatureUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.lang.InvalidKeyException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.LOCAL_URL;
import static com.sumasoft.accountaggregator.constant.GlobalConstant.VER;

@Component
public class AccountAggregatorClientImpl implements AccountAggregatorClient {
    private static final Logger logger = LogManager.getLogger(AccountAggregatorClientImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    // Generate content signature in the compact serialization format.
    private final GenerateJWSSignatureUtil util = new GenerateJWSSignatureUtil();
    private final VerifyJWSSignatureUtil verifyJWSSignatureUtil = new VerifyJWSSignatureUtil();
    @Autowired
    private AccountAggregatorDao accountAggregatorDao;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private HeaderGenerator headerGenerator;

    public AccountAggregatorClientImpl() throws Exception {
    }

    public ResponseEntity<?> generateConsent(GenerateConsentRequest generateConsentRequest) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        GenerateConsentResponse generateConsentResponse = new GenerateConsentResponse();
        Map map = new HashMap();
        String jwsSignature = null;
        JSONObject json = new JSONObject();
        try {
            // Generate consent string for signing.
            String consentDetailToSign = mapper.writeValueAsString(generateConsentRequest);
            logger.info("generateConsent requestBody: " + consentDetailToSign);
            //String signature = util.signEmbedded(consentDetailToSign);
            String signature = util.doSign(consentDetailToSign, true);
            // logger.info("consent artifact: " + new String(Base64.getDecoder().decode(verifierJws.getEncodedPayload())));
            //headers.set("fip_api_key", fipKey);
            response = restTemplate.exchange(LOCAL_URL + "Consent", HttpMethod.POST, headerGenerator.generatePost(consentDetailToSign, signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                generateConsentResponse = CommonUtil.json2Java(response.getBody(), GenerateConsentResponse.class);
                if (!(jsonObject.has("ver") && jsonObject.has("timestamp") && jsonObject.has("Customer") && jsonObject.getJSONObject("Customer").has("id") && jsonObject.has("ConsentHandle"))) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid Schema");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                } else if (generateConsentResponse.getVer() == null || !generateConsentResponse.getVer().equals(VER)) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid Version");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                } else if (!commonUtil.isValid(generateConsentResponse.getTimestamp())) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid Date Format");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                } else if (!commonUtil.dateDiff(generateConsentResponse.getTimestamp())) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "TimeStamp Expired");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                } else if (!generateConsentResponse.getCustomer().getId().equals(generateConsentRequest.getConsentDetail().getCustomer().getId())) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid Customer Id");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                } else if (!generateConsentResponse.getTxnid().equals(generateConsentRequest.getTxnid())) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid Transaction Id");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
                //validate jws signature
                else if (!isValidJwsSignature(new JSONObject(response.getHeaders()), response.getBody())) {
                    map.put("errorCode", HttpStatus.BAD_REQUEST.value());
                    map.put("errorMessage", "Invalid JWS Signature");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
                accountAggregatorDao.saveGenerateConsentResponse(generateConsentResponse);
            }
        } catch (InvalidKeyException invalidKeyException) {
            logger.error(invalidKeyException);
            return new ResponseEntity<>(invalidKeyException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<?> checkConsentHandleStatus(String consentHandle) throws Exception {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Generate consent string for signing.
            String signature = util.doSign(consentHandle, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, consentHandle);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            String consent_url = LOCAL_URL + "Consent/handle/" + consentHandle;
            response = restTemplate.exchange(consent_url, HttpMethod.GET, null, String.class);
            //response = restTemplate.exchange(consent_url, HttpMethod.GET, headerGenerator.generateGet(signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                ConsentHandleResponse consentHandleResponse = new ConsentHandleResponse();
                consentHandleResponse = CommonUtil.json2Java(response.getBody(), ConsentHandleResponse.class);
                accountAggregatorDao.saveConsentHandleResponse(consentHandleResponse);
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

    @Override
    public ResponseEntity<?> fetchConsent(String id) throws Exception {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        FetchConsentResponse fetchConsentResponse = null;
        try {
            // Generate consent string for signing.
            String signature = util.doSign(id, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, id);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            String consent_url = LOCAL_URL + "Consent/" + id;
            //response = restTemplate.exchange(consent_url, HttpMethod.GET, headerGenerator.generateGet(signature), String.class);
            response = restTemplate.exchange(consent_url, HttpMethod.GET, null, String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                if (jsonObject.has("is_checked") && jsonObject.get("is_checked").equals(true)) {
                    return new ResponseEntity<>("Provided ConsentId Invalid", HttpStatus.BAD_REQUEST);
                }//scenario no. 1003 (The consent id in the api should match with the consent id provided by AA)
                else if (!jsonObject.get("consentId").equals(id)) {
                    return new ResponseEntity<>("Provided ConsentId Invalid", HttpStatus.BAD_REQUEST);
                }
                    fetchConsentResponse = new FetchConsentResponse();
                    fetchConsentResponse = CommonUtil.json2Java(response.getBody(), FetchConsentResponse.class);
                    accountAggregatorDao.saveFetchConset(fetchConsentResponse);
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
     * @param fiRequestBody
     * @return
     */
    @Override
    public ResponseEntity fetchFiRequest(String fiRequestBody) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        FiRequestResponse fiRequestResponse = new FiRequestResponse();
        try {
            // Generate consent string for signing.
            String signature = util.doSign(fiRequestBody, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, fiRequestBody);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            //headers.set("fip_api_key", fipKey);
            response = restTemplate.exchange(LOCAL_URL + "FI/request", HttpMethod.POST, headerGenerator.generatePost(fiRequestBody, signature), String.class);
            logger.info(response.getBody());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                fiRequestResponse = CommonUtil.json2Java(response.getBody(), FiRequestResponse.class);
                accountAggregatorDao.saveFiReqeustResponse(fiRequestResponse);
            }
           /* } else {
                return new ResponseEntity<>("Invalid Jws signature", HttpStatus.BAD_REQUEST);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    /**
     * @param sessionId
     * @return
     */
    @Override
    public ResponseEntity<?> fetchSessionDataById(String sessionId) throws Exception {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        FIFetchSessionResponse fiFetchSessionResponse = null;
        try {
            // Generate consent string for signing.
            String signature = util.doSign(sessionId, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, sessionId);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            String consent_url = LOCAL_URL + "FI/fetch/" + sessionId;
            response = restTemplate.exchange(consent_url, HttpMethod.GET, null, String.class);
            //response = restTemplate.exchange(consent_url, HttpMethod.GET, headerGenerator.generateGet(signature), String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                fiFetchSessionResponse = new FIFetchSessionResponse();
                fiFetchSessionResponse = CommonUtil.json2Java(response.getBody(), FIFetchSessionResponse.class);
                accountAggregatorDao.saveFetchSessionData(fiFetchSessionResponse);
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
     * @param accountLinkNotificationDto
     * @return
     */
    @Override
    public ResponseEntity getAccountLinkNotification(AccountLinkNotificationDto accountLinkNotificationDto) throws Exception {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Generate consent string for signing.
            String consentDetailToSign = mapper.writeValueAsString(accountLinkNotificationDto);
            String signature = util.doSign(consentDetailToSign, true);
            //JsonWebSignature verifierJws = util.parseSign(signature, consentDetailToSign);
            // now validate the signature.
            //logger.info("Signature valid?: " + verifierJws.verifySignature());
            //if (verifierJws.verifySignature()) {
            String consent_url = LOCAL_URL + "Account/link/Notification";
            response = restTemplate.exchange(consent_url, HttpMethod.POST, headerGenerator.generatePost(signature, consentDetailToSign), String.class);
            logger.info(response.getBody());
            /*} else {
                return new ResponseEntity<>("Invalid Jws signature", HttpStatus.BAD_REQUEST);
            }*/
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;
    }

    public Boolean isValidJwsSignature(JSONObject jsonObject, String body) {
        try {
            JSONArray jsonArray = (JSONArray) jsonObject.get("x-jws-signature");
            return verifyJWSSignatureUtil.parseSign(jsonArray.getString(0), body).verifySignature();
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
    }

    /**
     * @param jwsSignature
     * @param body
     * @return
     */
    @Override
    public Boolean isValidSignature(String jwsSignature, String body) {
        try {
            return verifyJWSSignatureUtil.parseSign(jwsSignature, body).verifySignature();
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
    }
}