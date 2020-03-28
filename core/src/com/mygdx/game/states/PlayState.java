package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyBird;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

public class PlayState extends State {
    private Bird bird; //экзепляр класса берд
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;


    public static final int TRUE_SPACING = 125;
    public static final int TRUE_COUNT = 5;
    private Array<Tube> tubes;
    private static final int GROUND_Y_OFFSENT = -30;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGHT / 2);
        bird = new Bird(100, 300);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSENT );
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth() ,GROUND_Y_OFFSENT);


        tubes = new Array<Tube>();
        for (int i = 1; i < TRUE_COUNT; i++) {
            tubes.add(new Tube(i * (TRUE_SPACING + Tube.TUBE_WIDTH)));
        }


    }


    @Override
    protected void handelInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }

    }

    @Override
    public void update(float dt) {
        bird.update(dt);
        updateGround();
        handelInput();
        camera.position.x = bird.getPosition().x;

        for (int i = 0; i < tubes.size; i++) {

            Tube tube = tubes.get(i);
            if (camera.position.x - camera.viewportWidth / 2 > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TRUE_SPACING) * TRUE_COUNT);
            }

            if (tube.collids(bird.getBounds())){
                gsm.set(new PlayState(gsm));
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);//обновления камеры и фиксирование отрисовки обьектов
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground,  groundPos1.x, groundPos1.y);
        sb.draw(ground,  groundPos2.x, groundPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        ground.dispose();
        bird.dispose();
        for (Tube  tube : tubes)
            tube.dispose();
        System.out.println("PlayState Disposed");

    }

    private void updateGround(){
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0 );
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0 );
    }
}
