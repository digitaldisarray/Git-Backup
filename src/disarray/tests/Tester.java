package disarray.tests;

import disarray.util.GitHubUtil;

public class Tester {
	public static void main(String[] args) {
		
		new Tester().testLinkType();
	}
	
	/**
	 * Tests GitHubUtil.getLinkType() function
	 * @return Returns true when passing the test
	 */
	private boolean testLinkType() {
		System.out.println("Testing getLinkType()");
		return (testLink("" , -1) && testLink("https://github.com//username", -1));
	}
	
	
	private boolean testLink(String link, int expectedType) {
		int actualType = GitHubUtil.getLinkType(link);
		
		if (actualType != expectedType) {
			System.out.println("FAIL - getLinkType - " + link);
			System.out.println("Expected: " + expectedType + "  Actual: " + actualType);
			return false;
		} else {
			System.out.println("PASS");
			return true;
		}
	}
}
