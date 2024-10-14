package com.iadv.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateDifference {
	public static String getdayNDiff() {
		String dayndiff = "";
		try {
			LocalDate today = LocalDate.now();
			LocalDate specificDate = LocalDate.of(2024, 9, 30);
			long daysDifference = ChronoUnit.DAYS.between(specificDate, today);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			dayndiff = dayndiff + (today.format(formatter) + "(" + daysDifference + " days delay)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dayndiff;
	}
	
	public static void main(String args[])
	{
		System.out.println(getdayNDiff());
	}
}
