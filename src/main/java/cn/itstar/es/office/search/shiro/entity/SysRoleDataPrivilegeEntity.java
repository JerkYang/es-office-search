package cn.itstar.es.office.search.shiro.entity;


import java.io.Serializable;

/**
 * 角色与数据权限对应关系
 */
public class SysRoleDataPrivilegeEntity implements Serializable {
	
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
	 * 数据权限ID
	 */
	private Long dataPrivilegeId;

	public SysRoleDataPrivilegeEntity() {
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

	public Long getDataPrivilegeId() {
		return dataPrivilegeId;
	}

	public void setDataPrivilegeId(Long dataPrivilegeId) {
		this.dataPrivilegeId = dataPrivilegeId;
	}

}
