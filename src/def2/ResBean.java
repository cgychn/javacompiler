package def2;

import java.util.ArrayList;
import java.util.List;

public class ResBean {
	String content = "";
	List<String> types = new ArrayList<String>();
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getType() {
		return types;
	}
	public void setType(List<String> type) {
		this.types = types;
	}
}
