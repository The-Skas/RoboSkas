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
     abstract class State<T> {
         abstract void enter(T obj);
         abstract void execute(T obj);
         abstract void exit(T obj);
     }
     class Flee extends State<AdvancedRobot>{

        @Override
        void enter(AdvancedRobot obj) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        void execute(AdvancedRobot obj) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        void exit(AdvancedRobot obj) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

       
     }
     private float direction = 1;
     private float turnDirection = 1;
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

            // If it's close enough, fire!
            if (Math.abs(bearingFromGun) <= 3) {
                    setTurnGunRight(bearingFromGun);
                    // We check gun heat here, because calling fire()
                    // uses a turn, which could cause us to lose track
                    // of the other robot.
                    if (getGunHeat() == 0) {
                            fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
                    }
            } // otherwise just set the gun to turn.
            // Note:  This will have no effect until we call scan()
            else {
                    this.setTurnGunRight(bearingFromGun);
            }
            // Generates another scan event if we see a robot.
            // We only need to call this if the gun (and therefore radar)
            // are not turning.  Otherwise, scan is called automatically.
            
          
            
            
            if(e.getDistance() < 100)
            {
                if(e.getBearing() > 5)
                {
                   this.setTurnLeft(5);
                }
                else if(e.getBearing() < -5)
                {
                   this.setTurnRight(5);
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
            
            this.setAhead(direction*(e.getDistance()+5.0));
            
            waitFor(new TurnCompleteCondition(this));
           
           
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
        this.scan();
    }

 }


