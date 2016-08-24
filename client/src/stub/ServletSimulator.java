package stub;
import model.*;

public class ServletSimulator {
	public FileNode getRoot(){
		FileNode f = new FileNode();		
		f.setName("root");
		f.setType("root");
		f.setId(16);
		FileNode f1 = new FileNode();
		f1.setType("folder");
		f1.setName("RootFolder");
		f1.setId(1);
	
		FileNode f11 = new FileNode();
		f11.setType("rar");
		f11.setName("FirstLevelRar");
		f11.setId(20);
		FileNode f12 = new FileNode();
		f12.setType("txt");
		f12.setName("FistLevelTxt");
		f12.setId(19);
		FileNode f2 = new FileNode();
		f2.setType("mp3");
		f2.setName("RootMp3");
		f2.setId(18);
		f.getChildren().add(f1);
		f.getChildren().add(f2);
		f1.setParent(f);
		f2.setParent(f);
		f1.getChildren().add(f11);
		f1.getChildren().add(f12);
		f11.setParent(f1);
		f12.setParent(f1);
		
		FileNode f13 = new FileNode();
		f13.setType("folder");
		f13.setId(30);
		f13.setName("RootFolder");
		f13.setParent(f1);
		f1.getChildren().add(f13);
		
		FileNode f131 = new FileNode();
		f131.setType("pdf");
		f131.setId(31);
		f131.setName("SecondLevelPdf");
			
		
		f131.setParent(f13);
		f13.getChildren().add(f131);
		return f;
	}
	
	public void printTree(FileNode f,int level){
		String preStr = "";
		for(int i=0;i<level;i++){
			preStr += "----";
		}
		System.out.println(preStr+f.getType());
		for(FileNode child : f.getChildren()){
			printTree(child,level+1);
		}
	}
	public void saveNewFolder(FileNode newF,int currentFileNodeId,int userId){
		FileNode currentFileNode = new FileNode();
		currentFileNode.setId(currentFileNodeId);
		newF.setParent(currentFileNode);
		currentFileNode.getChildren().add(newF);
		System.out.println("saved");
	}
	
}
