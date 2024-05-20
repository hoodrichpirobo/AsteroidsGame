// ______   __  __   __  __    
///\  ___\ /\ \/\ \ /\_\_\_\   
//\ \ \____\ \ \_\ \\/_/\_\/_  
// \ \_____\\ \_____\ /\_\/\_\
//  \/_____/ \/_____/ \/_/\/_/

import javafx.scene.shape.*;
import java.util.*;

public class Asteroid extends Character{
    private double rotationalMovement;
    
    public Asteroid(int x, int y){
        super(new PolygonFactory().createPolygon(), x, y);
        
        Random rnd = new Random();
        
        super.getCharacter().setRotate(rnd.nextInt(360));
        
        int accelerationAmount = 1 + rnd.nextInt(10);  //it reaches the max acceleration
        for(int i = 0; i < accelerationAmount; i++){    //when it does the random amount of a spin
            accelerate();
        }
        
        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }
    
    @Override
    public void move(){
        super.move();                                   //it rotation is set to random
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);  
    }
}
//keep taking care of your goals, 
//mental health, self care, recognize 
//cognitive distortions and rebel against them!!
//one step at a time, don't be afraid to 
//ask 4 help or to fail at times.
