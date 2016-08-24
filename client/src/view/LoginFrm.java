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
	private JLabel lblID=new JLabel("�û���");
    private JLabel lblPwd=new JLabel("����");
    private JTextField txtID=new JTextField();
    private JPasswordField txtPwd=new JPasswordField();
    private JButton btnOK=new JButton("��¼");
    private JButton btnCancel=new JButton("ȡ��");
    private JButton signUp=new JButton("�û�ע��");
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
		// ����Ĭ�ϵ�httpClientʵ��.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ����httppost  
		HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/user.action");
		// ������������  
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("uid", uid));
		formparams.add(new BasicNameValuePair("upwd", upwd));
		//ģ���
		UrlEncodedFormEntity uefEntity;
		try {
			//�����
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			//post�����������Ѿ���UrlEncodedFormEntity��װ�ı�
			httppost.setEntity(uefEntity);
			//System.out.println("executing request " + httppost.getURI());
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
    private void btnOK_Clicked(){
    	String uid=txtID.getText().trim();
    	String upwd=txtPwd.getText().trim();
    	String result = Login(uid,upwd).trim();
    	if(result.equals("success")){
    		new MainFrm();
    	}
    	else
    		JOptionPane.showMessageDialog(this, "�û�������");
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
