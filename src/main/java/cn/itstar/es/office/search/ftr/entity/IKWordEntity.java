package cn.itstar.es.office.search.ftr.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * IK 拓展词
 * @author star
 *
 */
public class IKWordEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ik拓展词ID
	 */
	private String ikWordId;
	
	/**
	 * IK拓展词的名称
	 */
	private String ikWordName;
	
	/**
	 * 创建拓展词的用户的 id
	 */
	private Long userIdCreate;
	
	/**
	 * 修改拓展词的用户的 id
	 */
	private Long userIdUpdate;
	
	/**
	 * 创建拓展词的用户的 名称
	 */
	private String userNameCreate;
	
	/**
	 * 修改拓展词的用户的 名称
	 */
	private String userNameUpdate;
	
	/**
	 * 状态(0：禁用   1：正常)
	 */
	private Integer status;
	
	/**
	 * 拓展词创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 拓展词更新时间
	 */
	private Timestamp gmtModified;

	public String getIkWordId() {
		return ikWordId;
	}

	public void setIkWordId(String ikWordId) {
		this.ikWordId = ikWordId;
	}

	public String getIkWordName() {
		return ikWordName;
	}

	public void setIkWordName(String ikWordName) {
		this.ikWordName = ikWordName;
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
}
