package com.jointcorp.jcdata.utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {

	/**
	 * 发送httpGet请求，并返回数据
	 * @param url 请求地址
	 * @param param 请求参数
	 * @return JSONObject (通过JSONObject.get(key)的方式获取json对象的属性值)
	 * @author xyc 2015-9-22 上午11:28:35
	 */
	public static String httpGet(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			URIBuilder e = new URIBuilder(url);
			if(param != null) {
				Iterator uri = param.keySet().iterator();
				while(uri.hasNext()) {
					String httpGet = (String)uri.next();
					e.addParameter(httpGet, (String)param.get(httpGet));
				}
			}
			URI uri = e.build();
			HttpGet httpGet = new HttpGet(uri);
			response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200) {
				String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
				return resultString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException var15) {
				var15.printStackTrace();
			}
		}
		return null;
	}

	public static String httpGet(String url)  {
		return httpGet(url, null);
	}

	/**
	 * 发送httpPost请求，并返回数据
	 * @param url 请求地址
	 * @param param 请求参数
	 * @return JSONObject (通过JSONObject.get(key)的方式获取json对象的属性值)
	 * @author xyc 2015-9-22 下午14:33:10
	 */
	public static String httpPost(String url,Map<String, Object> param)  {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			if(param != null) {
				ArrayList paramList = new ArrayList();
				Iterator entity = param.keySet().iterator();
				while(entity.hasNext()) {
					String key = (String)entity.next();
					paramList.add(new BasicNameValuePair(key, (String)param.get(key)));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "utf-8");
				httpPost.setEntity(formEntity);
			}
			response = httpClient.execute(httpPost);

			if(response.getStatusLine().getStatusCode() == 200) {
				String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
				return resultString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException var16) {
				var16.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 发送Json数据
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String doPostJson(String url, String json)  {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost e = new HttpPost(url);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			e.setEntity(entity);
			response = httpClient.execute(e);
			if(response.getStatusLine().getStatusCode() == 200) {
				String resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
				return resultString;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException var14) {
				var14.printStackTrace();
			}
		}
		return null;
	}
}
