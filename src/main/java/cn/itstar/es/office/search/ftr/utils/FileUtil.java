package cn.itstar.es.office.search.ftr.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.itstar.es.office.search.common.entity.R;

/**
 * 上传和删除文件
 * @author star
 *
 */
public class FileUtil {
	/**
     * 删除某个文件夹下的所有文件夹和文件
     * 
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
	public static boolean deleteAllFile(String delpath) throws Exception {
		try {
            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                        System.out.println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deleteAllFile(delpath + "\\" + filelist[i]);
                    }
                }
                System.out.println(file.getAbsolutePath() + "删除成功");
                file.delete();
            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
	}
	
	
	/**
     * 删除指定路径下的文件
     * 
     * @param filePath
     *            String
     * @return boolean
     */
	public static Boolean deleteFileByPath(String filePath) {
		try {
            File file = new File(filePath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            }
        } catch (Exception e) {
        	return false;
        }
        return true;
	}
	 /**
     * 输出某个文件夹下的所有文件夹和文件路径
     * 
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
	public static boolean readFile(String filepath)
            throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            System.out.println("遍历的路径为：" + file.getAbsolutePath());
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时（即文件夹下有子文件时），返回 true
            if (!file.isDirectory()) {
                System.out.println("该文件的绝对路径：" + file.getAbsolutePath());
                System.out.println("名称：" + file.getName());
            } else if (file.isDirectory()) {
                // 得到目录中的文件和目录
                String[] filelist = file.list();
                if (filelist.length == 0) {
                    System.out.println(file.getAbsolutePath()
                            + "文件夹下，没有子文件夹或文件");
                } else {
                    System.out
                            .println(file.getAbsolutePath() + "文件夹下，有子文件夹或文件");
                }
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    System.out.println("遍历的路径为：" + readfile.getAbsolutePath());
                    if (!readfile.isDirectory()) {
                        System.out.println("该文件的路径："
                                + readfile.getAbsolutePath());
                        System.out.println("名称：" + readfile.getName());
                    } else if (readfile.isDirectory()) {
                        System.out.println("-----------递归循环-----------");
                        readFile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile() Exception:" + e.getMessage());
        }
        return true;
    }
	
	 /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (!file.isDirectory()) {
            if (file.delete()) {
            	System.out.println("删除单个文件" + fileName + "成功！");
            	return true;
            } else {
            	System.out.println("删除单个文件" + fileName + "失败！");
            	return false;
            }
        } else {
        	System.out.println("删除单个文件失败：" + fileName + "不存在！");
        	return false;
        }
    }
    
    /**
     * 根据指定的文件前缀路径，在当前文件路径下根据日期动态创建日期目录文件夹
     * @param prePath
     * @return 如果创建成功则返回文件夹路径，如果创建失败则返回 null
     */
    public static String createFiles(String prePath) {
    	Date date = new Date();
		//SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");

		String sufPath = format.format(date.getTime());

		File filePath = new File(prePath + "\\" + sufPath);
		String strPath = filePath.toString();
		/*if (!filePath.getParentFile().exists()) {
            // 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
			filePath.getParentFile().mkdirs();
			return strPath;
        } else if (filePath.getParentFile().exists()) {
        	filePath.
        }*/
		try {
			// 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
			if (!filePath.getParentFile().exists()) {
	            // 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
				filePath.getParentFile().mkdirs();
	        } else if (filePath.getParentFile().exists()) {
	        	filePath.mkdir();
	        }
			// filePath.getParentFile().mkdirs();
			return strPath;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    /**
     * 创建时间文件夹
     * @return
     */
    public static String createTimeFiles(String prePath) {
    	Date date = new Date();
		//SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");

		String sufPath = format.format(date.getTime());

		File filePath = new File(prePath +sufPath);
		String strPath = filePath.toString();
		/*if (!filePath.getParentFile().exists()) {
            // 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
			filePath.getParentFile().mkdirs();
			return strPath;
        } else if (filePath.getParentFile().exists()) {
        	filePath.
        }*/
		try {
			// 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
			if (!filePath.getParentFile().exists()) {
	            // 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
				filePath.getParentFile().mkdirs();
	        } else if (filePath.getParentFile().exists()) {
	        	filePath.mkdir();
	        }
			// filePath.getParentFile().mkdirs();
			return strPath;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }


}
