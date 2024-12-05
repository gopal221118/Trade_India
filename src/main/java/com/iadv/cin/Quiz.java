package com.iadv.cin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.iadv.data.XLSXtoHashMap_Row_Col;

public class Quiz {
	
	public static void main(String args[])
	{
		try
		{
			ArrayList<Double> templist = new ArrayList<Double>();
			HashMap<Double, ArrayList<String>> quizNMap =new HashMap<Double, ArrayList<String>>();
			HashMap<Integer, ArrayList<String>> quizMap = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);	
			for(int i=1;i<quizMap.size();i++)
			{
				Double tempkey=Math.random();
				quizNMap.put(tempkey, quizMap.get(i));
				templist.add(tempkey);
			}
			Collections.sort(templist);
			for(Double d:templist)
			{
				System.out.println(quizNMap.get(d));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
