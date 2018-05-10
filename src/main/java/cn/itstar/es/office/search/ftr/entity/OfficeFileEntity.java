package cn.itstar.es.office.search.ftr.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class OfficeFileEntity implements Serializable {

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


	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public Long getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Long userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getUserNameCreate() {
		return userNameCreate;
	}

	public void setUserNameCreate(String userNameCreate) {
		this.userNameCreate = userNameCreate;
	}

	public String getUserNameUpdate() {
		return userNameUpdate;
	}

	public void setUserNameUpdate(String userNameUpdate) {
		this.userNameUpdate = userNameUpdate;
	}

	public Integer getFileLevelId() {
		return fileLevelId;
	}

	public void setFileLevelId(Integer fileLevelId) {
		this.fileLevelId = fileLevelId;
	}

	public String getFileLevelName() {
		return fileLevelName;
	}

	public void setFileLevelName(String fileLevelName) {
		this.fileLevelName = fileLevelName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
}
