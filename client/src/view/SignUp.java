package view;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
public class SignUp extends JFrame{
	private JLabel lName=new JLabel("�û���");
    private JLabel lPwd=new JLabel("����");
    private JTextField tName=new JTextField();
    private JPasswordField tPwd=new JPasswordField();
    private JButton btnOK=new JButton("ע��");
    private JButton btnCancel=new JButton("ȡ��");
    public SignUp(){
    	
    	
    	JPanel jp=(JPanel)this.getContentPane();
    	jp.setLayout(new GridLayout(3,2));
    	jp.add(lName);jp.add(tName);
    	jp.add(lPwd);jp.add(tPwd);
    	jp.add(btnOK);jp.add(btnCancel);
    	btnOK.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			btnOK_Clicked();
    		}

			
    	});
    	btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dlg = new JFileChooser();
		    	dlg.setDialogTitle("Open JPEG file");
		    	int result = dlg.showOpenDialog(null);  // ��"���ļ�"�Ի���
		    	// int result = dlg.showSaveDialog(this);  // ��"�������ļ�"�Ի���
		    	if (result == JFileChooser.APPROVE_OPTION) {
		    	File file = dlg.getSelectedFile();
		    	String type = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
		    	System.out.println(type);
		    	}
			}
    		
    	});
    	this.setDefaultLookAndFeelDecorated(true);
		this.setSize(600,200);
		this.setVisible(true);
    }
    private void btnOK_Clicked() {
		// TODO Auto-generated method stub
		
	}
    public static void main(String[] args) {
		new SignUp();
	}
    
}
