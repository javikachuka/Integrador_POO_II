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
    @OneToMany(mappedBy="usuario")
    private List<Pregunta> preguntas ;
    @OneToMany(mappedBy="usuario")
    private List<Respuesta> respuestas ;
    @ManyToMany
    private List<Materia> materias ;
    @OneToMany(mappedBy="usuario")
    private List<Voto> votos ;

    public UsuarioAcademico(){
        super();
        this.reputacion = 0;
        this.preguntas = new ArrayList<>() ;
        this.respuestas = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.votos = new ArrayList<>();
        
    }
    
    public UsuarioAcademico(String nombre , String apellido, String dni , String tipoUsuario, String email, String pass){
        super(nombre , apellido, dni , tipoUsuario, email, pass) ;
        this.reputacion = 0;
        this.preguntas = new ArrayList<>() ;
        this.respuestas = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.votos = new ArrayList<>();
    }
    
    public int getReputacion() {
        return reputacion;
    }

    public void setReputacion(int reputacion) {
        this.reputacion = reputacion;
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
    
    public void agregarMateria(Materia m){
        this.materias.add(m) ;
    }
    
    public void quitarMateria(Materia m){
        this.materias.remove(m);
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public void agregarPregunta(Pregunta p){
        this.preguntas.add(p) ;
    }
    
    public void eliminarPregunta(Pregunta p){
        this.preguntas.remove(p) ;
    }
    
    public void agregarRespuesta(Respuesta r){
        this.respuestas.add(r) ;
    }
    
    public void eliminarRespuesta(Respuesta r){
        this.respuestas.remove(r) ;
    }
    
    public int getCantResp(){
        return this.respuestas.size() ;
    }
    
    public int getCantPreg(){
        return this.preguntas.size() ;
    }
    
    public void agregarVoto(Voto v){
        this.votos.add(v) ;
    }
   
    public void sumarReputacion(){
        this.reputacion += 5 ;
    }
    
    public void restarReputacion(){
        if((reputacion-2)>0){
            this.reputacion -= 2;
        }else{
            reputacion = 0;
        }
    }
    
    public void eliminarVoto(Voto v){
        this.votos.remove(v );
    }
    
    @Override
    public String toString() {
        return super.getApellido() + " " + super.getNombre() + " ( " + super.getTipoUsuario() + " )" ;
    }
    
    
    
}
