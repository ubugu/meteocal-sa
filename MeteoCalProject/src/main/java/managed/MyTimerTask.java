/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.util.TimerTask;
import javax.ejb.Stateless;


/**
 *
 * @author Walter
 */
@Stateless
public class MyTimerTask extends TimerTask{
       
    @Override
    public void run(){
     
        
        System.out.println("ook");
    }
}
