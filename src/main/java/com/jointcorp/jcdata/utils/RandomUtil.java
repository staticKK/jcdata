package com.jointcorp.jcdata.utils;

import sun.security.jca.GetInstance;

import java.util.Date;
import java.util.Random;

/**
 * @author Jack 2014年11月13日 下午7:25:09
 *
 */
public class RandomUtil extends Random {

	private RandomUtil(){}

	private static class RandomUtilHandler{
		public static RandomUtil randomUtil = new RandomUtil();
	}

	public static RandomUtil getInstance(){
		return RandomUtilHandler.randomUtil;
	}


	/**
	 * 生成一个范围的随机数
	 * @param start
	 * @param end
	 * @return
	 *
	 * @date  2016年9月6日 上午10:17:52
	 */
	public int getRandom(int start,int end) {
		return RandomUtil.getInstance().nextInt(end - start + 1) + start;
	}

}
