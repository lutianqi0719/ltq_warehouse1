package com.lutianqi.model;

public class CheckEmail
{
	public boolean checkemail(String str){
		boolean bl = false ;
		System.out.println(str);
		int aIndex = str.indexOf("@");	
		int dotIndex = str.indexOf(".") ;
		if(str.indexOf("@", aIndex+1)!=-1 ||  str.indexOf(".", dotIndex+1)!=-1){
//			System.out.println("不满足有且仅有一个@和.");
		}else if(aIndex<1 || dotIndex>(str.length()-2) || aIndex>dotIndex ){
//			System.out.println("不满足@在.之前 且@不能是第一位  .不能是最后一位");
		}else if(aIndex+1==dotIndex){
//			System.out.println("@与.不能相邻");
		}else{
			bl = true ;
		}
		return bl ;
	}
}
