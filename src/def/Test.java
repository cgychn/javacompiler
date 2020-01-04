package def;
import java.util.regex.*;

public class Test {
	public static void main(String args[]){
		String str = "char *str = \"String\'\" 123\"";
		String temp = "";
		Pattern pattern = Pattern.compile("\".*\"");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			System.out.println(temp = matcher.group(0));
		}
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\".*\"", temp);
		System.out.println(str);
	}
}
