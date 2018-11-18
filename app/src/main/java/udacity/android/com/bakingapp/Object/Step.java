package udacity.android.com.bakingapp.Object;

// Step of Recipe
public class Step {

    private int id;
    private String short_description;
    private String description;
    private String video_url;
    private String thumbnail_url;

    public Step() {
        id = 0;
        short_description = "";
        description = "";
        video_url = "";
        thumbnail_url = "";
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", short_description='" + short_description + '\'' +
                ", description='" + description + '\'' +
                ", video_url='" + video_url + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                '}';
    }

    //region GET and SET
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
    //endregion
}
