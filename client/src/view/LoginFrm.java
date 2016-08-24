package view;
import javax.swing.*;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
public class LoginFrm extends JFrame{
	private JLabel lblID=new JLabel("用户名");
    private JLabel lblPwd=new JLabel("密码");
    private JTextField txtID=new JTextField();
    private JPasswordField txtPwd=new JPasswordField();
    private JButton btnOK=new JButton("登录");
    private JButton btnCancel=new JButton("取消");
    private JButton signUp=new JButton("用户注册");
    public LoginFrm(){
    	JPanel jp=(JPanel)this.getContentPane();
    	jp.setLayout(new GridLayout(3,2));
    	jp.add(lblID);jp.add(txtID);
    	jp.add(lblPwd);jp.add(txtPwd);
    	jp.add(btnOK);jp.add(signUp);
    	btnOK.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			btnOK_Clicked();
    		}
    	});
    	signUp.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			new SignUp();
    		}
    	});
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String Login(String uid,String upwd) {
    	String rvalue="";
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost  
		HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/user.action");
		// 创建参数队列  
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("uid", uid));
		formparams.add(new BasicNameValuePair("upwd", upwd));
		//模拟表单
		UrlEncodedFormEntity uefEntity;
		try {
			//构造表单
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			//post请求中设置已经过UrlEncodedFormEntity封装的表单
			httppost.setEntity(uefEntity);
			//System.out.println("executing request " + httppost.getURI());
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
    private void btnOK_Clicked(){
    	String uid=txtID.getText().trim();
    	String upwd=txtPwd.getText().trim();
    	String result = Login(uid,upwd).trim();
    	if(result.equals("success")){
    		new MainFrm();
    	}
    	else
    		JOptionPane.showMessageDialog(this, "用户名错误");
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		LoginFrm frm=new LoginFrm();
		frm.setSize(600,200);
		frm.setVisible(true);

	}
}
