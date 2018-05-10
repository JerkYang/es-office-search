package cn.itstar.es.office.search.ftr.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
/**
 * Apache POI 是用Java编写的免费开源的跨平台的 Java API，Apache POI提供API给Java程式对Microsoft
 * Office格式档案读和写的功能。 POI为"Poor Obfuscation Implementation"的首字母缩写，意为"可怜的模糊实现"。
 * solr对文档内容进行分词建索引，最终文档解析的过程实现选择了通过poi进行文档内容的读取
 * 
 * @author star
 *
 */
public class POIUtil {
	
	private static List<String> listFiles;

	/**
	 * 初始化 listFiles
	 * @param path 路径
	 * @return 文件全路径名的集合
	 */
	public static List<String> getAllFileNames(String path) {
		// 初始化 listFiles 的值为 null，否则静态变量 listFiles 的值在服务器启动后会一直增加
		listFiles = new ArrayList<String>();
		
		return getAllFile(path);
	}
	/**
	 * 得到文件夹下所有的文件名的全路径集合
	 * @param path 路径
	 * @param return 文件全路径名的集合
	 * 			[C:\Users\star\Desktop\Lucene\guge\ggu\-风中有朵雨做的云.txt,
	 *			 C:\Users\star\Desktop\Lucene\guge\ggu\ggo\东南西北风-黄安.txt,
	 *			 ...
	 *          ]
	 */
	private static List<String> getAllFile(String path) {
		File file = new File(path);
		//获取指定目录下当前的所有文件夹或者文件对象
		File[] files = file.listFiles();
		String fileStr = null;
		
		for (File fileName : files) {//fileName = C:\Users\star\Desktop\Lucene\guge\ggu
			if (fileName.isDirectory()) {
				getAllFile(fileName.getAbsolutePath());
			} else {
				fileStr = fileName.toString();
				listFiles.add(fileStr);
			}
		}
		return listFiles;
	}
	
	public static List<String> getFile(String path) {
		File file = new File(path);
		//获取指定目录下当前的所有文件夹或者文件对象
		File[] files = file.listFiles();
		String fileStr = null;
		for (File fileName : files) {//fileName = C:\Users\star\Desktop\Lucene\guge\ggu
			if (fileName.isDirectory()) {
				getAllFile(fileName.getAbsolutePath());
			} else {
				fileStr = fileName.toString();
				listFiles.add(fileStr);
			}
		}
		return listFiles;
	}
	
	/**
	 * 得到文件夹下所有的文件名
	 * @param path 路径
	 * @param fileName 文件名
	 */
	public static ArrayList<String> getAllFileName(String path) {
		File file = new File(path);
		// 获取指定目录下当前的所有文件夹或者文件对象
		File[] files = file.listFiles();
		String[] names = file.list();
		ArrayList<String> fileNames = new ArrayList<String>();
		if (names != null)
			fileNames.addAll(Arrays.asList(names));
		for (File a : files) {
			if (a.isDirectory()) {
				getAllFileName(a.getAbsolutePath());
			} else {
				return fileNames;
			}
		}
		return fileNames;
	}
	
