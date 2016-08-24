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
			// ����Ĭ�ϵ�httpClientʵ��.  
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// ����httppost  
			HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/"+action);
			// ������������  
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();		
			//ģ���
			UrlEncodedFormEntity uefEntity;
			try {
				//�����
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				//post�����������Ѿ���UrlEncodedFormEntity��װ�ı�
				httppost.setEntity(uefEntity);
				System.out.println("executing request " + httppost.getURI());
				//ͨ��ִ��httpclient.execute(httppost)�������response����
				CloseableHttpResponse response = httpclient.execute(httppost);
				try {
					//����ѷ�װ����Ӧ����
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						//EntityUtils�е�toString����������װ���󣬱����ʽ��
						rvalue=EntityUtils.toString(entity, "UTF-8");
					}
				} finally {
					//�ر�response����
					response.close();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// �ر�����,�ͷ���Դ  
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return rvalue;
		}
}
