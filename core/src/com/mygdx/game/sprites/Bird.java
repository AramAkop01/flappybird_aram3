package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    public static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;

    private Texture bird;
    private Rectangle bounds;

    private Sound flap;

    //констрктор класса Bird
    public Bird(int x, int y ) {
        position = new Vector3(x,y, 0);
        velocity = new Vector3( 0, 0, 0);
        bird = new Texture("bird.png");
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getBird() {
        return bird;
    }

    public void update(float dt){
        velocity.add( 0, GRAVITY, 0);
        velocity.scl(dt); //сколярна величина может отвечать за значения времени
        position.add(MOVEMENT * dt, velocity.y, 0);

        velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);
    }
    public Rectangle getBounds(){
        return bounds;

    }


    public void jump() {
        velocity.y = 250;
        flap.play();
    }

    public void dispose() {
        flap.dispose();
    }
}
