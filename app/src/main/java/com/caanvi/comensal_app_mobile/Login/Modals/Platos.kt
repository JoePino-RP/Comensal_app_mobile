package com.caanvi.comensal_app_mobile.Login.Modals

import java.io.Serializable

data class Platos (var id_pla :String,
                   var nombre_pla : String,
                   var imagen_pla: String,
                   var precio_pla: String,
                   var descripcion_pla: String,
                  ) : Serializable