package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {

    private static String regexHour = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    private static String regexFloat = "^(\\d+\\.\\d+|\\d+)$";
    private static String regexDay = "^\\d{4}-\\d{2}-\\d{2}$";  // Kiểm tra định dạng ngày theo chuẩn "yyyy-MM-dd":


    public static String checkRegex(String regex, String time, String notice) {
        // Kiểm tra chuỗi stringA có khớp với biểu thức chính quy regex hay không
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);

        if (matcher.matches()) {
            // Nếu khớp, trả về rỗng
            return "";
        } else {
            // Nếu không khớp, trả về chuỗi stringB
            return notice;
        }
    }

    public static String hourCheck(String hour, String prefix){
        String a = checkRegex(regexHour,hour,"You must enter the time in the format (HH:mm) \n");
        if (a.equals("")){
            return a;
        }else {
            return prefix + a;
        }

    }
    public static String numberCheck(String num, String prefix){
        String a = checkRegex(regexFloat,num,"You must enter a positive number format. \n");
        if (a.equals("")){
            return a;
        }else {
            return prefix + a;
        }

    }
    public static String dayCheck(String num, String prefix){
        String a = checkRegex(regexDay,num,"You must enter the date in the format (yyyy-MM-dd). \n");
        if (a.equals("")){
            return a;
        }else {
            return prefix + a;
        }

    }


}
