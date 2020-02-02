package app.test;

import app.utils.ServletUtil;

public class Tester {

	public static void main(String[] args) {
		String xml="<RESET><STATUS>TRUE</STATUS></RESET>";
		System.out.println(ServletUtil.getStatusAfterPwdChange(xml));

	}

}
