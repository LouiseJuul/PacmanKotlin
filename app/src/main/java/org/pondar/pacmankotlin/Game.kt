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

open class Game(private var context: Context, view: TextView) {
    private var pointsView: TextView = view
    private var points: Int = 0

    var pacBitmap: Bitmap //pacman bitmap
    var enemyBitmap: Bitmap //enemy bitmap
    var golBitmap: Bitmap //golcoins bitmap
    var pacx: Int = 0
    var pacy: Int = 0
    var enemyx: Int = 400
    var enemyy: Int = 0
    var enemyAlive = true
    var direction = 0
    var directionEnemy = 1
    var counter: Int = 0
    var timer: Int = 30
    var running: Boolean = false

    var coinsInitialized = false  //did we initialize the coins?
    var enemiesInitialized = false

    var coins = ArrayList<GoldCoin>()//arraylist of goldcoins
    var enemies = ArrayList<Enemy>() //arraylist of enymies

    private var gameView: GameView? = null //reference to the gameview
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen

    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
    }

    init {
        golBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.rsz_coin)
    }

    init {
        enemyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.gost)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    fun initializeEnemy() {
        enemies.add(Enemy(false, true, 900, 900))
        enemiesInitialized = true
    }

    //initialize the arrylist of goldcoins and add coins
    fun initializeGoldcoins() {
        coins.clear()
        coins.add(GoldCoin(false, 10, 10))
        coins.add(GoldCoin(false, 10, 900))
        coins.add(GoldCoin(false, 10, 1400))
        coins.add(GoldCoin(false, 900, 900))
        coins.add(GoldCoin(false, 450, 450))
        coins.add(GoldCoin(false, 450, 900))
        coins.add(GoldCoin(false, 450, 1400))
        coins.add(GoldCoin(false, 900, 10))
        coins.add(GoldCoin(false, 900, 450))
        coins.add(GoldCoin(false, 900, 1400))
        coinsInitialized = true
    }


    fun newGame() {
        pacx = 50 //starting coordinates
        pacy = 400 //starting coordinates
        timer = 30
        counter = 0
        running = false
        coinsInitialized = false
        points = 0 //reset the points
        // initializeGoldcoins() // to make sure coins are
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen

    }

    fun gameOver() {
        if (timer == 1) {
            timer = 0
            running = false
            counter = 0
            Toast.makeText(context, "Time is up, you loose", Toast.LENGTH_LONG).show()
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

    //Move enemy
    fun moveEnemy(pixels: Int) {
        //still within our boundaries?
        if (directionEnemy == 2) { //direction is down
            if (enemyy + pixels + enemyBitmap.height < h) {
                enemyy = enemyy + pixels
                directionEnemy = 2
            } else {
                directionEnemy = 1
            }

        } else { // direction is up
            if (enemyy - pixels > 0) {
                enemyy = enemyy - pixels
                directionEnemy = 1
            } else {

                directionEnemy = 2
            }
        }
    }


    fun distance(pacx: Int, pacy: Int, golx: Int, goly: Int): Double {

        // calculate distance and return it
        var cordinatation = (sqrt(((pacx - golx) * (pacx - golx) + (pacy - goly) * (pacy - goly)).toDouble()))

        return cordinatation;
    }

    fun distanceEnemy(pacx: Int, pacy: Int, enemyx: Int, enemyy: Int): Double {

        // calculate distance and return it
        var cordinatation = (sqrt(((pacx - enemyx) * (pacx - enemyx) + (pacy - enemyy) * (pacy - enemyy)).toDouble()))

        return cordinatation;
    }


    //TODO check if the pacman touches a gold coin
//and if yes, then update the neccesseary data
//for the gold coins and the points
//so you need to go through the arraylist of goldcoins and
//check each of them for a collision with the pacman
    fun doCollisionCheck() {

        enemies.forEach {
            if (distanceEnemy(pacx, pacy, enemyx, enemyy) < 180) {
                running = false
                Toast.makeText(context, "You died", Toast.LENGTH_SHORT).show()
            }
        }

        coins.forEach {
            if (distance(pacx, pacy, it.golx, it.goly) < 200) {
                if (it.taken == false) {
                    points++
                    it.taken = true
                    pointsView.text = "${context.resources.getString(R.string.points)} $points"

                    //check if all 10 goldcoins are collected = win
                    if (points === 10) {
                        running = false
                        Toast.makeText(context, "You won the game", Toast.LENGTH_SHORT).show()
                    }

                    Log.d("points", points.toString())
                }
            }
        }
    }
}
