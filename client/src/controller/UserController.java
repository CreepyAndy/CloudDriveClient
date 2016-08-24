package controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.FileNode;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class UserController {
	 public String getRootString() {
	    	String rvalue="";
			// 创建默认的httpClient实例.  
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 创建httppost  
			HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/node.action");
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
	 public FileNode getRoot(){
		 String jsonString = getRootString();
		 JSONObject jsonObject = JSONObject.fromObject(jsonString);
		 //user = (User) JSONObject.toBean(jsonObject, User.class);
		 FileNode root=(FileNode)JSONObject.toBean(jsonObject,FileNode.class);
		 return root;
	 }
	 public void download(File file,int fileId,String type){			
			String url = "http://localhost:8080/httpclientweb/download.action";			
			HttpClient client = new DefaultHttpClient();
			HttpPost get = new HttpPost(url);
			List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
			formparams.add(new BasicNameValuePair("fileId", fileId+""));
			UrlEncodedFormEntity uefEntity;
			try {
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				get.setEntity(uefEntity);
				HttpResponse response = client.execute(get);
				InputStream is = response.getEntity().getContent();	
				String no = fileId+"";
				String localfile = file.toURL()+no+"."+type;
				
				localfile=localfile.substring(6, localfile.length());		
				OutputStream os = new FileOutputStream(localfile);
				
				int read = 0;
				byte[] temp = new byte[1024*1024];
				
				while((read=is.read(temp))>0){
					byte[] bytes = new byte[read];
					
					os.write(bytes);
				}
				os.flush();
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	 public void upload(File f){
			
			HttpClient httpclient = new DefaultHttpClient();
			
			try {
		
				HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/file.action");
				
			    FileBody bin = new FileBody(f);
				  
			    //FileBody bin2 = new FileBody(f);
			    
			    //StringBody comment = new StringBody(filename1);

			    MultipartEntity reqEntity = new MultipartEntity();
			    
			    reqEntity.addPart("file1", bin);//file1为请求后台的File upload;属性    
			   //  reqEntity.addPart("file2", bin2);//file2为请求后台的File upload;属性
			   //  reqEntity.addPart("filename1", comment);//filename1为请求后台的普通参数;属性	
			    httppost.setEntity(reqEntity);
			    
			    HttpResponse response = httpclient.execute(httppost);
			    
				    
			    int statusCode = response.getStatusLine().getStatusCode();
			    
				    
				if(statusCode == HttpStatus.SC_OK){
				    	
					System.out.println("服务器正常响应.....");
					
			    	HttpEntity resEntity = response.getEntity();				   			    	
			    	System.out.println(EntityUtils.toString(resEntity));//httpclient自带的工具类读取返回数据		    				    	
			    	System.out.println(resEntity.getContent());   
			    	EntityUtils.consume(resEntity);
			    }
				    
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
				    try { 
				    	httpclient.getConnectionManager().shutdown(); 
				    } catch (Exception ignore) {
				    	
				    }
				}
			}
}
