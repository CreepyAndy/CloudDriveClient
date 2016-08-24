package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileNodeTest {
	public static FileNode f;
	@BeforeClass
	public static void beforeClass(){
		f = new FileNode();		
		f.setName("root");
		f.setType("root");
		f.setId(0);
		FileNode f1 = new FileNode();
		
		f1.setType("folder");
		f1.setName("1");
		f1.setId(1);
		FileNode f11 = new FileNode();
		f11.setType("rar");
		f11.setName("11");
		f11.setId(11);
		FileNode f12 = new FileNode();
		f12.setType("txt");
		f12.setName("12");
		f12.setId(12);
		FileNode f2 = new FileNode();
		f2.setType("mp3");
		f2.setName("2");
		f2.setId(2);
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
		f13.setId(13);
		f13.setName("13");
		f13.setParent(f1);
		f1.getChildren().add(f13);
		
		FileNode f131 = new FileNode();
		f131.setType("rar");
		f131.setId(131);
		f131.setName("131");
		
		f131.setParent(f13);
		f13.getChildren().add(f131);
	}

	@Test
	public void testFindFileNodeById() {
		System.out.println(f.findTreeNodeById(13).getType());
	}
	@Test
	public void testDeleteChildNode() {
		f.deleteChildNode(1);
		f.printTree(f, 0);
	}
	@Test
	public void testDeleteNode() {
		FileNode[] dn = new FileNode[10];
		f.getChildren().toArray(dn);
		dn[1].deleteNode();
		f.printTree(f, 0);
	}
	
	@AfterClass
    public static void afterClass(){
		
	}

}
