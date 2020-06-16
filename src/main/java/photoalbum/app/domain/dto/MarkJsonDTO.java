package photoalbum.app.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarkJsonDTO {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
