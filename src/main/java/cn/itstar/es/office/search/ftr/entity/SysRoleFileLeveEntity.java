package cn.itstar.es.office.search.ftr.entity;


import java.io.Serializable;

/**
 * 角色与文件级别对应关系
 */
public class SysRoleFileLeveEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 记录id
	 */
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 文件级别ID(0:绝密      1:机密      2:秘密      3:敏感        4:公开)
	 */
	private Long fileLevelId;

	public SysRoleFileLeveEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFileLevelId() {
		return fileLevelId;
	}

	public void setFileLevelId(Long fileLevelId) {
		this.fileLevelId = fileLevelId;
	}

}
