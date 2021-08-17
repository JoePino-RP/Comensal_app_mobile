package com.caanvi.comensal_app_mobile.Login.Api

import com.caanvi.comensal_app_mobile.Login.Modals.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("LoginUsuario/loginUsuario.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<loginResponse>


    @FormUrlEncoded
    @POST("LoginUsuario/olvideContrasena.php")
    fun restorePassword(
        @Field ("email") email:String
    ): Call<restorePassword>


    @FormUrlEncoded
    @POST("LoginUsuario/registro.php")
    fun userRegistro(
        @Field ("nombre") nombre:String,
        @Field ("apellido") apellido:String,
        @Field ("contacto") contacto:String,
        @Field ("email") email:String,
        @Field ("password") password:String
    ): Call<registro>

    //
    @FormUrlEncoded
    @POST("Restaurante/consultarRestaurantes.php")
    fun getRestaurant(
        @Field ("latitud") latitud:Double,
        @Field ("longitud") longitud:Double,
    ) : Call<RestaurantResponse>

    //
    @FormUrlEncoded
    @POST("Restaurante/keywordRestaurantes.php")
    fun getRestaurantKeywrod(
        @Field ("latitud") latitud:Double,
        @Field ("longitud") longitud:Double,
        @Field ("keyword") keyword:String,
    ) : Call<RestaurantResponse>


    //
    @FormUrlEncoded
    @POST("Restaurante/listKeywordRestaurantes.php")
    fun searchFilterRestaurant(
        @Field ("latitud") latitud:Double,
        @Field ("longitud") longitud:Double,
        @Field ("listKeywordCarnes") listKeywordCarnes:String,
        @Field ("listKeywordVegetales") listKeywordVegetales:String,
        @Field ("listKeywordPostres") listKeywordPostres:String,
        @Field ("listKeywordTacos")  listKeywordTacos:String,
        @Field ("listKeywordComidaMar")listKeywordComidaMar:String,
        @Field ("listKeywordComidaRapida")listKeywordComidaRapida :String

    ) : Call<RestaurantResponse>

    @FormUrlEncoded
    @POST("Restaurante/buscarRestaurantes.php")
    fun searchRestaurant(
        @Field ("name") name:String
    ) : Call<RestaurantResponse>

    //
    @FormUrlEncoded
    @POST("Favoritos/consultarFavoritos.php")
    fun getFavorite(
        @Field ("id_comensal") id_comensal:String
    ) : Call<RestaurantResponse>

    //
    @FormUrlEncoded
    @POST("Favoritos/agregarFavoritos.php")
    fun addFavorite(
        @Field ("id_comensal") id_comensal:String,
        @Field ("id_restaurante") id_restaurante:String
    ) : Call<FavoriteResponse>

    //
    @FormUrlEncoded
    @POST("Platos/consultarPlatos.php")
    fun getPlatos(
        @Field ("rest") rest: String
    ) : Call<PlatosResponse>


    //
    @GET("Restaurante/consultarCategoriasComida.php")
    fun getCategorias() : Call<CategoriasResponse>

}

