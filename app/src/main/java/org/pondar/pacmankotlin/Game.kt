package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import java.util.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

        private var pointsView: TextView = view
        private var points : Int = 0
        //bitmap of the pacman
        var pacBitmap: Bitmap
        var pacx: Int = 0
        var pacy: Int = 0
        ///////////////////////LOU adding golcoins bitmap
        var golBitmap: Bitmap



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
    ////////////////////LOU trying to add drawable to
    init {
        golBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rsz_coin)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    ////////////////LOU added var taken

    //TODO initialize goldcoins also here
    fun initializeGoldcoins()
    {
        //DO Stuff to initialize the array list with coins.
        ///////////LOU adding coins
        coins.add (GoldCoin(false,10,10))
        coins.add (GoldCoin(false, 10, 400))
        coins.add (GoldCoin(false, 10,1100))
        coins.add (GoldCoin(false, 400, 1000))
        coins.add (GoldCoin(false, 400,10))
        coins.add (GoldCoin(false, 400,650))
        coins.add (GoldCoin(false, 900,10))
        coins.add (GoldCoin(false, 1100, 1500))
        coins.add (GoldCoin(false, 50,1500))
        coins.add (GoldCoin(false, 400,1500))

        coinsInitialized = true
    }


    fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
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
            gameView!!.invalidate()
        }
    }

    fun movePacmanLeft(pixels: Int) {
        //still within our boundaries?
        if (pacx - pixels > 0) {
            pacx = pacx - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanUp(pixels: Int) {
        //still within our boundaries?
        if (pacy + pixels > 0) {
            pacy = pacy - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        //still within our boundaries?
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {

    }


}