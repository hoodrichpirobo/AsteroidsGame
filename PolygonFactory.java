// ______   __  __   __  __    
///\  ___\ /\ \/\ \ /\_\_\_\   
//\ \ \____\ \ \_\ \\/_/\_\/_  
// \ \_____\\ \_____\ /\_\/\_\
//  \/_____/ \/_____/ \/_/\/_/

import java.util.*;
import javafx.scene.shape.*;

public class PolygonFactory {
    public Polygon createPolygon(){
        Random rnd = new Random();
        
        double size = 10 + rnd.nextInt(10);     //10 to 20 size
        
        Polygon polygon = new Polygon();
        double c1 = Math.cos(Math.PI * 2 / 5);  //math formula
        double c2 = Math.cos(Math.PI / 5);      //from wolfram
        double s1 = Math.sin(Math.PI * 2 / 5);  //to calculate the locations
        double s2 = Math.sin(Math.PI * 4 / 5);  //of an asteroids corners
        
        polygon.getPoints().addAll(
            size, 0.0,
            size * c1, -1 * size * s1,
            -1 * size * c2, -1 * size * s2,
            -1 * size * c2, size * s2,
            size * c1, size * s1);
        
        for(int i = 0; i < polygon.getPoints().size(); i++){        //this is to vary the form of the asteroids a bit
            int change = rnd.nextInt(5) - 2;
            polygon.getPoints().set(i, polygon.getPoints().get(i) + change);
        }
        
        return polygon;
    }
}
//keep taking care of your goals, 
//mental health, self care, recognize 
//cognitive distortions and rebel against them!!
//one step at a time, don't be afraid to 
//ask 4 help or to fail at times.
