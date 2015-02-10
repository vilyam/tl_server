package com.c17.yyh.misc;

import com.c17.yyh.constants.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        try {
            return dateFormat.parse(v);
        } catch (ParseException e) {
            logger.error("Wrong notification start date format - {}", e.getMessage());
            throw e;
        }
    }

}
