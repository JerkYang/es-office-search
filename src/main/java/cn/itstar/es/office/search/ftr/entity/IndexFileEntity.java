package cn.itstar.es.office.search.ftr.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class IndexFileEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件记录 id
	 */
	private Long fileId;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 创建文件的用户的 id
	 */
	private Long userIdCreate;
	
	/**
	 * 修改文件的用户的 id
	 */
	private Long userIdUpdate;
	
	/**
	 * 创建文件的用户的 名称
	 */
	private String userNameCreate;
	
	/**
	 * 修改文件的用户的 名称
	 */
	private String userNameUpdate;
	
	/**
	 * 文件级别ID(0:绝密      1:机密      2:秘密      3:敏感        4:公开)
	 */
	private Integer fileLevelId;
	
	/**
	 * 文件级别名称
	 */
	private String fileLevelName;
	
	/**
	 * 状态(0：禁用   1：正常)
	 */
	private Integer status;
	
	/**
	 * 文件创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 文件更新时间
	 */
	private Timestamp gmtModified;
	
	/**
	 * 文件内容
	 */
	private String contentText;

	
}
