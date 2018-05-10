package cn.itstar.es.office.search.ftr.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class PreSubFixUtil {
	
	/**
	 * 通过带后缀的文件名截取得到不带后缀的文件名
	 * @param fileNameWithSuffix
	 * @return
	 */
	public static String getFileNameWithoutSuffix(String fileNameWithSuffix) {
		return StringUtils.substringBeforeLast(fileNameWithSuffix,".");
	}
	
	/**
	 * 获取到带后缀的文件名
	 * @param clientName
	 * 		      通过前端上传文件得到的文件名(可能带路径,因浏览器而异)
	 * @return
	 */
	public static String getFileNameWithSuffix(String clientName) {
		if (clientName.contains("\\")) {//如果包含"\"表示是一个带路径的名字,则截取最后的文件名
			return clientName = clientName.substring(clientName.lastIndexOf("\\")).substring(1);
		}else {
			return clientName.substring(clientName.lastIndexOf(".") + 1);
        }
	}
	
	
	/**
	 * 通过文件全路径得到文件名称
	 * @param filePath 文件路径
	 * @return 文件名称
	 */
	@SuppressWarnings("finally")
	public static String getFileNames(String filePath) {
		
		
		
		// 获取带后缀的文件名,例如:abc.txt
		String fileNameWithPreSuffix = "";
		if (filePath.contains("\\")) {//如果包含"\"表示是一个带路径的名字,则截取最后的文件名
			fileNameWithPreSuffix = filePath.substring(filePath.lastIndexOf("\\")).substring(1);
		}else {
			fileNameWithPreSuffix = filePath;
        }
		
		int length = UUID.randomUUID().toString().length();
		String fileNameWithSuffix = "";
		try {
			// 去掉前面的 UUID 字段
			String subString = fileNameWithPreSuffix.substring(0, length);
			// 将 subString 转换为 UUID 看是否会报 IllegalArgumentException 异常
			UUID uuID = UUID.fromString(subString);
			fileNameWithSuffix  = StringUtils.substringAfter(fileNameWithPreSuffix, uuID.toString());
			
//		} catch (Exception ex) {
//			
//			
//			ex.printStackTrace();
			// throw new Exception();
			
		} catch (StringIndexOutOfBoundsException e) {
			fileNameWithSuffix = fileNameWithPreSuffix;
			// System.out.println("文件名没有UUID前缀！");
			
		} finally {
			return StringUtils.substringBeforeLast(fileNameWithSuffix,".");
		}
	}
	// 去掉前面的 UUID 字段
//			fileNameWithSuffix = fileNameWithPreSuffix.substring(length);
//			System.out.println(substring);
//			String subString = fileNameWithPreSuffix.substring(0, length);
//			String replace = subString.replace("-", "");
//			UUID uuID = UUID.fromString(subString);
//			fileNameWithSuffix  = StringUtils.substringAfter(fileNameWithPreSuffix, uuID.toString());


	/**
	 * 通过文件全路径得到文件名称
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件名称
	 */
	@SuppressWarnings("finally")
	public static String getFileNameWithSub(String filePath) {

		// 获取带后缀的文件名,例如:abc.txt
		String fileNameWithPreSuffix = "";
		if (filePath.contains("\\")) {// 如果包含"\"表示是一个带路径的名字,则截取最后的文件名
			fileNameWithPreSuffix = filePath.substring(filePath.lastIndexOf("\\")).substring(1);
		} else {
			fileNameWithPreSuffix = filePath;
		}

		int length = UUID.randomUUID().toString().length();
		String fileNameWithSuffix = "";
		try {
			// 去掉前面的 UUID 字段
			String subString = fileNameWithPreSuffix.substring(0, length);
			// 将 subString 转换为 UUID 看是否会报 IllegalArgumentException 异常
			UUID uuID = UUID.fromString(subString);
			fileNameWithSuffix = StringUtils.substringAfter(fileNameWithPreSuffix, uuID.toString());

			// } catch (Exception ex) {
			//
			//
			// ex.printStackTrace();
			// throw new Exception();

		} catch (StringIndexOutOfBoundsException e) {
			fileNameWithSuffix = fileNameWithPreSuffix;
			// System.out.println("文件名没有UUID前缀！");

		} finally {
			return fileNameWithSuffix;
		}
	}
	// 去掉前面的 UUID 字段
	// fileNameWithSuffix = fileNameWithPreSuffix.substring(length);
	// System.out.println(substring);
	// String subString = fileNameWithPreSuffix.substring(0, length);
	// String replace = subString.replace("-", "");
	// UUID uuID = UUID.fromString(subString);
	// fileNameWithSuffix = StringUtils.substringAfter(fileNameWithPreSuffix,
	// uuID.toString());
}
