package com.jointcorp.jcdata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiao on 2017/3/22.
 */
public class DateUtil {

	private static SimpleDateFormat ymdSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

	private static SimpleDateFormat ymdhmsSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


	/**
	 * 将当前时间格式化成字符串
	 * @return String 时间字符串
	 * @param date 时间，如果为空则返回当前时间
	 * @param patten 需要返回时间的形式  如：yyyy-MM-dd
	 * @author xiao  2017年3月22日
	 */
	public static String format(Date date,String patten){

		if(date == null){
			return new SimpleDateFormat(patten).format(new Date());
		}
		return new SimpleDateFormat(patten).format(date);
	}

	public static String format(long time,String patten){
		if(time == 0){
			return new SimpleDateFormat(patten).format(new Date());
		}
		return new SimpleDateFormat(patten).format(new Date(time));
	}

	public static Date parseDateTime(String dateTime){
		synchronized (ymdSimpleDateFormat){
			try {
				return ymdhmsSimpleDateFormat.parse(dateTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 返回当天0点时间戳
	 * @return long 时间毫秒
	 * @param str
	 * @author xiao  2017年3月22日
	 */
	public static long toLong(String str){
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0); //HOUR_OF_DAY:24小时制
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}


	static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	static DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

	public static LocalDateTime parseDataTime(String time) {
		return LocalDateTime.parse(time,df1);
	}

	public static LocalDate parseDate(String time) {
		return LocalDate.parse(time,df);
	}

	/**
	 * 获取指定年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static LocalDate getFirstDayOfMonth(int year, int month) {
		//本月的第一天
		return LocalDate.of(year,month,1);
	}

	/**
	 * 获取指定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static LocalDate getLastDayOfMonth(int year, int month) {
		//本月的最后一天
		LocalDate lastDay = LocalDate.of(year,month,1);
		lastDay = lastDay.with(TemporalAdjusters.lastDayOfMonth());
		return lastDay;
	}

	public static String format(String time) {
		return  time.replaceAll("[:.\\s]","");
	}

	static DateTimeFormatter df_ = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static DateTimeFormatter df1_ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static DateTimeFormatter df2_ = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
	static DateTimeFormatter df3 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	static DateTimeFormatter df_hm = DateTimeFormatter.ofPattern("HH.mm");

	public static String format3(LocalDateTime time) {
		return df3.format(time);
	}


	public static String formatHm(LocalDateTime time) {
		return df_hm.format(time);
	}

	public static String format(LocalDateTime time) {
		return df1_.format(time);
	}

	public static LocalDateTime parseToLocalDateTime(String time) {
		return LocalDateTime.parse(time,df1_);
	}

	public static LocalDateTime parseToLocalDateTime2(String time) {
		return LocalDateTime.parse(time,df2_);
	}


	/**
	 * 当前时间距离当天0点的分钟数
	 * @param time
	 * @return
	 */
	public static int durationTo0(LocalDateTime time) {
		LocalDate date = LocalDate.of(time.getYear(),time.getMonth(),time.getDayOfMonth());
		LocalDateTime today_start = LocalDateTime.of(date, LocalTime.MIN);

		Duration duration = Duration.between(today_start, time);
		return (int) duration.toMinutes();
	}

	public static int durationToyesterday12(LocalDateTime time,LocalDateTime standard) {
		LocalDate date = LocalDate.of(standard.getYear(),standard.getMonth(),standard.getDayOfMonth());
		LocalDateTime yesterday12 = LocalDateTime.of(date, LocalTime.NOON);
		Duration duration = java.time.Duration.between(yesterday12, time);
		return (int) duration.toMinutes();
	}

	public static void main(String[] args) {

		System.out.println(format3(LocalDateTime.now()));
	}

}
