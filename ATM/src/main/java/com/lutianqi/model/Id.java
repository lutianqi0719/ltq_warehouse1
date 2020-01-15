package com.lutianqi.model;

import java.util.Calendar;

public class Id
{
private static int countrycode = 86 ;
	
	/**
	 * 邮编
	 */
	private static int areacode = 215636 ;
	
	/**
	 * 序号
	 */
//	private  int count ;
	
	
	
//	private static int getCount() {
//		return count;
//	}
//
//	private static void setCount(int count) {
//		Id.count = count;
//	}

	/**
	 * 创建ID
	 * @return
	 */
	public static long createid(){
		
		String idStr = "" ;
		//国家编号+区号
		idStr = idStr + countrycode + areacode ;
		//年月
		Calendar cal = Calendar.getInstance() ;
		String format = "%02d" ;
		idStr += cal.get(Calendar.YEAR) + String.format(format,  (cal.get(Calendar.MONTH)+1) );
		//计数器
		format = "%04d";
		idStr += String.format(format, Bank.getInstance().getIndex());
//		int count = Bank.getInstance().getIndex() ;
//		idStr += String.format(format, Id.getCount());
//		if(Id.getCount()==9999){
//			Id.setCount(0);
//		}else{
//			count++ ;
//		}
		//转换long类型
		long id = Long.parseLong(idStr) ;
		System.out.println("id-->+"+id);
		return id ;
	}
}
