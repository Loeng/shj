package com.turbid.explore.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CodeLib {

    private static String[] headimg = new String[]{"https://anoax-1258088094.cos.ap-chengdu.myqcloud.com/public/2020040214512610203253965845083531.png"};

    private static String[] code=new String[]{
            "0","1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F","G","H","J","K","M",
            "N","P","Q","R","S","T","U","V","W","X","Y"
    };

    //获取随机头像
    public static String getHeadimg() {
        Random random = new Random();
        int number = random.nextInt(headimg.length);
        return headimg[number];
    }

    //获取随机编码
    public static String randomCode(int length,int type) {
        String base = "";
        if(type==0){
            base="abcdefghijklmnopqrstuvwxyz";
        }else if(type==1){
            base="0123456789";
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    private static final String slat = "&%5123***&&%%$$#@";
    public static String encrypt(String dataStr) {
        try {
            dataStr = dataStr + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public static boolean createFile(File fileName)throws Exception{
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public static boolean writeTxtFile(String content,File  fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }

    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

    public static String getAddressByIp(String ip) {
        if (ip == null || ip.equals("")) {
            return null;
        }
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String thisUrl ="http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
        try {
            URL url = new URL(thisUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            return result;

        } catch (Exception e) {
            System.out.println("获取IP地址失败");
            return "{'country':'','region':'','city':''}";
        }

    }


    public static String getNikeName(StringRedisTemplate stringRedisTemplate){

        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        Integer year = Integer.parseInt(strNow[0].substring(2,4));
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        String key="c"+year+"m"+month+"d"+day;

        if(null==stringRedisTemplate.opsForValue().get(key)) {
            stringRedisTemplate.opsForValue().set(key, "1");
            return "U"+code[year]+code[month]+code[day]+"0001";
        }
        Integer value=Integer.parseInt(stringRedisTemplate.opsForValue().get(key))+1;
        String count= CodeLib.getCode("",value);
        System.out.println(count.length());
        if(count.length()<4){
            for (int i=0;i<=(5-count.length());i++){
                count="0"+count;
            }
        }
        stringRedisTemplate.opsForValue().set(key,value.toString());
        return "U"+code[year]+code[month]+code[day]+count;
    }

    public static String getCode(String str,Integer value){
        Integer number= value/code.length;
        if(number>0){
            str= str+code[value-(number*code.length)];
            return getCode(str,number);
        }else {
            return code[value]+str;
        }


    }

    public static String getSHC() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        return str;
    }


    /**
     * 从html代码中，获得指定标签的指定属性的取值
     * @param html  HTML代码
     * @param tagName  指定的标签名称
     * @param propertyName 指定的属性名称
     * @return
     */
    public static final List<String> listTagPropertyValue(final String html, final String tagName, final String propertyName) {
        // 结果集合
        ArrayList<String> result = new ArrayList<String>();
        // 找出HTML代码中所有的tagName标签
        List<String> list = RegexUtils.getMatchList(html, "<" + tagName + "[^>]*>", true);
        // 循环遍历每个标签字符串，找出其中的属性字符串,比如 src=....
        for (String tagStr : list) {
            // 去掉标签结尾的/>，方便后面 src 属性的正则表达式。
            // 这样可以适应  <video src=http://www.yourhost.com/xxx>  这样的标签
            if (tagStr.endsWith("/>")) {
                tagStr = tagStr.substring(0, tagStr.length() - 2);
                tagStr = tagStr + " ";
            }
            // 去掉标签结尾的>，方便后面匹配属性的正则表达式。
            // 这样可以适应  <video src=http://www.yourhost.com/xxx>  这样的标签
            else if (tagStr.endsWith(">")) {
                tagStr = tagStr.substring(0, tagStr.length() - 1);
                tagStr = tagStr + " ";
            }
            // 去掉字符串开头的 <video 或 <source
            tagStr = tagStr.substring(1 + tagName.length());
            tagStr = " " + tagStr;

            // 取出属性的值
            String regSingleQuote = "^" + propertyName + "='[^']*'"; // 使用单引号
            String regDoubleQuote = "^" + propertyName + "=\"[^\"]*\""; // 使用双引号
            String reg = "^" + propertyName + "=[^\\s]*\\s"; // 不使用引号
            int index = 0;
            int length = tagStr.length();
            while (index <= length) {
                String subStr = tagStr.substring(index);
                String str = RegexUtils.getFirstMatch(subStr, regSingleQuote, true);
                if (null != str) {
                    // 往后跳过已经匹配的字符串。
                    index += str.length();
                    String srcStr = str;
                    srcStr = srcStr.trim();
                    // 去掉 src=
                    srcStr = srcStr.substring(propertyName.length() + 1);
                    // 去掉单引号
                    srcStr = srcStr.substring(1);
                    srcStr = srcStr.substring(0, srcStr.length() - 1);
                    // 结果中加入图片URL
                    result.add(srcStr);
                } else if ((str = RegexUtils.getFirstMatch(subStr, regDoubleQuote, true)) != null) {
                    // 往后跳过已经匹配的字符串。
                    index += str.length();
                    String srcStr = str;
                    srcStr = srcStr.trim();
                    // 去掉 src=
                    srcStr = srcStr.substring(propertyName.length() + 1);
                    // 去掉双引号
                    srcStr = srcStr.substring(1);
                    srcStr = srcStr.substring(0, srcStr.length() - 1);
                    // 结果中加入图片URL
                    result.add(srcStr);
                } else if ((str = RegexUtils.getFirstMatch(subStr, reg, true)) != null) {
                    // 往后跳过已经匹配的字符串。
                    index += str.length();
                    String srcStr = str;
                    srcStr = srcStr.trim();
                    // 去掉 src=
                    srcStr = srcStr.substring(propertyName.length() + 1);
                    // 结果中加入图片URL
                    result.add(srcStr);
                } else if ((str = RegexUtils.getFirstMatch(subStr, "^[\\w]+='[^']*'", true)) != null) {
                    // 往后跳过已经匹配的字符串。
                    index += str.length();
                } else {
                    index++;
                }
            }
        } // end for (String tagStr : list)
        result.trimToSize();
        return result;
    }

    /**
     * 从html代码中找出img标签的图片路径
     * @param html  HTML代码
     * @return  字符串列表，里面每个字符串都是图片链接地址
     */
    public static List<String> listImgSrc(final String html) {
        return listTagPropertyValue(html, "img", "src");
    }


    public static String getStandardDate(long t) {

        StringBuffer sb = new StringBuffer();


        long time = System.currentTimeMillis() - (t*1000);
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }



}
