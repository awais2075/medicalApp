package application.medical.interface_;

import java.util.HashMap;

import application.medical.retrofit.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("json")
    Call<Example> getNearbyLocations(@QueryMap HashMap<String,String> hashMap);
}
