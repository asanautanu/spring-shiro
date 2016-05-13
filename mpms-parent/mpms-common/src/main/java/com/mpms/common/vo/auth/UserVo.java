package com.mpms.common.vo.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.mpms.common.po.auth.Role;
import com.mpms.common.po.auth.User;

/**
 * 用户Vo
 * 
 * @date 2016年1月13日 下午12:04:18
 * @author luogang
 */
/**
 *
 * @date 2016年1月25日 下午12:35:57
 * @author luogang
 */
public class UserVo implements Serializable {

	private static final long serialVersionUID = 1721810106147456726L;

	private Integer id;

	public UserVo(User user) {
		try {
			BeanUtils.copyProperties(this, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 真实姓名
	 */
	private String name;

	/**
	 * 工号
	 */
	private String number;

	/**
	 * 是否登录
	 */
	private Boolean login;

	/**
	 * 所属机构 Institution
	 */

	private String institutionName;

	/***
	 * 创建者
	 */

	private String createrName;

	/**
	 * 职位
	 */

	private List<String> positionNames;

	/**
	 * 客户数量
	 */
	private Integer custNumber;
	/**
	 * 借款申请中数量
	 */
	private Integer waiting;

	/**
	 * 借款还款中数量
	 */
	private Integer payment;

	/**
	 * 借款逾期中数量
	 */
	private Integer overdue;

	/**
	 * 借款结清数量
	 */
	private Integer settle;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 运营人员最后操作时间
	 */
	private Date auditTime;
	/**
	 * 审核中数量
	 */
	private Integer auditNum;

	/**
	 * 分配数量
	 */
	private Integer allotNum;
	/**
	 * 已审核
	 */
	private Integer authNum;

	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public List<String> getPositionNames() {
		return positionNames;
	}

	public void setPositionNames(List<String> positionNames) {
		this.positionNames = positionNames;
	}

	public Integer getWaiting() {
		return waiting;
	}

	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public Integer getSettle() {
		return settle;
	}

	public void setSettle(Integer settle) {
		this.settle = settle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(Integer auditNum) {
		this.auditNum = auditNum;
	}

	public Integer getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(Integer custNumber) {
		this.custNumber = custNumber;
	}

	public Integer getAllotNum() {
		return allotNum;
	}

	public void setAllotNum(Integer allotNum) {
		this.allotNum = allotNum;
	}

	public Integer getAuthNum() {
		return authNum;
	}

	public void setAuthNum(Integer authNum) {
		this.authNum = authNum;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public static List<UserVo> copyUserVos(List<User> users) {
		List<UserVo> userVos = null;

		if (CollectionUtils.isNotEmpty(users)) {
			userVos = Lists.newArrayListWithCapacity(users.size());

			for (User user : users) {
				UserVo userVo = new UserVo(user);
				userVos.add(userVo);
			}
		}
		return userVos;
	}

}
