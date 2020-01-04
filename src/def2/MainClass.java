package def2;

public class MainClass {
	static Compiler compiler = null;
	public static void main(String args[]){
//		compiler = new Compiler();
//		System.out.println("asdasdasdasd*asd&asdasd");
		System.out.println("asdasdasd".split("bbb").length);
	}
}































//public static void checkFacetypes(String str , ResBean resBean1){//  asdsd*_dsadasdasd_*asdswdaw*sdsdsdwda*asdsd_asdwda_sdsdas
//	List<Integer> boldAndItalicLeftIndex = findAll(str,"*_","");        //{5,11,22}
//	List<Integer> boldAndItalicRightIndex = findAll(str,"_*","begin");  //{7,14,26}
//	//5-7一对  11-14一对   22-26一对
//	for(int i = 0 ; i < boldAndItalicLeftIndex.size() ; i++){
//		//取出被修饰内容
//		String content = str.substring(boldAndItalicLeftIndex.get(i),boldAndItalicRightIndex.get(i));
//		//加入修饰词
//		List<String> list = new ArrayList<String>();
//		list.add("bold");
//		list.add("italic");
//		ResBean resBean = new ResBean();
//		resBean.setContent(content);
//		resBean.setType(list);
//	}
//	
//	
//	
//	
//	List<Integer> indexs = new ArrayList<Integer>();
//	for(int i = 0 ; i < str.length() ; i++){
//		if(str.charAt(i) == '_'){
//			if(i != 0 && str.charAt(i-1) == '\\'){
//				//看看前面有没有转义字符
//				//说明这个_不是标识符
//				System.out.println("its not real _");
//			}
//			else{
//				//_是标识符
//				System.out.println("its real _");
//				indexs.add(i);
//			}
//		}
//	}
//	//是偶数
//	if(indexs.size() % 2 == 0){
//		//说明没有语法错误
//		//将里面文字提取出来，加上倾斜属性
//		str = str.substring(1 , str.length() - 1);
//	}
//	else{
//		System.out.println("error");
//	}
//	return tempResList;
//} 
//
//public static List<Integer> findAll(String str,String sign,String returnType){
//	List<Integer> listBegin = new ArrayList<Integer>();
//	List<Integer> listEnd = new ArrayList<Integer>();
//	int flag = 0;
//	for(int i = 0 ; i < str.length() ; i++){
//		if(str.substring(flag,i+1).equals(sign)){
//			flag = i+1;
//			listBegin.add(flag);
//			listEnd.add(i+1);
//		}
//	}
//	if(returnType.equals("begin")){
//		return listBegin;
//	}
//	return listEnd;
//}
//
//
//
//
//
//
////匹配字体
//public int checkTypeface(String str , String type , String identifier){
//	//去除标题标识符
//	if(type.startsWith("title")){
//		int titleType = Integer.parseInt(type.replaceAll("title", ""));
//		//将标题标识符内的东西拿出来
//		str = str.replaceFirst(identifier, "");
//		str = reverse(reverse(str).replaceFirst(identifier, ""));
//	}
//	//看什么字体
//	List<Integer> indexs = new ArrayList<Integer>();
//	for(int i = 0 ; i < str.length() ; i++){
//		if(str.charAt(i) == '*'){
//			if(i != 0 && str.charAt(i-1) == '\\'){
//				//看看前面有没有转义字符
//				//说明这个*不是标识符
//				System.out.println("its not real *");
//			}
//			else{
//				//*是标识符
//				System.out.println("its real *");
//				indexs.add(i);
//			}
//		}
//	}
//	//执行完for之后就有一个标识符的下标列表
//	//如果*的个数是偶数就说明这一行都没有残留，下一行不需要接着这一行继续匹配
//	if(indexs.size() % 2 == 0){
//		//说明依然没合并掉
//		if(leftFlag == 1){
//			//就将第一个下表拿出来,和下标0配对
//			int firstIndex1 = 0;
//			int lastIndex1 = indexs.get(0);
//			ResBean resBean1 = new ResBean();
//			
//			resBean1.setContent(str.substring(firstIndex1,lastIndex1 + 1));
//			resBean1.setType("bold");
//			resList.add(resBean1);
//			
//			indexs.remove(0);
//			
//			
//			
//			int firstIndex2 = indexs.get(indexs.size()-1);
//			int lastIndex2 = str.length()-1;
//			
//			ResBean resBean2 = new ResBean();
//			resBean2.setContent(str.substring(firstIndex2,lastIndex2 + 1));
//			resBean2.setType("bold");
//			resList.add(resBean2);
//			
//			indexs.remove(indexs.size()-1);
//		}
//		System.out.println("未残留");
//		leftFlag = 0;
//		//将集合中的元素两两配对
//		int i = 0;
//		while(i < indexs.size()-1){
//			int boldStartIndex = indexs.get(i);
//			int boldEndIndex = indexs.get(i+1);
//			i = i + 2;
//			//继续检查有没有倾斜字体语法
//			
//		}
//	}
//	
//	
//	else{
//		if(leftFlag == 1){
//			//说明这次能合并掉了
//			if(indexs.size() >= 3){
//				//先把前面的匹配掉,将最后一个删掉
//				int lastIndex = indexs.get(indexs.size()-1);
//				indexs.remove(indexs.size()-1);
//				int i = 0;
//				while(i < indexs.size()-1){
//					int boldStartIndex = indexs.get(i);
//					int boldEndIndex = indexs.get(i+1);
//					i = i + 2;
//					//继续检查有没有倾斜字体语法
//					
//				}
//			}
//		}
//		System.out.println("残留");
//		leftFlag = 1;
//		
//		
//	}
//	//反之继续在下一行中匹配
//	for(int i : indexs){
//		
//	}
//	return -1;
//}
//

//
//