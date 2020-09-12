
package id.sam.postapibiodatadiri.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registrasi implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<Registrasi> CREATOR = new Creator<Registrasi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Registrasi createFromParcel(Parcel in) {
            return new Registrasi(in);
        }

        public Registrasi[] newArray(int size) {
            return (new Registrasi[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3432988034822085770L;

    protected Registrasi(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Registrasi() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public Registrasi(Boolean status, String message) {
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
