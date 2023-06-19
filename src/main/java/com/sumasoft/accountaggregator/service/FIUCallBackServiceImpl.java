package com.sumasoft.accountaggregator.service;

import com.sumasoft.accountaggregator.client.AccountAggregatorClient;
import com.sumasoft.accountaggregator.dao.AccountAggregatorDao;
import com.sumasoft.accountaggregator.dao.FIUCallBackDao;
import com.sumasoft.accountaggregator.dto.*;
import com.sumasoft.accountaggregator.entity.ConsentNotification;
import com.sumasoft.accountaggregator.entity.FINotification;
import com.sumasoft.accountaggregator.util.CommonUtil;
import com.sumasoft.accountaggregator.util.ConsentStatusEnum;
import com.sumasoft.accountaggregator.util.ErrorCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.*;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
@Service
public class FIUCallBackServiceImpl implements FIUCallBackService {

    private static final Logger logger = LogManager.getLogger(FIUCallBackServiceImpl.class);
    Pattern pattern = Pattern.compile(UUID_REGEX);
    @Autowired
    private FIUCallBackDao fiuCallBackDao;
    @Autowired
    private AccountAggregatorClient accountAggregatorClient;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private AccountAggregatorDao accountAggregatorDao;

    /**
     * @param request
     * @param token
     * @param xjwsSignature
     * @return
     */
    @Override
    public ResponseEntity<?> conSentNotification(Map request, String token, String xjwsSignature) {
        ConsentNotification consentNotification = new ConsentNotification();
        Map map = new HashMap<>();
        try {
            String[] parts = token.split("\\.");
            //JSONObject header = new JSONObject(CommonUtil.decode(parts[0]));
            JSONObject payload = new JSONObject(CommonUtil.decode(parts[1]));
            //String signature = CommonUtil.decode(parts[2]);
            JSONObject jsonObject = new JSONObject(request);
            logger.info("Call Back API conSentNotification request Body:: " + jsonObject);
            consentNotification.setVer((String) request.get("ver"));
            consentNotification.setTimestamp((String) request.get("timestamp"));
            consentNotification.setTxnid(UUID.randomUUID().toString());
            consentNotification.setConsentStatusNotification(setConsentStatusNotificationRequestBody(request));
            consentNotification.setNotifier(setNotifier(request));
            //Pattern pattern = Pattern.compile(UUID_REGEX);
            consentNotification.getConsentStatusNotification().getConsentHandle();
            //scenario no. 1036
            if (!accountAggregatorClient.isValidSignature(xjwsSignature, jsonObject.toString())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.SignatureDoesNotMatch);
                map.put("errorMsg", "Invalid JWS Signature");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            //Scenario no. 1011
            else if (accountAggregatorDao.fetchConsentByConsentHandle(consentNotification.getConsentStatusNotification().getConsentHandle()) == null) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Request");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (!consentNotification.getNotifier().getId().equals(NOTIFIER_ID)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidNotifier);
                map.put("errorMsg", "Invalid Notifier Id");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (!consentNotification.getNotifier().getType().equals(NOTIFIER_TYPE)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidNotifier);
                map.put("errorMsg", "Invalid notifier type");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (!pattern.matcher(consentNotification.getConsentStatusNotification().getConsentId()).matches()) {
                map.put("ver", VER);
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", consentNotification.getTxnid());
                map.put("errorCode", ErrorCodeEnum.InvalidConsentId);
                map.put("errorMsg", "Invalid consentId");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            //check schematic error
            else if (!(jsonObject.has("ver") && jsonObject.has("timestamp") && jsonObject.has("Notifier") && jsonObject.getJSONObject("Notifier").has("type") && jsonObject.getJSONObject("Notifier").has("id") && jsonObject.has("ConsentStatusNotification") && jsonObject.getJSONObject("ConsentStatusNotification").has("consentId") && jsonObject.getJSONObject("ConsentStatusNotification").has("consentHandle") && jsonObject.getJSONObject("ConsentStatusNotification").has("consentStatus"))) {
                map.put("ver", VER);
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", consentNotification.getTxnid());
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Schema");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                //checking 15min variation in timestamp
            } else if (!commonUtil.dateDiff(consentNotification.getTimestamp())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid timestamp");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                //checking Invalid ver error
            } else if (consentNotification.getVer().equals(null) || !consentNotification.getVer().equals(VER)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.NoSuchVersion);
                map.put("errorMsg", "Invalid Version");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                //checking Invalid timestamp format
            } else if (!commonUtil.isValid(consentNotification.getTimestamp())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid timestamp format");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                //alternate AA with invalid timestamp format
            } else if (!commonUtil.isValid(consentNotification.getTimestamp()) && !consentNotification.getNotifier().getType().equals(NOTIFIER_TYPE)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid AA and Invalid timestamp format");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (consentNotification.getConsentStatusNotification().getConsentStatus().equals(ConsentStatusEnum.PAUSED.name())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "consentStatus is PAUSED , user is not able to make FI request");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (consentNotification.getConsentStatusNotification().getConsentStatus().equals(ConsentStatusEnum.EXPIRED.name())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "consentStatus is EXPIRED , user is not able to make FI request");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else if (consentNotification.getConsentStatusNotification().getConsentStatus().equals(ConsentStatusEnum.REVOKED.name())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "consentStatus is REVOKED , user is not able to make FI request");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            //with Invalid API key error
            else if (!payload.get("clientId").toString().equals(NOTIFIER_ID) && !commonUtil.dateTimeDiff((Integer) payload.get("exp"))) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "client Id and expiry time invalid");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
            //with alternate AA with API key
            else if (!payload.get("roles").toString().equals(NOTIFIER_TYPE) && !commonUtil.dateTimeDiff((Integer) payload.get("exp"))) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "client Id and expiry time invalid");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            } else {
                //
                fiuCallBackDao.saveConsentNotification(consentNotification);
                map.put("ver", VER);
                map.put("timestamp", DATEFORMATTER.format(new Date()));
                map.put("txnid", consentNotification.getTxnid());
                map.put("response", "OK");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (DateTimeParseException exception) {
            logger.error(exception);
            map.put("ver", request.get("ver").toString());
            map.put("timestamp", request.get("timestamp"));
            map.put("txnid", request.get("txnid"));
            map.put("errorCode", ErrorCodeEnum.InvalidRequest);
            map.put("errorMsg", "Invalid timestamp format");
            logger.info("Call Back API conSentNotification response Body:: " + map);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
    }

    /**
     * @param request
     * @param token
     * @param xjwsSignature
     * @return
     */
    @Override
    public ResponseEntity<?> fINotification(Map request, String token, String xjwsSignature) {
        FINotification fiNotification = new FINotification();
        Map map = new HashMap();
        try {
            String[] parts = token.split("\\.");
            JSONObject header = new JSONObject(CommonUtil.decode(parts[0]));
            JSONObject payload = new JSONObject(CommonUtil.decode(parts[1]));
            //String signature = CommonUtil.decode(parts[2]);
            JSONObject jsonObject = new JSONObject(request);

            logger.info("Call Back API FINotification request Body:: " + jsonObject);
            fiNotification.setVer((String) request.get("ver"));
            fiNotification.setTimestamp((String) request.get("timestamp"));
            fiNotification.setTxnid((String) request.get("txnid"));
            fiNotification.setNotifier(setNotifier(request));
            fiNotification.setFIStatusNotification(setFIStatusNotification(request));
            // scenario no. 2003 (Invalid Version)
            if (!fiNotification.getVer().equals(VER)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.NoSuchVersion);
                map.put("errorMsg", "Invalid Version");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2004 (Invalid session Id)
            else if (accountAggregatorDao.findBySessionId(fiNotification.getFIStatusNotification().getSessionId()) == null) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidSessionId);
                map.put("errorMsg", "Invalid Session");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2005 (Alternate AA In Notifier Id)
            else if (!fiNotification.getNotifier().getId().equals(NOTIFIER_ID)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Notifier Id");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2006 (Invalid schema)
            else if (!(jsonObject.has("ver") && !jsonObject.get("ver").equals("") && jsonObject.has("txnid") && !jsonObject.get("txnid").equals("") && jsonObject.has("timestamp") && !jsonObject.get("timestamp").equals("") && jsonObject.has("Notifier") && jsonObject.getJSONObject("Notifier").has("id") && !jsonObject.getJSONObject("Notifier").get("id").equals("") && jsonObject.getJSONObject("Notifier").has("type") && !jsonObject.getJSONObject("Notifier").get("type").equals("") && jsonObject.has("FIStatusNotification") && jsonObject.getJSONObject("FIStatusNotification").has("sessionStatus") && !jsonObject.getJSONObject("FIStatusNotification").get("sessionStatus").equals("") && jsonObject.getJSONObject("FIStatusNotification").has("sessionId") && !jsonObject.getJSONObject("FIStatusNotification").get("sessionId").equals("") && !new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).isEmpty() && ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).has("Accounts") && ((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).has("linkRefNumber") && !((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).get("linkRefNumber").equals("") && ((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).has("description") && !((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).get("description").equals("") && ((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).has("FIStatus") && !((JSONObject) ((JSONObject) new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0)).getJSONArray("Accounts").get(0)).get("FIStatus").equals("") && ((JSONObject) (new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0))).has("fipID") && !((JSONObject) (new JSONArray(jsonObject.getJSONObject("FIStatusNotification").getJSONArray("FIStatusResponse")).get(0))).get("fipID").equals(""))) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Schematic Error");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2007 (Invalid Transaction Id)
            else if (!pattern.matcher(fiNotification.getTxnid()).matches()) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Transaction Id");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2008 (checking 15min variation in timestamp)
            else if (!commonUtil.dateDiff(fiNotification.getTimestamp())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Timestamp");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2009 (selected details of alternate AA case 1: Different AA id in notifier Id)
            else if (!fiNotification.getNotifier().getId().equals(NOTIFIER_ID)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Notifier Id");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2009 (selected details of alternate AA case 2: Session id generated for different AA)
            else if (accountAggregatorDao.findBySessionId(fiNotification.getFIStatusNotification().getSessionId()) != null && !fiNotification.getNotifier().getId().equals(NOTIFIER_ID)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidSessionId);
                map.put("errorMsg", "Invalid Notifier Type");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2009 (selected details of alternate AA case 3: Account details of different AA)
            /*else if (!fiNotification.getNotifier().getId().equals(NOTIFIER_ID)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Notifier Id");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }*/ //scenario no. 2011 (FIStatusNotification.sessionStatus as EXPIRED)
            else if (fiNotification.getFIStatusNotification().getSessionStatus().equals(ConsentStatusEnum.EXPIRED.name())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Session Status Expired");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2012 (invalid notifier type)
            else if (!fiNotification.getNotifier().getType().equals(NOTIFIER_TYPE)) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Invalid Notifier Type");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2012 (FIStatusNotification.sessionStatus as FAILED)
            else if (fiNotification.getFIStatusNotification().getSessionStatus().equals(ConsentStatusEnum.FAILED.name())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "Session Status Failed");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2027 (invalid JWS signature)
            else if (!accountAggregatorClient.isValidSignature(xjwsSignature, jsonObject.toString())) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", request.get("timestamp"));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.SignatureDoesNotMatch);
                map.put("errorMsg", "Invalid JWS Signature");
                logger.info("Call Back API FINotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }//scenario no. 2028 (invalid API key)
            else if (!payload.get("clientId").toString().equals(NOTIFIER_ID) && !commonUtil.dateTimeDiff((Integer) payload.get("exp"))) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "client Id and expiry time invalid");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }//scenario no. 2029 with alternate AA with API key
            else if (!payload.get("roles").toString().equals(NOTIFIER_TYPE) && !commonUtil.dateTimeDiff((Integer) payload.get("exp"))) {
                map.put("ver", request.get("ver").toString());
                map.put("timestamp", DATE_FORMATTER.format(new Date()));
                map.put("txnid", request.get("txnid"));
                map.put("errorCode", ErrorCodeEnum.InvalidRequest);
                map.put("errorMsg", "client Id and expiry time invalid");
                logger.info("Call Back API conSentNotification response Body:: " + map);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            fiuCallBackDao.savefINotification(fiNotification);
            map.put("ver", "1.0");
            map.put("timestamp", DATE_FORMATTER.format(new Date()));
            map.put("txnid", fiNotification.getTxnid());
            map.put("response", "OK");
            logger.info("Call Back API FINotification response Body:: " + map);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error(exception);
            map.put("ver", request.get("ver").toString());
            map.put("timestamp", request.get("timestamp"));
            map.put("txnid", request.get("txnid"));
            map.put("errorCode", ErrorCodeEnum.InvalidRequest);
            map.put("errorMsg", "Invalid timestamp format");
            logger.info("Call Back API FINotification response Body:: " + map);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity checkLinkNotification(Map request) {
        ResponseEntity entity = null;
        NotifierDto notifier = new NotifierDto();
        AccountLinkStatusNotificationDto accountLinkStatusNotificationDto = new AccountLinkStatusNotificationDto();
        AccountLinkNotificationDto accountLinkNotificationDto = new AccountLinkNotificationDto();
        try {
            if (request.containsKey("type") && request.containsKey("id") && !request.get("type").toString().equalsIgnoreCase("") && !request.get("id").toString().equalsIgnoreCase("")) {
                notifier.setId(request.get("id").toString());
                notifier.setType(request.get("type").toString());
            }
            if (request.containsKey("accRefNumber") && request.containsKey("customerAddress") && request.containsKey("linkRefNumber") && request.containsKey("linkStatus") && !request.get("accRefNumber").toString().equalsIgnoreCase("") && !request.get("customerAddress").toString().equalsIgnoreCase("") && !request.get("linkRefNumber").toString().equalsIgnoreCase("") && !request.get("linkStatus").toString().equalsIgnoreCase("")) {
                accountLinkStatusNotificationDto.setLinkStatus(request.get("linkStatus").toString());
                accountLinkStatusNotificationDto.setAccRefNumber(request.get("accRefNumber").toString());
                accountLinkStatusNotificationDto.setLinkRefNumber(request.get("linkRefNumber").toString());
                accountLinkStatusNotificationDto.setCustomerAddress(request.get("customerAddress").toString());
            }
            accountLinkNotificationDto.setVer(VER);
            accountLinkNotificationDto.setNotifier(notifier);
            accountLinkNotificationDto.setTimestamp(DATEFORMATTER.format(new Date()));
            accountLinkNotificationDto.setTxnid(UUID.randomUUID().toString());
            accountLinkNotificationDto.setAccountLinkStatusNotificationDto(accountLinkStatusNotificationDto);

            entity = accountAggregatorClient.getAccountLinkNotification(accountLinkNotificationDto);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(accountLinkNotificationDto, HttpStatus.OK);
    }

    private FIStatusNotificationDto setFIStatusNotification(Map request) {
        FIStatusNotificationDto fiStatusNotificationDto = new FIStatusNotificationDto();
        try {
            if (request.containsKey("FIStatusNotification")) {
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) request.get("FIStatusNotification");
                fiStatusNotificationDto.setSessionId(map.get("sessionId"));
                fiStatusNotificationDto.setSessionStatus(map.get("sessionStatus"));
                fiStatusNotificationDto.setFIStatusResponse((ArrayList<FIStatusResponseDto>) ((LinkedHashMap) map).get("FIStatusResponse"));
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
        return fiStatusNotificationDto;
    }

    private NotifierDto setNotifier(Map request) {
        NotifierDto notifierDto = new NotifierDto();
        try {
            if (request.containsKey("Notifier")) {
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) request.get("Notifier");
                notifierDto.setType(map.get("type"));
                notifierDto.setId(map.get("id"));
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
        return notifierDto;
    }

    private ConsentStatusNotificationDto setConsentStatusNotificationRequestBody(Map request) {
        ConsentStatusNotificationDto consentStatusNotificationDto = new ConsentStatusNotificationDto();
        try {
            if (!request.isEmpty()) {
                LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) request.get("ConsentStatusNotification");
                consentStatusNotificationDto.setConsentId(linkedHashMap.get("consentId"));
                consentStatusNotificationDto.setConsentHandle(linkedHashMap.get("consentHandle"));
                consentStatusNotificationDto.setConsentStatus(linkedHashMap.get("consentStatus"));
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
        return consentStatusNotificationDto;
    }
}
