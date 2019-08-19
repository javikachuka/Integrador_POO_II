/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="materias")
public class Materia {
    @Id
    @SequenceGenerator(name="sec_materias",initialValue=1,allocationSize = 1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sec_materias")
    private int id ;
    private String nombre ;
    private String enlace ;
    @ManyToMany(mappedBy = "materias")
    private List<UsuarioAcademico> profesores ;
    
    public Materia(){
        this.profesores = new ArrayList<>() ;
    }
    
    public Materia(String materia , String link){
        this.nombre = materia ;
        this.enlace = link ;
        this.profesores = new ArrayList<>() ;
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

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public List<UsuarioAcademico> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<UsuarioAcademico> profesores) {
        this.profesores = profesores;
    }

    @Override
    public String toString() {
        return nombre ;
    }
    
    

    
}
