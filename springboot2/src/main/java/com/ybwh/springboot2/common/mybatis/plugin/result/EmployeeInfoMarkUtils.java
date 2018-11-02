package com.ybwh.springboot2.common.mybatis.plugin.result;

/**
 * 员工信息打码工具
 *
 * @author: Fan Beibei
 * @date: 2018/7/3 10:49
 * @Modified By:
 */
public class EmployeeInfoMarkUtils {

    private EmployeeInfoMarkUtils(){}


    private static final String C_MARK19 = "*******************";

    /**
     * 给手机号打码
     *
     * @param tel
     * @return
     */
    public static String  markTel(String tel){
        if (null != tel && !"".equals(tel.trim())) {
//                        System.out.println("@@@@@@@@@@"+idNum);
            if (tel.length() > 3) {//防止脏数据抛异常
                tel = tel.substring(0, 3) + C_MARK19.substring(0,6) + tel.substring(tel.length() - 2);
            } else {
                tel = tel + C_MARK19.substring(0,11 - tel.length());
            }
        }

        return tel;
    }

    /**
     * 给身份证打码
     *
     * @param idNum
     * @return
     */
    public static String  markIdNum(String idNum){
        if (null != idNum && !"".equals(idNum.trim())) {
//                        System.out.println("@@@@@@@@@@"+idNum);
            if (idNum.length() > 4) {//防止脏数据抛异常
                idNum = idNum.substring(0, idNum.length() - 4) + C_MARK19.substring(0,4) ;
            } else {
                idNum = idNum + C_MARK19.substring(0,19 - idNum.length());
            }
        }

        return idNum;
    }
}
