package com.qaapi.util;

import net.sf.json.JSONObject;


/**
 * 此类的用于构造返回给前台数据的标准格式
 * 
 * @author IceAsh
 *
 */
public class ReturnJson implements ReturnJsonKey{
	
	/**
	 * 
	 * 带other的成功返回
	 * 
	 * @param data
	 * @param msg
	 * @param other
	 * @return jsonObject
	 */
	public static JSONObject ok(Object data, String msg, Object other) {
		JSONObject json = new JSONObject();
		try {
			json.put(DATA, data);
			json.put(MSG, msg);
			json.put(STATUS, GlobeStatus.SUCCESS_200);
			json.put(OTHER, other);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 
	 * 带other的失败返回
	 * 
	 * @param data
	 * @param msg
	 * @param other
	 * @return jsonObject
	 */
	public static JSONObject notok(Object data, String msg, int status, Object other) {
		JSONObject json = new JSONObject();
		try {
			// 失败的返回码不能是200
			if(status == GlobeStatus.SUCCESS_200){
				throw new Exception();
			}
			json.put(DATA, data);
			json.put(MSG, msg);
			json.put(STATUS, status);
			json.put(OTHER, other);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 
	 * 不带other的失败返回
	 * 
	 * @param data
	 * @param msg
	 * @param other
	 * @return jsonObject
	 */
	public static JSONObject ok(Object data, String msg) {
		JSONObject json = new JSONObject();
		try {
			json.put(DATA, data);
			json.put(MSG, msg);
			json.put(STATUS, GlobeStatus.SUCCESS_200);
			json.put(OTHER, "{}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 
	 * 不带other的失败返回
	 * 
	 * @param data
	 * @param msg
	 * @param other
	 * @return jsonObject
	 */
	public static JSONObject notok(Object data, String msg, int status) {
		JSONObject json = new JSONObject();
		try {
			// 失败的返回码不能是200
			if(status == GlobeStatus.SUCCESS_200){
				throw new Exception();
			}
			json.put(DATA, data);
			json.put(MSG, msg);
			json.put(STATUS, status);
			json.put(OTHER, "{}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
