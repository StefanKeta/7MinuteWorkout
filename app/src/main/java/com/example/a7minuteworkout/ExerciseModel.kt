package com.example.a7minuteworkout

class ExerciseModel (
    private var id:Int,
    private var name:String,
    private var image:Int,
    private var isCompleted:Boolean,
    private var isSelected:Boolean){

    fun getID():Int{
        return this.id
    }

    fun setID(id:Int){
        this.id=id
    }

    fun getName():String{
        return this.name
    }

    fun setName(name: String){
        this.name=name
    }

    fun getImage():Int{
        return this.image
    }

    fun setImage(image:Int){
        this.image=image
    }

    fun isCompleted():Boolean{
        return this.isCompleted
    }

    fun setCompleted(isCompleted: Boolean){
        this.isCompleted=isCompleted
    }

    fun isSelected():Boolean{
        return this.isSelected
    }

    fun setSelected(isSelected:Boolean){
        this.isSelected=isSelected
    }


}