// ______   __  __   __  __    
///\  ___\ /\ \/\ \ /\_\_\_\   
//\ \ \____\ \ \_\ \\/_/\_\/_  
// \ \_____\\ \_____\ /\_\/\_\
//  \/_____/ \/_____/ \/_/\/_/

import javafx.geometry.*;
import javafx.scene.shape.*;

public abstract class Character {   //good practice when using inheritance for the father to be abstract
    private Polygon character;
    private Point2D movement;           //this the thingey that makes it move left and right
    private boolean isAlive;
    
    public Character(Polygon character, int x, int y){
        this.character = character;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.isAlive = true;
        this.movement = new Point2D(0, 0);
    }
    
    public Polygon getCharacter(){
        return character;
    }
    
    public void turnLeft(){
        this.character.setRotate(this.character.getRotate() - 5);           //we rotate that bih up in itself
    }
    
    public void turnRight(){
        this.character.setRotate(this.character.getRotate() + 5);
    }
    
    public void move(){     //here we make use of the Point2D from b4, but without accelerate() it does nun cos it's at 0, 0
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());    //i think we take from where it's at
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());    //and make it move on itself from left to right
        
        if(this.character.getTranslateX() < 0){     //if it's outside the screen on the upper, make it appear on the bottom
            this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.WIDTH);
        }
        
        if(this.character.getTranslateX() > AsteroidsApplication.WIDTH){    //if it's leaving the screen on the lower, make it come back on top
            this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.WIDTH);
        }
        
        if(this.character.getTranslateY() < 0){
            this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.HEIGHT);
        }
        
        if(this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.HEIGHT);
        }
    }
    
    public Point2D getMovement(){
        return this.movement;
    }
    
    public void setMovement(Point2D movement){
        this.movement = movement;
    }
    
    public void accelerate(){
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));  //from it's angle we make it move
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));  //one way or another
        
        changeX *= 0.05;        //this is just to control the speed
        changeY *= 0.05;        //steer the object a bit when it moves
        
        this.movement = this.movement.add(changeX, changeY);                    //now Point2D won't be 0, 0
    }
    
    public  boolean collide(Character other){       //making use of Shape class to detect collision
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
    
    public void setAlive(boolean kill){
        this.isAlive = kill;
    }
    
    public boolean isAlive(){
        return this.isAlive;
    }
}

//keep taking care of your goals, 
//mental health, self care, recognize 
//cognitive distortions and rebel against them!!
//one step at a time, don't be afraid to 
//ask 4 help or to fail at times.
