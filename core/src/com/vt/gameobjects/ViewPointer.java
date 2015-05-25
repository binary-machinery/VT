package com.vt.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class ViewPointer extends GameObject {
    class Controller extends InputListener {
        private Vector2 touchPosition = new Vector2(0, 0);
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                setActive(true);
                setPosition(x, y, Align.center);
            }
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                setActive(true);
                setPosition(x, y, Align.center);
            }
        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            if (keycode == Input.Keys.CONTROL_LEFT && Gdx.input.isTouched()) {
                setActive(true);
                event.toCoordinates(event.getListenerActor(), touchPosition);
                setPosition(touchPosition.x, touchPosition.y, Align.center);
            }
            return true;
        }
    }

    public ViewPointer() {
        setSize(Constants.VIEW_POINTER_WIDTH, Constants.VIEW_POINTER_HEIGHT);
        setOrigin(Align.center);
        setTexture(Assets.getInstance().viewPointer);
        this.setName(Constants.VIEW_POINTER_ACTOR_NAME);
        this.setActive(true);
    }

    public InputListener getInputListener() {
        return new Controller();
    }

    public Vector2 getPosition() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
    }
}
