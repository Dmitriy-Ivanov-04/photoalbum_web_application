package photoalbum.app.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProfileJsonDTO {
	private String nickname;
	private String fullName;
	private String linkAvatar;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLinkAvatar() {
		return linkAvatar;
	}
	public void setLinkAvatar(String linkAvatar) {
		this.linkAvatar = linkAvatar;
	}
}
