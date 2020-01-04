package def;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import def.LexAnalyse;
import def.Parser.Word;
/**
 * @author 
 *
 */
public class MainClass {
	//将文件内容读出
	public static String readFile(String fileName) throws IOException {
		StringBuilder sbr = new StringBuilder();
		String str;
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
		BufferedReader in = new BufferedReader(isr);
		while ((str = in.readLine()) != null) {
			sbr.append(str).append('\n');
		}
		in.close();
		return sbr.toString();
	}
	
	public static void main(String args[]){
		String filePath = "C:\\Users\\cgych\\Desktop\\tinyComplier\\tinyCompiler\\b.c";
		MainClass mainClass = new MainClass();
		//获取词法分析的结果
		try {
			//将文件读入字符串
			String fileContext = mainClass.readFile(filePath);
			//将文件内容作词法分析
			LexAnalyse lexAnalyse=new LexAnalyse(fileContext);
			//将词法分析结果写入文件
			lexAnalyse.outputWordList();
			//获得词法分析的结果
			ArrayList<Word> lexAnalyseRes = lexAnalyse.wordList;
			System.out.println(lexAnalyseRes);
			//将词法分析结果作为语法分析输入,语法分析后产生一串wordlist，为词法分析的结果
			Parser parser = new Parser(lexAnalyseRes);
			//将语法分析的结果写入文件
			parser.outputRes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
