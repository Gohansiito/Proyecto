package com.example.pfg.Musica
class Cancion {
    private val imagen:Int
    private var artista:String
    private var nombre:String
    private var cancion:Int

    constructor(imagen:Int,artista:String,nombre:String,cancion:Int){
        this.imagen=imagen
        this.artista=artista
        this.nombre=nombre
        this.cancion=cancion
    }
    fun getImagen():Int{
        return this.imagen
    }
    fun getArtista():String{
        return this.artista
    }
    fun getNombre():String{
        return this.nombre
    }
    fun getCancion():Int{
        return this.cancion
    }
}


