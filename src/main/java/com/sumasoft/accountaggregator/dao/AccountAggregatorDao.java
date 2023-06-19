package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.*;

public interface AccountAggregatorDao {
    Boolean saveGenerateConsentResponse(GenerateConsentResponse generateConsentResponse);

    Boolean saveFetchConset(FetchConsentResponse fetchConsentResponse);

    Boolean saveFetchSessionData(FIFetchSessionResponse fiFetchSessionResponse);

    FetchConsentResponse fetchConsentByConsentId(String consentId);

    Boolean saveFiReqeustResponse(FiRequestResponse fiRequestResponse);

    Boolean saveConsentHandleResponse(ConsentHandleResponse consentHandleResponse);

    GenerateConsentResponse fetchConsentByConsentHandle(String consentHandle);

    FiRequestResponse findBySessionId(String sessionId);
}
