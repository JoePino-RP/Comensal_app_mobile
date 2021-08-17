package com.caanvi.comensal_app_mobile.Login.Modals

import java.io.Serializable

data class Platos (var id_platos :String,
                   var nombre_plato : String,
                   var descripcion_plato: String,
                   var precio_plato: String,
                   var imagen_plato: String,
                   var categoria: String,
                   var rest: String,
                  ) : Serializable