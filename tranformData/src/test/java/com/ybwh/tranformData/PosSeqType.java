package com.ybwh.tranformData;

//import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @author weiyuqing
 * @description
 * @date 2018/7/2 10:34
 **/

//岗位序列枚举类
public enum PosSeqType {
    xmhx(1, "项目核心"),
    xshx(2, "销售核心"),
    zn(3, "职能"),
    hd(4, "后端"),
    cy(5, "产研"),
    hx(6, "核心"),
    qt(7, "其他（外包，渠道）"),;

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", getName());
        return map;
    }


    PosSeqType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public static String getNameById(Integer id) {
        for (PosSeqType p : PosSeqType.values()) {
            if (p.getId() == id) {
                return p.getName();
            }
        }
        return "";
    }

    public static Integer getIdByName(String name){
        for (PosSeqType p : PosSeqType.values()) {
            if (p.getName().equals(name)) {
                return p.getId();
            }
        }
        return -1;
    }


    
    
    public static void main(String[] args) {
		System.out.println(PosSeqType.values());
		System.out.println(JSON.toJSON(PosSeqType.values()));
		
	}
}
