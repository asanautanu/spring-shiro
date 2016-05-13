package com.mpms.common.vo.auth;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单封装
 * 
 * @date 2015年12月24日 下午3:06:48
 * @author luogang
 */
public class ResourceVo implements Serializable {

	private static final long serialVersionUID = 417852814447055337L;

	private Integer id;
	private String name;
	private Integer pId;
	private String url;

	private List<ResourceVo> node;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ResourceVo> getNode() {
		return node;
	}

	public void setNode(List<ResourceVo> node) {
		this.node = node;
	}

	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getpId() {
		return pId;
	}

	
	public void setpId(Integer pId) {
		this.pId = pId;
	}

}
