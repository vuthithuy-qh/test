/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author ADMIN
 */
public class ValidateInforOfUser {
    public static final String NAME_REGEX = "^[\\p{L}]+(?:[ '\\-][\\p{L}]+)*$";
    public static final String PHONE_REGEX_VN = "^(03|05|07|08|09)\\d{8}$";
    private static final String ADDRESS_REGEX = "^[a-zA-Z0-9À-ỹà-ỹ\\s,./-]{5,100}$";


    
    public static boolean validUsername(String username) {
        if (username == null || username.trim().length() < 2 || username.trim().length() > 20) {
            return false;
        }
        
        return true; 
   }
    
    public static boolean  validPassword (String password){
        if (password == null || password.length() < 3 || password.length() > 20){
            return false; 
        }
        
        return true; 
    }
    
    public static boolean  isValidName(String name){
        if (name == null || name.trim().isEmpty()){
            return false; 
        }
        
        String trimmedName = name.trim(); 
        if (trimmedName.length() > 50){
            return false; 
        }
        
        return trimmedName.matches(NAME_REGEX); 
    }
    
    public static boolean isValidPhone(String phone){
        if (phone == null || phone.trim().isEmpty()){
            return false; 
        } 
       return phone.matches(PHONE_REGEX_VN);
    }
    
    public static boolean isValidShipAdd(String address){
        if (address == null || address.trim().isEmpty()){
            return false; 
        }
        
        String cleaned = address.trim(); 
        if (cleaned.length() < 5 || cleaned.length() > 100){
            return false; 
            
        }
        
        return cleaned.matches(ADDRESS_REGEX); 
    }
    
    
    
}
