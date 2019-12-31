package com.wulang.redis.utils.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {
	private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

	public static void writeDone(PrintWriter writer,Object object){
		try {
//			log.debug("-------{}",json);
			//支持jsonp格式
			/*String cb = HttpObjUtils.getHttpServletRequest().getParameter("cb");
			if("jsonp".equals(HttpObjUtils.getHttpServletRequest().getParameter("format"))){
				cb = (Inspection.isBlank(cb)?Final.JSONP_CALL_BACK_DEFAULT:cb);
				if(!json.startsWith(cb)){
					json = org.apache.commons.lang3.StringUtils.join(cb,"(",json,")");
				}
			}*/
			String json = toJSONString(object);
			writer.write(json);
		} catch (Exception e) {
			log.error("writeDone, --error:{}",e);
		}finally{
			writer.close();
		}
	}

	public static String toJSONString(Object obj) {
		//禁用fastJson的‘循环引用检测’机制
		return JSON.toJSONString(obj,new NameFilter() {

			@Override
			public String process(Object object, String name, Object value) {
				// TODO Auto-generated method stub
				//首字母大写将不转换
				//launcher接口actionParam参数对应的值的属性名称是由dms后台动态配置的，不用做转换处理
				if(object instanceof JSONObject){
					return name;
				}
				//非launcher接口actionParam需要做下划线转换处理
				return Character.isLowerCase(name.charAt(0)) ? camelToUnderline(name):name;
			}
		} , SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.DisableCircularReferenceDetect);
	}

	public static String camelToUnderline(String param){
		Pattern ppattern = Pattern.compile("[A-Z]");
		if(param==null ||param.equals("")){
			return "";
		}
		StringBuilder builder=new StringBuilder(param);
		Matcher mc=ppattern.matcher(param);
		int i=0;
		while(mc.find()){
			builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());
			i++;
		}
		if('_' == builder.charAt(0)){
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
}