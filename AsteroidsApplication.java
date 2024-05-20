// ______   __  __   __  __    
///\  ___\ /\ \/\ \ /\_\_\_\   
//\ \ \____\ \ \_\ \\/_/\_\/_  
// \ \_____\\ \_____\ /\_\/\_\
//  \/_____/ \/_____/ \/_/\/_/

import javafx.application.*;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

public class AsteroidsApplication extends Application {
    public static int WIDTH = 300;      //this is for the objects
    public static int HEIGHT = 200;     //to stay on the screen
    
    @Override
    public void start(Stage stage) throws Exception{
        Pane pane = new Pane();
        Text text = new Text(10, 20, "Points: 0");
        pane.setPrefSize(WIDTH, HEIGHT);
        
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        List<Projectile> projectiles = new ArrayList<>();
        List<Asteroid> asteroids = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }
        
        pane.getChildren().addAll(ship.getCharacter(), text);
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        
        Scene scene = new Scene(pane);
        
        AtomicInteger points = new AtomicInteger();
        
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

            //here what we do is kinda store the information of which keys
            //the user is pressing in a more efficient way
        
        scene.setOnKeyPressed(event ->{
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });
            
        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
            //now we do sum with that info (move the ship)
            
        new AnimationTimer(){
            @Override
            public void handle(long now){
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)){
                    ship.turnLeft();           //when true we rotate that bih up in itself
                }
                
                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();           //if there's no info on that key we assume it's false
                }
                
                if(pressedKeys.getOrDefault(KeyCode.UP, false)){    //when u hit it up it will be like a launch
                    ship.accelerate();                              //cos the Point2D won't be 0,0 no more and it
                }                                                   //will keep updating itself (accelerating)
                
                if(pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3){     //when pressing space, we keep create a normal amount of projectiles right where the ship is at
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);
                    
                    projectile.accelerate();        // this makes the projectile move after being created
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                    
                    pane.getChildren().add(projectile.getCharacter());
                }
                
                ship.move();     //here we make use of the Point2D from b4
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                
                asteroids.forEach(asteroid -> {
                    if(ship.collide(asteroid)){     //if they crash it stops
                        stop();
                    }
                });
                
                projectiles.forEach(projectile -> { //if a projectile crashes, it kills the asteroids n the projectile
                    asteroids.forEach(asteroid ->{
                        if(projectile.collide(asteroid)){
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    
                    if(!projectile.isAlive()){
                        text.setText("Points: " + points.addAndGet(1000));
                    }
                });
                
                //first collect those asteroids n projectiles that collide with each other
                projectiles.stream() //and them remove them from the screen
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));                
                
                projectiles.removeAll(projectiles.stream()  //and also from the lists
                                        .filter(projectile -> !projectile.isAlive())
                                        .collect(Collectors.toList()));
                
                //repeat for asteroids
                
                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                
                asteroids.removeAll(asteroids.stream()
                                            .filter(asteroid -> !asteroid.isAlive())
                                            .collect(Collectors.toList()));
                
                if(Math.random() < 0.005){
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if(!asteroid.collide(ship)){
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            }
        }.start();          //AnimationTimer() does it's magic 60 times/second

        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

//keep taking care of your goals, 
//mental health, self care, recognize 
//cognitive distortions and rebel against them!!
//one step at a time, don't be afraid to 
//ask 4 help or to fail at times.
