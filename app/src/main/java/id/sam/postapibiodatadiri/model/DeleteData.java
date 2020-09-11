
package id.sam.postapibiodatadiri.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteData implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<DeleteData> CREATOR = new Creator<DeleteData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DeleteData createFromParcel(Parcel in) {
            return new DeleteData(in);
        }

        public DeleteData[] newArray(int size) {
            return (new DeleteData[size]);
        }

    }
    ;
    private final static long serialVersionUID = 9178038459512637711L;

    protected DeleteData(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeleteData() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public DeleteData(Boolean status, String message) {
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
