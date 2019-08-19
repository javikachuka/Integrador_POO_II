/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import persistencia.Persistencia;
import vista.ViewLogin;

/**
 *
 * @author kachu
 */
public class Sistema {
    public static void main(String[] args) {
        ViewLogin vistaLogin = new ViewLogin() ;
        vistaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaLogin.setLocationRelativeTo(null);     
        vistaLogin.setVisible(true);

    }
}
