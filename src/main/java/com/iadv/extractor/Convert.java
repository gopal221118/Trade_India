package com.iadv.extractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Convert {

	public static ArrayList<ArrayList<String>> data = new ArrayList<>();
	// Create an Excel workbook
	public static Workbook workbook = new XSSFWorkbook();
	// Create a sheet in the workbook
	public static Sheet sheet = workbook.createSheet("PDF Data");

	public static String excelFilePath = "output.xlsx";

	public static void remover(String path) {

		String pdfFilePath = path;

		try {
			// Load the PDF document
			PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath));

			// Extract text from PDF
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String pdfText = pdfStripper.getText(pdfDocument);

			// Close the PDF document
			pdfDocument.close();
			HashMap<String, String> pdfmap = new HashMap<String, String>();
			String pdfvals[] = pdfText.split(":");
			for (int i = 0; i < pdfvals.length; i++) {
				System.out.println(i + "------------>" + pdfvals[i]);
				if(pdfvals[i].contains("Office"))
				{
					String city= pdfvals[i].substring(pdfvals[i].lastIndexOf(" ")+1,pdfvals[i].length());
					pdfmap.put("City", city);
				}
				else if(pdfvals[i].startsWith("800"))
				{
					pdfmap.put("Registration_No", pdfvals[i].substring(0, pdfvals[i].indexOf(" ")));
				}
				else if(pdfvals[i].startsWith("800"))
				{
					pdfmap.put("Registration_No", pdfvals[i].substring(0, pdfvals[i].indexOf(" ")));
				}
				else if(pdfvals[i].contains("Ph. No."))
				{
					String ft=pdfvals[i].replace("Ph. No.", "~");
					String addNPhone[]= ft.split("~");
					pdfmap.put("Address", (addNPhone[0]).trim());
					String findmob=(addNPhone[1]).trim();
					pdfmap.put("Mobile_No",findmob.substring(0,findmob.indexOf(" ")) );
				}
				else if(pdfvals[i].contains(" - ") && pdfvals[i].contains(".20"))
				{
					String expdt[]=pdfvals[i].split("-");
							String lpt=(expdt[1].trim());
					pdfmap.put("Expiry_Date",expdt[0].trim()+"-"+lpt.substring(0,lpt.indexOf(" ")));
       		    }
				else if(pdfvals[i].contains("/"))
				{
					int slcount = (int) pdfvals[i].chars().filter(ch -> ch == '/').count();
					if(slcount==3)
					{
						pdfmap.put("License_Number",pdfvals[i]);
	
					}

				}
				else if(pdfvals[i].contains("S/o"))
				{
					String namex= pdfvals[i].replace("S/o", "~");
					String names[]=namex.split("~");
					String name=names[0].substring(0,names[0].lastIndexOf("~"));
					pdfmap.put("Name",name);
				}
				else if(pdfvals[i].contains(".00"))
				{
					pdfmap.put("Fees",pdfvals[i]);

				}

			}
			String cityx=pdfText.substring(pdfText.lastIndexOf(" ")+1,pdfText.length());
			pdfmap.put("Real_City",cityx);
			

			System.out.println("*****"+pdfmap);
			int rowNum = 0;
			// Split the PDF text by lines and add to Excel
			String[] lines = pdfText.split("\\r?\\n");
			ArrayList<String> temp = new ArrayList<>();
			StringBuilder str = new StringBuilder();
//            System.out.println(lines[51]);
			// System.out.println(lines.length);

//            for (int i =0; i< lines.length; i++)
//            {
//                str.append(lines[i]);
//
//                    System.out.println("line no. "+i+"--> " + lines[i]);
//
//
//
//            }

//            String strarr[] = str.toString().split("मा�लक फोटो");
//            for (int i =0; i< strarr.toString().length(); i++)
//            {
//
//                System.out.println("curent i "+i+" --. "+strarr[i]);
//
//               // str.append(lines[i]);
//            }
			// System.out.println(str.toString());
//            for (int i =0; i<lines.length; i++)
//            {
//
//
//
//                //System.out.println("Block number " + j );
//                if(i==16 || i==17 || i==15 || i==7 || i==0 || i==11) {
//                    String line = lines[i];
//                    String[] lists = line.split("[-,:]");
//                    boolean tracker = false;
//
//
//                    for (int j = 0; j < 1; j++) {
//
//
//                        if (i == 16) {
//                            if (lists.length >= 2) {
//
//                                System.out.println(lists[1]);
//                                tracker = true;
//
//                                break;
//                            }
//
//                        } else if (i == 17) {
//                            String dummy[] = line.split(",");
//                            if (dummy.length >= 2) {
//                                System.out.println(dummy[1]);
//                                tracker = true;
//
//                            }
//                            String text[] = {};
//                            if (lists.length >= 4) {
//                                text = lists[3].split(" ");
//
//                            }
//
//                            if (text.length >= 3) {
//                                System.out.println(text[2]);
//                                tracker = true;
//                                break;
//                            }
//
//                        } else if (i == 21) {
//                            if (lists.length >= 3) {
//                                System.out.println(lists[1] + "-");
//                                String text[] = lists[2].split(" ");
//                                if (text.length >= 2) {
//
//                                    System.out.println(" " + text[1]);
//                                    tracker = true;
//                                    break;
//                                }
//
//                            }
//
//                        } else if (i == 15) {
//                            if (lists.length >= 2) {
//
//                                String text[] = lists[1].split(" ");
//                                if (text.length >= 3) {
//
//                                    System.out.println(" " + text[1] + " " + text[2]);
//                                    tracker = true;
//                                    break;
//                                }
//                            }
//                            //System.out.println(list);
//
//
//                        } else if (i == 7) {
//                            if (lists.length >= 2) {
//                                System.out.println(lists[1]);
//                                tracker = true;
//                                break;
//
//                            }
//
//                        } else if (i == 11) {
//                            if (lists.length >= 2) {
//                                System.out.println(lists[1]);
//                                tracker = true;
//                                break;
//
//                            }
//
//                        } else if (i == 0) {
//                            if (lists.length >= 3) {
//                                System.out.println(lists[2]);
//                                tracker = true;
//                                break;
//
//                            }
//
//                        }
//
//                    }
//                    if(tracker==false)
//                    {
//                        System.out.println("");
//                    }
//                }
//                //System.out.println("*****************************");
//
//            }

			data.add(temp);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) throws Exception {

		ArrayList<String> list = new ArrayList<>();
		list.add("Registration Number");
		list.add("City");
		list.add("Name");
		list.add("Fine Fee");
		list.add("Phone Number");
		list.add("DD/MM/YYYY");
		list.add("DD/MM/YYYY");
		list.add("Registration Name");
		list.add("Registration Address");
		data.add(list);

		File[] files = new File("\"C:\\Users\\Superuser\\Desktop\\Pdf_to_excel\\Pdf_to_excel\\src\\MP\"").listFiles();

		// Calling method 1 to
		// display files*/
		displayFiles(files);

		int rawi = 0;
		for (ArrayList<String> l : data) {
			int cell = 0;
			Row row = sheet.createRow(rawi++);
			for (String cdata : l) {

				row.createCell(cell++).setCellValue(cdata);
			}
		}

		// Write the Excel file to disk
		FileOutputStream fileOut = new FileOutputStream(excelFilePath);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();

		System.out.println("PDF has been successfully converted to Excel!");

	}

	public static void displayFiles(File[] files) {
		// Traversing through the files array
//        for (File filename : files) {
//            // If a sub directory is found,
//            // print the name of the sub directory
//            if (filename.isDirectory()) {
//                System.out.println("Directory: "
//                        + filename.getAbsolutePath());
//
//                // and call the displayFiles function
//                // recursively to list files present
//                // in sub directory
//                displayFiles(filename.listFiles());
//            }
//
//            // Printing the file name present in given path
//            else {
//                // Getting the file name
//                System.out.println(filename.getAbsolutePath());
//                remover(filename.getAbsolutePath());
//                //Convertor(filename.getAbsolutePath());
//            }
		remover("C:/Users/Superuser/Desktop/Pdf_to_excel/Pdf_to_excel/src/MP/eNagarPalika_Trade_Renewal_20240305054250.pdf");
	}

	public static void Convertor(String path) {
		String pdfFilePath = path;

		try {
			// Load the PDF document
			PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath));

			// Extract text from PDF
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String pdfText = pdfStripper.getText(pdfDocument);

			// Close the PDF document
			pdfDocument.close();

			int rowNum = 0;
			// Split the PDF text by lines and add to Excel
			String[] lines = pdfText.split("\\r?\\n");
			ArrayList<String> temp = new ArrayList<>();

			for (int i = 0; i < lines.length; i++) {

				// System.out.println("Block number " + j );
				if (i == 16 || i == 17 || i == 15 || i == 7 || i == 0 || i == 11) {
					String line = lines[i];
					String[] lists = line.split("[-,:]");
					boolean tracker = false;

					for (int j = 0; j < 1; j++) {

						if (i == 16) {
							if (lists.length >= 2) {

								temp.add(lists[1]);
								tracker = true;

								break;
							}

						} else if (i == 17) {
							String dummy[] = line.split(",");
							if (dummy.length >= 2) {
								temp.add(dummy[1]);
								tracker = true;

							}
							String text[] = {};
							if (lists.length >= 4) {
								text = lists[3].split(" ");

							}

							if (text.length >= 3) {
								temp.add(text[2]);
								tracker = true;
								break;
							}

						} else if (i == 21) {
							if (lists.length >= 3) {
								temp.add(lists[1] + "-");
								String text[] = lists[2].split(" ");
								if (text.length >= 2) {

									temp.add(" " + text[1]);
									tracker = true;
									break;
								}

							}

						} else if (i == 15) {
							if (lists.length >= 2) {

								String text[] = lists[1].split(" ");
								if (text.length >= 3) {

									temp.add(" " + text[1] + " " + text[2]);
									tracker = true;
									break;
								}
							}
							// System.out.println(list);

						} else if (i == 7) {
							if (lists.length >= 2) {
								temp.add(lists[1]);
								tracker = true;
								break;

							}

						} else if (i == 11) {
							if (lists.length >= 2) {
								temp.add(lists[1]);
								tracker = true;
								break;

							}

						} else if (i == 0) {
							if (lists.length >= 3) {
								temp.add(lists[2]);
								tracker = true;
								break;

							}

						}

					}
					if (tracker == false) {
						temp.add("");
					}
				}
				// System.out.println("*****************************");

			}

			data.add(temp);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
