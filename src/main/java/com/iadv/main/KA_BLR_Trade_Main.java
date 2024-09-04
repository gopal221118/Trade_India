package com.iadv.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.data.XLSXtoHashMap_Row_Header;



public class KA_BLR_Trade_Main {

	public static void main(String args[]) {
		try {
			HashMap<Integer,ArrayList<String>> xlsxMapCol= XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			System.out.println(xlsxMapCol);
			
			HashMap<Integer,HashMap<String, String>> xlsxMapHeader= XLSXtoHashMap_Row_Header.getConvertedMap(args[0]);
			System.out.println(xlsxMapHeader);
			
			System.out.println(xlsxMapCol.get(2).get(5));
			System.out.println(xlsxMapHeader.get(2).get("Company Category"));

		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
