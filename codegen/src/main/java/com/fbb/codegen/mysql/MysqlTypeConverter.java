package com.fbb.codegen.mysql;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * 
 * mysql数据表列类型转化为Java类型
 * 
 * @author fanbeibei
 *
 */
public class MysqlTypeConverter {

	// private static final Logger logger =
	// Logger.getLogger(MysqlTypeConverter.class);
	private static final String MYSQL = "mysql";
	/**
	 * 列类型 ---> java类型
	 */
	private static Map<String, String> typeMap;

	static {
		InputStream typeMapXml = Object.class.getResourceAsStream("/typeMap.xml");

		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(typeMapXml);
			Element codegen = doc.getRootElement();
			Element typeMaps = codegen.getChild("typeMaps");

			String db = typeMaps.getAttributeValue("db");
			if (!MYSQL.equals(db.toLowerCase())) {
				throw new Exception("your typeMaps is for " + db + ",not for mysql!!");
			}

			List<Element> typeMapList = typeMaps.getChildren();
			if (typeMapList.size() > 0) {
				typeMap = new HashMap<String, String>();
				for (Element tm : typeMapList) {
					typeMap.put(tm.getAttributeValue("columnType").toLowerCase(), tm.getAttributeValue("javaType"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String columnType2JavaType(String colType) throws Exception {
		if (null == colType || "".equals(colType.trim())) {
			throw new IllegalArgumentException("colType can not be blank！！");
		}

		if (null == typeMap) {
			throw new Exception("type map read error!!");
		}

		return typeMap.get(colType.toLowerCase());
	}

}
