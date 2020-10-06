package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import kotlin.math.sqrt
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 *
 * This class should contain all your game logic
 */

open class Game(private var context: Context,view: TextView) {


    //bitmap of the pacman
    var pacBitmap: Bitmap
    var pacx: Int = 0
    var pacy: Int = 0
    var direction = 0
    var counter : Int = 0
    var timer: Int = 60
    var Timer: Timer = Timer()
    //you should put the running in the game class
    private var running: Boolean = false

    private var pointsView: TextView = view
    private var points: Int = 0


    ///golcoins bitmap
    var golBitmap: Bitmap

    ///////LOU direction variable
    var directionUp: Int = 1
    var directionDown: Int = 2
    var directionLeft: Int = 3
    var directionRight:Int = 4

    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>() //creating an empty arraylist


    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen


    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
    }

    init {
        golBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rsz_coin)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //initialize the array list with coins.
        ///add coins
        coins.add(GoldCoin(false, 10, 10))
        coins.add(GoldCoin(false, 10, 900))
        coins.add(GoldCoin(false, 10, 1400))
        coins.add(GoldCoin(false, 450, 10))
        coins.add(GoldCoin(false, 450, 450))
        coins.add(GoldCoin(false, 450, 900))
        coins.add(GoldCoin(false, 450, 1400))
        coins.add(GoldCoin(false, 900, 10))
        coins.add(GoldCoin(false, 900, 900))
        coins.add(GoldCoin(false, 900, 1400))
        coinsInitialized = true
    }

    fun newGame() {
        pacx = 50
        pacy = 400 //starting coordinates
        timer = 60
        counter = 0
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        //timer.text = getString(R.string.timer,counter)
        gameView?.invalidate() //redraw screen
    }

    fun gameOver(){
        if (timer===0)
        {
            Toast.makeText(context, "You loose", Toast.LENGTH_LONG).show()
            Log.d("dead",timer.toString())
        }
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx + pixels
            doCollisionCheck()
            direction = 4
            gameView!!.invalidate()
        }
    }

    fun movePacmanLeft(pixels: Int) {
        //still within our boundaries?
        if (pacx - pixels > 0) {
            pacx = pacx - pixels
            doCollisionCheck()
            direction = 3
            gameView!!.invalidate()
        }
    }

    fun movePacmanUp(pixels: Int) {
        //still within our boundaries?
        if (pacy - pixels > 0) {
            pacy = pacy - pixels
            doCollisionCheck()
            direction = 1
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        //still within our boundaries?
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            doCollisionCheck()
            direction = 2
            gameView!!.invalidate()
        }
    }

    fun distance(pacx: Int, pacy: Int, golx: Int, goly: Int): Double {

        // calculate distance and return it
        var cordinatation = (sqrt(((pacx - golx) * (pacx - golx) + (pacy - goly) * (pacy - goly)).toDouble()))

        return cordinatation;
    }


    /*

    fun win(){
        if (points ===10){
         timer(conter??) =< 60
         running = false
        }
    }
    */



    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {
        coins.forEach {
            if (distance(pacx, pacy, it.golx, it.goly) < 220) {
                if (it.taken===false){
                    points++
                    it.taken=true
                    pointsView.text = "${context.resources.getString(R.string.points)} $points"
                }

                //check if all 10 goldcoins are collected = win
                if (points ===10) {
                    Toast.makeText(context, "You won the game", Toast.LENGTH_LONG).show()
                    newGame()
                }

                Log.d("points", points.toString())
            }
        }
    }
}