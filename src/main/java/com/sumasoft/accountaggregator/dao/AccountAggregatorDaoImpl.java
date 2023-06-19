package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.*;
import com.sumasoft.accountaggregator.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountAggregatorDaoImpl implements AccountAggregatorDao {
    private static final Logger logger = LogManager.getLogger(AccountAggregatorDaoImpl.class);

    @Autowired
    private GenerateConsentResponseRepository generateConsentResponseRepository;

    @Autowired
    private FetchConsentRepository fetchConsentRepository;

    @Autowired
    private FIFetchSessionResponseRepository fIFetchSessionResponseRepository;

    @Autowired
    private FiRequestResponseRepository fiRequestResponseRepository;

    @Autowired
    private ConsentHandleResponseRespository consentHandleResponseRespository;


    /**
     * @param generateConsentResponseBody
     * @return
     */
    @Override
    public Boolean saveGenerateConsentResponse(GenerateConsentResponse generateConsentResponseBody) {
        Boolean result = false;
        try {
            GenerateConsentResponse generateConsentResponse = generateConsentResponseRepository.save(generateConsentResponseBody);
            if (generateConsentResponse != null) {
                result = true;
            }
        } catch (Exception exception) {
            logger.error(exception);
            result = false;
        }
        return result;
    }

    /**
     * @param fetchConsentResponse
     * @return
     */
    @Override
    public Boolean saveFetchConset(FetchConsentResponse fetchConsentResponse) {
        FetchConsentResponse response = null;
        try {
            response = fetchConsentRepository.findByConsentId(fetchConsentResponse.getConsentId());
            if (response != null) {
                fetchConsentRepository.delete(response);
                fetchConsentRepository.save(fetchConsentResponse);
            } else {
                response = fetchConsentRepository.save(fetchConsentResponse);
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
    }

    /**
     * @param fiFetchSessionResponseReqeustBody
     * @return
     */
    @Override
    public Boolean saveFetchSessionData(FIFetchSessionResponse fiFetchSessionResponseReqeustBody) {
        try {
            FIFetchSessionResponse fIFetchSessionRes = fIFetchSessionResponseRepository.save(fiFetchSessionResponseReqeustBody);
            if (fIFetchSessionRes != null) {
                return true;
            }
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
        return false;
    }

    /**
     * @param consentId
     * @return
     */
    @Override
    public FetchConsentResponse fetchConsentByConsentId(String consentId) {
        FetchConsentResponse response = null;
        try {
            response = fetchConsentRepository.findByConsentId(consentId);
        } catch (Exception exception) {
            logger.error(exception);
        }
        return response;
    }

    /**
     * @param fiRequestResponse
     * @return
     */
    @Override
    public Boolean saveFiReqeustResponse(FiRequestResponse fiRequestResponse) {
        try {
            FiRequestResponse response = fiRequestResponseRepository.findByConsentId(fiRequestResponse.getConsentId());
            if (response != null) {
                fiRequestResponseRepository.delete(response);
            }
            FiRequestResponse fiRequestResponseDetail = fiRequestResponseRepository.save(fiRequestResponse);
            if (fiRequestResponseDetail != null) {
                return true;
            }
        } catch (Exception exception) {
            logger.info(exception);
            return false;
        }
        return false;
    }

    /**
     * @param consentHandleResponse
     * @return
     */
    @Override
    public Boolean saveConsentHandleResponse(ConsentHandleResponse consentHandleResponse) {
        try {
            ConsentHandleResponse ConsentHandleResponseDtail = consentHandleResponseRespository.save(consentHandleResponse);
            if (ConsentHandleResponseDtail != null) {
                return true;
            }
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
        return false;
    }

    /**
     * @param consentHandle
     * @return
     */
    @Override
    public GenerateConsentResponse fetchConsentByConsentHandle(String consentHandle) {
        GenerateConsentResponse generateConsentResponse = null;
        try {
            generateConsentResponse = generateConsentResponseRepository.findByConsentHandle(consentHandle);
        } catch (Exception exception) {
            logger.error(exception);
        }
        return generateConsentResponse;
    }

    /**
     * @param sessionId
     * @return
     */
    @Override
    public FiRequestResponse findBySessionId(String sessionId) {
        return fiRequestResponseRepository.findBySessionId(sessionId);
    }
}
