package com.sumasoft.accountaggregator.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class GlobalConstant {


    //TODO use DateTimeFormatter instead of SimpleDateFormat
    public static final DateFormat DATEFORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    //TODO use DateTimeFormatter instead of SimpleDateFormat
    public static SimpleDateFormat BASIC_DATE_FORMAT = new SimpleDateFormat("MMMMM dd, yyyy");
    public static SimpleDateFormat WEB_DATE_FORMAT_yyyyMMDD_DASH = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat TIME_FORMAT_WEB = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat DATE_FORMAT_YYMMDD = new SimpleDateFormat("yyMMdd");
    public static SimpleDateFormat DATE_FORMAT_yyyyMMDD = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat DATE_FORMAT_YYYYMMDD_SLASH = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat DATE_FORMAT_MMDDYYYY_SLASH = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat DATE_FORMAT_DDMMYYYY = new SimpleDateFormat("ddMMyyyy");
    public static SimpleDateFormat DATE_FORMAT_DDMMMYYYY_SLASH = new SimpleDateFormat("dd/MMM/yyyy");
    public static SimpleDateFormat DATE_FORMAT_YYMMDD_HHMM = new SimpleDateFormat("yyMMdd HHmm");
    public static SimpleDateFormat DATE_FORMAT_YYMMDDHHMM = new SimpleDateFormat("yyMMddHHmm");
    public static SimpleDateFormat DATE_FORMAT_YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyyMMdd HHmmss");
    public static SimpleDateFormat DATE_FORMAT_DDMMMYY = new SimpleDateFormat("ddMMMyy");
    public static SimpleDateFormat DATE_FORMAT_DDMMMYY_HHMM = new SimpleDateFormat("ddMMMyy HH:mm");
    public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HHMM_DASH = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat DATE_FORMAT_MMDDYYYY_HHMMSS = new SimpleDateFormat("MMddyyyyHHmmss");
    public static SimpleDateFormat DATE_FORMAT_YYYYMMDDHHMM = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat DATE_FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat DATE_FORMAT_MMDDYYYY_HHMM_SLASH = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    public static SimpleDateFormat DATE_FORMAT_DDMMMYYYY_DASH = new SimpleDateFormat("dd-MMM-yyyy");
    public static SimpleDateFormat DATE_FORMAT_DDMMYYYY_DASH = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat DATE_FORMAT_DDMMMYY_DASH = new SimpleDateFormat("dd-MM-yy");
    public static SimpleDateFormat DATE_FORMAT_YYMMDDHHMMSS = new SimpleDateFormat("yyMMddHHmmss");
    public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HHMMSS_DASH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_T_HHMMSS_SSSXXX_DASH = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static SimpleDateFormat DATE_FORMAT_DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    public static SimpleDateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
    public static final String URL = "https://aauat.finvu.in/API/V1/";

    public static final String LOCAL_URL = "https://sahamati-suma-azb7fa3pfa-uc.a.run.app/";
    public static final String FIP_API_KEY = "eyJraWQiOiJqYXlAZmludnUuaW4iLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJjb29raWVqYXIiLCJleHAiOjE1OTEyNjMwMDMsImp0aSI6IjRxeXFpWV9aZGJpTW1qQ2lqT0ZMckEiLCJpYXQiOjE1NTk3MjcwMDMsIm5iZiI6MTU1OTcyNzAwMywic3ViIjoiamF5QGZpbnZ1LmluIiwiZ3JvdXBzIjpbImFnZ3JlZ2F0b3IiXX0.Ctd9EUpn0CrfEG8b9ouBcG8ilVuPWzXkxZFOhuTCRdpiVzi6M05ABJ9THJ1QE4n__r74r8KHSxDgdeV1L-8mek68i9m5BYgqaka5VKoCLiN0B22Xvy2QFski1ZLQhm7Va2NioVvilGGWBDlpW_csuChA2yobml1jqomr8SGya6MFbDURRmJjdL6oRTysoCYxeFzU2_JCud2ljMvaWsjSlnrYXQWaWaoJBAhz3AWNXdzU0u6mhq1mvksRl3xXJDiAVkY0KvZXRNjsJDYQfVJjavh9n0fZelJIWMHznp9mLpxRfrr_qo6hiTkX2QtbbmKfhc0_BXsKNOdxEXJmieOPSA";
    public static final String SIGNATURE = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjUzMzEwNzkyLTgwMDYtNGU3NC1iYmJlLWNiZGRlYjg5ZjMwYiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..h1FWwXanmcWgxUtHwlNWtD5_sE3HLjqd2rjE_c1WiPBvOHKJ4hs5oYlRdT663r7z7FV3WqP7tErNCzOlWUpu7j9JXKu7t4ObkCwx4pEZVySFcrI_0dpPAB43JlHeADeBwRX145tx6UC4yk4mIOTBrjdh2Yf0RZ7wOoyKys44mNqf0WJIZ2gO7bcm-WfTDW_5Y-tAO3LfyU2cQSyi1Jnc5q8qODXb_TsAYJOvANq0QGlstjEFwytnFsgPtyELjnjHMWgeTuDerw45xdNhdtNkR8UUHgajG2CFQwjEgpeOvZXbj0EeqbIgszXHsTHEfzRM6ZMMMEGcO_aXRUV3A6zZJQ";


    public static final String VER = "1.1.2";
    public static final String NOTIFIER_TYPE = "AA";
    public static final String NOTIFIER_ID = "SUMASOFTAA";
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";


    //Constant Variable
    public static final String TIMESTAMP = "timestamp";
    public static final String VERSION = "ver";
    public static final String TXNID = "txnid";
}
