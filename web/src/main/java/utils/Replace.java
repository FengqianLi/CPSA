package utils;

public class Replace {
	private static String replacement = "_";

	public static String replaceAll(String str) {
		return str.replaceAll(" ", replacement);
	}
}
