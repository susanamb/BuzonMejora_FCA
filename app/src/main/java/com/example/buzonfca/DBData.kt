package com.example.buzonfca

//DATOS DE LA QUEJA/SUGERENCIA EN LA BASE DE DATOS
data class DBData( var Categoria : String ?= null,var Asunto : String ?= null,var Status : String ?= null, var Comentario : String ?= null)