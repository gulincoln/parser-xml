package xmlparser.file.object;
import lombok.Data;

@Data
public class Campo {
	private String value;
	private String type;
	
	public Campo(String value, String type) {
		super();
		this.value = value;
		this.type = type;
	}
	
	
}
