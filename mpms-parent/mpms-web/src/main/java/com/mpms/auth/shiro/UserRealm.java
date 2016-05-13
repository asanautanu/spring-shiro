package com.mpms.auth.shiro;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Constants;
import com.google.common.collect.Lists;
import com.mpms.common.constant.Constant.Auths;
import com.mpms.common.exception.CaptchaException;
import com.mpms.common.po.auth.Role;
import com.mpms.common.po.auth.User;
import com.mpms.common.service.auth.UserService;
import com.mpms.utils.encrypt.EncoderHandler;

/**
 * 用户登录授权service(shrioRealm)
 *
 * @date 2015年11月4日 下午12:24:29
 * @author seiya
 */
public class UserRealm extends AuthorizingRealm {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String HASH_ALGORITHM = "SHA-1";

	public static final int HASH_INTERATIONS = 1024;

	@Autowired
	private UserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 * 
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 * @date 2015年11月9日 下午2:58:47
	 * @author seiya
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		User user = null;
		user = userService.findUserByLoginName(token.getUsername());
		if (doCaptchaValidate(token) && user != null) {
			if (user.getLogin() == null || !user.getLogin()) {
				throw new DisabledAccountException("用户已禁用");
			}
			byte[] salt = EncoderHandler.decodeHex(user.getSalt());

			Set<Role> roles = user.getRoles();
			// 权限
			List<Integer> roleIds = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(roles)) {
				for (Role role : roles) {
					roleIds.add(role.getId());
				}
			}
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(Auths.SESSION_MAP_KEY_USERNAME, user.getUserName());
			return new SimpleAuthenticationInfo(
					new ShiroUser(user.getId(), user.getUserName(), user.getName(), null, roleIds), user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			// 返回空为用户不存在的，SHIRO会抛出UnknownAccountException异常
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 * 
	 * @param principals
	 * @return
	 * @date 2015年11月9日 下午2:58:39
	 * @author seiya
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		List<String> permissions = userService.findPermissionByUserName(shiroUser.getUserName());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		if (!CollectionUtils.isEmpty(permissions)) {
			info.addStringPermissions(permissions);
		}
		return info;
	}

	/**
	 * 验证码校验
	 * 
	 * @param token
	 * @return
	 * @date 2015年11月9日 下午2:58:57
	 * @author seiya
	 */

	protected boolean doCaptchaValidate(UsernamePasswordCaptchaToken token) {
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new CaptchaException("验证码错误！");
		}
		return true;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 * 
	 * @date 2015年11月9日 下午2:59:04
	 * @author seiya
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 * 
	 * @date 2015年12月24日 下午12:39:45
	 * @author seiya
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = 1729373344680913232L;

		private Integer id;

		private String userName;

		private String name;

		private List<Integer> roleIds;

		public ShiroUser(Integer id, String userName, String name, Integer institutionId, List<Integer> roleIds) {
			this.id = id;
			this.userName = userName;
			this.name = name;
			this.roleIds = roleIds;
		}

		public Integer getId() {
			return id;
		}

		public String getUserName() {
			return userName;
		}

		public String getName() {
			return name;
		}

		public List<Integer> getRoleIds() {
			return roleIds;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (userName == null) {
				if (other.userName != null) {
					return false;
				}
			} else if (!userName.equals(other.userName)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return this.userName.toString();
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(userName);
		}
	}
}
