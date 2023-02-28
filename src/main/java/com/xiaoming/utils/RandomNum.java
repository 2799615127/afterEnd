package com.xiaoming.utils;

import java.util.Random;

/***
 * 与前端的验证图片相对应
 * @author 86155
 *
 */
public class RandomNum {
	public static String[] identifyCodes= {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","z","c","b"};
	
	public static String random() {
		String str="";
		for(int i=0;i<4;i++) {
			int random=new Random().nextInt(identifyCodes.length);
			if(i==0) {
				str=random+str;
			}else {
				str=random+","+str;
			}
		}
		return str;
    }
}
