package com.wulang.redis.utils.util;

import com.google.common.base.Strings;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证相关的源是否遵循相关的规
 */
public class Inspection {
	private static Logger log = LoggerFactory.getLogger(Inspection.class);

	// ------------------常量定义
	/**
	 * Email正则表达式=^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
	 */
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	/**
	 * 电话号码正则表达式=
	 * (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|
	 * (^0?1[35]\d{9}$)
	 */
	public static final String PHONE = "(^(}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
	/**
	 * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$
	 */
	public static final String MOBILE = "^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$";

	/**
	 * IP地址正则表达式
	 * ((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\
	 * d|[01]?\\d?\\d))
	 */
	public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";

	/**
	 * Integer正则表达式 ^-?(([1-9]\d*$)|0)
	 */
	public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
	/**
	 * 正整数正则表达式 >=0 ^[1-9]\d*|0$
	 */
	public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
	/**
	 * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
	 */
	public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
	/**
	 * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
	 */
	public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
	/**
	 * 正Double正则表达式 >=0 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$　
	 */
	public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
	/**
	 * 负Double正则表达式 <= 0 ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
	 */
	public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
	/**
	 * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
	 */
	public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
	/**
	 * 邮编正则表达式 [1-9]\d{5}(?!\d) 国内6位邮编
	 */
	public static final String CODE = "[1-9]\\d{5}(?!\\d)";
	/**
	 * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
	 */
	public static final String STR_ENG_NUM_ = "^\\w+$";
	/**
	 * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
	 */
	public static final String STR_ENG_NUM = "^\\w+$";
	/**
	 * 匹配由26个英文字母组成的字符串 ^[A-Za-z]+$
	 */
	public static final String STR_ENG = "^[A-Za-z]+$";
	/**
	 * 匹配中文字符 ^[\u0391-\uFFE5]+$
	 */
	public static final String STR_CHINA = "^[\u0391-\uFFE5]+$";
	/**
	 * 过滤特殊字符串正则 regEx=
	 * "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	 */
	public static final String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	/**
	 * 过滤特殊字符串正则 regEx=不包括顿号
	 * "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]";
	 */
	public static final String STR_SPECIAL2 = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]";
	/**
	 * 过滤特殊字符串正则 regEx=不包括.号
	 * "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]";
	 */
	public static final String STR_SPECIAL3 = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]";
	/**
	 * 匹配特殊字符：!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
	 */
	public final static String SPECIAL_CHARACTER = "\\p{Punct}";
	/**
	 *只能输英文 数字 中文 ^[a-zA-Z0-9\u4e00-\u9fa5]+$
	 */
	public static final String STR_ENG_CHA_NUM = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
	/**  
     *    
     */
	/***
	 * 日期正则 支持： YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD的形式
	 */
	public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)"
			+ "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)"
			+ "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)"
			+ "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)"
			+ "(0?2)([-\\/\\._]?)(29)$)"
			+ "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)"
			+ "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|"
			+ "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";

	/**
	 * URL正则表达式 匹配 http https www ftp
	 */
	public static final String URL = "^(http|https|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
			+ "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
			+ "(\\w*:)*(\\w*\\+)*(\\w*\\.)*"
			+ "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

	/**
	 * 身份证正则表达式
	 */
	public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})"
			+ "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
			+ "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
	/**
	 * 1.匹配科学计数 e或者E必须出现有且只有一次 不含Dd 正则
	 * ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]
	 * ?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$
	 */
	public final static String SCIENTIFIC_A = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$";
	/**
	 * 2.匹配科学计数 e或者E必须出现有且只有一次 结尾包含Dd 正则
	 * ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012
	 * ]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$
	 */
	public final static String SCIENTIFIC_B = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$";
	/**
	 * 3.匹配科学计数 是否含有E或者e都通过 结尾含有Dd的也通过（针对Double类型） 正则
	 * ^[-+]?(\d+(\.\d*)?|\.\d+)([
	 * eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$
	 */
	public final static String SCIENTIFIC_C = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";
	/**
	 * 4.匹配科学计数 是否含有E或者e都通过 结尾不含Dd 正则
	 * ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?
	 * \d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$
	 */
	public final static String SCIENTIFIC_D = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$";
	
	

	
	
	/**
	 * 判断字段是否为空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static  boolean strIsNull(String str) {
		return Strings.isNullOrEmpty(str);
	}

	/**
	 * 判断字段是非空 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean strNotNull(String str) {
		return !strIsNull(str);
	}

	/**
	 * 字符串null转空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String nulltoStr(String str) {
		return strIsNull(str) ? "" : str;
	}

	/**
	 * 字符串null赋值默认值
	 * 
	 * @param str
	 *            目标字符串
	 * @param defaut
	 *            默认值
	 * @return String
	 */
	public static String nullToStr(String str, String defaut) {
		return strIsNull(str) ? defaut : str;
	}

	/**
	 * 判断字段是否为Email 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
		return regular(str, EMAIL);
	}

	/**
	 * 判断是否为电话号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isPhone(String str) {
		return regular(str, PHONE);
	}

	/**
	 * 判断是否为手机号码 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		return regular(str, MOBILE);
	}

	/**
	 * 判断是否为Url 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isUrl(String str) {
		return regular(str, URL);
	}

	/**
	 * 判断是否为IP地址 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIpAddress(String str) {
		return regular(str, IPADDRESS);
	}

	/**
	 * 判断字段是否为数字 正负整数 正负浮点数 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		return regular(str, DOUBLE);
	}

	/**
	 * 判断字段是否为INTEGER 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isInteger(String str) {
		return regular(str, INTEGER);
	}

	/**
	 * 判断字段是否为正整数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIntegerNegative(String str) {
		return regular(str, INTEGER_NEGATIVE);
	}

	/**
	 * 判断字段是否为负整数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIntegerPositive(String str) {
		return regular(str, INTEGER_POSITIVE);
	}

	/**
	 * 判断字段是否为DOUBLE 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDouble(String str) {
		return regular(str, DOUBLE);
	}

	/**
	 * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDoubleNegative(String str) {
		return regular(str, DOUBLE_NEGATIVE);
	}
	/**
	 * 判断字段是否为正浮点数正则表达式 >=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDoubleNegative(Double  str) {
		return null == str ?false:regular(str.toString(), DOUBLE_NEGATIVE);
	}

	/**
	 * 判断字段是否为负浮点数正则表达式 <=0 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDoublePositive(String str) {
		return regular(str, DOUBLE_POSITIVE);
	}

	/**
	 * 判断字段是否为日期 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDate(String str) {
		return regular(str, DATE_ALL);
	}

	/**
	 * 判断字段是否为年龄 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isAge(String str) {
		return regular(str, AGE);
	}

	/**
	 * 判断字段是否超长 字串为空返回fasle, 超过长度{leng}返回ture 反之返回false
	 * 
	 * @param str
	 * @param leng
	 * @return boolean
	 */
	public static boolean isLengOut(String str, int leng) {
		return strIsNull(str) ? false : str.trim().length() > leng;
	}

	/**
	 * 判断字段是否为身份证 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isIdCard(String str) {
		if (strIsNull(str)) {
			return false;
		}
		if (str.trim().length() == 15 || str.trim().length() == 18) {
			return regular(str, IDCARD);
		} else {
			return false;
		}

	}

	/**
	 * 判断字段是否为邮编 符合返回ture
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isCode(String str) {
		return regular(str, CODE);
	}

	/**
	 * 判断字符串是不是全部是汉字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isChina(String str) {
		return regular(str, STR_CHINA);
	}

	/**
	 * 判断字符串是不是全部是英文字母
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEnglish(String str) {
		return regular(str, STR_ENG);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEngAndNum(String str) {
		return regular(str, STR_ENG_NUM);
	}

	/**
	 * 判断字符串是不是全部是英文字母+数字+下划线
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEngAndNumAnd_(String str) {
		return regular(str, STR_ENG_NUM_);
	}

	/**
	 * 过滤特殊字符串 返回过滤后的字符串
	 * 
	 * @param str
	 * @return boolean
	 */
	public static String filterStr(String str) {
		Pattern p = Pattern.compile(STR_SPECIAL);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 匹配是否符合正则表达式pattern 匹配返回true
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param pattern
	 *            匹配模式
	 * @return boolean
	 */
	public static boolean regular(String str, String pattern) {
		if (null == str || str.trim().length() <= 0) {
			return false;
		}
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是不是科学计数法 如果是 返回true 匹配科学计数 e或者E必须出现有且只有一次 结尾不含D 匹配模式可参考本类定义的
	 * SCIENTIFIC_A，SCIENTIFIC_B,SCIENTIFIC_C,SCIENTIFIC_D 若判断为其他模式可调用
	 * regular(String str,String pattern)方法
	 * 
	 * @param str
	 *            科学计数字符串
	 * @return boolean
	 */
	public static boolean isScientific(String str) {
		if (strIsNull(str)) {
			return false;
		}
		return regular(str, SCIENTIFIC_A);
	}

	/**
	 * 根据制定的匹配模式与字符串进行完全匹配，
	 * 
	 * @param regex
	 *            匹配模式
	 * @param matcherString
	 *            被匹配的字符
	 * @return true 与匹配模式相符； false 与匹配模式不
	 * @author Administrator
	 * 
	 */
	public static boolean matcher(String regex, String matcherString) {
		Pattern pa = Pattern.compile(regex); // 创建匹配模式(正则表达式的表现形式)
		Matcher ma = pa.matcher(matcherString.trim()); // 通过模式创建匹配�?
		return ma.matches();
	}

	/**
	 * 字符串是否为空，
	 * 
	 * @param arg
	 *            字符
	 * @param includeBlank
	 *            true：有空格也视为空
	 * @return 返回结果，true
	 */
	public static boolean isNull(String arg, boolean includeBlank) {
		if (null == arg) {
			return true;
		}
		if (includeBlank) {
			arg = arg.trim();
		}
		if ("".equals(arg)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串是否为空
	 *
	 */
	public static boolean isBlank(String arg) {
		return isNull(arg, true);
	}

	/**
	 * 字符串是否不为空
	 */
	public static boolean isNotBlank(String arg) {
		return !isNull(arg, true);
	}

	/**
	 * 字符串是否为数字,含正负数
	 * 
	 * @param arg
	 *            字符
	 * @param includeDecimalPlace
	 *            true：小数点存在并小数点后有精度也被视为数字
	 * @return 返回结果，true：是数字
	 */
	public static boolean isNumber(String arg, boolean includeDecimalPlace) {
		String regex;
		if (includeDecimalPlace) {
			regex = "-?\\d+\\.{1}\\d+"; // 包括小数点后的精度
		} else {
			regex = "-?\\d+";
		}
		return matcher(regex, arg);
	}
	
	

	public static boolean isNumeric(String arg) {
		return isNumber(arg, false);
	}

	public static boolean isNotNumeric(String arg) {
		return !isNumber(arg, false);
	}

	public static File[] valid(String... path) {
		ArrayList<File> files = null;
		File file;
		for (String string : path) {
			file = new File(string);
			if (file.exists() && file.isFile() && file.canRead()) {
				if (null == files) {
					files = new ArrayList<>();
				}
				files.add(file);
			} else {
				continue;
			}
		}
		if (null == files || files.size() == 0) {
			return null;
		}
		return (File[]) files.toArray();
	}

	public static File valid(String path) {
		File file = new File(path);
		if (file.exists() && file.canRead()) {
			return file;
		}
		return null;
	}

	public static boolean isNull(Object[] array) {
		if (null == array || array.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object[] array) {
		return !Inspection.isNull(array);
	}
	
	public static boolean isNotEquals(Object obj,Long ... objs){
		if(null == obj || Inspection.isNumber(obj.toString()) || null == objs || objs.length==0 ){
			return true;
		}
		boolean result = true;
		Long temp =  Long.valueOf(obj.toString());
		for (Long object : objs) {
			if(temp .equals(object)){
				result = false;
				break;
			}
		}
		return result;
	}
	
	public static boolean isNotEquals(Object obj,String ... objs){
		if(null == obj || Inspection.isNumber(obj.toString()) || null == objs || objs.length==0 ){
			return true;
		}
		boolean result = true;
		String temp =  String.valueOf(obj.toString());
		for (String object : objs) {
			if(temp .equals(object)){
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**不包含特殊字符：不包括顿号 空格
	 * @param str
	 */
	public static boolean isNotIncludeSpecialCharacter(String str){
		return isNotIncludeSpecialCharacter(str,false);
	}
	/**不包含特殊字符：不包括顿号 空格
	 * @param str
	 */
	public static boolean isNotIncludeSpecialCharacter(String str,boolean allowNull){
		if(Inspection.isBlank(str) && !allowNull){
			return false;
		}
		log.info("pattern=" + STR_SPECIAL2);
		Pattern p = Pattern.compile(STR_SPECIAL2);
		Matcher m = p.matcher(str);
		return !m.find();
	}
	/**检测图片是否包含特殊字符
	 * @param str
	 */
	public static boolean isImg(String str){
		if(Inspection.isBlank(str) ){
			return false;
		}
		Pattern p = Pattern.compile(STR_SPECIAL3);
		Matcher m = p.matcher(str);
		return !m.find();
	}

	public static boolean isCN(String str) {
		try {
			byte[] bytes = str.getBytes("UTF-8");
			if (bytes.length == str.length()) {
				return false;
			} else {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
		}
		return false;
	}

	/**将图片相对地址转换成url
	 * @param imgAdress
	 * @return
	 */
	public static String convertImgUrl(String imgAdress) {
		if(Inspection.isNotBlank(imgAdress)&& !imgAdress.startsWith("http://")&& !imgAdress.startsWith("https://") && !imgAdress.startsWith("/")){
			imgAdress="/"+imgAdress;
		}
//		return Inspection.isNotBlank(imgAdress) ?(imgAdress.indexOf("http://") > -1 ||  imgAdress.indexOf("https://") > -1  ? imgAdress : LoadData.getDicValueByKey(Constant.AbstractSiteDomainFile.SITE_DOMAIN_FILE_OFFICIAL)+imgAdress):null;
		//TODO 自行补充
		return null;
	}

	public static String encodeMD5(String data){
		try {
			byte[] data2 = MessageDigest.getInstance("MD5").digest(data.getBytes());
			return Base64.encode(data2);
		} catch (NoSuchAlgorithmException e) {
		}
		return data;
	}

}
