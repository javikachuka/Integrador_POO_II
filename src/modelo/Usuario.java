/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kachu
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @SequenceGenerator(name="sec_user",initialValue=1,allocationSize = 1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sec_user")
    private int id ;
    private String nombre ;
    private String apellido ;
    private String dni ;
    private String tipoUsuario ;
    private String email ;
    private String password ;
    private Boolean borrado ;
    
    public static final String ADMINISTRADOR = "ADMINISTRADOR" ;
    public static final String PROFESOR = "PROFESOR" ;
    public static final String ESTUDIANTE = "ESTUDIANTE" ;
    public static final String REGISTRADOR = "REGISTRADOR" ;

    
    
    public Usuario(){
        
    }
    
    public Usuario(String nombre, String apellido, String dni, String tipoUsuario, String email, String pass){
        this.nombre = nombre ;
        this.apellido = apellido ;
        this.dni = dni ;
        this.tipoUsuario = tipoUsuario ;
        this.email = email ;
        this. password = pass ;
        this.borrado = false ;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    
    public Boolean getBorrado() {
        return borrado;
    }
    
    @Override
    public String toString() {
        return apellido + " " + nombre + " ( " + tipoUsuario + " )" ;
    }
    
}
