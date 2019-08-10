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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id ;
    private String nombre ;
    private String enlace ;
    @ManyToMany(targetEntity = UsuarioAcademico.class, mappedBy = "materias")
    private List<UsuarioAcademico> profesores ;
    public Materia(){
        
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
    
    

    
}