	/**
	 * 复制文件到指定目录下
	 * @param sour
	 * @param dest
	 */
	public static void copyFile(String sour, String dest) {
		File sourFile = new File(sour);
		String filename = sourFile.list()[0];

		String inputname = sour + filename;
		String outputname = dest + filename;
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(inputname);
			output = new FileOutputStream(outputname);

			int in = input.read();

			while (in != -1) {
				output.write(in);
				in = input.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据文件后缀名读取文件内容
	 * @param file
	 * @param sufName	文件后缀
	 * @return
	 */
	public static String off2String(File file, String sufName) {
		if("txt".equalsIgnoreCase(sufName)) {
			return txt2String(file);
		} else if ("doc".equalsIgnoreCase(sufName)) {
			return doc2String(file);
		} else if ("docx".equalsIgnoreCase(sufName)) {
			return docx2String(file);
		} else if ("xls".equalsIgnoreCase(sufName)) {
			return xls2String(file);
		} else if ("xlsx".equalsIgnoreCase(sufName)) {
			return xlsx2String(file);
		} else if ("ppt".equalsIgnoreCase(sufName)) {
			return ppt2String(file);
		} else if ("pdf".equalsIgnoreCase(sufName)) {
			return pdf2String(file);
		}
		return null;
	}
	
	/**
	 * 根据不同类型文件读取文件内容
	 * @param inputStream
	 * @param sufName	文件后缀
	 * @return
	 */
	public static String off2String(InputStream inputStream, String sufName) {
		if("txt".equalsIgnoreCase(sufName)) {
			return txt2String(inputStream);
		} else if ("doc".equalsIgnoreCase(sufName)) {
			return doc2String(inputStream);
		} else if ("docx".equalsIgnoreCase(sufName)) {
			return docx2String(inputStream);
		} else if ("xls".equalsIgnoreCase(sufName)) {
			return xls2String(inputStream);
		} else if ("xlsx".equalsIgnoreCase(sufName)) {
			return xlsx2String(inputStream);
		} else if ("ppt".equalsIgnoreCase(sufName)) {
			return ppt2String(inputStream);
		} else if ("pdf".equalsIgnoreCase(sufName)) {
			return pdf2String(inputStream);
		}
		return null;
	}
	
	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 读取txt文件的内容
	 * @param inputStream 
	 * @return
	 * 		  返回文件内容
	 */
	public static String txt2String(InputStream inputStream) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取doc文件内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	@SuppressWarnings("resource")
	public static String doc2String(File file) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			Range range = doc.getRange();
			result += range.text();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 读取doc文件内容
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String doc2String(InputStream inputStream) {
		String result = "";
		try {
			HWPFDocument doc = new HWPFDocument(inputStream);
			Range range = doc.getRange();
			result += range.text();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取docx文件
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String docx2String(File file) {
		String str = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			XWPFDocument xdoc = new XWPFDocument(fis);
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			String doc1 = extractor.getText();
			str += doc1;
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 读取docx文件
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String docx2String(InputStream inputStream) {
		String str = "";
		try {
			XWPFDocument xdoc = new XWPFDocument(inputStream);
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			String doc1 = extractor.getText();
			str += doc1;
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 读取xls文件内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String xls2String(File file) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(fis);
			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					for (int k = 0; k < cells.length; k++)
						sb.append(cells[k].getContents());
				}
			}
			fis.close();
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 读取xls文件内容
	 * @param inputStream
	 * @return
	 */
	public static String xls2String(InputStream inputStream) {
		String result = "";
		try {
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(inputStream);
			Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					for (int k = 0; k < cells.length; k++)
						sb.append(cells[k].getContents());
				}
			}
			inputStream.close();
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取xlsx文件
	 */
	@SuppressWarnings("resource")
	public static String xlsx2String(File file) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			XSSFWorkbook xwb = new XSSFWorkbook(fis);
			XSSFSheet sheet = xwb.getSheetAt(0);
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
					sb.append(row.getCell(j).toString());
				}
			}
			fis.close();
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 读取xlsx文件
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String xlsx2String(InputStream inputStream) {
		String result = "";
		try {
			StringBuilder sb = new StringBuilder();
			XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = xwb.getSheetAt(0);
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
					sb.append(row.getCell(j).toString());
				}
			}
			inputStream.close();
			result += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 读取ppt
	 */
	public static String ppt2String(File file) {
		FileInputStream fi = null;
		PowerPointExtractor ppExtractor = null;
		try {
			fi = new FileInputStream(file);
			ppExtractor = new PowerPointExtractor(fi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ppExtractor.getText();
	}
	
	/**
	 * 读取ppt
	 * @param inputStream
	 * @return
	 */
	public static String ppt2String(InputStream inputStream) {
		PowerPointExtractor ppExtractor = null;
		try {
			ppExtractor = new PowerPointExtractor(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ppExtractor.getText();
	}

	/**
	 * 读取pdf
	 * 
	 * @param file
	 * @return
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static String pdf2String(File file) {
		PDDocument document = null;
		PDFTextStripper stripper = null;
		try {
			document = PDDocument.load(file);
			stripper = new PDFTextStripper();
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stripper.setSortByPosition(false);
		String result = null;
		try {
			result = stripper.getText(document);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 读取pdf
	 * @param inputStream
	 * @return
	 */
	public static String pdf2String(InputStream inputStream) {
		PDDocument document = null;
		PDFTextStripper stripper = null;
		try {
			document = PDDocument.load(inputStream);
			stripper = new PDFTextStripper();
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stripper.setSortByPosition(false);
		String result = null;
		try {
			result = stripper.getText(document);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@SuppressWarnings("resource")
	public static String pptx2String(String file) throws IOException, XmlException, OpenXML4JException {
	     return new XSLFPowerPointExtractor(POIXMLDocument.openPackage(file)).getText();   
	}

}
