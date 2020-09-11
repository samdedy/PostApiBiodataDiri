
package id.sam.postapibiodatadiri.model.getall;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("biodata")
    @Expose
    private List<Biodatum> biodata = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2490633789572986918L;

    protected Data(Parcel in) {
        in.readList(this.biodata, (id.sam.postapibiodatadiri.model.getall.Biodatum.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param biodata
     */
    public Data(List<Biodatum> biodata) {
        super();
        this.biodata = biodata;
    }

    public List<Biodatum> getBiodata() {
        return biodata;
    }

    public void setBiodata(List<Biodatum> biodata) {
        this.biodata = biodata;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(biodata);
    }

    public int describeContents() {
        return  0;
    }

}
