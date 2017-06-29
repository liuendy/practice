package com.ybwh.utils;


// 演示性质的DataParser,任意4个字节就解析成功
public class Any4DataParser  {

    static {
        System.out.println("&&&&&&");
        /**
         * 声明数据包版本与解析器类关系
         */
        DataParserManager.register("incar-any4-1.0.0", Any4DataParser.class);
    }


}
