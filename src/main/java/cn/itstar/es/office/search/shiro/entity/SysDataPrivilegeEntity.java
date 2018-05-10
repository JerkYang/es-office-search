package cn.itstar.es.office.search.shiro.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 数据权限
 */
public class SysDataPrivilegeEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据权限id
	 */
	private Long dataId;
	
	/**
	 * 上级数据权限id，一级部门为0
	 */
	private Long parentId;
	
	/**
	 * 上级数据权限名称
	 */
	private String parentName;
	
	/**
	 * 数据权限编码
	 */
	private String code;
	
	/**
	 * 数据权限名称
	 */
	private String name;
	
	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * 可用标识，1：可用，0：不可用
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Timestamp gmtModified;
	
	/**
	 * ztree属性
	 */
	private Boolean open;
	
	private List<?> list;

	public SysDataPrivilegeEntity() {
		super();
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
