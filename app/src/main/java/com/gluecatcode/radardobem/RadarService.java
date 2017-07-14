package com.gluecatcode.radardobem;

import com.gluecatcode.radardobem.entities.Entidade;
import com.gluecatcode.radardobem.entities.Localizacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by caio on 13/04/17.
 */

public interface RadarService {

    @FormUrlEncoded
    @POST("load.php")
    Call<Entidade[]> findEntidades(@Field("lat") Float lat, @Field("lng") Float lng);


}
