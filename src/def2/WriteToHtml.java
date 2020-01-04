package def2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class WriteToHtml {
	static List<String> strList = new ArrayList<String>();
	static int flag = -1;
	static String[] keyWords = {"NOTE:","TIP:","WARNNING:","IMPORTANT:","CAUTION:"};
	public static void write(){
		try{
			File file = new File("C:\\Users\\cgych\\Desktop\\暑假计划表1.txt");
			String strTemp = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			FileWriter fw = new FileWriter(new File("C:\\Users\\cgych\\Desktop\\暑假计划表.html"), true);
            BufferedWriter bw = new BufferedWriter(fw);
            
			//将文件按行读入分析
			while((strTemp = reader.readLine()) != null){
				if(!strTemp.equals("===>")){
					strList.add(strTemp);
				}
			}
			for(int i = 0 ; i < strList.size() ; i++){
				String str = strList.get(i);
				
				if(str.equals("")){
					//说明很有可能是标题或者图片、引用之类的
					if((flag+1) == (i-1)){
						//检查类型
						String[] strs = strList.get(i-1).split("===>");
						if(strs.length != 1){
							String[] types = strs[1].trim().split(" ");
							String temp = "";
							if(types[0].startsWith("title")){
								//获得标题号
								temp = types[0];
								int titleLevel = Integer.parseInt(temp.substring(5,temp.length()));
								String toWrite = "<h"+(titleLevel+1)+">"+strs[0].replaceAll(" ", "&nbsp")+"</h"+(titleLevel+1)+">\n";
								bw.write(toWrite);
							}
							else if(types[0].trim().startsWith("REFLINE")){
								//得到第几引用
								String info = types[0].trim();
								int refLevel = Integer.parseInt(info.substring(7,info.length()));
								String toWrite = "<p id=\"REFLINE"+refLevel+"\">"+"["+(refLevel+1)+"]"+
									strs[0].replaceAll("\\-\\[\\[\\[", " (").replaceAll("\\]\\]\\]", ")")
									+"</p>";
								System.out.println(toWrite);
								bw.write(toWrite);
							}
							else if(types[0].trim().startsWith("IMG")){
								String strInfo = types[0].trim();
								System.out.println(strInfo);
								String[] info = strInfo.split("\\|");
								String source = info[1];
								String width = info[2];
								String height = info[3];
								String toWrite = "<img src=\""+source+"\" width=\""+width+"\" height=\""+height+"\" /></br>";
								System.out.println(toWrite);
								bw.write(toWrite);
							}
							else{
								System.out.println("bbb"+strList.get(i-1));
								for(int j = 0 ; j < keyWords.length ; j++){
									if(strList.get(i-1).trim().startsWith(keyWords[j])){
										System.out.println(keyWords[j]);
										if(keyWords[j].equals("NOTE:")){
											String toWrite = "<div style=\"box-shadow: 0px 0px 8px darkgray; padding: 7px; border-radius: 5px; background: #CCCCCC; margin: 10px\">"+
													strList.get(i-1).split("===>")[0].replaceAll(" ", "&nbsp")
													+"</div>";
											bw.write(toWrite);
										}
										if(keyWords[j].equals("TIP:")){
											String toWrite = "<div style=\"box-shadow: 0px 0px 8px darkgray; padding: 7px; border-radius: 5px; background: #D0EEFF; margin: 10px\">"+
													strList.get(i-1).split("===>")[0].replaceAll(" ", "&nbsp")
													+"</div>";
											bw.write(toWrite);
										}
										if(keyWords[j].equals("WARNNING:")){
											String toWrite = "<div style=\"box-shadow: 0px 0px 8px darkgray; padding: 7px; border-radius: 5px; background: #FF0000; color: white; margin: 10px\">"+
													strList.get(i-1).split("===>")[0].replaceAll(" ", "&nbsp")
													+"</div>";
											bw.write(toWrite);
										}
										if(keyWords[j].equals("IMPORTANT:")){
											String toWrite = "<div style=\"box-shadow: 0px 0px 8px darkgray; padding: 7px; border-radius: 5px; background: aquamarine; margin: 10px\">"+
													strList.get(i-1).split("===>")[0].replaceAll(" ", "&nbsp")
													+"</div>";
											bw.write(toWrite);
										}
										if(keyWords[j].equals("CAUTION:")){
											String toWrite = "<div style=\"box-shadow: 0px 0px 8px darkgray; padding: 7px; border-radius: 5px; background: #FFE4C4; margin: 10px\">"+
													strList.get(i-1).split("===>")[0].replaceAll(" ", "&nbsp")
													+"</div>";
											bw.write(toWrite);
										}
									}
								}
							}
						}
						else{
							String toWrite = "<p>"+strs[0]+"</p>\n";
							bw.write(toWrite);
						}
					}
					
					else{
						//这是一个段落
						for(int j = flag + 1 ; j < i ; j++){
							String temp = strList.get(j);
							String[] strs = temp.split("===>");
							if(strs.length == 1){
								String toWrite = strs[0].replaceAll(" ", "&nbsp");
								bw.write(toWrite);
							}
							else{
								String[] types = strs[1].trim().split(" ");
								if(types.length == 1){
									if(types[0].equals("bold")){
										String toWrite = "<b>"+strs[0].replaceAll(" ", "&nbsp")+"</b>";
										bw.write(toWrite);
									}
									if(types[0].equals("italic")){
										String toWrite = "<i>"+strs[0].replaceAll(" ", "&nbsp")+"</i>";
										bw.write(toWrite);
									}
									//引用
									if(types[0].startsWith("REF")){
										//获得引用编号
										String refLevel = types[0].substring(3,types[0].length());
										String toWrite = "<a href=\"#REFLINE"+refLevel+"\">["+(Integer.parseInt(refLevel)+1)+"]</a>";
										bw.write(toWrite);
									}
								}
								if(types.length > 1){
									String toWrite = "<b><i>"+strs[0].replaceAll(" ", "&nbsp")+"</i></b>";
									bw.write(toWrite);
								}
							}
						}
						bw.write("</br>");
					}
					flag = i;
				}
			}
			
			
			bw.close();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		write();
		
	}
	
}
