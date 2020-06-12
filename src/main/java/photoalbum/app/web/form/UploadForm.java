package photoalbum.app.web.form;

import javax.validation.constraints.NotNull;

public class UploadForm {
    @NotNull
    private String tags;

    @NotNull
    private String description;

    @NotNull
    private Long albumId;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
