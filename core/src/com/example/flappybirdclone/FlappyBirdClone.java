package com.example.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBirdClone extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity = 0;
	float gap = 400;
	float maxTubeOffset = 0;
	Random random;
	float tubeOffset = 0;
	float tubeVelocity = 4;
	float tubeX;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
	    birds[0] = new Texture("bird.png");
	    birds[1] = new Texture("bird2.png");
	    topTube = new Texture("toptube.png");
	    bottomTube = new Texture("bottomtube.png");
	    birdY = Gdx.graphics.getHeight()/ 2 - birds[0].getHeight()/2;
	    gravity = 10;
	    maxTubeOffset = Gdx.graphics.getHeight()/2 - gap /2 - 100;
	    random = new Random();
	    tubeX = Gdx.graphics.getWidth()/2 ;
	}

	@Override
	public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if(gameState != 0) {
            if(Gdx.input.justTouched()) {
                velocity = -10;
                tubeOffset = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight() - gap -200);
            }
            if(birdY > 0 || velocity < 0) {
                if(flapState != 0){
                    velocity++;
                }
                birdY = birdY - velocity;
            }
        } else {
            if(Gdx.input.justTouched()) {
                gameState = 1;
            }
        }

        batch.draw(topTube, tubeX, Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset, 75, Gdx.graphics.getHeight()/2);
        batch.draw(bottomTube, tubeX, Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight()/3 + tubeOffset , 75, Gdx.graphics.getHeight()/2 );

	    if(flapState == 0) {
	        flapState = 1;
        } else {
	        flapState = 0;
        }
        batch.draw(birds[flapState], Gdx.graphics.getWidth()/ 2 - birds[flapState].getWidth()/2, birdY, 65, 50);
		batch.end();
	}

}
