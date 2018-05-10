package cn.itstar.es.office.search.ftr.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.UUID;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import cn.itstar.es.office.search.common.entity.R;
import cn.itstar.es.office.search.common.entity.SysUserEntity;
import cn.itstar.es.office.search.common.utils.HttpContextUtils;
import cn.itstar.es.office.search.ftr.entity.OfficeFileEntity;
import cn.itstar.es.office.search.shiro.service.SysUserService;

@Controller
public class UpAndDownLoadUtil {
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param filepath
	 * @param officeFile
	 * @return
	 * @throws Exception
	 */
	public static Boolean UpLoad(HttpServletRequest request, HttpServletResponse response,String prepath, OfficeFileEntity officeFile) throws Exception{
		response.setContentType("text/html;charset=utf-8");//设置响应编码
        request.setCharacterEncoding("utf-8");
        //PrintWriter writer = response.getWriter();//获取响应输出流
        
        //ServletInputStream inputStream = request.getInputStream();//获取请求输入流
        
        // 最终保存在磁盘的路径(带文件全名,如:C:\guge\3\49a050da-0f53-4f84-a585-04c9d9cef4b5药品集中采购系统3.1.1.docx)
        String diskFilePath = null;
        
        /*
         * 1、创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
         *    该类有两个构造方法一个是无参的构造方法，
         *    另一个是带两个参数的构造方法
         * @param  int sizeThreshold,该参数设置内存缓冲区的大小，默认值为10K。当上传文件大于缓冲区大小时，fileupload组件将使用临时文件缓存上传文件
         * @param  java.io.File repository,该参数指定临时文件目录，默认值为System.getProperty("java.io.tmpdir");
         * 
         *    如果使用了无参的构造方法，则使用setSizeThreshold(int sizeThreshold),setRepository(java.io.File repository)
         *    方法手动进行设置 
         */
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        int sizeThreshold=1024*1024;
        factory.setSizeThreshold(sizeThreshold);
        
//        File repository = new File(request.getSession().getServletContext().getRealPath("temp"));
        // 设置上传临时路径
        
        File repository = new File(prepath + "\\temp");
        if (!repository.exists()) {
        	repository.mkdirs();
        }
//        System.out.println(request.getSession().getServletContext().getRealPath("temp"));
//        System.out.println(request.getRealPath("temp"));
        factory.setRepository(repository);
        
        /*
         * 2、使用DiskFileItemFactory对象创建ServletFileUpload对象，并设置上传文件的大小
         *    
         *    ServletFileUpload对象负责处理上传的文件数据，并将表单中每个输入项封装成一个FileItem
         *    该对象的常用方法有：
         *           boolean isMultipartContent(request);判断上传表单是否为multipart/form-data类型
         *           List parseRequest(request);解析request对象，并把表单中的每一个输入项包装成一个fileItem 对象，并返回一个保存了所有FileItem的list集合
         *           void setFileSizeMax(long filesizeMax);设置单个上传文件的最大值
         *           void setSizeMax(long sizeMax);设置上传温江总量的最大值
         *           void setHeaderEncoding();设置编码格式，解决上传文件名乱码问题
         */
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        upload.setHeaderEncoding("utf-8");//设置编码格式，解决上传文件名乱码问题
        /*
         * 3、调用ServletFileUpload.parseRequest方法解析request对象,得到一个保存了所有上传内容的List对象
         */
        List<FileItem> parseRequest=null;
        try {
             parseRequest = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        
        /*
         * 4、对list进行迭代，每迭代一个FileItem对象，调用其isFormField方法判断是否是文件上传
         *    true表示是普通表单字段，则调用getFieldName、getString方法得到字段名和字段值
         *    false为上传文件，则调用getInputStream方法得到数据输入流，从而读取上传数据
         *    
         *    FileItem用来表示文件上传表单中的一个上传文件对象或者普通的表单对象
         *    该对象常用方法有：
         *         boolean isFormField();判断FileItem是一个文件上传对象还是普通表单对象
         *         true表示是普通表单字段，
         *                 则调用getFieldName、getString方法得到字段名和字段值
         *         false为上传文件，
         *                 则调用getName()获得上传文件的文件名，注意：有些浏览器会携带客户端路径，需要自己减除
         *                 调用getInputStream()方法得到数据输入流，从而读取上传数据
         *                 delete(); 表示在关闭FileItem输入流后，删除临时文件。
         */
        
        for (FileItem fileItem : parseRequest) {
            if (fileItem.isFormField()) {//表示普通字段
                if ("fileName".equals(fileItem.getFieldName())) {
                    String fileName = fileItem.getString("UTF-8");
                    //将fileName 字段的值保存到 OfficeFile对象中
                    officeFile.setFileName(fileName);
                   // writer.write("您的文件名："+fileName+"<br>");
                }
                /*if ("filePath".equals(fileItem.getFieldName())) {
                    String filePath = fileItem.getString();
                    writer.write("您的密码："+filePath+"<br>");
                }*/
                
            }else {//表示是上传的文件
                //不同浏览器上传的文件可能带有路径名，需要自己切割
                String clientName = fileItem.getName();
            	String filename = "";
            	if (clientName.contains("\\")) {//如果包含"\"表示是一个带路径的名字,则截取最后的文件名
            		filename = clientName.substring(clientName.lastIndexOf("\\")).substring(1);
            	}else {
            		filename = clientName;
            	}
            	
            	// 如果上传了文件
            	if (!("".equals(clientName))) {
                	
                	UUID randomUUID = UUID.randomUUID();//生成一个128位长的全球唯一标识
                	
                	filename = randomUUID.toString()+filename;
                	
                	/*
                	 * 设计一个目录生成算法，如果所用用户上传的文件总数是亿数量级的或更多，放在同一个目录下回导致文件索引非常慢，
                	 * 所以，设计一个目录结构来分散存放文件是非常有必要，且合理的
                	 * 将UUID取哈希算法，散列到更小的范围，
                	 * 将UUID的hashcode转换为一个8位的8进制字符串，
                	 * 从这个字符串的第一位开始，每一个字符代表一级目录，这样就构建了一个八级目录，每一级目录中最多有16个子目录
                	 * 这无论对于服务器还是操作系统都是非常高效的目录结构
                	 */
                	/*
                int hashUUID =randomUUID.hashCode();
                String hexUUID = Integer.toHexString(hashUUID);
                //System.out.println(hexUUID);
                //获取将上传的文件存存储在哪个文件夹下的绝对路径
//                String filepath=request.getSession().getServletContext().getRealPath("upload");
                // String filepath = ConstantUtil.path;
                for (char c : hexUUID.toCharArray()) {
                    filepath = filepath+"/"+c;
                }
                //如果目录不存在就生成八级目录
                File filepathFile = new File(filepath);
                if (!filepathFile.exists()) {
                    filepathFile.mkdirs();
                }
                	 */
                	
                	// 这里采用 FileUtil 工具类动态创建日期文件目录
                	String filePath = FileUtil.createFiles(prepath);
                	
                	//从Request输入流中读取文件，并写入到服务器
                	InputStream inputStream2 = fileItem.getInputStream();
                	
                	//在服务器端创建文件
                	File file  = new File(filePath+"/"+filename);
                	
                	// 最终的文件路径(带文件名)
                	diskFilePath = file.toString();
                	
                	// 将 diskFilePath 的值存到 OfficeFile 对象中
                	officeFile.setFilePath(diskFilePath);
                	
                	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                	
                	byte[] buffer = new byte[10*1024];
                	int len = 0;
                	while ((len= inputStream2.read(buffer, 0, 10*1024))!=-1) {
                		bos.write(buffer, 0, len);
                	}
                	// writer.write("您上传文件"+clientName+"成功<br>");
                	//关闭资源
                	bos.close();
                	inputStream2.close();
                	fileItem.delete();
                	
                } else {
                	// 文件名为空，未上传文件
                	officeFile.setStatus(null);
                	officeFile.setFilePath(null);
                }
            }
        }
    //注意Eclipse的上传的文件是保存在项目的运行目录，而不是workspace中的工程目录里。
		return true;
	}
	
	/**
	 * 更改文件
	 * @param request
	 * @param response
	 * @param prePath 文件路径前缀，在此路径下创建日期文件夹
	 * @param officeFile
	 * @return
	 * @throws Exception
	 */
	public static Boolean UpLoadUpdate(HttpServletRequest request, HttpServletResponse response, String prePath, OfficeFileEntity officeFile, SysUserService sysUserService) throws Exception{
		response.setContentType("text/html;charset=utf-8");//设置响应编码
        request.setCharacterEncoding("utf-8");
        // PrintWriter writer = response.getWriter();//获取响应输出流
        // 最终保存在磁盘的路径(带文件全名,如:C:\guge\3\49a050da-0f53-4f84-a585-04c9d9cef4b5药品集中采购系统3.1.1.docx)
        String diskFilePath = null;
        // 要删除的原文件全路径
        String deleteFilePath = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(0);
        
        
        int sizeThreshold=1024*1024;
        factory.setSizeThreshold(sizeThreshold);
        File repository = new File(prePath + "\\temp");
        if (!repository.exists()) {
        	repository.mkdirs();
        }
        factory.setRepository(repository);
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        upload.setHeaderEncoding("utf-8");//设置编码格式，解决上传文件名乱码问题
        List<FileItem> parseRequest=null;
        try {
             parseRequest = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        
        for (FileItem fileItem : parseRequest) {
            if (fileItem.isFormField()) {//表示普通字段
            	if ("id".equals(fileItem.getFieldName())) {
                    String id = fileItem.getString();
                    //将id 字段的值保存到 OfficeFile对象中
                    officeFile.setFileId(Long.parseLong(id));
                    continue;
                }
            	if ("createUser".equals(fileItem.getFieldName())) {
            		String createPerson = fileItem.getString();
            		//将createPerson 字段的值保存到 OfficeFile对象中
            		//SysUserEntity createUser = sysUserService.getUserById(Long.parseLong(userId));
            		//officeFile.setCreatePerson(createUser);
            		continue;
            	}
            	if ("updateUser".equals(fileItem.getFieldName())) {
            		String updatePerson = fileItem.getString();
            		//将updatePerson 字段的值保存到 OfficeFile对象中
            		//SysUserEntity updateUser = sysUserService.getUserById(updatePerson);
            		//officeFile.setUpdatePerson(updateUser);
            		continue;
            	}
                if ("fileName".equals(fileItem.getFieldName())) {
                    String fileName = fileItem.getString("UTF-8");
                    //将fileName 字段的值保存到 OfficeFile对象中
                    officeFile.setFileName(fileName);
                    continue;
                }
                // id,fileName,createPerson,createAt,updatePerson,updateAt,filePath,reFileName,
                if ("createAt".equals(fileItem.getFieldName())) {
                    String createAt = fileItem.getString();
                    //将createAt 字段的值保存到 OfficeFile对象中
                    timestamp.setTime(sdf.parse(createAt).getTime());
                    //officeFile.setCreateAt(timestamp);
                    continue;
                }
                
                if ("updateAt".equals(fileItem.getFieldName())) {
                    String updateAt = fileItem.getString();
                    //将updateAt 字段的值保存到 OfficeFile对象中
                    timestamp.setTime(sdf.parse(updateAt).getTime());
                    //officeFile.setUpdateAt(timestamp);
                    continue;
                }
                if ("filePath".equals(fileItem.getFieldName())) {
                	deleteFilePath = fileItem.getString("UTF-8");
                	// deleteFilePath = deleteFilePath.trim();
                	/*
                	 * 如果原文件路径不为空，则删除原路径下的文件
                	 */
                	if ("".equals(deleteFilePath)) {
                		boolean deleteFile = FileUtil.deleteFile(deleteFilePath.toString());
                		if (!deleteFile) {
                			return false;
                		}
                	}
                	/*// 设置路径
                	if (deleteFilePath.contains("\\")) {// 如果包含"\"表示是一个带路径的名字,则截取最后的文件名
                		deleteFilePath = deleteFilePath.substring(0, deleteFilePath.lastIndexOf("\\")).substring(0);
                	}*/
                	// officeFile.setFilePath(filePath);
                	continue;
                }
            }else {//表示是上传的文件
                //不同浏览器上传的文件可能带有路径名，需要自己切割
                String clientName = fileItem.getName();
                String fileName = "";
                if (clientName.contains("\\")) {//如果包含"\"表示是一个带路径的名字,则截取最后的文件名
                	fileName = clientName.substring(clientName.lastIndexOf("\\")).substring(1);
                }else {
                	fileName = clientName;
                }
                
                UUID randomUUID = UUID.randomUUID();//生成一个128位长的全球唯一标识
                // 文件名不为空时
                if (!("".equals(fileName))) {
	                fileName = randomUUID.toString() + fileName;
	
	                /*
	                 * 调用 FileUtil 工具类中的方法动态创建日期目录文件夹，并返回路径 
	                 */
	                String filePath = FileUtil.createFiles(prePath);
	                // File filePathFile = new File(filePath);
	                /*//如果目录不存在就生成该目录
	                if (!filePathFile.getParentFile().exists()) {
	                    // 分两次mkdirs，是为了避免目录层级过高导致目录创建失败的情况
	                	filePathFile.getParentFile().mkdirs();
	                }
	                if (!filePathFile.exists()) {
	                	filePathFile.createNewFile();
	                }*/
	
	                //从Request输入流中读取文件，并写入到服务器
	                InputStream inputStream2 = fileItem.getInputStream();
	                //在服务器端创建文件
	            	File file  = null;
            	
            	
                	//在服务器端创建文件
                	file  = new File(filePath + "\\" + fileName);
                	// 最终的文件路径(带文件名)
                	diskFilePath = file.toString();
                	
                	// 将 diskFilePath 的值存到 OfficeFile 对象中
                	officeFile.setFilePath(diskFilePath);
                	FileOutputStream outputStream = new FileOutputStream(file);
                	BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                	
                	byte[] buffer = new byte[10*1024];
                	int len = 0;
                	while ((len= inputStream2.read(buffer, 0, 10*1024))!=-1) {
                		bos.write(buffer, 0, len);
                	}
                	//关闭资源
                	bos.close();
                	inputStream2.close();
                	fileItem.delete();
                } else {
                	// 文件名为空，未上传文件
                	officeFile.setFilePath(null);
                }
            }
        }
		return true;
	}
	
	
	/**
	 * 文件的下载
	第一步：设置要下载文件的MIME类型，设置头信息 Content-Disposition，无论是什么格式，都是以下载方式打开
	第二步：从服务器上得到要下载的文件的输入流
	第三步：创建输出流，通过输出流把文件写到浏览器
	第四步：流对接，关闭流
	 * @return 
	 * @throws Exception 
	 */
	public static R DownLoad(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {
		//得到要下载文件的路径
		//根据路径得到文件的名称  c:\aa\c.jpg
		String filename = PreSubFixUtil.getFileNameWithSub(path);
		
//		int lens = path.lastIndexOf("\\");
//		String filename = path.substring(lens+1);
//		System.out.println(path);
//		System.out.println(filename);
		
		//得到当前请求的浏览器类型 ，使用头 User-Agent
		String agent = request.getHeader("User-Agent");
//		System.out.println(agent);
		if(agent.contains("Firefox")) {//如果是火狐浏览器
			//base64编码
			Encoder encoder = Base64.getEncoder();
			byte[] encode = encoder.encode(filename.getBytes("utf-8"));
			filename = "=?UTF-8?B?"+ encode +"?=";
		} else {
			//url编码
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		response.setContentType("application/force-download");// 设置强制下载不打开
		//设置要下载文件的mime类型
		String type = request.getServletContext().getMimeType(filename);
//		//设置mime类型
		response.setContentType(type);
		//设置头信息 Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		//得到要下载文件的输入流
		InputStream in = new FileInputStream(path);
		//使用输出流操作
		OutputStream out = response.getOutputStream();
		//流的对接
		int len = 0;
		byte[] b = new byte[1024];
		while((len=in.read(b))!=-1) {
			out.write(b, 0, len);
		}
		//关闭流
		in.close();
		return null;
	}
	
	
	/**
	 * 文件的下载
	第一步：设置要下载文件的MIME类型，设置头信息 Content-Disposition，无论是什么格式，都是以下载方式打开
	第二步：从服务器上得到要下载的文件的输入流
	第三步：创建输出流，通过输出流把文件写到浏览器
	第四步：流对接，关闭流
	 * @throws Exception 
	 */
	public static R DownLoadFile(String path) throws Exception {
		//得到要下载文件的路径
		//根据路径得到文件的名称  c:\aa\c.jpg
		String filename = PreSubFixUtil.getFileNameWithSuffix(path);
		
//		BufferedInputStream bis = null; 
//        BufferedOutputStream bos = null; 
        
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		HttpServletResponse response = HttpContextUtils.getHttpServletResponse();


		// 得到当前请求的浏览器类型 ，使用头 User-Agent
		String agent = request.getHeader("User-Agent");
		// System.out.println(agent);
		if (agent.contains("Firefox")) {// 如果是火狐浏览器
			// base64编码
			Encoder encoder = Base64.getEncoder();
			byte[] encode = encoder.encode(filename.getBytes("utf-8"));
			filename = "=?UTF-8?B?" + encode + "?=";
		} else {
			// url编码
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		response.setContentType("application/force-download");// 设置强制下载不打开
		// 设置要下载文件的mime类型
		String type = request.getServletContext().getMimeType(filename);
		// //设置mime类型
		response.setContentType(type);
		// 设置头信息 Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		// 得到要下载文件的输入流
		InputStream in = new FileInputStream(path);
		// 使用输出流操作
		OutputStream out = response.getOutputStream();
		// 流的对接
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		// 关闭流
		in.close();
		return null;
	}
	
	
	public static void fileDownload(String filePath, String fileName) throws Exception{  
	     
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
		
	    byte[] data = toByteArray2(filePath);  
	    fileName = URLEncoder.encode(fileName, "UTF-8");  
	    response.reset();  
	    //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
	    response.setContentType("multipart/form-data");  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	   // response.setContentType("application/octet-stream;charset=UTF-8");  
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	    response.flushBuffer();
	    
	} 
	
	
	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 文件批量打包压缩下载
	 * @param objs
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void downloadFiles(List<OfficeFileEntity> objs) throws ServletException, IOException {

		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
		request.setCharacterEncoding("UTF-8");
		// List<Element> elements = doc.getRootElement().elements();
		List<File> files = new ArrayList<File>();
		int index = 0;
		long fileLength = 0;
		for (OfficeFileEntity officeFile : objs) {
			String filePath = officeFile.getFilePath();
			File file = new File(filePath);
			files.add(file);
			fileLength += file.length();
			index++;
		}
		String fileName = UUID.randomUUID().toString() + ".zip";
		// 在服务器端创建打包下载的临时文件
		String outFilePath = "D:\\" + fileName;
		File file = new File(outFilePath);
		// 文件输出流
		FileOutputStream outStream = new FileOutputStream(file);
		// 压缩流
		ZipOutputStream toClient = new ZipOutputStream(outStream);
		toClient.setEncoding("gbk");
		zipFile(files, toClient);
		toClient.close();
		outStream.close();
		downloadZip(file, response);
	}

	/**
	 * 压缩文件列表中的文件
	 * 
	 * @param files
	 * @param outputStream
	 * @throws IOException
	 */
	public static void zipFile(List files, ZipOutputStream outputStream) throws IOException, ServletException {
		try {
			int size = files.size();
			// 压缩列表中的文件
			for (int i = 0; i < size; i++) {
				File file = (File) files.get(i);
				zipFile(file, outputStream);
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 将文件写入到zip文件中
	 * 
	 * @param inputFile
	 * @param outputstream
	 * @throws Exception
	 */
	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);

					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
												// and positions the stream for
												// writing the next entry
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				throw new ServletException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 下载打包的文件
	 * 
	 * @param file
	 * @param response
	 */
	public static void downloadZip(File file, HttpServletResponse response) {
		try {
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			file.delete(); // 将生成的服务器端文件删除
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
