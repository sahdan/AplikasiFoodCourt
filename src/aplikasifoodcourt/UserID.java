/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

/**
 *
 * @author User
 */
public class UserID {
    private static String username;
    private static String id;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UserID.id = id;
    }
    
    public static void setUserLogin(String username){
        UserID.username = username;
        
    }
    
    public static String getUserLogin(){
    return username;
    }
}
