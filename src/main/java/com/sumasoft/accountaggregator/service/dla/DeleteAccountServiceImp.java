package com.sumasoft.accountaggregator.service.dla;

import com.sumasoft.accountaggregator.accountslink.AccountDto;
import com.sumasoft.accountaggregator.accountslink.DeleteLinkedAccountRequestDto;
import com.sumasoft.accountaggregator.client.FIPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.DATEFORMATTER;
import static com.sumasoft.accountaggregator.constant.GlobalConstant.VER;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
@Service
public class DeleteAccountServiceImp implements DeleteAccountService {
    private static final Logger logger = LogManager.getLogger(DeleteAccountServiceImp.class);

    @Autowired
    private FIPClient fipClient;

    /**
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> deleteLinkedAccount(Map request) {
        DeleteLinkedAccountRequestDto deleteLinkedAccountRequestDto = new DeleteLinkedAccountRequestDto();
        AccountDto accountDto = new AccountDto();
        Date date = new Date();
        ResponseEntity<?> response = null;
        try {
            accountDto.setCustomerAddress(request.get("customerAddress").toString());
            accountDto.setLinkRefNumber(request.get("linkRefNumber").toString());
            deleteLinkedAccountRequestDto.setVer(VER);
            deleteLinkedAccountRequestDto.setTxnid(UUID.randomUUID().toString());
            deleteLinkedAccountRequestDto.setTimestamp(DATEFORMATTER.format(date));
            deleteLinkedAccountRequestDto.setAccount(accountDto);
            logger.info(deleteLinkedAccountRequestDto);
            response = fipClient.deleteLinkedAccount(new JSONObject(request));
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity<>(exception.getMessage(), ((HttpClientErrorException.Unauthorized) exception).getStatusCode());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
