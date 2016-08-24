package view;

import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import controller.UserController;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.*;
import stub.*;

public class MainFrm extends JFrame implements ActionListener {
	private int userId = 2;
	private FileNode preFileNode;
	private FileNode currentFileNode;
	private StringBuffer currentPath = new StringBuffer("Root\\");
	private JButton user;
	private JPanel userInfo = new JPanel();
	private JButton cloudDriver;
	private JButton pic;
	private JButton hiddenArea;
	private JButton functions;
	private JButton upload;
	private JButton download;
	private JButton share;
	private JButton del;
	private JButton newFolder;
	private JButton back;
	private JButton forward;
	private JLabel tSearch = new JLabel("当前目录");
	private JButton refresh;
	private JButton search;
	private JButton allPics = new JButton("所有照片");
	private JButton onlinePics = new JButton("云盘照片");
	private JButton localPics = new JButton("本地照片");
	private JTextField newFolderName = new JTextField(20);
	private JComboBox picComboBox;
	private JPanel content = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel west = new JPanel();
	private JPanel east = new JPanel();
	private JPanel south = new JPanel();
	private int currentNodeId;
	private int preNodeId;
	private FileNode root;
	private int x = 100;
	private File file;
	private UserController um = new UserController();
	private ServletSimulator sm = new ServletSimulator();

