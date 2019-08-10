/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author kachu
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class UsuarioAcademico extends Usuario {
    
    private int reputacion ;
    private int votosPositivos ;
    private int votosNegativos ;
    @OneToMany(targetEntity = Pregunta.class)
    private List<Pregunta> preguntas ;
    @OneToMany(targetEntity = Respuesta.class)
    private List<Respuesta> respuestas ;
    @ManyToMany(targetEntity = Materia.class)
    private List<Materia> materias ;

    public UsuarioAcademico(){
        super();
        this.reputacion = 0;
        this.votosPositivos = 0;
        this.votosNegativos = 0 ;
        this.preguntas = new ArrayList<>() ;
        this.respuestas = new ArrayList<>();
        this.materias = new ArrayList<>();
        
    }
    
    public int getReputacion() {
        return reputacion;
    }

    public void setReputacion(int reputacion) {
        this.reputacion = reputacion;
    }

    public int getVotosPositivos() {
        return votosPositivos;
    }

    public void setVotosPositivos(int votosPositivos) {
        this.votosPositivos = votosPositivos;
    }

    public int getVotosNegativos() {
        return votosNegativos;
    }

    public void setVotosNegativos(int votosNegativos) {
        this.votosNegativos = votosNegativos;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    
    
}
