package com.sumasoft.accountaggregator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumasoft.accountaggregator.client.AccountAggregatorClient;
import com.sumasoft.accountaggregator.dao.AccountAggregatorDao;
import com.sumasoft.accountaggregator.dto.*;
import com.sumasoft.accountaggregator.entity.FetchConsentResponse;
import com.sumasoft.accountaggregator.entity.UserAccount;
import com.sumasoft.accountaggregator.repository.FetchConsentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.*;

@Service
public class AccountAggregatorServiceImpl implements AccountAggregatorService {
    private static final Logger logger = LogManager.getLogger(AccountAggregatorServiceImpl.class);
    @Autowired
    private AccountAggregatorClient accountAggregatorClient;

    @Autowired
    private AccountAggregatorDao accountAggregatorDao;
    @Autowired
    private FetchConsentRepository fetchConsentRepository;

    @Autowired
    private ECCService eccService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity<?> generateConsent(Map requestBody) throws Exception {
        Map response = new HashMap<>();
        ConsentDetailDto consentDetailsDto = new ConsentDetailDto();
        UserAccount account = new UserAccount();
        GenerateConsentRequest generateConsentRequest = new GenerateConsentRequest();
        DataConsumerDto dataConsumer = new DataConsumerDto();
        CustomerDto customer = new CustomerDto();
        PurposeDto PurposeDto = new PurposeDto();
        Frequency Frequency = new Frequency();
        Frequency DataLife = new Frequency();
        FIDataRangeDto fIDataRangeDto = new FIDataRangeDto();
        DataFilter dataFilter = new DataFilter();
        Date date = new Date();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ResponseEntity<?> entity = null;
        try {
            if (requestBody.containsKey("consentStart") && requestBody.containsKey("consentExpiry") && !requestBody.get("consentStart").toString().equalsIgnoreCase("") && !requestBody.get("consentExpiry").toString().equalsIgnoreCase("")) {
                Date startDate = formatter.parse(requestBody.get("consentStart").toString());
                Date endDate = formatter.parse(requestBody.get("consentExpiry").toString());
                consentDetailsDto.setConsentStart(dateFormatter.format(startDate));
                consentDetailsDto.setConsentExpiry(dateFormatter.format(endDate));
            }
            if (requestBody.containsKey("consentMode") && requestBody.get("consentMode") != null && !requestBody.get("consentMode").toString().equalsIgnoreCase(""))
                consentDetailsDto.setConsentMode(requestBody.get("consentMode").toString());
            if (requestBody.containsKey("fetchType") && requestBody.get("fetchType") != null && !requestBody.get("fetchType").toString().equalsIgnoreCase(""))
                consentDetailsDto.setFetchType(requestBody.get("fetchType").toString());
            if (requestBody.containsKey("DataConsumer") && requestBody.get("DataConsumer") != null && !requestBody.get("DataConsumer").toString().equalsIgnoreCase("")) {
                dataConsumer.setId(requestBody.get("DataConsumer").toString());
                consentDetailsDto.setDataConsumer(dataConsumer);
            }
            if (requestBody.containsKey("Customer") && requestBody.get("Customer") != null && !requestBody.get("Customer").toString().equalsIgnoreCase("")) {
                customer.setId(requestBody.get("Customer").toString());
                consentDetailsDto.setCustomer(customer);
            }
            if (requestBody.containsKey("FIDataRangeFrom") && requestBody.get("FIDataRangeFrom") != null && !requestBody.get("FIDataRangeFrom").toString().equalsIgnoreCase("") && requestBody.containsKey("FIDataRangeTo") && requestBody.get("FIDataRangeTo") != null && !requestBody.get("FIDataRangeTo").toString().equalsIgnoreCase("")) {
                Date fistartDate = formatter.parse(requestBody.get("FIDataRangeFrom").toString());
                Date fiendDate = formatter.parse(requestBody.get("FIDataRangeTo").toString());
                fIDataRangeDto.setFrom(dateFormatter.format(fistartDate));
                fIDataRangeDto.setTo(dateFormatter.format(fiendDate));

                consentDetailsDto.setfIDataRange(fIDataRangeDto);
            }
            if (requestBody.containsKey("dataUnit") && requestBody.get("dataUnit") != null && !requestBody.get("dataUnit").toString().equalsIgnoreCase("") && requestBody.containsKey("dataValue") && requestBody.get("dataValue") != null && !requestBody.get("dataValue").toString().equalsIgnoreCase("")) {
                DataLife.setUnit(requestBody.get("dataUnit").toString());
                DataLife.setValue(Integer.parseInt(requestBody.get("dataValue").toString()));
                consentDetailsDto.setDataLife(DataLife);
            }
            if (requestBody.containsKey("frequencyUnit") && requestBody.get("frequencyUnit") != null && !requestBody.get("frequencyUnit").toString().equalsIgnoreCase("") && requestBody.containsKey("frequencyValue") && requestBody.get("frequencyValue") != null && !requestBody.get("frequencyValue").toString().equalsIgnoreCase("")) {
                Frequency.setUnit(requestBody.get("frequencyUnit").toString());
                Frequency.setValue(Integer.parseInt(requestBody.get("frequencyValue").toString()));
                consentDetailsDto.setFrequency(Frequency);
            }
            PurposeDto.setText("Wealth management service");
            PurposeDto.setRefUri("https://api.rebit.org.in/aa/purpose/101.xml");
            PurposeDto.setCode("101");
            PurposeDto.setCategory(new CategoryDto());
            consentDetailsDto.setPurpose(PurposeDto);
            List<String> consentTypesList = new ArrayList<>();
            consentTypesList.add("TRANSACTIONS");
            //consentTypesList.add("Attachment");
            consentDetailsDto.setConsentTypes(consentTypesList);

            List<String> fiTypesList = new ArrayList<>();
            fiTypesList.add("DEPOSIT");
            //fiTypesList.add("CREDIT");
            consentDetailsDto.setFiTypes(fiTypesList);
            List<DataFilter> dataFilterList = new ArrayList<>();
            if (requestBody.containsKey("type") && requestBody.get("type") != null && !requestBody.get("type").toString().equalsIgnoreCase("") && requestBody.containsKey("operator") && requestBody.get("operator") != null && !requestBody.get("operator").toString().equalsIgnoreCase("") && requestBody.containsKey("value") && requestBody.get("value") != null && !requestBody.get("value").toString().equalsIgnoreCase("")) {
                dataFilter = new DataFilter();
                dataFilter.setOperator(requestBody.get("operator").toString());
                dataFilter.setType(requestBody.get("type").toString());
                dataFilter.setValue(requestBody.get("value").toString());
                dataFilterList.add(dataFilter);
            }
            consentDetailsDto.setDataFilter(dataFilterList);


            generateConsentRequest.setConsentDetail(consentDetailsDto);
            generateConsentRequest.setVer(VER);
            generateConsentRequest.setTxnid(String.valueOf(UUID.randomUUID()));
            generateConsentRequest.setTimestamp(dateFormatter.format(date));

            //response.put("data", generateConsentRequest);
            entity = accountAggregatorClient.generateConsent(generateConsentRequest);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return entity;
    }

    @Override
    public ResponseEntity<?> checkConsentHandleStatus(String consentHandle) throws Exception {
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorClient.checkConsentHandleStatus(consentHandle);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return entity;
    }

    @Override
    public ResponseEntity<?> fetchConsent(String id) throws Exception {
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorClient.fetchConsent(id);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return entity;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> fetchFiRequest(Map request) {
        FIRequestDto fiRequestDto = new FIRequestDto();
        ResponseEntity entity = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        FIDataRangeDto fiDataRange = new FIDataRangeDto();
        DHPublicKeyDto dHPublicKey = new DHPublicKeyDto();
        Map map = new HashMap();
        try {
            if (!request.isEmpty()) {
                if (request.containsKey("FIDataRangeFrom") && request.containsKey("FIDataRangeTo") && !request.get("FIDataRangeFrom").toString().equalsIgnoreCase("") && !request.get("FIDataRangeTo").toString().equalsIgnoreCase("")) {
                    Date fistartDate = formatter.parse(request.get("FIDataRangeFrom").toString());
                    Date fiendDate = formatter.parse(request.get("FIDataRangeTo").toString());
                    fiDataRange.setFrom(dateFormat.format(fistartDate));
                    fiDataRange.setTo(dateFormat.format(fiendDate));
                }
//                if (request.containsKey("expiry") && request.containsKey("Parameters") && request.containsKey("KeyValue") && !request.get("expiry").toString().equalsIgnoreCase("") && !request.get("Parameters").toString().equalsIgnoreCase("") && !request.get("KeyValue").toString().equalsIgnoreCase("")) {
//                    Date expiry = formatter.parse(request.get("expiry").toString());
//                    dHPublicKey.setParameters(request.get("Parameters").toString());
//                    dHPublicKey.setExpiry(dateFormat.format(expiry).toString());
//                    dHPublicKey.setKeyValue(request.get("KeyValue").toString());
//                }
                map.put(VERSION, VER);
                map.put(TXNID, UUID.randomUUID());
                map.put(TIMESTAMP, DATEFORMATTER.format(new Date()));
                logger.info("Generate Key");
//                SerializedKeyPair serializedKeyPair = eccService.getKeyPair();
                map.put("KeyMaterial", eccService.getKeyPair().getKeyMaterial());
                map.put("FIDataRange", fiDataRange);
                map.put("Consent", setConsentBody(request.get("consentId").toString()));
                String consentDetailToSign = mapper.writeValueAsString(map);
                entity = accountAggregatorClient.fetchFiRequest(consentDetailToSign);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return entity;
    }

    private ConsentDto setConsentBody(String consentId) {
        FetchConsentResponse fetchConsentResponse = null;
        ConsentDto consentDto = null;
        try {
            fetchConsentResponse = accountAggregatorDao.fetchConsentByConsentId(consentId);
            if (fetchConsentResponse != null) {
                consentDto = new ConsentDto();
                consentDto.setId(consentId);
                consentDto.setDigitalSignature(fetchConsentResponse.getSignedConsent());
            }
        } catch (Exception exception) {
            logger.error(exception);
        }
        return consentDto;
    }

    /**
     * @param sessionId
     * @return
     */
    @Override
    public ResponseEntity fetchSessionDataById(String sessionId) {
        ResponseEntity<?> response = null;
        try {
            response = accountAggregatorClient.fetchSessionDataById(sessionId);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return response;

    }

//    private KeyMaterialDto setKeyMaterialBody(Map request) {
//        KeyMaterialDto keyMaterialDto = new KeyMaterialDto();
//        DHPublicKeyDto dhPublicKeyDto = new DHPublicKeyDto();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            if (request.containsKey("params") && request.containsKey("curve") && request.containsKey("cryptoAlg") && request.containsKey("cryptoAlg") && !request.get("params").toString().equalsIgnoreCase("") && !request.get("curve").toString().equalsIgnoreCase("") && !request.get("cryptoAlg").toString().equalsIgnoreCase("") && !request.get("Nonce").toString().equalsIgnoreCase("")) {
//                keyMaterialDto.setNonce(generageNance());
//                keyMaterialDto.setParams(request.get("params").toString());
//                keyMaterialDto.setCurve(request.get("curve").toString());
//                keyMaterialDto.setCryptoAlg(request.get("cryptoAlg").toString());
//            }
//            if (request.containsKey("expiry") && request.containsKey("Parameters") && request.containsKey("KeyValue") && !request.get("expiry").toString().equalsIgnoreCase("") && !request.get("Parameters").toString().equalsIgnoreCase("") && !request.get("KeyValue").toString().equalsIgnoreCase("")) {
//                Date expiry = formatter.parse(request.get("expiry").toString());
//                dhPublicKeyDto.setKeyValue(request.get("KeyValue").toString());
//                dhPublicKeyDto.setExpiry(dateFormat.format(expiry).toString());
//                dhPublicKeyDto.setParameters(request.get("Parameters").toString());
//            }
//            keyMaterialDto.setdHPublicKey(dhPublicKeyDto);
//
//            return keyMaterialDto;
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
//        return keyMaterialDto;
//    }

    @Override
    public String generateNance() {
        Random random = ThreadLocalRandom.current();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        String encoded = Base64.getUrlEncoder().encodeToString(randomBytes);
        return encoded;
    }
}