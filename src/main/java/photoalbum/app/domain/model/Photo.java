package photoalbum.app.domain.model;

import java.sql.Date;

public class Photo {
	
	private Long id;
	private Long profile_id;
	private Long album_id;
	private float rating;
	private String description;
	private Date date;
	private int mark_counter;
	private String link_photo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}
	public Long getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(Long album_id) {
		this.album_id = album_id;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMark_counter() {
		return mark_counter;
	}
	public void setMark_counter(int mark_counter) {
		this.mark_counter = mark_counter;
	}
	public String getLink_photo() {
		return link_photo;
	}
	public void setLink_photo(String link_photo) {
		this.link_photo = link_photo;
	}
	
	@Override
	public String toString() {
		return "Profile [id=" + id + ", profile_id=" + album_id + ", rating=" + rating + ", description=" + description + 
				", date" + date + ", mark_counter" + mark_counter + ", link_photo" + link_photo + "]";
	}
}