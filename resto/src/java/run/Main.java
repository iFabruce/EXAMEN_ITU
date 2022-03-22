/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import component.BdTable;
import java.util.ArrayList;
import model.Menu;


/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) throws Exception{
      
        ArrayList<Menu> lm = new Menu().findAll();
        int i=0;
        for(Menu m : lm){
         
            System.out.println(m.getProduit());
             System.out.println(m.getCategorie());
              System.out.println(m.getPrix());
            i++;
        }
        
        
    }
}
