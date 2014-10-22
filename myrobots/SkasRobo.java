/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skas
 */
 package myrobots;
 import robocode.*;
 import static robocode.util.Utils.normalRelativeAngleDegrees;
 public class SkasRobo extends AdvancedRobot {
    private double previousEnergy = 100;
    private float direction = 1;
    private float turnDirection = 1;
    private boolean justChangedDirection = false;
//     abstract class State<T> {
//         abstract void enter(T obj);
//         abstract void execute(T obj);
//         abstract void exit(T obj);
//     }
//     class Flee extends State<AdvancedRobot>{
//
//        @Override
//        void enter(AdvancedRobot obj) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        void execute(AdvancedRobot obj) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        void exit(AdvancedRobot obj) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//       
//     }
//     
//     class Default extends State<AdvancedRobot>{
//
//        @Override
//        void enter(AdvancedRobot obj) {
//            
//        }
//
//        @Override
//        void execute(AdvancedRobot obj) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        void exit(AdvancedRobot obj) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//       
//     }
     public void run() {
        this.setAdjustGunForRobotTurn(true);

         while (true) {

             this.turnGunLeft(100);
            
         }
     }
  
     public void onScannedRobot(ScannedRobotEvent e) {
            // Calculate exact location of the robot
            
            double absoluteBearing = getHeading() + e.getBearing();
            System.out.println("My Heading: "+getHeading());
            System.out.println("Enemy Bearing: "+ e.getBearing());
            
            System.out.println("Enemy ABS bearing: "+ absoluteBearing);
            double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
            System.out.println("Enemy Velocity: "+ e.getVelocity());
            //Fire if close enough
            if (Math.abs(bearingFromGun) <= 3) {
                    setTurnGunRight(bearingFromGun);
          
            } 
            else {
                    this.setTurnGunRight(bearingFromGun);
            }
            
            //Move to state
            if (getGunHeat() == 0) 
            {
                double difference_heading = Math.abs(e.getHeading() - getHeading());
                
               
                fire(Math.min(3, getEnergy() - .1));
                
            }
          
            
            
            if(e.getDistance() < 10)
            {
                if(e.getBearing() > 5)
                {
                   this.setTurnRight(3*this.direction);
                }
                else if(e.getBearing() < -5)
                {
                   this.setTurnLeft(3*this.direction);
                }
            }
            else
            {
                if(e.getBearing() > 75)
                {
                    this.turnDirection = -1;
                }
                else if(e.getBearing() < -75)
                {
                    this.turnDirection = 1;
                }
                this.setTurnLeft(2*turnDirection);
            }
            
            //If the bearing is too alrge then change direction youre heading.
            
            //Check to see if opponents energy levels have changed, this means
            //the opponent has fired
            double changeInEnergy =   previousEnergy-e.getEnergy();
//         
            previousEnergy = e.getEnergy();
            
            this.setAhead(direction*(e.getDistance()+5.0));
            
            waitFor(new TurnCompleteCondition(this));
           
            this.justChangedDirection = false;
            scan();
            
            
           
     }
     
    public void onBulletHit(BulletHitEvent event) 
    {
      out.println("I hit " + event.getName() + "!");
    }
     
    public void onBulletMissed(BulletMissedEvent event)
    {
      out.println("Drat, I missed.");
    }
    public void onBulletHitBullet(BulletHitBulletEvent event)
    {
      out.println("Cointer bullet!");
    }
    
    public void onHitWall(HitWallEvent event) {
        this.direction *= -1;
        this.justChangedDirection = true;
        this.scan();
    }
    

    @Override
    public void onWin(WinEvent event) {
        super.onWin(event); //To change body of generated methods, choose Tools | Templates.
    }


 }


