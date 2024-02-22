package com.xiaosong.music.server.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class StringUtil {
    //去HTML
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
    //去：空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 根据传递的类型格式化时间
     *
     * @param str
     * @param type
     *            例如：yy-MM-dd
     * @return
     */
    public static String getDateTimeByMillisecond(String str, String type) {
        if (str==null || type==null){
            return null;
        }else {
            if (str.length() == 13){
                str = Long.parseLong(str)+"";
            }else{
                str = (Long.parseLong(str)*1000)+"";
            }
            Date date = new Date(Long.valueOf(str));
            SimpleDateFormat format = new SimpleDateFormat(type);
            String time = format.format(date);
            return time;
        }
    }

    /**
     * md5加密 32位 小写
     * @param plainText
     * @return
     */
    public static String md5(String plainText) {
        if (plainText!=null){
            String re_md5 = new String();
            //加盐
            String salt= "X1aoS0ng";
            plainText= plainText+salt;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                re_md5 = buf.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return re_md5;
        }else{
            return  null;
        }

    }

    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据 不懂的qq1023732997
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }


    private static boolean checkIp(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) ) {
            return false;
        }
        return true;
    }
    public static String TtoBr(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        List<Integer> listArr = new ArrayList<>();
        for (int i = -1; i <= str.lastIndexOf("\n"); ++i) {
            i = str.indexOf("\n", i);
            listArr.add(i);
        }
        for (int i = listArr.size() - 1; i >= 0; i--) {
            if(listArr.get(i)!=-1){
                stringBuilder.replace(listArr.get(i), listArr.get(i) + 1, "\n</br>\n");
            }
        }
        return stringBuilder.toString();
    }

    //判断email是否合法
    public static boolean emailValidator(String email){
        String pat="[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com))";
        Pattern p = Pattern.compile(pat);//实例化Pattern类
        Matcher m = p.matcher(email);//验证内容是否合法
        if(m.matches())
            return true;
        else
            return false;
    }
    //判断账号是否合法
    public static boolean isAccountValid(String input) {
        // 步骤1: 检查长度是否大于等于6位，小于15位
        if (input == null || input.length() < 6 || input.length() > 15) {
            return false;
        }
        // 步骤2: 遍历字符串的每个字符
        for (char c : input.toCharArray()) {
            // 检查字符是否为英文字符、数字或特定的特殊符号
            if (!isAllowedCharacter(c)) {
                return false;
            }
        }
        // 如果所有检查都通过，则返回true
        return true;
    }

    //判断密码是否合法
    public static boolean isPasswordValid(String input) {
        // 步骤1: 检查长度是否大于等于6位，小于15位
        if (input == null || input.length() < 6 || input.length() > 15) {
            return false;
        }
        // 步骤2: 遍历字符串的每个字符
        for (char c : input.toCharArray()) {
            // 检查字符是否为英文字符、数字或特定的特殊符号
            if (!isAllowedCharacter(c)) {
                return false;
            }
        }
        // 如果所有检查都通过，则返回true
        return true;
    }

    /**
     * 判断字符是否为允许的字符。
     *
     * @param c 要检查的字符。
     * @return 如果字符是允许的，则返回true。
     */
    private static boolean isAllowedCharacter(char c) {
        // ASCII码范围检查：大写字母(A-Z), 小写字母(a-z), 数字(0-9), 特定特殊符号
        return (c >= 'A' && c <= 'Z') ||
                (c >= 'a' && c <= 'z') ||
                (c >= '0' && c <= '9') ||
                (c == '!' || c == '@' || c == '#' || c == '$' ||
                        c == '%' || c == '^' || c == '&' || c == '*' ||
                        c == '(' || c == ')' || c == '-' || c == '+' ||
                        c == '=' || c == '[' || c == ']' || c == '{' ||
                        c == '}' || c == ';' || c == ':' || c == ',' ||
                        c == '.' || c == '<' || c == '>' || c == '/' ||
                        c == '?' || c == '`' || c == '~' || c == '|' ||
                        c == '\\');
    }
}