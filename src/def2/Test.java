package def2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	static Map<String,String> map = new HashMap<String,String>();
	static List<MyTree> resList = new ArrayList<MyTree>();
	public static void main(String args[]){
		List<MyTree> list = new ArrayList<MyTree>();
		String str = "123*4567_888888_99999*11111*6666_33_22*000000";//asd   qweas_dasdasd_asdas  dasd  asd_as_das  dasdasd
		map.put("*", "bold");
		map.put("_", "xie");
		compile(str , "*" , 0 , null);
		for(MyTree node : resList){
			if(node.getChildList().size() == 0){
				System.out.print(node.getData().getContent()+"  type:  ");
				for(int i = 0 ; i < node.getData().getType().size() ; i++){
					System.out.print(node.getData().getType().get(i)+" ");
				}
				System.out.println("");
			}			
		}
		
	}
	public static void compile(String str , String sign , int flag , MyTree parent){
		String currentStr = str;
		String[] strArr = currentStr.split("\\"+sign);
		if(strArr.length == 1 && (currentStr.indexOf('*') == -1) && (currentStr.indexOf('_') == -1)){
			return;
		}
		for(int i = 0 ; i < strArr.length ; i++){
			MyTree node = new MyTree();
			
			///////////////////////////////////////////////////////
			if(i % 2 != 0){
				//被修饰的
				ResBean resBean = new ResBean();
				resBean.setContent(strArr[i]);
				//将属性添加到节点列表
				resBean.getType().add(map.get(sign));
				node.setData(resBean);
				if(parent != null){
					node.setParent(parent);
					for(int index = 0 ; index < parent.getData().getType().size() ; index++){
						resBean.getType().add(parent.getData().getType().get(index));
					}
					parent.getChildList().add(node);
				}
				//递归
				compile(strArr[i] , "_" , 0 , node);
			}
			else{
				//未被修饰的
				ResBean resBean = new ResBean();
				resBean.setContent(strArr[i]);
				node.setData(resBean);
				if(parent != null){
					node.setParent(parent);
					for(int index = 0 ; index < parent.getData().getType().size() ; index++){
						resBean.getType().add(parent.getData().getType().get(index));
					}
					parent.getChildList().add(node);
				}
				compile(strArr[i] , "_" , 0 , node);
			}
			///////////////////////////////////////////////////////////
			
			resList.add(node);
		}
	}
}
