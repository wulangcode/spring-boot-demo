package com.wulang.redis.test3Recommend;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Detect {


	public static final short INVALID_NUMBER_VALUE = 0;

	public static final String EMPTY_STRING = StringUtils.EMPTY;

	public static final String DELIMITER = ",";
	
	public static final String CONNECTORFLAG = "=" ;//导入中使用
	
	public static final String REFERENCELIST_SUFFIX = "_LIST" ;//导入中使用
	
	public static <T extends Object> byte[] convertToByteArray(T t){
		byte[] bytes = null;
		if(t!=null){
			ByteArrayOutputStream  byteArrayOutputStream = null;
			ObjectOutputStream objectOutputStream = null;
			try{
				byteArrayOutputStream = new ByteArrayOutputStream();
				objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(t);
				bytes = byteArrayOutputStream.toByteArray();
			}catch(Exception e){
				throw new RuntimeException(e);
			}finally{
				if(objectOutputStream!=null){
					try {
						objectOutputStream.close();
					} catch (IOException e) {
					}
				}
				
				if(byteArrayOutputStream!=null){
					try {
						byteArrayOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return bytes;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T convertToObject(byte[] bytes){
		T t = null;
		if(bytes!=null){
			ByteArrayInputStream  byteArrayInputputStream = null;
			ObjectInputStream objectInputStream = null;
			try{
				byteArrayInputputStream = new ByteArrayInputStream(bytes);
				objectInputStream = new ObjectInputStream(byteArrayInputputStream);
				
				t = (T) objectInputStream.readObject();
			}catch(Exception ex){
				throw new RuntimeException(ex);
			}finally{
				if(objectInputStream!=null){
					try {
						objectInputStream.close();
					} catch (IOException e) {
					}
				}
				
				if(byteArrayInputputStream!=null){
					try {
						byteArrayInputputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return t;
	}
	
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	/**
	 * 随机生成20位数字ID
	 */
	public static synchronized String generate20CodeId() {
		String codeId = System.nanoTime() + Detect.EMPTY_STRING ;
		codeId = StringUtils.leftPad(codeId, 20, "0");

		return "8" + codeId;
	}

	public static boolean bizIdEquals(long bizId1, long bizId2) {
		return bizId1 > 0 && bizId2 > 0 && bizId1 == bizId2;
	}
	
	public static boolean bizIdEquals(double bizId1, double bizId2) {
		return bizId1 > 0 && bizId2 > 0 && bizId1 == bizId2;
	}

	/**  */
	public static boolean notEmpty(String string) {
		return null != string && !EMPTY_STRING.equals(string) && !"\"null\"".equals(string)  && !"null".equals(string); 
	}
	
	public static boolean notEmpty(byte[] bytes) {
		return (null != bytes && 0 < bytes.length);
	}

	public static boolean notEmpty(List<?> list) {
		return null != list && !list.isEmpty();
	}

	public static boolean notEmpty(Map<?, ?> map) {
		return null != map && !map.isEmpty();
	}

	public static boolean notEmpty(Collection<?> collection) {
		return null != collection && !collection.isEmpty();
	}

/*	public static boolean notEmpty(String[] array) {
		return null != array && array.length > 0;
	}*/

	public static boolean notEmpty(short[] array) {
		return null != array && array.length > 0;
	}
	
	public static boolean notEmpty(int[] array) {
		return null != array && array.length > 0;
	}

	public static boolean notEmpty(long[] array) {
		return null != array && array.length > 0;
	}
	
	public static boolean notEmpty(String[] array) {
		return null != array && array.length > 0;
	}
	
	public static boolean notEmpty(Integer value) {
		return null != value;
	}
	
	public static <T extends Object> boolean notEmpty(T[] array) {
		return null != array && array.length > 0;
	}

	/**  */
	public static boolean isNegative(Double value) {
		return value != null && value < 0;
	}

	public static boolean isPositive(Double value) {
		return value != null && value.doubleValue() > 0;
	}
	public static boolean isPositive(Short value) {
		return value != null && value.shortValue() > 0;
	}
	public static boolean isPositive(Long value) {
		return value != null && value.longValue() > 0;
	}
	public static boolean isPositive(Integer value) {
		return value != null && value.intValue() > 0;
	}

	public static boolean isTrue(Boolean value) {
		return Boolean.TRUE.equals(value);
	}

	public static boolean isFalse(Boolean value) {
		return Boolean.FALSE.equals(value);
	}

	/**  */
	public static boolean contains(long value, long[] values) {
		if (notEmpty(values)) {
			int length = values.length;
			for (int i = 0; i < length; i++) {
				if (values[i] == value) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**  */
	public static boolean containsAll(long[] values, long[] subValues) {
		if (!notEmpty(values) && !notEmpty(subValues)) {
			return true;
		}
		if (notEmpty(subValues)) {
			for (int i = 0; i < subValues.length; i++) {
				if (!contains(subValues[i], values)) {
					return false;
				}
			}
			return true;
		} else if (notEmpty(values)){
			return true;
		} 
		return false;
	}

	public static <E> boolean contains(E one, List<E> list) {
		if (notEmpty(list) && null != one) {
			for (E item : list) {
				if (one.equals(item)) {
					return true;
				}
			}
		}
		return false;
	}

	/** *array */
	public static double[] doubleArray(String value, String delimiter) {
		String[] values = StringUtils.split(value, delimiter);

		double[] doubleValues = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = Double.parseDouble(values[i]);
		}
		return doubleValues;
	}

	public static short[] shortArray(String value) {
		return shortArray(value, DELIMITER);
	}

	public static short[] shortArray(String value, char delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		short[] shortValues = new short[values.length];
		for (int i = 0; i < values.length; i++) {
			shortValues[i] = Short.parseShort(values[i]);
		}
		return shortValues;
	}

	public static short[] shortArray(String value, String delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		short[] shortValues = new short[values.length];
		for (int i = 0; i < values.length; i++) {
			shortValues[i] = Short.parseShort(values[i]);
		}
		return shortValues;
	}

	public static long[] longArray(String value) {
		return longArray(value, DELIMITER);
	}

	public static long[] longArray(String value, char delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		long[] longValues = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.parseLong(values[i]);
		}
		return longValues;
	}

	public static long[] longArray(String value, String delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		long[] longValues = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			if (notEmpty(values[i])) {
				longValues[i] = Long.parseLong(values[i].trim());
			}
		}
		return longValues;
	}
	
	public static Integer[] integerArray(String value, String delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		Integer[] integerValues = new Integer[values.length];
		for (int i = 0; i < values.length; i++) {
			if (notEmpty(values[i])) {
				integerValues[i] = Integer.valueOf(values[i].trim());
			}
		}
		return integerValues;
	}
	
	public static List<String> stringList(String value,String delimiter) {
		if (!notEmpty(value)) {
			return null;
		}
		String[] values = StringUtils.split(value, delimiter);

		List<String> listValues= new ArrayList<String>();
		for (int i = 0; i < values.length; i++) {
			listValues.add(i, values[i]);
		}
		return listValues;
	}

	public static List<long[]> grouping(long[] values, int groupSize) {
		if (notEmpty(values)) {

//			Assertion.isPositive(groupSize, "divideSize must be bigger than 0");

			int groupLength = values.length / groupSize + ((values.length % groupSize) > 0 ? 1 : 0);

			List<long[]> longArryGroup = new LinkedList<long[]>();
			long[] valueArray = null;
			for (int i = 0; i < groupLength; i++) {
				int arrayLength = (i < groupLength - 1 || values.length % groupSize == 0) ? groupSize : (values.length % groupSize);

				valueArray = new long[arrayLength];
				for (int j = 0; j < arrayLength; j++) {
					valueArray[j] = values[i * groupSize + j];
				}
				longArryGroup.add(valueArray);
			}

			return longArryGroup;
		}
		return null;
	}

	public static <T> List<List<T>> grouping(List<T> values, int groupSize) {
		if (values != null && values.size() > 0) {

//			Assertion.isPositive(groupSize, "divideSize must be bigger than 0");

			int groupLength = values.size() / groupSize + ((values.size() % groupSize) > 0 ? 1 : 0);

			List<List<T>> longArryGroup = new LinkedList<List<T>>();
			for (int i = 0; i < groupLength; i++) {
				int arrayLength = (i < groupLength - 1 || values.size() % groupSize == 0) ? groupSize : (values.size() % groupSize);

				List<T> valueArray = new ArrayList<T>();
				for (int j = 0; j < arrayLength; j++) {
					valueArray.add(values.get(i * groupSize + j));
				}
				longArryGroup.add(valueArray);
			}

			return longArryGroup;
		}
		return null;
	}

	/** join */
	public static String join(long[] values) {
		return join(values, DELIMITER);
	}

	public static String join(String[] values) {
		return StringUtils.join(values, DELIMITER);
	}

	public static String join(long[] values, String delimiter) {
		return StringUtils.join(ArrayUtils.toObject(values), delimiter);
	}
	
	public static String join(short[] values) {
		return join(values, DELIMITER);
	}
	
	public static String join(short[] values, String delimiter) {
		return StringUtils.join(ArrayUtils.toObject(values), delimiter);
	}

	/** as */
	public static short asPrimitiveShort(Object value) {
		String stringValue = asString(value);
		if (notEmpty(asString(stringValue))) {
			if (org.apache.commons.lang.math.NumberUtils.isNumber(stringValue)) {
				return Short.parseShort(stringValue);
			}
		}
		return INVALID_NUMBER_VALUE;
	}

	public static List<?> asList(Object object) {
		if (object != null && object instanceof List<?>) {
			return (List<?>) object;
		}
		return null;
	}

	public static long asPrimitiveLong(Object value) {
		String stringValue = asString(value);
		if (notEmpty(stringValue)) {
			if (org.apache.commons.lang.math.NumberUtils.isNumber(stringValue)) {
				return Long.parseLong(stringValue);
			}
		}
		return INVALID_NUMBER_VALUE;
	}

	public static int asPrimitiveInt(Object value) {
		String stringValue = asString(value);
		if (notEmpty(stringValue)) {
			if (org.apache.commons.lang.math.NumberUtils.isNumber(stringValue)) {
				return Integer.parseInt(stringValue);
			}
		}
		return INVALID_NUMBER_VALUE;
	}

	public static double asPrimitiveDouble(Object value) {
		String stringValue = asString(value);
		if (notEmpty(asString(stringValue))) {
			if (org.apache.commons.lang.math.NumberUtils.isNumber(stringValue)) {
				return Double.parseDouble(stringValue);
			}
		}
		return INVALID_NUMBER_VALUE;
	}
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("^[0-9]+.+$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	public static String asString(Object object) {
		if (null != object) {
			return StringUtils.trim(String.valueOf(object));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> E[] asArray(List<E> list) {
		if (notEmpty(list)) {
			return (E[]) list.toArray();
		}
		return null;
	}

	public static boolean equal(String left, String right) {
		if (null == left && null == right) {
			return true;
		}
		if (null != left && null != right && left.trim().equals(right.trim())) {
			return true;
		}
		return false;
	}
	public static boolean equal(Long left, Long right) {
		if (null == left && null == right) {
			return false;
		}
		if (null != left && null != right && left.longValue() == right.longValue()) {
			return true;
		}
		return false;
	}
	public static boolean equal(Integer left, Integer right) {
		if (null == left && null == right) {
			return false;
		}
		if (null != left && null != right && left.intValue() == right.intValue()) {
			return true;
		}
		return false;
	}
	public static boolean equal(Short left, Short right) {
		if (null == left && null == right) {
			return false;
		}
		if (null != left && null != right && left.shortValue() == right.shortValue()) {
			return true;
		}
		return false;
	}
	public static boolean equal(Double left, Double right) {
		if (null == left && null == right) {
			return false;
		}
		if (null != left && null != right && left.doubleValue() == right.doubleValue()) {
			return true;
		}
		return false;
	}
	public static boolean equal(Float left, Float right) {
		if (null == left && null == right) {
			return false;
		}
		if (null != left && null != right && left.floatValue() == right.floatValue()) {
			return true;
		}
		return false;
	}
	public static String trim(String string) {
		if (null == string) {
			return null;
		}

		return StringUtils.trim(string);
	}
	
	public static int getByteLength(String string) {
		if (StringUtils.isEmpty(string)) {
			return 0;
		}
		try {
			return string.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("GBK charset not supported", e);
		}
	}
	
	public static String trimString(String string, int byteLength) {
		try {
			if (notEmpty(string) && string.getBytes("GBK").length > byteLength) {
				byte[] bytes = string.getBytes("GBK");
				bytes = ArrayUtils.subarray(bytes, 0, byteLength);
				String s = new String(bytes, "GBK");
				return s;
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("GBK charset not supported", e);
		}
		return string;
	}

	/** escape */
	public static long[] escapeDuplication(long[] values) {
		if (Detect.notEmpty(values)) {
			Set<Long> set = new LinkedHashSet<Long>();
			set.addAll(Arrays.asList(ArrayUtils.toObject(values)));
			return ArrayUtils.toPrimitive((Long[]) set.toArray(new Long[0]));
		}
		return null;
	}

	public static <E> List<E> escapeEmpty(List<E> list) {
		if (notEmpty(list)) {
			return list;
		}
		return null;
	}

	public static String escapeVarchar(String value) {
		if (notEmpty(value) && value.length() > 2000) {
			// return value.substring(0, 3990);
			return value.substring(0, 1990);
		}
		return value;
	}

	public static short[] safeArray(short[] values) {
		if (null == values) {
			values = new short[0];
		}
		return values;
	}

	public static int[] safeArray(int[] values) {
		if (null == values) {
			values = new int[0];
		}
		return values;
	}

	public static long[] safeArray(long[] values) {
		if (null == values) {
			values = new long[0];
		}
		return values;
	}

	public static double[] safeArray(double[] values) {
		if (null == values) {
			values = new double[0];
		}
		return values;
	}

	public static <E> List<E> safeList(List<E> list) {
		if (null == list) {
			list = new ArrayList<E>();
		}
		return list;
	}

	public static <E> E firstOne(List<E> list) {
		if (notEmpty(list)) {
			return list.get(0);
			// return list.iterator().next();
		}
		return null;
	}

	public static <E> E lastOne(List<E> list) {
		if (notEmpty(list)) {
			return list.get(list.size() - 1);
			// return list.iterator().next();
		}
		return null;
	}

	public static boolean onlyOne(List<?> list) {
		if (notEmpty(list) && list.size() == 1) {
			return true;
		}
		return false;
	}

	public static <E> List<E> unmodifiableList(List<E> list) {
		if (notEmpty(list)) {
			return Collections.unmodifiableList(list);
		}
		return null;
	}

	public static boolean between(long value, long floor, long ceil) {
		if (value >= floor && value <= ceil) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void notNull(Object obj, String message) {
		if (null == obj) {
			throw new IllegalArgumentException(message);
		}
	}

	public static String changeSpecialChar(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\r", "\\r");
		s = s.replace("\n", "\\n");
		s = s.replace("\"", "\\\"");
		s = s.replace("\'", "\\\'");
		return s;
	}
	
	public static String replaceSpecialCharForCatSearch(String s) {
		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			if (!Character.isLetterOrDigit(c) && c != 12288 &&  !(c > 65280 && c < 65375)) {
				// 12288是全角空格
				// 65281是！, 65374是～，之间都是全角符号
				s = StringUtils.replace(s, Character.toString(c), " ");
			}
		}
		return s;
	}
	/**
	 * 随机生成指定位数的整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(final int min, final int max)
	{
		Random rand;
		rand= new Random(); 
	    int tmp = Math.abs(rand.nextInt());
	    return tmp % (max - min + 1) + min;
	}
	
	public static String transcodingValue(String str){
		if(notEmpty(str)){
			str = str.replaceAll("Y", "年").replaceAll("M", "月").replaceAll("D", "天");
		}
		return str;
	}
	public static String transcodingAgeValue(String str){
		if(notEmpty(str)){
			str = str.replaceAll("D", "天").replaceAll("S", "周岁");
		}
		return str;
	}
	public static String asChinaValue(String str){
		if(notEmpty(str)){
			if (str.toUpperCase().endsWith("Y")) {
				return "年";
			}else if(str.toUpperCase().endsWith("M")){
				return "月";
			}else if(str.toUpperCase().endsWith("D")){
				return "天";
			}
		}
		return str;
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(Detect.getUUID());
		System.out.println(isNumeric("23d"));
		
		
	}
	
	
	
}
