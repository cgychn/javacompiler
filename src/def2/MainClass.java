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
//	//5-7һ��  11-14һ��   22-26һ��
//	for(int i = 0 ; i < boldAndItalicLeftIndex.size() ; i++){
//		//ȡ������������
//		String content = str.substring(boldAndItalicLeftIndex.get(i),boldAndItalicRightIndex.get(i));
//		//�������δ�
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
//				//����ǰ����û��ת���ַ�
//				//˵�����_���Ǳ�ʶ��
//				System.out.println("its not real _");
//			}
//			else{
//				//_�Ǳ�ʶ��
//				System.out.println("its real _");
//				indexs.add(i);
//			}
//		}
//	}
//	//��ż��
//	if(indexs.size() % 2 == 0){
//		//˵��û���﷨����
//		//������������ȡ������������б����
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
////ƥ������
//public int checkTypeface(String str , String type , String identifier){
//	//ȥ�������ʶ��
//	if(type.startsWith("title")){
//		int titleType = Integer.parseInt(type.replaceAll("title", ""));
//		//�������ʶ���ڵĶ����ó���
//		str = str.replaceFirst(identifier, "");
//		str = reverse(reverse(str).replaceFirst(identifier, ""));
//	}
//	//��ʲô����
//	List<Integer> indexs = new ArrayList<Integer>();
//	for(int i = 0 ; i < str.length() ; i++){
//		if(str.charAt(i) == '*'){
//			if(i != 0 && str.charAt(i-1) == '\\'){
//				//����ǰ����û��ת���ַ�
//				//˵�����*���Ǳ�ʶ��
//				System.out.println("its not real *");
//			}
//			else{
//				//*�Ǳ�ʶ��
//				System.out.println("its real *");
//				indexs.add(i);
//			}
//		}
//	}
//	//ִ����for֮�����һ����ʶ�����±��б�
//	//���*�ĸ�����ż����˵����һ�ж�û�в�������һ�в���Ҫ������һ�м���ƥ��
//	if(indexs.size() % 2 == 0){
//		//˵����Ȼû�ϲ���
//		if(leftFlag == 1){
//			//�ͽ���һ���±��ó���,���±�0���
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
//		System.out.println("δ����");
//		leftFlag = 0;
//		//�������е�Ԫ���������
//		int i = 0;
//		while(i < indexs.size()-1){
//			int boldStartIndex = indexs.get(i);
//			int boldEndIndex = indexs.get(i+1);
//			i = i + 2;
//			//���������û����б�����﷨
//			
//		}
//	}
//	
//	
//	else{
//		if(leftFlag == 1){
//			//˵������ܺϲ�����
//			if(indexs.size() >= 3){
//				//�Ȱ�ǰ���ƥ���,�����һ��ɾ��
//				int lastIndex = indexs.get(indexs.size()-1);
//				indexs.remove(indexs.size()-1);
//				int i = 0;
//				while(i < indexs.size()-1){
//					int boldStartIndex = indexs.get(i);
//					int boldEndIndex = indexs.get(i+1);
//					i = i + 2;
//					//���������û����б�����﷨
//					
//				}
//			}
//		}
//		System.out.println("����");
//		leftFlag = 1;
//		
//		
//	}
//	//��֮��������һ����ƥ��
//	for(int i : indexs){
//		
//	}
//	return -1;
//}
//

//
//