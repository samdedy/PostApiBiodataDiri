package id.sam.postapibiodatadiri.service;
import id.sam.postapibiodatadiri.ListBiodataActivity;
import id.sam.postapibiodatadiri.model.DeleteData;
import id.sam.postapibiodatadiri.model.UpdateData;
import id.sam.postapibiodatadiri.model.getall.ListBiodata;
import id.sam.postapibiodatadiri.model.login.Authentication;
import id.sam.postapibiodatadiri.model.Result;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterfacesRest {

  /*  @GET("weather")
    Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String appid);

    @GET("weather")
    Call<WeatherModel> getWeatherBasedLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid);

    @GET("forecast")
    Call<ForcastWeatherModel> getForecastBasedLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String appid);
*/

/*
    @GET("users")
    Call<com.juaracoding.absensi.model.reqres.User> getUserReqres(@Query("page") String page);

    @GET("posts")
    Call<List<Post>> getPost();

    @GET("comments")
    Call<List<Comment>> getComment(@Query("postId") String postId);*/




/*
    @GET("api/user_mobile/all")
    Call<Authentication> getUser(@Query("username_k") String user);

   @GET("api/komplain/ticket")
   Call<KomplainModel> getTicket(@Query("username") String username);

    @FormUrlEncoded
    @POST("api/komplain/add")
    Call<UpdateKomplain> addKomplain(@Field("username_komplain") String username, @Field("kode_edc") String kode_edc, @Field("masalah") String masalah, @Field("notes_komplain") String notes);

    @GET("api/edc_problem/all")
    Call<MasalahEdcModel> getEDCProblem();
*//*
    @GET("api/dataorder/all")
    Call<ModelOrder> getOrder(@Query("username") String user);



/*
            @Part MultipartBody.Part img1,
           @Part MultipartBody.Part img2,
           @Part MultipartBody.Part img3,
 */

   @FormUrlEncoded
   @POST("user/login")
   Call<Authentication> getAuthentication(@Field("username") String username,
                                          @Field("password") String password);

   @GET("biodata/all")
   Call<ListBiodata> getListBiodata();

   @Multipart
   @POST("biodata/add")
   Call<Result> addData(
           @Part("nama") RequestBody nama,
           @Part("alamat") RequestBody alamat,
           @Part("telepon") RequestBody telepon,
           @Part("lat") RequestBody lat,
           @Part("lon") RequestBody lon,
           @Part MultipartBody.Part img1
   );

   @Multipart
   @POST("Biodata/update")
   Call<UpdateData> updateData(
           @Part("id") RequestBody id,
           @Part("nama") RequestBody nama,
           @Part("alamat") RequestBody alamat,
           @Part("telepon") RequestBody telepon,
           @Part("lat") RequestBody lat,
           @Part("lon") RequestBody lon,
           @Part MultipartBody.Part img1
   );

   @Multipart
   @POST("Biodata/update")
   Call<UpdateData> updateDatEnoughPhoto(
           @Part("id") RequestBody id,
           @Part("nama") RequestBody nama,
           @Part("alamat") RequestBody alamat,
           @Part("telepon") RequestBody telepon,
           @Part("lat") RequestBody lat,
           @Part("lon") RequestBody lon,
           @Part("photo") RequestBody photo
   );

   @FormUrlEncoded
   @POST("Biodata/delete")
   Call<DeleteData> deleteData(@Field("id") Integer id);

/*
   @Multipart
   @POST("api/komplain/update")
   Call<UpdateKomplain> sendImage(
           @Part("id") RequestBody id,
           @Part("username_penanganan") RequestBody username_komplain,
           @Part("hasil_penanganan") RequestBody masalah,
           @Part("tanggal_penanganan") RequestBody kode_edc,
           @Part("notes_penanganan") RequestBody notes_komplain,
           @Part("status") RequestBody status,
           @Part MultipartBody.Part img1


   );*/

}
