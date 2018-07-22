package com.template.admin.base;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.other.CustomException;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MyCustomDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(!StringUtils.hasText(text)){
            setValue(null);
        }else {
            try {
                setValue(this.dateAdapter(text));
            }catch (CustomException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (value != null ? dateFormat.format(value) : "");
    }

    public static Date dateAdapter(String dateStr){
        Date date = null;
        String temp = dateStr;
        if(!(null==dateStr||"".equals(dateStr))){
            if(dateStr.contains("CST")){
                date = new Date(dateStr);
            }else {
                dateStr=dateStr.replace("年", "-").replace("月", "-").replace("日", "").replaceAll("/", "-").replaceAll("\\.", "-").trim();
                String fm="";
                //确定日期格式
                if(Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()){
                    fm = "yyyy-MM-dd";
                }else if(Pattern.compile("^[0-9]{4}-[0-9]{1}-[0-9]+.*||^[0-9]{4}-[0-9]+-[0-9]{1}.*").matcher(dateStr).matches()){
                    fm = "yyyy-M-d";
                }else if(Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()){
                    fm = "yy-MM-dd";
                }else if(Pattern.compile("^[0-9]{2}-[0-9]{1}-[0-9]+.*||^[0-9]{2}-[0-9]+-[0-9]{1}.*").matcher(dateStr).matches()){
                    fm = "yy-M-d";
                }
                //确定时间格式
                if(Pattern.compile(".*[ ][0-9]{2}").matcher(dateStr).matches()){
                    fm+= " HH";
                }else if(Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}").matcher(dateStr).matches()){
                    fm+= " HH:mm";
                }else if(Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(dateStr).matches()){
                    fm+= " HH:mm:ss";
                }else if(Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,3}").matcher(dateStr).matches()){
                    fm+= " HH:mm:ss:sss";
                }

                if(!"".equals(fm)){
                    try {
                        date = new SimpleDateFormat(fm).parse(dateStr);
                    } catch (ParseException e) {
                        throw new CustomException(HttpResponse.HTTP_ERROR,"参数字符串"+dateStr+"无法被转换为日期格式！");
                    }
                }
            }
        }
        return date;
    }

}
