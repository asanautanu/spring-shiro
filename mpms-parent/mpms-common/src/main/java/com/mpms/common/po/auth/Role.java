package com.mpms.common.po.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpms.common.constant.Constant.Global;

/**
 * 
 * 用户后台登陆用户角色表--RBAC权限管理实体
 *
 * @date 2015年10月29日 下午2:42:16
 * @author luogang
 */
@Entity
@Table(name = Global.TABLE_PREFIX + "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -4918428203603100036L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 删除标志
	 */
	@Column(name = "delete_flag")
	private boolean deleteFlag;

	/**
	 * 角色名
	 */
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	/**
	 * 角色描述
	 */
	@Column(name = "description", length = 512)
	private String description;

	/**
	 * 拥有权限
	 */
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "mpms_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "resource_id") })
	private Set<Resource> resource;

	/***
	 * 创建者
	 */
	@Column(name = "creater")
	private Integer creater;

	@Column(name = "creater_name", length = 55)
	private String createrName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Set<Resource> getResource() {
		return resource;
	}

	public void setResource(Set<Resource> resource) {
		this.resource = resource;
	}

}