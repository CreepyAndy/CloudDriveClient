package communicationUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	 public String getRootString(String action) {
	    	String rvalue="";
			// 创建默认的httpClient实例.  
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 创建httppost  
			HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/"+action);
			// 创建参数队列  
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();		
			//模拟表单
			UrlEncodedFormEntity uefEntity;
			try {
				//构造表单
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				//post请求中设置已经过UrlEncodedFormEntity封装的表单
				httppost.setEntity(uefEntity);
				System.out.println("executing request " + httppost.getURI());
				//通过执行httpclient.execute(httppost)方法获得response对象
				CloseableHttpResponse response = httpclient.execute(httppost);
				try {
					//获得已封装的响应对象
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						//EntityUtils中的toString方法，（封装对象，编码格式）
						rvalue=EntityUtils.toString(entity, "UTF-8");
					}
				} finally {
					//关闭response对象
					response.close();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭连接,释放资源  
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return rvalue;
		}
}
