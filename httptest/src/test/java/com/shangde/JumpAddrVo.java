package com.shangde;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 推送跳转信息
 * 
 * @author: Fan Beibei
 * @date: 2018年6月7日 下午3:01:55
 */
public class JumpAddrVo {
	/**
	 * 推送跳转-android
	 */
	private String pushJumpAddrAndroid;
	/**
	 * 推送跳转-ios
	 */
	private String pushJumpAddrIos;
	/**
	 * 列表跳转-android
	 */
	private String listJumpAddrAndroid;
	/**
	 * /** 列表跳转-ios
	 * 
	 */
	private String listJumpAddrIos;

	public JumpAddrVo() {
	}

	public JumpAddrVo(String pushJumpAddrAndroid, String pushJumpAddrIos, String listJumpAddrAndroid,
			String listJumpAddrIos) {
		this.pushJumpAddrAndroid = pushJumpAddrAndroid;
		this.pushJumpAddrIos = pushJumpAddrIos;
		this.listJumpAddrAndroid = listJumpAddrAndroid;
		this.listJumpAddrIos = listJumpAddrIos;
	}

	public String getPushJumpAddrAndroid() {
		return pushJumpAddrAndroid;
	}

	public void setPushJumpAddrAndroid(String pushJumpAddrAndroid) {
		this.pushJumpAddrAndroid = pushJumpAddrAndroid;
	}

	public String getPushJumpAddrIos() {
		return pushJumpAddrIos;
	}

	public void setPushJumpAddrIos(String pushJumpAddrIos) {
		this.pushJumpAddrIos = pushJumpAddrIos;
	}

	public String getListJumpAddrAndroid() {
		return listJumpAddrAndroid;
	}

	public void setListJumpAddrAndroid(String listJumpAddrAndroid) {
		this.listJumpAddrAndroid = listJumpAddrAndroid;
	}

	public String getListJumpAddrIos() {
		return listJumpAddrIos;
	}

	public void setListJumpAddrIos(String listJumpAddrIos) {
		this.listJumpAddrIos = listJumpAddrIos;
	}
	
	public String acquireListJumpAddrJson() {

		Map<String, String> map = new HashMap<>();
		if (null != listJumpAddrAndroid && !"".equals(listJumpAddrAndroid.trim())) {
			map.put("android", listJumpAddrAndroid);
		}

		if (null != listJumpAddrIos && !"".equals(listJumpAddrIos.trim())) {
			map.put("ios", listJumpAddrIos);
		}
		return JSONObject.toJSONString(map);
	}

	public String acquirePushJumpAddrJson() {
		Map<String, String> map = new HashMap<>();
		if (null != pushJumpAddrAndroid && !"".equals(pushJumpAddrAndroid.trim())) {
			map.put("android", pushJumpAddrAndroid);
		}

		if (null != pushJumpAddrIos && !"".equals(pushJumpAddrIos.trim())) {
			map.put("ios", pushJumpAddrIos);
		}
		return JSONObject.toJSONString(map);
	}

	@Override
	public String toString() {
		return "JumpAddrVo [pushJumpAddrAndroid=" + pushJumpAddrAndroid + ", pushJumpAddrIos=" + pushJumpAddrIos
				+ ", listJumpAddrAndroid=" + listJumpAddrAndroid + ", listJumpAddrIos=" + listJumpAddrIos + "]";
	}

}
