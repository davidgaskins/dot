/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david, david, tan
 * purpose: We call this class to make sure that the input provided is an integer or if it has alpha values in it
 */
public class InputChecker {
    private String toBeChecked;
    public InputChecker(String s){
        toBeChecked = s;
    }
    //checks to make sure the values are ints
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
    //the negation of the above, if it is not an int, it contains non numeric data, and as such is not good input for integers purposes
    boolean hasAlpha(){
        return !isInt();
    }
}
