package com.example.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

public class FlappyBirdClone extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;                 // background image
	Texture bird;                       // bird up
	Texture bird2;                      // bird down
	Texture tubeTop;                    // top tube
	Texture tubeBotm;                   // bottom tube
    Texture gameOverImage;
    Texture playImage;
	float screenWidth;                  // width of the screen
	float screenHeight;                 // height of the screen
	float birdPositionX;                // bird position in X axis
	float birdPositionY;                // bird position in Y axis
	boolean fly;                        // flying state
	float tubeWidth;                    // with of the tube
	boolean gameStarted;                // game state - started or not
	boolean gameOver;                   // game state - over or not
	float fallingHeight;                // height of the falling
	float fallingGravityFactor;         // falling gravity factor
	float tubeGap;				        // gap between top and bottom // const
	float tubeGapOffSets[];				// changing gap between
	float tubePositionX[];
	int numberOfTubes;
	Random rand;
    float min;
	float tubeSpeed;
	float tubeOffset;					// distance
	int gameScore;
	BitmapFont font;
	float birdWidth;                    // width of the bird
	float birdHeight;					//
	float[] topTubeStart;				// in y axis
	float topTubeEnd;					//  ,,
	float bottomTubeStart;				//  ,,
	float[] bottomTubeEnd;				//  ,,
	boolean[] positionCondition;        // tube and bird are in same y axis or not
    float birdXEnd;
    // this should replace with replay button
    boolean tap1;
    boolean tap2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
	    bird = new Texture("bird.png");
	    bird2 = new Texture("bird2.png");
	    tubeTop = new Texture("toptube.png");
	    tubeBotm = new Texture("bottomtube.png");
	    gameOverImage = new Texture("gameover.png");
	    playImage = new Texture("play.png");
	    screenHeight = Gdx.graphics.getHeight();
	    screenWidth = Gdx.graphics.getWidth();
	    birdPositionX = screenWidth/4 - screenWidth/18;
	    birdPositionY = screenHeight/2 - screenWidth/18;
	    fly = true;
	    gameStarted = false;
	    gameOver = false;
	    fallingHeight =  10;
	    fallingGravityFactor = 1;
	    tubeGap = screenHeight/3;
	    tubeWidth = screenWidth/7;
	    tubeSpeed = screenWidth/100;
	    numberOfTubes = 6;
	    rand = new Random();
		tubeOffset = screenWidth/2;
		tubePositionX = new float[numberOfTubes];
		tubeGapOffSets = new float[numberOfTubes];
		topTubeStart = new float[numberOfTubes];
		bottomTubeEnd = new float[numberOfTubes];
		positionCondition = new boolean[numberOfTubes];
		gameScore = 0;
		birdWidth = screenWidth/9;
		birdHeight = screenWidth/11;
		min = (float) -0.5;
		topTubeEnd = screenHeight/2;
		bottomTubeStart = 0;
		birdXEnd = birdPositionX + birdWidth;
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(3);
	    for(int i = 0; i < numberOfTubes; i ++) {
			tubePositionX[i] = screenWidth + i*tubeOffset;
			tubeGapOffSets[i] = tubeGap*(rand.nextFloat()+ min);
			topTubeStart[i] = screenHeight / 2 + tubeGap / 2 + tubeGapOffSets[i];
			bottomTubeEnd[i] = screenHeight / 2 - tubeGap / 2 + tubeGapOffSets[i];
	    }

	    tap1 = false;
	    tap2 = false;

	}

	@Override
	public void render () {
        batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// before start
		if(!gameStarted) {
            if(Gdx.input.justTouched()) {
                gameStarted = true;
            }
			// bird don't fly and fall down
			batch.draw(bird, birdPositionX, birdPositionY ,birdWidth, birdHeight);
            batch.draw(playImage, screenWidth/2 - screenWidth/8, screenHeight/2 - screenHeight/16, screenWidth/4, screenHeight/8);
		}

		// playing
		if(gameStarted && !gameOver) {

			// getting touch inputs
			if(Gdx.input.justTouched()) {
				fallingHeight = -11;
			} else {
				fallingHeight = fallingHeight + fallingGravityFactor;
			}

			// falling down
			if(birdPositionY <= 0) {
				birdPositionY = 0;
			} else {
				birdPositionY = birdPositionY - fallingHeight;
			}

			// flying bird
			if(fly) {
				batch.draw(bird, birdPositionX, birdPositionY ,birdWidth, birdHeight);
				fly = false;
			} else {
				batch.draw(bird2, birdPositionX, birdPositionY,birdWidth, birdHeight);
				fly= true;
			}

			// moving tubes
			for (int i = 0; i < numberOfTubes; i++) {
				if (tubePositionX[i] <= -tubeWidth) {
					tubePositionX[i] = (numberOfTubes) * tubeOffset - tubeWidth;
					gameScore = gameScore + 1;
					//System.out.println(gameScore);
				}
				tubePositionX[i] = tubePositionX[i] - tubeSpeed;

				// game over
                // this condition for check whether tube is in the bird range
                boolean condition = ( (birdPositionX < tubePositionX[i]) || (birdPositionX < tubePositionX[i] + tubeWidth ) )
                        && ((birdXEnd > tubePositionX[i]) || ( birdXEnd > tubePositionX[i]));

                if( (condition)) {
                    // check bird is in between the tube
					if ( (bottomTubeEnd[i] >= birdPositionY) || (birdPositionY + birdHeight >= topTubeStart[i]) ) {
						gameOver = true;
					}
				}
				batch.draw(tubeTop, tubePositionX[i], topTubeStart[i], tubeWidth, topTubeEnd);
				batch.draw(tubeBotm, tubePositionX[i], bottomTubeStart, tubeWidth, bottomTubeEnd[i]);
			}
		}

		// when game over
		if(gameOver) {
			// tubes
			for (int i = 0; i < numberOfTubes; i++) {
				batch.draw(tubeTop, tubePositionX[i], topTubeStart[i], tubeWidth, screenHeight / 2);
				batch.draw(tubeBotm, tubePositionX[i], 0, tubeWidth, bottomTubeEnd[i]);
			}
			batch.draw(gameOverImage, screenWidth/2 - screenWidth/6, screenHeight/2 -screenHeight/16, screenWidth/3, screenHeight/8);
			// bird
			batch.draw(bird, birdPositionX, birdPositionY ,birdWidth, birdHeight);

			// this should replace play again button
			if(Gdx.input.justTouched()) {
			    if(tap1 &&Gdx.input.justTouched()) {
                    if (tap1 && Gdx.input.justTouched()) {
                        gameOver = false;
                        gameStarted = false;
                        for (int i = 0; i < numberOfTubes; i++) {
                            tubePositionX[i] = screenWidth + i * tubeOffset;
                            tubeGapOffSets[i] = tubeGap * (rand.nextFloat() + min);
                            topTubeStart[i] = screenHeight / 2 + tubeGap / 2 + tubeGapOffSets[i];
                            bottomTubeEnd[i] = screenHeight / 2 - tubeGap / 2 + tubeGapOffSets[i];
                        }
                        birdPositionY = screenHeight / 2 - screenWidth / 18;
                        tap1 = false;
                        tap2 = false;
                        gameScore = 0;
                    }
                    tap2 = true;
                }
                tap1 = true;
            }
		}
		font.draw(batch, String.valueOf(gameScore), screenWidth - 90, screenHeight - 20);
		batch.end();
	}

}
