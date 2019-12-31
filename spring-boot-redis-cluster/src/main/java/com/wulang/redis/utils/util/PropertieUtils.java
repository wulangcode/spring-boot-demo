package com.wulang.redis.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;



/**操作属性文件的帮助类
 * 属性文件中的路径可参考对应的注释
 * @author binzhao
 *
 */
public class PropertieUtils {
	private static Logger log = LoggerFactory.getLogger(PropertieUtils.class);
	private Properties properties;
	private ResourceBundle rb;
	private boolean isProperties;
	private boolean isLoadSuccess;
	
	public PropertieUtils() {
		// TODO Auto-generated constructor stub
	}
	/**根据系统类加载器来初始化环境：用来加载类的搜索路径打开具有指定名称的资源
	 * com/yourcompany/struts/ApplicationResources_zh_CN.properties
	 * @name 资源名称 
	 */
	public void initBySystemResourceAsStream(String name) {
		isProperties = true;;
		InputStream in = ClassLoader.getSystemResourceAsStream(name);
		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			isLoadSuccess = false;
			log.error("initBySystemResourceAsStream, --error:"+e.getMessage());
		}
		isLoadSuccess = true;
	}
	
	/** 绝对资源名需以“/”开头
	 * /com/yourcompany/struts/ApplicationResources_zh_CN.properties
	 * @param name
	 */
	public void initByResourceAsStream(String name) {
		isProperties = true;
		InputStream in = Properties.class.getResourceAsStream(name);
		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			isLoadSuccess = false;
			log.error("initByResourceAsStream, --error:"+e.getMessage());
		}
		isLoadSuccess = true;
	}
	
	/**根据文件路径来初始化文件，参考下面路径来设置相关值
	 * src/com/yourcompany/struts/ApplicationResources_zh_CN.properties
	 * @param path 文件路径
	 */
	public void initByFilePath(String path) {
		isProperties = true;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			isLoadSuccess = false;
			log.error("file not fount, --error:"+e.getMessage());
		}
		properties = new Properties();
		try {
			properties.load(in);
			isLoadSuccess = false;
		} catch (IOException e) {
			log.error("initByFilePath, --error:"+e.getMessage());
		}
		isLoadSuccess = true;
	}
	
	/**根据资源包名初始化环境，参考下面路径来设置相关值
	 * com.yourcompany.struts.ApplicationResources
	 * @param path 资源包路径
	 */
	public void initByBundle(String path) {
		isProperties = false;
		try {
			rb = ResourceBundle.getBundle(path, Locale.getDefault());
		} catch (Exception e) {
			isLoadSuccess = false;
			log.error("initByBundle, --error:"+e.getMessage());
		}
		isLoadSuccess = true;
	}
	
	public String getValue(String key) {
		String value = null;
		if (isProperties) {
			value = properties.getProperty(key);
		}else{
			try {
				value = rb.getString(key);
			} catch (Exception e) {
				// 键【"+key+"】不存在
				log.error("getValue, --error:"+e.getMessage());
			}
		}
		 return value;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	/**资源包对象,该对象提供根据键访问值的功能
	 * @return
	 */
	public ResourceBundle geteBundle() {
		return rb;
	}
	
	public static void main(String[] args) {
		// Properties a = new Properties();
		// try {
		// a.load( new BufferedInputStream(new FileInputStream("src/com/yourcompany/struts/ApplicationResources_zh_CN.properties")));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
//		ResourceBundle a = ResourceBundle.getBundle("mail.ApplicationResources", Locale.getDefault());
//		ResourceBundle a = ResourceBundle.getBundle("config", Locale.getDefault());
		ResourceBundle a = ResourceBundle.getBundle("config", new Locale("zh","CN"));
		String s = a.getString("ipdata.path");// server1=192.168.101.109:11211
		System.out.print(PropertieUtils.getValueByBundle("", "ipdata.path"));
	}
	
	/**根据资源包名初始化环境，参考下面路径来设置相关值
	 * com.yourcompany.struts.ApplicationResources
	 * @param path 资源包路径
	 */
	public static String getValueByBundle(String path,String key) {
		try {
//			System.out.println("================="+key+"="+ResourceBundle.getBundle("config", new Locale("zh","CN")).getObject(key));
//			return ResourceBundle.getBundle(SystemUtils.getProjectEnviroment()+"-conf/"+path, new Locale("zh","CN")).getString(key);
		} catch (Exception e) {
			log.error("getValueByBundle, --error:"+e.getMessage());
		}
		return null;
	}
	
	public static boolean getValueBooleanByBundle(String path,String key) {
		String value = getValueByBundle(path, key);
		return "true".equals(value)? true:false;
	}
	
	
	public static int getValueIntByBundle(String path,String key) {
		String value = getValueByBundle(path,key);
		if(null == value || value.trim().equals("")){
			return 0;
		}
		try {
			return Integer.valueOf(value.trim());
		} catch (NumberFormatException e) {
			log.error("getValueIntByBundle, --error:"+e.getMessage());
		}
		return 0;
	}
	
	/**示例：获取dev-conf目录下wx属性文件配置的wx.appId
	 * PropertieUtils.getValueByBundleFromConf("wx","wx.appId");
	 * @param path
	 * @param key
	 * @return
	 */
	public static String getValueByBundleFromConf(String path,String key) {
		return getValueByBundle(PropertieUtils.getEev(path),key);
	}
	public static int getValueIntByBundleFromConf(String path,String key) {
		return getValueIntByBundle(PropertieUtils.getEev(path),key);
	}
	
	
	public static String getEev(String path){
		StringBuffer buf = new StringBuffer();
		//TODO 自行补充
//		buf.append(System.getProperty(Final.ENV_KEY));
//		buf.append(Final.ENV_SUFFIX);
		buf.append(".");
		buf.append(path);
		return buf.toString();
	}
	
}
