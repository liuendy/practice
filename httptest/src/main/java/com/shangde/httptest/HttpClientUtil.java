package com.shangde.httptest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http工具类
 * 
 * @author fanbeibei
 *
 *
 *         maven <dependency> <groupId>org.apache.httpcomponents</groupId>
 *         <artifactId>httpclient</artifactId> <version>4.5.2</version>
 *         </dependency> <dependency>
 *         <groupId>org.apache.httpcomponents</groupId>
 *         <artifactId>httpcore</artifactId> <version>4.3.3</version>
 *         </dependency> <dependency>
 *         <groupId>org.apache.httpcomponents</groupId>
 *         <artifactId>httpmime</artifactId> <version>4.5.2</version>
 *         </dependency>
 *
 * 
 */
public class HttpClientUtil {

	/**
	 * 默认编码
	 */
	private static final String DEFULT_CHARSET = "UTF-8";
	/**
	 * 默认超时时间（毫秒）
	 */
	private static final int DEFULT_TIMEOUT = 3000;

	/**
	 * 发送post请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            头参数
	 * @param param
	 *            参数
	 * @return
	 */
	public static String postForStr(String url, Map<String, String> param, Map<String, String> headers) {
		return postForStr(url, param, headers, DEFULT_CHARSET, DEFULT_TIMEOUT, null);
	}

	/**
	 * 发送post请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数
	 * @param headers
	 *            头参数
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String postForStr(String url, Map<String, String> param, Map<String, String> headers, String charset) {
		return postForStr(url, param, headers, charset, DEFULT_TIMEOUT, null);
	}

	/**
	 * 发送post请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数
	 * @param headers
	 *            头参数
	 * @param charset
	 *            编码
	 * @param timeout
	 *            超时时间（毫秒）
	 * @param proxy
	 *            代理
	 * @return
	 */
	public static String postForStr(String url, Map<String, String> param, Map<String, String> headers, String charset,
			int timeout, HttpHost proxy) {

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient client = httpClientBuilder.build();

		try {
			HttpPost httpPost = new HttpPost(url);

			// 配置
			RequestConfig requestConfig = null;
			if (null != proxy) {
				requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(timeout)
						.setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			}

			httpPost.setConfig(requestConfig);
			
			
			if (null != headers && headers.size() > 0) {
				
			}

			// 创建参数队列
			if (null != param) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();

				for (Entry<String, String> entry : param.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}

				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, charset);
				httpPost.setEntity(formEntity);
			}

