package com.xiaoming.text;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class min {
	
	//最坏情况的时间复杂度：这个算法的最大运行时间 O(n)
	//最好情况的时间复杂度：这个算法的最小运行时间 O(1)
	//平均情况的时间复杂度：这个算法的平均运行时间 O(N/2) 最大运行时间平均数
	//程序最大运行时间的话就是O(N)
	static int fun6(int n,int i) {
		//4*3*2*1
		return n<2?n:fun6(n-1,i)*n;
	}
	
	//程序最大运行时间的话就是O(N^2)
	static int fun7(int n) {
		//代表最大运行时间  平方 ^2
		//二叉树
		return n<=1?1:fun7(n-1)+fun7(n-1);
    }
	
	
//	static long fun6(long n) {
//		//System.out.println(n);
//		return n<2?n:fun6(n-1)*n;
//	}
//	
//	static BigInteger fun7(BigInteger n) {
//		if (n.compareTo(new BigInteger("2"))<0)
//			return n;
//		else
//			return fun7(n.subtract(new BigInteger("1"))).multiply(n);
//	}
	
	
	static int fun7(int n,String i,int w) {
		//System.err.println(n);
		//System.err.println("ss："+w);
		if (n<2) {
			//System.err.println(i+1+"s");
			return 1;
		}
		else {
			//System.err.println(i+n);
			int ss= fun7(n-1,"x树枝",n)+fun7(n-1,"z树枝",n);
			System.err.println("节点："+ss+":"+i);
			return ss;
		}
	}
	
	static String fun9(int n,String w,String z) {
		//System.err.println(n);
		//System.err.println("ss："+w);
		
		if (n<2) {
			//System.err.println(i+1+"s");
			return w+":"+z;
		}
		else {
			//System.err.println(i+n);
			String ss= fun9(n-1," x","x")+fun9(n-1," y","y");
			System.out.println(ss);
			System.out.println("指针："+n);
			return ss;
		}
	}
	
	  void s(int n,String o) {
		//System.err.println(n);
		//System.err.println("ss："+w);
		  System.out.println(n+o);
		if (n<=1) {
			//System.err.println(i+1+"s");
		
		}
		else {
			//System.err.println(i+n);
			
			s(n-1,"x");
			s(n-1,"y");
			//System.out.println(ss);
		
		}
	}
	static int s=0;
	static String fun8(int z){
		List<String> ns=new ArrayList<>();
		for(int i=0;i<z;i++) {
			ns.add("  ");
		}
//		System.err.println(ns+"s");
		String x="";
		String n="";
		for(int i=0;i<ns.size();i++) {
			
			for(int w=0;i>w;w++) {
				n+=ns.get(w);
				//System.out.println(n+w);
			}
			x+=n+i;
			n="";
			
			for(int w=0;i<ns.size()-w-1;w++) {
				n+=ns.get(w);
				//System.out.println(n+w);
			}
			x+=n+n+i+"\n";
			n="";
		}
		return x;
	}

	public static void main(String[] args) {
		//System.err.println(min.fun7(new BigInteger("100")));;
		
		int w=0;
		for(int i=0;i<3;i++) {
			for(int y=0;y<3;y++) {
				w+=1;	
			}
			for(int y=0;y<3;y++) {
				w+=1;	
			}
			
		}
		System.out.println(w);
		//new min().s(3,"*");
//		String x="~"+"                   ~\n"
//				+ "   ~"+"            ~\n"
//				+ "      ~"+"      ~\n"
//				+ "         ~\n";
//				
//		
//		String y= 
//				"~\n";
	
		//System.out.println(1*2*3*4*5);
		//System.err.println(24*5);
		
		
	
//		String y="~";
//		n="  ";
//		for(int i=0;i<10;i++) {
//			x+=n+"~"+"\n";
//			n+="  ";
//		}
//		System.err.println(x);
	}
}
