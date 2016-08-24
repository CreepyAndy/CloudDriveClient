package online;

import static org.junit.Assert.*;


import org.junit.Test;

import controller.UserController;

import view.LoginFrm;

public class ComunicationTest {

	@Test
	public void test() {
		LoginFrm lf = new LoginFrm();
		System.out.println(lf.Login("admin", "1234"));
	}
	@Test
	public void testJsonString() {
		UserController sm = new UserController();
		System.out.println(sm.getRootString());
	}

}
