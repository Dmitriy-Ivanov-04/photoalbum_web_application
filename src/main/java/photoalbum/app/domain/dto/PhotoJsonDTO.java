package photoalbum.app.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoJsonDTO {
	
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
