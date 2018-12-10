package udacity.android.com.bakingapp.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import udacity.android.com.bakingapp.utils.BakingJsonUtils;

// Step of Recipe
public class Step implements Parcelable {

    @JsonProperty(BakingJsonUtils.JSON_STEPS_ID)
    private int id;
    @JsonProperty(BakingJsonUtils.JSON_STEPS_SHORT_DESCRIPTION)
    private String short_description;
    @JsonProperty(BakingJsonUtils.JSON_STEPS_DESCRIPTION)
    private String description;
    @JsonProperty(BakingJsonUtils.JSON_STEPS_VIDEO_URL)
    private String video_url;
    @JsonProperty(BakingJsonUtils.JSON_STEPS_THUMBNAIL_URL)
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

    //region Parcelable Step
    protected Step(Parcel in) {
        id = in.readInt();
        short_description = in.readString();
        description = in.readString();
        video_url = in.readString();
        thumbnail_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(short_description);
        parcel.writeString(description);
        parcel.writeString(video_url);
        parcel.writeString(thumbnail_url);
    }
    //endregion
}
