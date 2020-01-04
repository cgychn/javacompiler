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
	
	//��ű���ƥ���
	public static String[] titleHead = {"= ","== ","=== ","==== ","===== "}; 
	//��ŷ�����ɺ�Ľ��
	public static List<ResBean> resList = new ArrayList<ResBean>();
	//������� 1 ��ʾ���� 0 ��ʾû����
	public static int leftFlag = 0;
	//�﷨������
	public static List<MyTree> tree = new ArrayList<MyTree>();
	//������ͽ�����ֵ��
	public static Map<Character,String> typeMap = new HashMap<Character,String>();
	//��¼�ݹ�Ĳ���,ÿִ��һ�εݹ�ͻ��1
	public static int step = -1;
	//��ǹؼ���
	public static String[] keyWords = {"NOTE:","TIP:","WARNNING:","IMPORTANT:","CAUTION:"};
	//��¼���ú�
	public static int refIndex = -1;
	//��¼ȫ������������
	public static int refCount = 0;
	
	//���ļ���������,��������
	public static void AnalyseContent(File file){
		try {
			String strTemp = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//���ļ����ж������
			while((strTemp = reader.readLine()) != null){
				//�Ǳ���
				strTemp = strTemp.replaceAll("\n", "");
				int type = -1;
				String tipType = "";
				if((type = checkTitleType(strTemp)) != -1){
					ResBean resBean = new ResBean();
					//��������
					resBean.setContent(strTemp.replace(titleHead[type], "").replace(reverse(titleHead[type]), ""));
					//��������
					resBean.getType().add("title"+type);
					resList.add(resBean);
					//��ӻ��з�
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//����ʾ��
				else if(!(tipType = checkTips(strTemp)).equals("")){
					ResBean resBean = new ResBean();
					resBean.setContent(strTemp);
					resBean.getType().add(tipType);
					resList.add(resBean);
					//��ӻ��з�
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//ͼƬ
				else if(strTemp.trim().startsWith("image::")){
					//ͼƬ·������С
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
					//��ӻ��з�
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				//����
				else if(strTemp.trim().startsWith("-[[[")){
					++refIndex;
					ResBean resBean = new ResBean();
					resBean.setContent(strTemp);
					//���ǵڼ�����
					resBean.getType().add("REFLINE" + refIndex);
					resList.add(resBean);
					//��ӻ��з�
					ResBean resN = new ResBean();
					resN.setContent("\n");
					resList.add(resN);
				}
				else{
					//����
					distincRef(strTemp);
					//��ӻ��з�
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
	
	//�����﷨������
	public static void buildParsingTree(ResBean resBean , char sign , MyTree parent){
		//MyTree myTree = new MyTree();
		//���parent�ǿյľͳ�ʼ��
		//���ݹ�Ĳ�����1
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
					//����ǰ����û��ת���ַ�
					//˵��������Ǳ�ʶ��
					System.out.println("its not real "+sign);
				}
				else{
					//*�Ǳ�ʶ��
					System.out.println("its real "+sign);
					indexs.add(i);
				}
			}
		}
		//���������û�����﷨�ı�ʶ�������˳��㷨
		if(indexs.size() == 0){
			//˵��������Ҷ�ӽڵ�
			tree.add(parent);
			System.out.println(sign+"nonono");
			return;
		}
		//indexs�Ǳ�ʶ���±�ļ���
		for(int i = 0 ; i < indexs.size() ; i++){
			//�߽紦����һ����ʶ����ǰ�������Ҫ��һ�η��������һ����ʶ��Ҫ�ͺ����������һ���﷨����
			if(i == 0){
				String tempStr = strContent.substring(0,indexs.get(i));
				System.out.println("first: "+tempStr);
				ResBean tempRes = new ResBean();
				tempRes.setContent(tempStr);
				//�����������
				//��Ӹ��ڵ������
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//Ϊparent����ӽڵ�
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//ִ�еݹ飬������һ���﷨
				buildParsingTree(myTree.getData(), '_', myTree);
			}
			// i �� i-1  ��indexs�е�Ԫ�ظ���һ����ż�������Ե�0��1  2��3  4��5 ��������һ�ԣ� 0��1֮����ƥ�䵽�ģ�1��2֮����ûƥ�䵽��
			if(i%2 != 0){
				String tempStr = strContent.substring(indexs.get(i-1) + 1 , indexs.get(i));
				System.out.println("have type "+typeMap.get(sign)+" :"+tempStr);
				ResBean tempRes = new ResBean();
				//��������
				tempRes.setContent(tempStr);
				//�������
				tempRes.getType().add(typeMap.get(sign));
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//Ϊparent����ӽڵ�
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//ִ�еݹ�
				buildParsingTree(myTree.getData(), '_', myTree);
				if(i != indexs.size() - 1){
					String tempStr1 = strContent.substring(indexs.get(i) + 1 , indexs.get(i+1));
					System.out.println("no type :"+tempStr1);
					ResBean tempRes1 = new ResBean();
					tempRes1.setContent(tempStr1);
					//����������ͣ�ӦΪ������ӽ�ȥ���ı���Ҫ�����´��﷨����
					//��Ӹ��ڵ������
					if(parent != null){
						for(String type : parent.getData().getType()){
							tempRes1.getType().add(type);
						}
					}
					//Ϊparent����ӽڵ�
					MyTree myTree1 = new MyTree();
					myTree1.setData(tempRes1);
					myTree1.setParent(parent);
					if(parent != null){
						parent.getChildList().add(myTree1);
					}
					//ִ�еݹ�
					buildParsingTree(myTree1.getData(), '_', myTree1);
				}
			}
			if(i == indexs.size() - 1){
				String tempStr = strContent.substring(indexs.get(i) + 1,strContent.length());
				System.out.println("last: "+tempStr);
				ResBean tempRes = new ResBean();
				tempRes.setContent(tempStr);
				//�����������
				//��Ӹ��ڵ������
				if(parent != null){
					for(String type : parent.getData().getType()){
						tempRes.getType().add(type);
					}
				}
				//Ϊparent����ӽڵ�
				MyTree myTree = new MyTree();
				myTree.setData(tempRes);
				myTree.setParent(parent);
				if(parent != null){
					parent.getChildList().add(myTree);
				}
				//ִ�еݹ�
				buildParsingTree(myTree.getData(), '_', myTree);
			}
		}
	}
	
	//ʶ����������
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
		//for����֮�� list���������λ�±�Ԫ�ؾ�����������
		for(int i = 0 ; i < list.size() ; i++){
			if(i % 2 != 0){
				//������
				System.out.println(list.get(i)+" ==> ����");
				ResBean resBean = new ResBean();
				resBean.setContent(list.get(i));
				resBean.getType().add("REF"+refCount);
				++refCount;
				resList.add(resBean);
			}
			//��listż��λ�ϵ�Ԫ���ǻ�û�б�������Ԫ��
			else{
				System.out.println(list.get(i)+" ==> ��������");
				ResBean resBean = new ResBean();
				resBean.setContent(list.get(i));
				//�����﷨������
				buildParsingTree(resBean, '*', null);
				//��tree�е�Ԫ�ؼӵ�resList��
				for(MyTree myTree: tree){
					resList.add(myTree.getData());
				}
				//��ʼ��tree
				tree = new ArrayList<MyTree>();
			}
		}
		
		
	}
	
	//��ת�ַ���
	public static String reverse(String str){
		String strRes = "";
		for(int i = str.length()-1 ; i >= 0 ; i--){
			strRes+= str.charAt(i);
		}
		return strRes;
	}
	
	//����ʲô����
	public static int checkTitleType(String strLine){
		int res = -1;
		//����ͷ�ͽ�β
		for(String strItem : titleHead){
			//˵���Ǳ���
			if(strLine.startsWith(strItem) && strLine.endsWith(reverse(strItem))){
				System.out.println(strLine+" �� "+(strItem.length()-2)+" ������");
				res = strItem.length()-2;
			}
		}
		return res;
	}
	
	//���ǲ�����ʾ��ǩ
	public static String checkTips(String str){
		String tipType = "";
		for(int i = 0 ; i < keyWords.length ; i++){
			if(str.startsWith(keyWords[i])){
				//˵�������������Tip
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
	
	//��ʾ���
	public static void showTree(List<MyTree> list){
		//tree�ĽṹList<MyTree(MyTree parent , List<MyTree> childList , ResBean<String content,List<String> type> data)>
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
	
	//�����д��txt��
	public static void writeToTxt(List<ResBean> resList){
		try{
			for(ResBean res : resList){
				if(!res.getContent().equals("") && res.getContent() != null){
					System.out.print(res.getContent() + "  ==>  ");
					File file = new File("C:\\Users\\cgych\\Desktop\\��ټƻ���1.txt");
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
		AnalyseContent(new File("C:\\Users\\cgych\\Desktop\\��ټƻ���.txt"));
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