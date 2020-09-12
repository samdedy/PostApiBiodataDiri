
package id.sam.postapibiodatadiri.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateData implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<UpdateData> CREATOR = new Creator<UpdateData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public UpdateData createFromParcel(Parcel in) {
            return new UpdateData(in);
        }

        public UpdateData[] newArray(int size) {
            return (new UpdateData[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1024037261249869125L;

    protected UpdateData(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdateData() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public UpdateData(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
