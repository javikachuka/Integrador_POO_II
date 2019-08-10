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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author kachu
 */
@Entity 
@Table(name="foros")
public class Foro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id ;
    private String tema ;
    @OneToMany
    private List<Pregunta> preguntas ;

    public Foro(){
        this.preguntas = new ArrayList<>() ;
    }
    
    public int getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    
    public void agregarPregunta(Pregunta p){
        this.preguntas.add(p) ;
    }
    
    public void eliminarPregunta(Pregunta p){
        this.preguntas.remove(p) ;
    }
}
