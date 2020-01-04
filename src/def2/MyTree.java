package def2;

import java.util.ArrayList;
import java.util.List;

public class MyTree {
	public MyTree parent;
	public List<MyTree> childList = new ArrayList<MyTree>();
	public ResBean data;
	public MyTree getParent() {
		return parent;
	}
	public void setParent(MyTree parent) {
		this.parent = parent;
	}
	public ResBean getData() {
		return data;
	}
	public void setData(ResBean data) {
		this.data = data;
	}
	public List<MyTree> getChildList() {
		return childList;
	}
	public void setChildList(List<MyTree> childList) {
		this.childList = childList;
	}
	
	
}
