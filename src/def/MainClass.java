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
	//���ļ����ݶ���
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
		//��ȡ�ʷ������Ľ��
		try {
			//���ļ������ַ���
			String fileContext = mainClass.readFile(filePath);
			//���ļ��������ʷ�����
			LexAnalyse lexAnalyse=new LexAnalyse(fileContext);
			//���ʷ��������д���ļ�
			lexAnalyse.outputWordList();
			//��ôʷ������Ľ��
			ArrayList<Word> lexAnalyseRes = lexAnalyse.wordList;
			System.out.println(lexAnalyseRes);
			//���ʷ����������Ϊ�﷨��������,�﷨���������һ��wordlist��Ϊ�ʷ������Ľ��
			Parser parser = new Parser(lexAnalyseRes);
			//���﷨�����Ľ��д���ļ�
			parser.outputRes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
