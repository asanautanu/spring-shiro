package com.mpms.common.po.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.mpms.common.constant.Constant.Global;
import com.mpms.common.enums.auth.ResourceType;

/**
 * 用户后台资源资源权限表--RBAC权限管理实体
 *
 * @date 2015年10月29日 下午2:40:52
 * @author luogang
 */
@Entity
@Table(name = Global.TABLE_PREFIX + "resource")
@JsonIgnoreProperties("parent")
// @Audited
public class Resource implements Serializable {

	private static final long serialVersionUID = -8865754272969721766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 删除标志
	 */
	@Column(name = "delete_flag", nullable = false)
	private boolean deleteFlag;

	/**
	 * 资源类型
	 */
	@Column(name = "resource_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private ResourceType resourceType;

	/**
	 * 资源名称
	 */
	@Column(name = "name", length = 64, nullable = false)
	private String name;

	/**
	 * 链接
	 */
	@Column(name = "href", length = 200)
	private String href;

	/**
	 * 资源描述
	 */
	@Column(name = "description", length = 200)
	private String description;

	/**
	 * 图标
	 */
	@Column(name = "icon", length = 55)
	private String icon;

	/**
	 * 权限字符串
	 */
	@Column(name = "permission", length = 64, unique = true)
	private String permission;

	/**
	 * 排序
	 */
	@Column(length = 3, name = "order_no")
	private Integer orderNo;

	/**
	 * 父ID
	 */
	private Integer pId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
