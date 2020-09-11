
package id.sam.postapibiodatadiri.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authentication implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("token")
    @Expose
    private String token;
    public final static Creator<Authentication> CREATOR = new Creator<Authentication>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Authentication createFromParcel(Parcel in) {
            return new Authentication(in);
        }

        public Authentication[] newArray(int size) {
            return (new Authentication[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6317767277453905438L;

    protected Authentication(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.token = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Authentication() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     * @param token
     */
    public Authentication(Boolean status, String message, Data data, String token) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
        dest.writeValue(token);
    }

    public int describeContents() {
        return  0;
    }

}
