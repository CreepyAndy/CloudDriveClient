

import static org.junit.Assert.*;

import org.junit.Test;

import model.FileNode;
public class NodeSimulator {

	@Test
	public void test() {
		//���ڵ�Ϊf��������f1��f2���ӣ�f1����f11��f12���ӡ�
		//����f1Ϊfolder��f11Ϊrar��f12Ϊtxt,f2Ϊmp3
		FileNode f = new FileNode();		
		f.setName("root");
		f.setType("root");
		FileNode f1 = new FileNode();
		f1.setType("folder");
		f1.setName("1");
		FileNode f11 = new FileNode();
		f11.setType("rar");
		f11.setName("11");
		FileNode f12 = new FileNode();
		f12.setType("txt");
		f12.setName("12");
		FileNode f2 = new FileNode();
		f2.setType("mp3");
		f2.setName("2");
		f.getChildren().add(f1);
		f.getChildren().add(f2);
		f1.setParent(f);
		f2.setParent(f);
		f1.getChildren().add(f11);
		f1.getChildren().add(f12);
		f11.setParent(f1);
		f12.setParent(f1);
		printTree(f,0);
						
	}
	private void printTree(FileNode f,int level){
		String preStr = "";
		for(int i=0;i<level;i++){
			preStr += "----";
		}
		System.out.println(preStr+f.getType());
		for(FileNode child : f.getChildren()){
			printTree(child,level+1);
		}
	}

}
