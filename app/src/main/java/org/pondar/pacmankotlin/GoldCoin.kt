package org.pondar.pacmankotlin


//Here you need to fill out what should be in a GoldCoin and what should the constructor be
open class GoldCoin(val taken: Boolean, val golx: Int, val goly: Int) {

  //constructor(taken: Boolean) : this(taken, x = 0, y = 1)

    fun isTaken() : Boolean {
        if (taken === true)
            return true
        else
            return false
    }
}