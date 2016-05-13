package com.mpms.common.constant;

/**
 * 常量
 * 
 * @date 2016年5月12日
 * @author seiya
 */
public class Constant {

	/**
	 * 全局常量
	 * 
	 * @date 2016年5月12日
	 * @author seiya
	 */
	public static class Global {
		/**
		 * 数据库表前缀
		 */
		public static final String TABLE_PREFIX = "tb_";
	}

	/**
	 * 权限相关
	 * 
	 * @date 2016年5月12日
	 * @author seiya
	 */
	public static class Auths {

		/**
		 * session REDIS KEY
		 */
		public static final String REDIS_SHIRO_SESSION_KEY = "shiro_session_key";

		/**
		 * session REDIS FIELD
		 */
		public static final String REDIS_SHIRO_SESSION_FIELD = "shiro_session_field:";

		/**
		 * Cache REDIS KEY
		 */
		public static final String REDIS_SHIRO_CACHE_KEY = "shiro_cache_key";

		/**
		 * Cache REDIS FIELD
		 */
		public static final String REDIS_SHIRO_CACHE_FIELD = "shiro_cache_field:";

		public static final String SESSION_MAP_KEY_USERNAME = "userName";
		/**
		 * 初始化密码
		 */
		public static final String DEFAULT_PASSWORD = "123456";

		public static final String INIT_USER_NAME = "administrator";
	}

	/**
	 * 分页查询相关
	 * 
	 * @author lc
	 * @data 2015年12月1日 下午5:03:11
	 */
	public static class Page {

		/**
		 * 分页查询总条数 key
		 */
		public static final String RESULT_COUNT = "total_count";

		/**
		 * 分页查询返回数据 key
		 */
		public static final String RESULT_DATA = "data";
	}

}
