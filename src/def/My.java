package def;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.*;

public class My {
	Pattern pattern = null;
	Matcher matcher = null;
    public static int KEY = 0;
    public static int OP = 1;
    public static int ID = 2;
    public static int SEP = 3;
    public static int SPACE = 4;
    public static String[] types = {"KEY","OP","ID","SEP","VAR"};
	public static String[] KEYs = {"char","double","enum","float","int","long","short",
	                        "signed","struct","union","unsigned","void","for","do","while",
	                        "break","continue","if","else","goto","switch","case","default",
	                        "return","auto","extern","register","static","static","const",
	                        "sizeof","typedef","volatile"};
	public static String[] OPs = {".","\\,", "=", "+", "-", "*", "/", "%", "&&", "||", "!", ">", "<", ">=", "<=", "!=","++", "--"};
    public static String[] SEPs = {"#", "{", "}", "(", ")", "[", "]", "\"", "'", ";","/*", "*/", ",","\\\\","/**"};
	public static String[] IDs = {"main"};
	public static String[] SPACEs = {" "};
	public static String strInput = "";
	public static Map<Integer,String> typeMap = new LinkedHashMap<Integer,String>();
    public static String Res = "";
    public static List<String> resList = new ArrayList<String>();
    public static List<Integer> typeList = new ArrayList<Integer>();
	//将文件中的代码读入字符串
	public void ReadFileToStr(File file){
		try {
			String strTemp = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((strTemp = reader.readLine()) != null){
				if(!strTemp.replaceAll(" ", "")
						.replaceAll("\n", "")
						.replaceAll("\t", "")
						.startsWith("//")){
					System.out.println(strTemp);
					strInput += strTemp.trim().replaceAll("\n", "").replaceAll("\t", "");
				}
			}
			System.out.println(strInput);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//分词
	public void SplitCode(){
            int lastMatchedIndex = 0;
            String temp = "";
            int type = 0;
            int flag = 0;
            for(int i = 0 ; i < strInput.length(); i++){
                temp += strInput.charAt(i)+"";
                System.out.println("{temp}:"+temp);
                typeList.add(type = JudgeStrIn(temp , KEYs , OPs , IDs , SEPs));
                System.out.println("{type}:"+type);
                if(type != 11){
                    //说明匹配到了，但有可能后面还有匹配，选取最长的匹配项
                	flag = 1;
                	if(i == strInput.length() - 1){
                		resList.add("<"+temp+","+types[JudgeStrIn(temp , KEYs , OPs , IDs , SEPs)]+">");
                	}else{
                		System.out.println("{temp}con:"+temp);
                		continue;
                	}
                    
                }else{
                	System.out.println("else:"+temp);
                	//说明没匹配到
                	//判断上次是否匹配到是否
                	if(flag == 1){
                		System.out.println(temp.substring(0 , temp.length() - 1)+" 匹配到");
                		//说明上次匹配到了
                		//取上次匹配到的字符串写入Map
                		temp = temp.substring(0 , temp.length() - 1);
                		//System.out.println("temp:"+temp+" "+typeList.get(typeList.size()-2));
                		resList.add("<"+temp+","+typeMap.get(typeList.get(typeList.size()-2))+">");
                		//将已经匹配的移出待匹配串
                		i = i - 1;
                		temp = "";
                		flag = 0;
                	}else{
                		System.out.println(temp+" 没匹配到");
                		System.out.println("strInput.charAt(i):"+strInput.charAt(i)+"");
                		//说明上次就没匹配到
                		//继续走
                		if(JudgeStrIn(strInput.charAt(i)+"" , KEYs , OPs , IDs , SEPs) == SEP || 
                				JudgeStrIn(strInput.charAt(i)+"" , KEYs , OPs , IDs , SEPs) == OP ||
                				JudgeStrIn(strInput.charAt(i)+"" , KEYs , OPs , IDs , SEPs) == SPACE){
                			//说明最后一个是分隔符结束的,前面的就是ID
                			System.out.println(strInput.charAt(i)+"");
                			temp = temp.substring(0 , temp.length() - 1);
                			resList.add("<"+temp+","+"ID"+">");
                			flag = 0;
                			i = i - 1;
                			System.out.println("i = "+i);
                			temp = "";
                		}
                	}             
                }
   
            }
	}
	//判断字符串属于什么类型
    public int JudgeStrIn(String str , String[] KEYs , String[] OPs , String[] IDs , String[] SEPs){
        //看是否在KEYs里
    	System.out.println("str::"+str);
        for(int i = 0 ; i < KEYs.length ; i++){
            if(str.equals(KEYs[i])){
            	System.out.println("KEYs");
                return KEY;
            }
        }
        //...在OPs
        for(int i = 0 ; i <OPs.length ; i++){
            if(str.equals(OPs[i])){
            	System.out.println("OPs");
                return OP;
            }
        }
        //...在IDs里
        for(int i = 0 ; i < IDs.length ; i++){
            if(str.equals(IDs[i])){
            	System.out.println("IDs");
                return ID;
            }
        }
        //...在SEPs里
        for(int i = 0 ; i < SEPs.length ; i++){
            if(str.equals(SEPs[i])){
            	System.out.println("SEPs");
                return SEP;
            }
        }
        for(int i = 0 ; i < SPACEs.length ; i++){
        	if(str.equals(SPACEs[i])){
            	System.out.println("SPACEs");
                return SPACE;
            }
        }
        return 11;
    }
        
    public ArrayList<String> oPRes(ArrayList<String> resList){
    	//将两个双引号之间的字符串们变为一个字符串
    	
    	return resList;
    }
        
	public static void main(String args[]){
		typeMap.put(0,"KEY");
		typeMap.put(1,"OP");
		typeMap.put(2,"ID");
		typeMap.put(3, "SEP");
		typeMap.put(4, "SPACE");
		My my = new My();
		my.ReadFileToStr(new File("C:\\Users\\cgych\\Desktop\\aaa.txt"));
		my.SplitCode();
		System.out.println(resList);
	}
	
}
