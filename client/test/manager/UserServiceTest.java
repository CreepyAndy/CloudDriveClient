package manager;

import static org.junit.Assert.*;

import model.FileNode;

import org.junit.Test;

import controller.UserController;

public class UserServiceTest {
	
	
	@Test
	public void testGetRoot() {
		UserController sm = new UserController();
		FileNode root = new FileNode();
		root=sm.getRoot();
		root.printTree(root, 0);
	}
	
	@Test
	public void testGetRootString() {
		UserController sm = new UserController();
		System.out.println(sm.getRootString());
		//FileNode root = new FileNode();
		//System.out.println(sm.getRootString());
	}
	@Test
	public void testFileUploade() {
		UserController sm = new UserController();
		FileNode root = new FileNode();
		root=sm.getRoot();
		root.printTree(root, 0);
	}

}
