/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class InputChecker {
    private String toBeChecked;
    public InputChecker(String s){
        toBeChecked = s;
    }
    boolean isInt(){
        for(int i = 0; i < toBeChecked.length(); ++i){
            if(!Character.isDigit(toBeChecked.charAt(i))){
                if(i == 0){
                    if(toBeChecked.charAt(i) !='-'){
                        return false;
                    }
                }
                return false;
            }
        }
        return true;
    }
}