			HttpResponse response = client.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			if (respEntity != null) {
				String result = EntityUtils.toString(respEntity, charset);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 发送get请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数
	 * @param headers
	 *            头参数
	 * @return
	 */
	public static String getForStr(String url, Map<String, String> param, Header[] headers) {
		return getForStr(url, param,headers, DEFULT_CHARSET, DEFULT_TIMEOUT, null);
	}

	/**
	 * 发送get请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数
	 * @param headers
	 *            头参数
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String getForStr(String url, Map<String, String> param, Header[] headers, String charset) {
		return getForStr(url, param,headers, charset, DEFULT_TIMEOUT, null);
	}

	/**
	 * 发送get请求返回字符串
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数
	 * @param charset
	 *            编码
	 * @param timeout
	 *            超时时间（毫秒）
	 * @param proxy
	 *            代理
	 * @return
	 */
	public static String getForStr(String url, Map<String, String> param, Header[] headers, String charset, int timeout, HttpHost proxy) {
		if (isBlank(url) ) {
			throw new IllegalArgumentException("url  can not be null !!!");
		}

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient client = httpClientBuilder.build();

		try {

			// 创建参数队列

			if (null != param) {
				StringBuilder urlStr = new StringBuilder(url);
				urlStr.append("?");
				for (Entry<String, String> entry : param.entrySet()) {
					urlStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}

				url = urlStr.toString();

			}

			HttpGet httpGet = new HttpGet(url);
			// 配置
			RequestConfig requestConfig = null;
			if (null != proxy) {
				requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(timeout)
						.setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			}
			httpGet.setConfig(requestConfig);
			
			if(null != headers && headers.length >0) {
				httpGet.setHeaders(headers);
			}

			HttpResponse response = client.execute(httpGet);

			HttpEntity respentity = response.getEntity();
			if (respentity != null) {
				String result = EntityUtils.toString(respentity, charset);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 上传文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * @param files
	 *            文件
	 * @return
	 */
	public static String uploadFile(String url, Map<String, String> param, Map<String, File> files) {
		return uploadFile(url, param, files, DEFULT_CHARSET, DEFULT_TIMEOUT, null);
	}

	/**
	 * 上传文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * @param files
	 *            文件
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String uploadFile(String url, Map<String, String> param, Map<String, File> files, String charset) {
		return uploadFile(url, param, files, charset, DEFULT_TIMEOUT, null);
	}

	/**
	 * 上传文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * @param files
	 *            文件
	 * @param charset
	 *            编码
	 * @param timeout
	 *            超时时间（毫秒）
	 * @return
	 */
	public static String uploadFile(String url, Map<String, String> param, Map<String, File> files, String charset,
			int timeout, HttpHost proxy) {

		if (isBlank(url) ) {
			throw new IllegalArgumentException("url  can not be null !!!");
		}

		// 创建MultipartEntityBuilder
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (null != param) {// 参数
			for (Entry<String, String> entry : param.entrySet()) {

				StringBody stringBody = new StringBody(entry.getValue(), ContentType.MULTIPART_FORM_DATA);
				builder.addPart(entry.getKey(), stringBody);
			}
		}

		if( null != files && files.size() > 0) {
			for (Entry<String, File> entry : files.entrySet()) {// 添加文件

				FileBody fileBody = new FileBody(entry.getValue());
				builder.addPart(entry.getKey(), fileBody);
			}
		}
		

		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		// 创建entity
		HttpEntity entity = builder.build();

		// 创建post
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = null;
		if (null != proxy) {
			requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(timeout).setConnectTimeout(timeout)
					.build();// 设置请求和传输超时时间
		} else {
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();// 设置请求和传输超时时间
		}
		post.setConfig(requestConfig);
		post.setEntity(entity);

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient client = httpClientBuilder.build();

		try {
			HttpResponse response = client.execute(post);

			HttpEntity respEntity = response.getEntity();
			if (respEntity != null) {
				String result = EntityUtils.toString(respEntity, charset);
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 下载文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * 
	 * @return
	 */
	public static void downloadFile(String url, Map<String, String> param, String destFileName) {
		downloadFile(url, param, destFileName, DEFULT_CHARSET, DEFULT_TIMEOUT, null);
	}

	/**
	 * 下载文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * @param charset
	 *            编码
	 * @return
	 */
	public static void downloadFile(String url, Map<String, String> param, String destFileName, String charset) {
		downloadFile(url, param, destFileName, charset, DEFULT_TIMEOUT, null);
	}

	/**
	 * 下载文件
	 * 
	 * 
	 * @param url
	 *            地址
	 * @param param
	 *            附带参数
	 * @param destFileName
	 *            文件存放路径
	 * @param charset
	 *            编码
	 * @param timeout
	 *            超时时间（毫秒）
	 * @return
	 */
	public static void downloadFile(String url, Map<String, String> param, String destFileName, String charset,
			int timeout, HttpHost proxy) {

		if (isBlank(url) || null == param) {
			throw new IllegalArgumentException("url or param can not be null !!!");
		}

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient client = httpClientBuilder.build();

		try {

			// 创建参数队列

			if (null != param) {
				StringBuilder urlStr = new StringBuilder(url);
				urlStr.append("?");
				for (Entry<String, String> entry : param.entrySet()) {
					urlStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				}

				url = urlStr.toString();

			}

			HttpGet httpGet = new HttpGet(url);
			// 配置
			RequestConfig requestConfig = null;
			if (null != proxy) {
				requestConfig = RequestConfig.custom().setProxy(proxy).setSocketTimeout(timeout)
						.setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			} else {
				requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();// 设置请求和传输超时时间
			}
			httpGet.setConfig(requestConfig);

			HttpResponse response = client.execute(httpGet);

			HttpEntity respentity = response.getEntity();
			InputStream in = respentity.getContent();

			File file = new File(destFileName);
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
			fout.flush();
			fout.close();
			// 关闭低层流。
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static boolean isBlank(String url) {

		return null == url || "".equals(url.trim());
	}
}
