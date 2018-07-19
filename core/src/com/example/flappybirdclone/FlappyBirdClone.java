package com.example.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBirdClone extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
	    bird = new Texture("bird.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    batch.draw(bird, Gdx.graphics.getWidth()/ 2 - bird.getWidth()/2, Gdx.graphics.getHeight()/ 2 - bird.getHeight()/2);
		batch.end();
	}

}
