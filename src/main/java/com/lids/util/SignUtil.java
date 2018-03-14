package com.lids.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author yaoyou
 * @Description:微信请求校验类
 */
public class SignUtil {

    private static String token = "lids123456";

    public static boolean checkSignature(String signature,String timestamp,String nonce) {
        String[] arr = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for(int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }
        MessageDigest messageDigest = null;
        String tmpStr = null;

        try{
            messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()):false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

}
