package de.pfbeuth.game.breakout.gameEngine;
import javafx.scene.image.Image;
import static de.pfbeuth.game.breakout.gameEngine.Breakout.WIDTH;
import static de.pfbeuth.game.breakout.gameEngine.Breakout.HEIGHT;

/**
 * This class defines and control the object paddle and provides the update
 * method for the GamePlayTimer.
 */

class Paddle extends AnimatedGameObject {
    private Breakout breakout;  //creates context to Breakout-Class
    private final double PADDLE_INIT_X_POS = 0;
    private final double PADDLE_INIT_Y_POS = HEIGHT*0.4;
    private static final double PADDLE_X_DIMENSION = 200/2;
    private static final double RIGHT_SCREEN_BOUNDARY = WIDTH/2 - PADDLE_X_DIMENSION /2;
    private static final double LEFT_SCREEN_BOUNDARY = -(WIDTH/2 - PADDLE_X_DIMENSION /2);
    private final int PADDLE_INIT_VELOCITY = 15;

    /** ------ CONSTRUCTOR ------ */
    protected Paddle (Breakout iPaddle, String SVGdata, double xLocation, double yLocation, Image... sprites){
        super(SVGdata, xLocation, yLocation, sprites);
        breakout = iPaddle;
    }

    /** Update() is called by GamePlayTimer and updates every method with 60fps */
    @Override
    public void update() {
        setXYPosition();
        setScreenBoundaries();
        translatePaddle();
    }
    /* check, if ball moves in positive or negative X-direction */
    private void setXYPosition(){
        //check, if user pressed left arrow key
        if(breakout.getController().isLeft()) {
			setPositionX(getPositionX() - velocityX);
        }
        //check, if user pressed right arrow key
        if(breakout.getController().isRight()) {
			setPositionX(getPositionX() + velocityX);
		}
    }
    /* defines screen boundaries */
    private void setScreenBoundaries(){
        if(getPositionX() >= RIGHT_SCREEN_BOUNDARY){
            setPositionX(RIGHT_SCREEN_BOUNDARY);
        }
        if(getPositionX() <= LEFT_SCREEN_BOUNDARY){
            setPositionX(LEFT_SCREEN_BOUNDARY);
        }
    }
    /* translate X- and Y-Position */
    private void translatePaddle() {
        spriteImage.setTranslateX(getPositionX());
    }
    /** if a new game or level starts, paddle will set to the initial position */
    void resetState(){
        setVelocityX(PADDLE_INIT_VELOCITY);
        setPositionX(PADDLE_INIT_X_POS);
        setPositionY(PADDLE_INIT_Y_POS);
        spriteImage.setTranslateX(PADDLE_INIT_X_POS);
        spriteImage.setTranslateY(PADDLE_INIT_Y_POS);
    }
    /** set Paddle back in Z-Depth */
    void setPaddleToBack(){
        spriteImage.toBack();
    }
}