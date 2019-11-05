package com.yx.weather.controller;

import com.yx.weather.constant.WeatherConst;
import com.yx.weather.model.BaseRes;
import com.yx.weather.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * details：
 *
 * @ClassName: WeatherController
 * @Author yx
 * @Date 2019-10-31 12:26:09
 */
@Slf4j
@Controller
@RequestMapping(value = "/weather")
public class WeatherController {

    @RequestMapping(value = "/getWeather")
    @ResponseBody
    // http://localhost:8981/weather/getWeather?city=沈阳
    //https://www.tianqiapi.com/api/?appid=94351721&appsecret=bqo5gbhn&version=v51&city=%E6%82%89%E5%B0%BC
    public BaseRes getWeather(@RequestParam(value = "city", required = true) String city) {
        String result = HttpUtil.getRequest(

                WeatherConst.GET_WEATHER_URL,
                WeatherConst.GET_WEATHER_PARAM + StringUtils.trimWhitespace(city));
        String reChina = decodeUnicode(result);
        return new BaseRes(reChina);
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }


    //Unicode transform to chinese
    public static String decodeUnicode(final String unicode) {
        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 0; i < hex.length; i++) {

            try {
                if(hex[i].length()>=4){
                    String chinese = hex[i].substring(0, 4);
                    try {
                        int chr = Integer.parseInt(chinese, 16);
                        boolean isChinese = isChinese((char) chr);
                        if (isChinese){
                            string.append((char) chr);
                            String behindString = hex[i].substring(4);
                            string.append(behindString);
                        }else {
                            string.append(hex[i]);
                        }
                    } catch (NumberFormatException e1) {
                        string.append(hex[i]);
                    }

                }else{
                    string.append(hex[i]);
                }
            } catch (NumberFormatException e) {
                string.append(hex[i]);
            }
        }

        return string.toString();
    }

    /**
     * check if it is a chinese character
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
