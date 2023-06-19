package com.sumasoft.accountaggregator.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sisyphsu.dateparser.DateParserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import static com.sumasoft.accountaggregator.constant.GlobalConstant.DATEFORMATTER;
import static com.sumasoft.accountaggregator.constant.GlobalConstant.DATETIME_FORMATTER;

/**
 * Created by mukund.ghanwat on 07 Jun, 2023
 */
@Component
public class CommonUtil {

    private static final Logger logger = LogManager.getLogger(CommonUtil.class);

    public static <T> T json2Java(String json, Class<T> classType) {
        T t = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            t = mapper.readValue(json, classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public Boolean dateDiff(String userDate) {
        try {
            return java.time.Duration.between(LocalDateTime.parse(DATEFORMATTER.format(DateParserUtils.parseDate(userDate)), DATETIME_FORMATTER), LocalDateTime.parse(DATEFORMATTER.format(Calendar.getInstance().getTime()), DATETIME_FORMATTER)).toMinutes() <= 15 && java.time.Duration.between(LocalDateTime.parse(DATEFORMATTER.format(DateParserUtils.parseDate(userDate)), DATETIME_FORMATTER), LocalDateTime.parse(DATEFORMATTER.format(Calendar.getInstance().getTime()), DATETIME_FORMATTER)).toMinutes() >= -15;
        } catch (Exception exception) {
            //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());\
            logger.error(exception);
            return false;
        }
    }

    public boolean isValid(String dateStr) {
        try {
            DATEFORMATTER.parse(dateStr);
        } catch (DateTimeParseException | ParseException e) {
            return false;
        }
        return true;
    }

    public Boolean dateTimeDiff(Integer time) {
        //String requestDateString = DATEFORMATTER.format(new Date(time * 1000L));
        //Date requestedDate = DATEFORMATTER.parse(DATEFORMATTER.format(new Date(time * 1000L)));
        //Date date = new Date();
        try {
            return DATEFORMATTER.parse(DATEFORMATTER.format(new Date(time * 1000L))).after(new Date());
        } catch (Exception exception) {
            logger.error(exception);
            return false;
        }
    }
}
