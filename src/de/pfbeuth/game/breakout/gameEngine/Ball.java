package de.pfbeuth.game.breakout.gameEngine;
import de.pfbeuth.game.breakout.gamelogic.Life;
import de.pfbeuth.game.breakout.gamelogic.ScoreCounter;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import static de.pfbeuth.game.breakout.gameEngine.Breakout.HEIGHT;
import static de.pfbeuth.game.breakout.gameEngine.Breakout.WIDTH;

public class Ball extends AnimatedGameObject {
    private Breakout breakout;  //creates context to Breakout-Class


    private Life life;
    private final double BALL_INIT_X_POS = 0;
    private final double BALL_INIT_Y_POS = HEIGHT/3;
    private static final double BALL_RADIUS = 50/4; //TODO get rid of magic number; 50 = size of ball.png in px
    private static final double RIGHT_SCREEN_BOUNDARY = WIDTH/2 - BALL_RADIUS/4;
    private static final double LEFT_SCREEN_BOUNDARY = -(WIDTH/2 - BALL_RADIUS/4);
    private static final double TOP_SCREEN_BOUNDARY = -(HEIGHT/2 - BALL_RADIUS/4);
    private static final double BOTTOM_SCREEN_BOUNDARY = HEIGHT/2 + BALL_RADIUS/4;
    boolean ballPaddleCollision;
    boolean up = true;
    boolean right = true;
    double lastX, lastY;
    boolean ballIsDead;
    public Brick destroyedBrick;

    protected Ball(Breakout iBall, String SVGdata, double xLocation, double yLocation, Image... sprites) {
        super(SVGdata, xLocation, yLocation, sprites);
        breakout = iBall;
   }

    @Override
    void update(){
        checkCollision();
        setXYPosition();
        setScreenBoundaries();
        translateBall();
    }
    private void checkCollision(){
        for (int i = 0; i < breakout.getSpriteManager().getCurrentObjects().size(); i++) {
            GameObject collisionObject = breakout.getSpriteManager().getCurrentObjects().get(i);
            collision(collisionObject);
            if (collision(collisionObject) && collisionObject instanceof Brick){
                ((Brick) collisionObject).destroyBrick();
                destroyedBrick = (Brick) collisionObject;
                //TODO @Anna new method to compare brick colors
                if (((Brick)collisionObject).spriteImage.getImage().equals(breakout.getBrickImageGreen())) {
                    ScoreCounter.counter();
                    System.out.println("green brick hit");
                }
                brickCollision();
            }
            if(collision(collisionObject) && collisionObject instanceof Paddle) {
                ballPaddleCollision();
            }
        }
    }

    @Override
    boolean collision(GameObject object){
        boolean collisionDetect = false;
        //two step collision detection
        if(breakout.getBall().spriteImage.getBoundsInParent().intersects(object.getSpriteImage().getBoundsInParent()))
        {
            Shape intersection = SVGPath.intersect(breakout.getBall().getSpriteCollisionBound(), object.getSpriteCollisionBound());
            if(intersection.getBoundsInLocal().getWidth() != -1){
                collisionDetect = true;
            }
        }
        if (collisionDetect){
            if (!(object instanceof Paddle) && !(object instanceof Ball)) {
                breakout.getSpriteManager().removeCurrentObjects(object);
                breakout.getSpriteManager().addToRemovedObjects(object);
                //breakout.getRoot().getChildren().remove(object.getSpriteImage());
                //breakout.getSpriteManager().resetRemovedObjects();
            }
            return true;

        }
        return false;
    }

    public Brick getDestroyedBrick(){
        return destroyedBrick;
    }

    //set XY coordinates when arrow keys are used for gameobject control
    private void setXYPosition(){
        if (up) {
            positionY -= velocityY;
        } else {
            positionY += velocityY;
        }

        if(right){
            positionX += velocityX;
        } else {
            positionX -= velocityX;
        }

        //TODO delete following lines in final stage
     /*   if(breakout.controller.isLeft()) {
            positionX -= velocityX;
        }
        if(breakout.controller.isRight()) {
            positionX += velocityX;
        }
        if(breakout.controller.isUp()) {
            positionY -= velocityY;
        }
        if(breakout.controller.isDown()) {
            positionY += velocityY;
        }

        if(positionX >= RIGHT_SCREEN_BOUNDARY) {
            positionX -= velocityX;
        }
        if(positionX <= LEFT_SCREEN_BOUNDARY) {
            positionX += velocityX;
        }
        if(positionY >= TOP_SCREEN_BOUNDARY) {
            positionY -= velocityY;
        }
        if(positionY <= BOTTOM_SCREEN_BOUNDARY) {
            positionY += velocityY;
        }*/
    }

    private void brickCollision(){
       up = false;
    }

    private void setScreenBoundaries(){
        if(positionX >= RIGHT_SCREEN_BOUNDARY) {
           positionX = RIGHT_SCREEN_BOUNDARY - BALL_RADIUS;
           right = false;
           ballIsDead = false;
        }
        if(positionX <= LEFT_SCREEN_BOUNDARY) {
            positionX = LEFT_SCREEN_BOUNDARY + BALL_RADIUS;
            right = true;
            ballIsDead = false;
        }
        if(this.positionY <= TOP_SCREEN_BOUNDARY) {
            positionY = TOP_SCREEN_BOUNDARY + BALL_RADIUS ;
            up = false;
            ballIsDead = false;
        }
        if(this.positionY >= BOTTOM_SCREEN_BOUNDARY) {
            ballIsDead =  true;
            breakout.getGameOver().ballDied();
            //TODO: eine Verbindung zu der Klasse LIFE schaffen
            //life.loseLife();
            //ScoreCounter.stopcounting();

        }
    }

    private void translateBall () {
        spriteImage.setTranslateX(positionX);
        spriteImage.setTranslateY(positionY);
    }

    public boolean getBallIsDead(){
        return ballIsDead;
    }

    private void ballPaddleCollision(){
        up = true;
    }

    void resetState(){
        up = true;
        right = true;
        setVelocityX(12);
        setVelocityY(12);
        this.positionX = BALL_INIT_X_POS;
        this.positionY = BALL_INIT_Y_POS;
        spriteImage.setTranslateX(BALL_INIT_X_POS);
        spriteImage.setTranslateY(BALL_INIT_Y_POS);
    }

    public boolean isRight() {
        return right;
    }
}