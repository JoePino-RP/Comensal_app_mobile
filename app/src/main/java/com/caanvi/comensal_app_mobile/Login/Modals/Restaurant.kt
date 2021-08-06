package com.caanvi.comensal_app_mobile.Login.Modals

import java.io.Serializable

data class Restaurant ( var id_res :String,
                        var nombre_res : String,
                        var imagen_res: String,
                        var precio_res: String,
                        var telefono_res: String,
                        var direccion_res: String,
                        var latitud_res: Double,
                        var longitud_res: Double,
                        var favoritos_res: String,) : Serializable