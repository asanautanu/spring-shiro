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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpms.common.constant.Constant.Global;

/**
 * 用户后台登陆用户表--权限管理实体
 *
 * @date 2015年10月29日 下午2:42:04
 * @author luogang
 */
@Entity
@Table(name = Global.TABLE_PREFIX + "user")
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7721861566419318065L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户名
	 */
	@Column(name = "user_name", length = 50)
	private String userName;

	/**
	 * 密码
	 */
	@JsonIgnore
	@Column(name = "password", nullable = false, length = 64)
	private String password;

	/**
	 * 盐值
	 */
	@JsonIgnore
	@Column(name = "salt", nullable = false, length = 64)
	private String salt;

	/**
	 * 真实姓名
	 */
	@Column(name = "name", length = 10)
	private String name;

	@Transient
	@JsonIgnore
	private String planPassWord;

	/**
	 * 工号
	 */
	@Column(name = "number", length = 50)
	private String number;

	/**
	 * 是否登录
	 */
	@Column(name = "is_login", nullable = false)
	private Boolean login;

	/**
	 * 启停状态
	 */
	@Column(name = "state")
	private boolean state = true;

	/**
	 * 删除标志
	 */
	@Column(name = "delete_flag", nullable = false)
	private boolean deleteFlag = false;

	/***
	 * 创建者
	 */
	@Column(name = "userId")
	private Integer userId;

	@Column(name = "creater_name", length = 55)
	private String createrName;

	/**
	 * 电子邮件
	 */
	@Column(length = 50, name = "email")
	private String email;

	/**
	 * 电话
	 */
	@Column(length = 20, name = "phone")
	private String phone;

	/**
	 * 角色
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "mpms_user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") }, uniqueConstraints = {
							@UniqueConstraint(columnNames = { "user_id", "role_id" }) })
	private Set<Role> roles;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 运营人员最后操作时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "audit_time")
	private Date auditTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public String toString() {
		return this.userName.toString();
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

	public String getPlanPassWord() {
		return planPassWord;
	}

	public void setPlanPassWord(String planPassWord) {
		this.planPassWord = planPassWord;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}