	public MainFrm() {
		root = sm.getRoot();
		JPanel jp = (JPanel) this.getContentPane();
		jp.setLayout(new BorderLayout());
		jp.add(center, BorderLayout.CENTER);
		jp.add(north, BorderLayout.NORTH);
		jp.add(south, BorderLayout.SOUTH);
		north.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		north.add(user = new JButton(new ImageIcon("./resource/user.png")));
		north.add(userInfo);
		north.add(cloudDriver = new JButton(new ImageIcon(
				"./resource/cloudDrive.png")));
		north.add(pic = new JButton(new ImageIcon("./resource/pic.png")));
		north.add(hiddenArea = new JButton(new ImageIcon(
				"./resource/hiddenArea.png")));
		north.add(functions = new JButton(new ImageIcon(
				"./resource/functions.png")));
		center.setLayout(new BorderLayout());
		JPanel up = new JPanel();
		up.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		JPanel mid = new JPanel();
		mid.setLayout(new BorderLayout());
		JPanel down = new JPanel();
		down.setLayout(new GridLayout(5, 10));
		center.add(up, BorderLayout.NORTH);
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		// center.add(west,BorderLayout.WEST);
		// center.add(east,BorderLayout.EAST);
		center.add(mid, BorderLayout.CENTER);
		center.add(down, BorderLayout.SOUTH);
		up.add(upload = new JButton(new ImageIcon("./resource/upload.png")));
		up.add(download = new JButton(new ImageIcon("./resource/download.png")));
		up.add(share = new JButton(new ImageIcon("./resource/share.png")));
		up.add(del = new JButton(new ImageIcon("./resource/del.png")));
		up.add(newFolderName);
		up.add(newFolder = new JButton(
				new ImageIcon("./resource/newFolder.png")));
		mid.add(p1, BorderLayout.NORTH);
		mid.add(content, BorderLayout.CENTER);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		p1.add(back = new JButton(new ImageIcon("./resource/back.png")));
		p1.add(forward = new JButton(new ImageIcon("./resource/forward.png")));
		// tSearch.setSize(50, 10);
		// p1.add(tSearch);
		p1.add(refresh = new JButton(new ImageIcon("./resource/refresh.png")));

		mid.add(west, BorderLayout.WEST);
		content.setLayout(new GridLayout(5, 6));
		// west.add(allPics);west.add(onlinePics);west.add(localPics);
		// west.setVisible(false);
		pic.addActionListener(this);
		// 有问题，只能回退一层，而且回退后新建文件有问题。以后可以用栈来解决路径问题。
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				

				try {
					if (currentFileNode.getName().trim().equals("root"))
						JOptionPane.showMessageDialog(null, "已经是根节点");
					else {
						// System.out.println(preNodeId);
						preFileNode = root.findTreeNodeById(preNodeId);
						back_clicked_show(preFileNode);

					}
				} catch (NullPointerException e1) {
					preFileNode = root.findTreeNodeById(preNodeId);
					back_clicked_show(preFileNode);
				}
			}

		});
		// 向服务器发送目录请求
		cloudDriver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().invalidate();
				root = sm.getRoot();
				Set<FileNode> f1 = sm.getRoot().getChildren();
				currentFileNode = root;
				button_clicked_show(root);
				getContentPane().validate();
			}

		});
		// FIXME
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser dlg = new JFileChooser();
				dlg.setDialogTitle("Open JPEG file");
				int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
				// int result = dlg.showSaveDialog(this); // 打"开保存文件"对话框
				if (result == JFileChooser.APPROVE_OPTION) {
					file = dlg.getSelectedFile();
					System.out.println(file);
					um.upload(file);

					FileNode nf = new FileNode();
					nf.setId(x);
					// nf.setName("name");
					String type = file.getName().substring(
							file.getName().lastIndexOf(".") + 1,
							file.getName().length());
					nf.setType(type);
					String name = file.getName().substring(0,
							file.getName().lastIndexOf("."));
					nf.setName(name);
					// sm.saveNewFolder(nf, currentFileNode.getId(), userId);
					x++;
					currentFileNode = root.findTreeNodeById(currentNodeId);
					if (currentFileNode == null)
						currentFileNode = root;
					if (currentFileNode.getParent() != null)
						preNodeId = currentFileNode.getParent().getId();
					currentFileNode.addChildNode(nf);
					currentNodeId = currentFileNode.getId();
					
					newed_show(currentFileNode);

				}
			}
		});
		newFolder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileNode nf = new FileNode();
				nf.setId(x);
				// nf.setName("name");
				nf.setType("folder");
				// sm.saveNewFolder(nf, currentFileNode.getId(), userId);
				x++;
				nf.setName(newFolderName.getText());
				currentFileNode = root.findTreeNodeById(currentNodeId);
				if (currentFileNode == null)
					currentFileNode = root;
				if (currentFileNode.getParent() != null)
					preNodeId = currentFileNode.getParent().getId();
				currentFileNode.addChildNode(nf);
				currentNodeId = currentFileNode.getId();
				
				newed_show(currentFileNode);
			}

		});
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().invalidate();
				currentFileNode = root;
				button_clicked_show(root);
				getContentPane().validate();

			}

		});
		this.setSize(1000, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new MainFrm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pic) {
			// getContentPane().removeAll();
			getContentPane().invalidate();
			west.add(allPics);
			west.add(onlinePics);
			west.add(localPics);
			getContentPane().validate();

			// content.setVisible(true);
		}

	}

	private void back_clicked_show(FileNode preFileNode) {
		getContentPane().invalidate();

		content.removeAll();
		Set<FileNode> fcs = preFileNode.getChildren();
		JButton[] bs = new JButton[255];

		currentNodeId = preFileNode.getId();
		// System.out.println(currentNodeId);
		currentPath = currentPath.append(currentFileNode.getName() + "\\");
		tSearch.setText(currentPath.toString());

		for (final FileNode fx : fcs) {
			final int i = fx.getId();
			StringBuffer name = new StringBuffer();
			name.append(i + "                ");
			name.append(fx.getName());
			bs[i] = new JButton(name.toString(), new ImageIcon("./resource/"
					+ fx.getType() + ".png"));
			if (fx.getType().trim().equals("folder"))
				bs[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 当前节点ID！！！！！！！！！！！！！
						// System.out.println(i);

						currentNodeId = i;
						button_clicked_show(fx);
					}
				});
			else
				bs[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Object[] possibleValues = { "download", "delete",
						"copy" };
				
				Object selectedValue = JOptionPane.showInputDialog(
						null, "Choose one", "选择操作类型",
						JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
				if (selectedValue.equals("download")) {
					JFileChooser dlg = new JFileChooser();
					dlg.setDialogTitle("Choose save path");
					dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
					// int result = dlg.showSaveDialog(this); //
					// 打"开保存文件"对话框
					if (result == JFileChooser.APPROVE_OPTION)
						file = dlg.getSelectedFile();
					FileNode fy = root.findTreeNodeById(i);
				     try {
						um.download(file, i, fy.getType());
					} catch (Exception e2) {
						System.out.println(e2);
					}
				}
				else if(selectedValue.equals("delete")){
					root.findTreeNodeById(i).deleteNode();
					button_clicked_show(currentFileNode);
				}
				else if(selectedValue.equals("copy")){
					
				}

					}

				});
			content.add(bs[i]);
			content.updateUI();

		}
		getContentPane().validate();

	}

	public void newed_show(FileNode f) {
		getContentPane().invalidate();

		content.removeAll();
		Set<FileNode> fcs = f.getChildren();
		JButton[] bs = new JButton[255];

		for (final FileNode fx : fcs) {
			final int i = fx.getId();
			StringBuffer name = new StringBuffer();
			name.append(i + "                ");
			name.append(fx.getName());
			bs[i] = new JButton(name.toString(), new ImageIcon("./resource/"
					+ fx.getType() + ".png"));
			if (fx.getType().trim().equals("folder"))
				bs[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 当前节点ID！！！！！！！！！！！！！
						// System.out.println(i);

						currentNodeId = i;
						button_clicked_show(fx);
					}
				});
			else
				bs[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Object[] possibleValues = { "download", "delete",
						"copy" };
				
				Object selectedValue = JOptionPane.showInputDialog(
						null, "Choose one", "选择操作类型",
						JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
				if (selectedValue.equals("download")) {
					JFileChooser dlg = new JFileChooser();
					dlg.setDialogTitle("Choose save path");
					dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
					// int result = dlg.showSaveDialog(this); //
					// 打"开保存文件"对话框
					if (result == JFileChooser.APPROVE_OPTION)
						file = dlg.getSelectedFile();
					FileNode fy = root.findTreeNodeById(i);
				     try {
						um.download(file, i, fy.getType());
					} catch (Exception e2) {
						System.out.println(e2);
					}
				}
				else if(selectedValue.equals("delete")){
					root.findTreeNodeById(i).deleteNode();
					button_clicked_show(currentFileNode);
				}
				else if(selectedValue.equals("copy")){
					
				}

					}

				});
			content.add(bs[i]);
			content.updateUI();

		}
		getContentPane().validate();

	}

	public void button_clicked_show(FileNode f) {
		getContentPane().invalidate();

		content.removeAll();
		Set<FileNode> fcs = f.getChildren();
		JButton[] bs = new JButton[255];
		preFileNode = currentFileNode;
		preNodeId = preFileNode.getId();
		currentFileNode = f;
		currentPath = currentPath.append(f.getName() + "\\");
		tSearch.setText(currentPath.toString());

		for (final FileNode fx : fcs) {
			final int i = fx.getId();
			StringBuffer name = new StringBuffer();
			name.append(i + "                ");
			name.append(fx.getName());
			bs[i] = new JButton(name.toString(), new ImageIcon("./resource/"
					+ fx.getType() + ".png"));
			if (fx.getType().trim().equals("folder"))
				bs[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 当前节点ID！！！！！！！！！！！！！
						// System.out.println(i);

						currentNodeId = i;
						button_clicked_show(fx);
					}
				});
			else
				bs[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// FIXME
						Object[] possibleValues = { "download", "delete",
								"copy" };
						
						Object selectedValue = JOptionPane.showInputDialog(
								null, "Choose one", "选择操作类型",
								JOptionPane.INFORMATION_MESSAGE, null,
								possibleValues, possibleValues[0]);
						if (selectedValue.equals("download")) {
							JFileChooser dlg = new JFileChooser();
							dlg.setDialogTitle("Choose save path");
							dlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int result = dlg.showOpenDialog(null); // 打开"打开文件"对话框
							// int result = dlg.showSaveDialog(this); //
							// 打"开保存文件"对话框
							if (result == JFileChooser.APPROVE_OPTION)
								file = dlg.getSelectedFile();
							FileNode fy = root.findTreeNodeById(i);
						     try {
								um.download(file, i, fy.getType());
							} catch (Exception e2) {
								System.out.println(e2);
							}
						}
						else if(selectedValue.equals("delete")){
							root.findTreeNodeById(i).deleteNode();
							button_clicked_show(currentFileNode);
						}
						else if(selectedValue.equals("copy")){
							
						}

						

					}

				});
			content.add(bs[i]);
			content.updateUI();

		}
		getContentPane().validate();

	}

}
