package com.xiaoming.utils;
import java.math.BigInteger;
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Random;  
  
public class MD5Util {
	//加盐混淆
  private static byte[] letter= {'z','x','c','v','b','n','m','a','s','d','f','g'
			,'h','j','k','l','q','w','e','r','t','y','u','i','o','p'};
  private static byte[] letrerMixup= {'p','n','h','f','q','v','l','k','c','x','d','t',
			'j','a','o','i','e','m','w','g','s','u','z','b','r','y'};
  private static byte[] bigMixup= {'C','I','F','M','E','Q','A','S','V','H','R','K','X',
			'D','U','G','Z','B','W','L','T','P','Y','O','J','N'};
  private static byte[] letterBig= {'Z','X','C','V','B','N','M','A','S','D','F','G','H'
			,'J','K','L','Q','W','E','R','T','Y','U','I','O','P'};
  private static byte [] arr={'1','2','3','4','5','6','7','8','9','0'};
  private static byte [] arrRand= {'5','1','9','7','3','8','0','4','6','2'};
    
    public static  String getMD5(byte[] source){  
    	
        String s=null;  
        //用来将字节转换成16进制表示的字符  
        char[] hexDigits={'0','1','2','3','*','%','6','7','8','d',  
                'n','b','u','s','a','x'};  
        try {  
            MessageDigest md=MessageDigest.getInstance("MD5");  
            md.update(source);  
            //MD5的计算结果是一个128位的长整数，用字节表示为16个字节  
            byte[] tmp=md.digest();  
            //每个字节用16进制表示的话，使用2个字符(高4位一个,低4位一个)，所以表示成16进制需要32个字符  
            char[] str=new char[16*2];  
            int k=0;//转换结果中对应的字符位置  
            for(int i=0;i<16;i++){//对MD5的每一个字节转换成16进制字符  
                byte byte0=tmp[i];  
                str[k++]=hexDigits[byte0>>>4 & 0xf];//对字节高4位进行16进制转换  
                str[k++]=hexDigits[byte0 & 0xf];    //对字节低4位进行16进制转换  
            }  
            s=new String(str);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return setMD5(base64Encrypt(s));  
    }
    
    private static  String setMD5(byte[] source){
    	String s=null;  
        //用来将字节转换成16进制表示的字符  
        char[] hexDigits={'0','"','}','=','+','-','6','k','8','d',  
                'n','b','2','s','l','z'};  
        try {  
            MessageDigest md=MessageDigest.getInstance("MD5");  
            md.update(source);  
            //MD5的计算结果是一个128位的长整数，用字节表示为16个字节  
            byte[] tmp=md.digest();  
            //每个字节用16进制表示的话，使用2个字符(高4位一个,低4位一个)，所以表示成16进制需要32个字符  
            char[] str=new char[16*2];  
            int k=0;//转换结果中对应的字符位置  
            for(int i=0;i<16;i++){//对MD5的每一个字节转换成16进制字符  
                byte byte0=tmp[i];  
                str[k++]=hexDigits[byte0>>>4 & 0xf];//对字节高4位进行16进制转换  
                str[k++]=hexDigits[byte0 & 0xf];    //对字节低4位进行16进制转换  
            }  
            s=new String(str);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return s;  
    }
	
    
    
    /**
     * base64加密
     * @param content 待加密内容
     * @return byte[]
     */
    public static byte[] base64Encrypt(final String content) {
    	byte[] base=Base64.getEncoder().encode(content.getBytes());
    	for(int i=0;i<base.length;i++) {
    		if(base[i]<=57&&base[i]>=48) {
    			for(int a=0;a<arr.length;a++){
        			if(base[i]==arr[a]) {
        				base[i]=arrRand[a];
        				a=10;
            		}
        		}
    		}else if(base[i]<=122&&base[i]>=65) {
    			for(int a=0;a<letter.length;a++){
        			if(base[i]==letter[a]) {
        				base[i]=letrerMixup[a];
        				a=26;
        				
            		}else if(base[i]==letterBig[a]) {
            			base[i]=bigMixup[a];
            			a=26;
        				
            		}
        		}
    		}
    	}
        return base;
    }

    /**
     * base64解密
     * @param encoderContent 已加密内容
     * @return byte[]
     */
    public static byte[] base64Decrypt(final byte[] encoderContent) {
    	for(int i=0;i<encoderContent.length;i++) {
    		if(encoderContent[i]<=57&&encoderContent[i]>=48) {
    			for(int a=0;a<arrRand.length;a++){
        			if(encoderContent[i]==arrRand[a]) {
        				encoderContent[i]=arr[a];
        				a=10;
            		}
        		}
    		}else if(encoderContent[i]<=122&&encoderContent[i]>=65) {
    			for(int a=0;a<letrerMixup.length;a++){
        			if(encoderContent[i]==letrerMixup[a]) {
        				encoderContent[i]=letter[a];
        				a=26;
        				
            		}else if(encoderContent[i]==bigMixup[a]) {
            			encoderContent[i]=letterBig[a];
            			a=26;
        				
            		}
        		}
    		}
    	}
        return Base64.getDecoder().decode(encoderContent);
    }

  
} 