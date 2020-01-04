package def2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Compiler {
	
	//存放标题匹配符
	public static String[] titleHead = {"= ","== ","=== ","==== ","===== "}; 
	//存放分析完成后的结果
	public static List<ResBean> resList = new ArrayList<ResBean>();
	//残留标记 1 表示残留 0 表示没残留
	public static int leftFlag = 0;
	//语法分析树
	public static List<MyTree> tree = new ArrayList<MyTree>();
	//添加类型解析键值对
	public static Map<Character,String> typeMap = new HashMap<Character,String>();
	//记录递归的步数,每执行一次递归就会加1
	public static int step = -1;
	//标记关键字
	public static String[] keyWords = {"NOTE:","TIP:","WARNNING:","IMPORTANT:","CAUTION:"};
	//记录引用号
	public static int refIndex = -1;
	//记录全文中索引个数
	public static int refCount = 0;
	
	//从文件读入内容,并作分析
	public static void AnalyseContent(File file){
		try {
			String strTemp = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//将文件按行读入分析
			while((strTemp = reader.readLine()) != null){
				//是标题
				strTemp = strTemp.replaceAll("\n", "");
				int type = -1;
				String tipType = "";
				if((type = checkTitleType(strTemp)) != -1){
					ResBean resBean = new ResBean();
					//设置内容
					resBean.setContent(strTemp.replace(titleHead[type], "").replace(reverse(titleHead[type]), ""));
					//设置类型
					resBean.getType().add("title"+type);
					resList.add(resBean);
					//添加换行符
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//是提示语
				else if(!(tipType = checkTips(strTemp)).equals("")){
					ResBean resBean = new ResBean();
					resBean.setContent(strTemp);
					resBean.getType().add(tipType);
					resList.add(resBean);
					//添加换行符
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//图片
				else if(strTemp.trim().startsWith("image::")){
					//图片路径，大小
					String IMGInfo = strTemp.trim().substring(8,strTemp.length());
					String[] infos = IMGInfo.split(" ");
					String source = "|";
					String width = "|";
					String height = "|";
					for(String info : infos){
						if(info.startsWith("source:")){
							source+= info.substring(7,info.length());
						}
						if(info.startsWith("width:")){
							width+= info.substring(6,info.length());
						}
						if(info.startsWith("height:")){
							height+= info.substring(7,info.length());
						}
					}
					ResBean resBean = new ResBean();
					resBean.setContent(strTemp);
					resBean.getType().add("IMG"+source+width+height);
					resList.add(resBean);
					//添加换行符
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//引用
				else if(strTemp.trim().startsWith("-[[[")){
					++refIndex;
					ResBean resBean = new ResBean();
					resBean.setContent(strTemp);
					//这是第几引用
					resBean.getType().add("REFLINE" + refIndex);
					resList.add(resBean);
					//添加换行符
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				else{
					//段落
					distincRef(strTemp);
					//添加换行符
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//构造语法分析树
	public static void buildParsingTree(ResBean resBean , char sign , MyTree parent){
		//MyTree myTree = new MyTree();
		//如果parent是空的就初始化
		//将递归的步数加1
		++step;
		if(parent == null){
			parent = new MyTree();
			parent.setData(resBean);
			parent.setParent(null);
		}
		String strContent = resBean.getContent();
		List<Integer> indexs = new ArrayList<Integer>(); //{4,6    8,11    14,17}
		System.out.println("strContent:"+" "+strContent);
		for(int i = 0 ; i < strContent.length() ; i++){
			if(strContent.charAt(i) == sign){
				if(i != 0 && strContent.charAt(i-1) == '/'){
					//看看前面有没有转义字符
					//说明这个不是标识符
					System.out.println("its not real "+sign);
				}
				else{
					//*是标识符
					System.out.println("its real "+sign);
					indexs.add(i);
				}
			}
		}
		//如果内容中没有在语法的标识符，就退出算法
		if(indexs.size() == 0){
			//说明是树的叶子节点
			tree.add(parent);
			System.out.println(sign+"nonono");
			return;
		}
		//indexs是标识符下表的集合
		for(int i = 0 ; i < indexs.size() ; i++){
			//边界处理，第一个标识符和前面的内容要做一次分析，最后一个标识符要和后面的内容做一个语法分析
			if(i == 0){
				String tempStr = strContent.substring(0,indexs.get(i));
				System.out.println("first: "+tempStr);
				ResBean tempRes = new ResBean();
				tempRes.setContent(tempStr);
				//不添加新类型
				//添加父节点的类型
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//为parent添加子节点
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//执行递归，分析下一个语法
				buildParsingTree(myTree.getData(), '_', myTree);
			}
			// i 和 i-1  （indexs中的元素个数一定是偶数，所以第0和1  2和3  4和5 。。。是一对） 0和1之间是匹配到的，1和2之间是没匹配到的
			if(i%2 != 0){
				String tempStr = strContent.substring(indexs.get(i-1) + 1 , indexs.get(i));
				System.out.println("have type "+typeMap.get(sign)+" :"+tempStr);
				ResBean tempRes = new ResBean();
				//设置内容
				tempRes.setContent(tempStr);
				//添加类型
				tempRes.getType().add(typeMap.get(sign));
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//为parent添加子节点
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//执行递归
				buildParsingTree(myTree.getData(), '_', myTree);
				if(i != indexs.size() - 1){
					String tempStr1 = strContent.substring(indexs.get(i) + 1 , indexs.get(i+1));
					System.out.println("no type :"+tempStr1);
					ResBean tempRes1 = new ResBean();
					tempRes1.setContent(tempStr1);
					//不添加新类型，应为这里添加进去的文本需要进入下次语法分析
					//添加父节点的类型
					if(parent != null){
						for(String type : parent.getData().getType()){
							tempRes1.getType().add(type);
						}
					}
					//为parent添加子节点
					MyTree myTree1 = new MyTree();
					myTree1.setData(tempRes1);
					myTree1.setParent(parent);
					if(parent != null){
						parent.getChildList().add(myTree1);
					}
					//执行递归
					buildParsingTree(myTree1.getData(), '_', myTree1);
				}
			}
			if(i == indexs.size() - 1){
				String tempStr = strContent.substring(indexs.get(i) + 1,strContent.length());
				System.out.println("last: "+tempStr);
				ResBean tempRes = new ResBean();
				tempRes.setContent(tempStr);
				//不添加新类型
				//添加父节点的类型
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//为parent添加子节点
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//执行递归
				buildParsingTree(myTree.getData(), '_', myTree);
			}
		}
	}
	
	//识别文中索引
	public static void distincRef(String str){
		String[] left = str.split("<<");
		List<String> list = new ArrayList<String>();
		for(String temp : left){
			String[] right;
			if((right = temp.split(">>")).length >=1){
				for(String item : right){
					list.add(item);
				}
			}
			else{
				list.add(temp);
			}
		}
		//for出来之后 list里面的奇数位下标元素就是引用名字
		for(int i = 0 ; i < list.size() ; i++){
			if(i % 2 != 0){
				//是引用
				System.out.println(list.get(i)+" ==> 引用");
				ResBean resBean = new ResBean();
				resBean.setContent(list.get(i));
				resBean.getType().add("REF"+refCount);
				++refCount;
				resList.add(resBean);
			}
			//在list偶数位上的元素是还没有被分析的元素
			else{
				System.out.println(list.get(i)+" ==> 不是引用");
				ResBean resBean = new ResBean();
				resBean.setContent(list.get(i));
				//交给语法分析树
				buildParsingTree(resBean, '*', null);
				//将tree中的元素加到resList中
				for(MyTree myTree: tree){
					resList.add(myTree.getData());
				}
				//初始化tree
				tree = new ArrayList<MyTree>();
			}
		}
		
		
	}
	
	//反转字符串
	public static String reverse(String str){
		String strRes = "";
		for(int i = str.length()-1 ; i >= 0 ; i--){
			strRes+= str.charAt(i);
		}
		return strRes;
	}
	
	//看是什么标题
	public static int checkTitleType(String strLine){
		int res = -1;
		//看开头和结尾
		for(String strItem : titleHead){
			//说明是标题
			if(strLine.startsWith(strItem) && strLine.endsWith(reverse(strItem))){
				System.out.println(strLine+" 是 "+(strItem.length()-2)+" 级标题");
				res = strItem.length()-2;
			}
		}
		return res;
	}
	
	//看是不是提示标签
	public static String checkTips(String str){
		String tipType = "";
		for(int i = 0 ; i < keyWords.length ; i++){
			if(str.startsWith(keyWords[i])){
				//说明就是这个类别的Tip
				tipType = keyWords[i];
			}
		}
		if(tipType.equals("")){
			return "";
		}else{
			System.out.println(str+"    ===type===>    "+tipType);
			return tipType;
		}
	}
	
	//显示结果
	public static void showTree(List<MyTree> list){
		//tree的结构List<MyTree(MyTree parent , List<MyTree> childList , ResBean<String content,List<String> type> data)>
		for(int i = 0 ; i < list.size() ; i++){
			if(!list.get(i).getData().getContent().equals("")){
				System.out.print(list.get(i).getData().getContent()+"     ===type===>     ");
				for(String str : list.get(i).getData().getType()){
					System.out.print(str+" ");
				}
				System.out.println();
			}
		}
	}
	
	//将结果写入txt中
	public static void writeToTxt(List<ResBean> resList){
		try{
			for(ResBean res : resList){
				if(!res.getContent().equals("") && res.getContent() != null){
					System.out.print(res.getContent() + "  ==>  ");
					File file = new File("C:\\Users\\cgych\\Desktop\\暑假计划表1.txt");
					if(file.exists()){
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file, true);
		            BufferedWriter bw = new BufferedWriter(fw);
		            bw.write(res.getContent()+"===>");
					for(String type : res.getType()){
						System.out.print(type + " ");
						bw.write(type+" ");
					}
					bw.write("\n");
		            bw.close();
		            fw.close();
				}
				System.out.println();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		//System.out.println(new Compiler().reverse("a"));
		typeMap.put('*', "bold");
		typeMap.put('_', "italic");
		AnalyseContent(new File("C:\\Users\\cgych\\Desktop\\暑假计划表.txt"));
		System.out.println("/n/n/n===================RES===================");
		//showTree(tree);
		for(ResBean res : resList){
			if(!res.getContent().equals("")){
				System.out.print(res.getContent() + "  ==>  ");
			}
			for(String type : res.getType()){
				System.out.print(type + "  ");
			}
			System.out.println();
		}
		writeToTxt(resList);
	}
}