package com.sumasoft.accountaggregator.service;

import com.sumasoft.accountaggregator.al.dto.AccountLinkRequest;
import com.sumasoft.accountaggregator.al.dto.AccountsDto;
import com.sumasoft.accountaggregator.al.dto.CustomerDto;
import com.sumasoft.accountaggregator.client.FIPClient;
import com.sumasoft.accountaggregator.dao.FIPDao;
import com.sumasoft.accountaggregator.dto.AccountDiscoverRequest;
import com.sumasoft.accountaggregator.dto.FIPCustomerDto;
import com.sumasoft.accountaggregator.dto.Identifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.DATEFORMATTER;
import static com.sumasoft.accountaggregator.constant.GlobalConstant.VER;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
@Service
public class FIPServiceImpl implements FIPService {
    private static final Logger logger = LogManager.getLogger(FIPServiceImpl.class);

    @Autowired
    private FIPDao fipDao;

    @Autowired
    private FIPClient fipClient;

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> accountDiscover(Map request) {
        AccountDiscoverRequest accountDiscoverRequest = new AccountDiscoverRequest();
        Date date = new Date();
        ResponseEntity<?> entity = null;
        Identifiers identifiers = new Identifiers();
        ArrayList<Identifiers> identifiersArrayList = new ArrayList<>();
        FIPCustomerDto fipCustomerDto = new FIPCustomerDto();
        List<String> fiTypes = new ArrayList<>();
        try {
            accountDiscoverRequest.setVer(VER);
            accountDiscoverRequest.setTimestamp(DATEFORMATTER.format(date));
            accountDiscoverRequest.setTxnid(UUID.randomUUID().toString());
            identifiers.setCategory(request.get("category").toString());
            identifiers.setType(request.get("type").toString());
            identifiers.setValue(request.get("value").toString());
            identifiersArrayList.add(identifiers);
            fipCustomerDto.setIdentifiers(identifiersArrayList);
            fipCustomerDto.setId(request.get("customerId").toString());
            accountDiscoverRequest.setCustomer(fipCustomerDto);
            fiTypes.add(request.get("fiTypes").toString());
            accountDiscoverRequest.setFiTypes(fiTypes);
            entity = fipClient.accountDiscover(new JSONObject(accountDiscoverRequest));
            logger.info(accountDiscoverRequest);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> accountsLink(Map request) {
        AccountLinkRequest accountLinkRequest = new AccountLinkRequest();
        Date date = new Date();
        ResponseEntity<?> entity = null;
        CustomerDto customer = new CustomerDto();
        AccountsDto accountsDto = new AccountsDto();
        ArrayList<AccountsDto> accountsDtoArrayList = new ArrayList<AccountsDto>();
        try {
            accountsDto.setFiType(request.get("DEPOSIT").toString());
            accountsDto.setAccRefNumber(request.get("accType").toString());
            accountsDto.setMaskedAccNumber(request.get("maskedAccNumber").toString());
            accountsDtoArrayList.add(accountsDto);
            customer.setAccounts(accountsDtoArrayList);
            customer.setId(request.get("customerId").toString());
            accountLinkRequest.setVer(VER);
            accountLinkRequest.setTimestamp(DATEFORMATTER.format(date));
            accountLinkRequest.setTxnid(UUID.randomUUID().toString());
            accountLinkRequest.setCustomer(customer);
            entity = fipClient.accountsLink(new JSONObject(accountLinkRequest));
            logger.info(accountLinkRequest);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return new ResponseEntity<>(accountLinkRequest, HttpStatus.OK);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> fetchAccountDetails(Map request) {
        JSONObject jsonObject = new JSONObject(request);
        ResponseEntity<?> entity = null;
        try {
            logger.info(jsonObject);
            entity = fipClient.fetchAccountDetails(request);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}