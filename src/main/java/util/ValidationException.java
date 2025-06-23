/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class ValidationException extends Exception{
    private final Map<String, String> errors; 
    
    public ValidationException(String message, Map<String, String> errors){
        super(message);
        this.errors = errors; 
    }
    
    public Map<String, String> getErrors(){
        return errors; 
    }
}
