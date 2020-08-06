package hackerrank;

public class RepeatedStrings {

	static long findNoOfOccurances(String s, char ch) {
		return s.chars().mapToObj(i -> (char) i).filter(c -> {
			return c == ch;
		}).count();
	}

	static long repeatedString(String s, long n) {
		long count = (n / s.length()) * findNoOfOccurances(s, 'a');
		try {
			count += findNoOfOccurances(s.substring(0, (int) n % s.length()), 'a');
		} catch (StringIndexOutOfBoundsException e) {
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(repeatedString("aba", 10L));
		System.out.println(repeatedString("a", 1000000000000L));
		System.out.println(repeatedString(
				"kmretasscityylpdhuwjirnqimlkcgxubxmsxpypgzxtenweirknjtasxtvxemtwxuarabssvqdnktqadhyktagjxoanknhgilnm",
				736778906400L));
		System.out.println(repeatedString(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				534802106762L));
	}

}
