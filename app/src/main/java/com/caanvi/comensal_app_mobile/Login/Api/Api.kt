package com.caanvi.comensal_app_mobile.Login.Api

import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.Login.Modals.registro
import com.caanvi.comensal_app_mobile.Login.Modals.restorePassword
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    fun useRegistro(
        @Field ("nombre") nombre:String,
        @Field ("apellido") apellido:String,
        @Field ("contacto") contacto:String,
        @Field ("email") email:String,
        @Field ("password") password:String
    ): Call<registro>


}

