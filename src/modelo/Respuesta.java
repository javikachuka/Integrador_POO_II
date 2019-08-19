/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.swing.DefaultListModel;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="respuestas")
public class Respuesta {
    @Id
    @SequenceGenerator(name="sec_respuesta",initialValue=1,allocationSize = 1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sec_respuesta")
    private int id ;
    private String respuesta ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha ;
    @ManyToOne
    private UsuarioAcademico usuario ;
    @ManyToOne
    private Pregunta pregunta ;
    @OneToMany(mappedBy="respuesta")
    private List<Voto> votos ;

    public Respuesta(){
        this.fecha = new Date() ;
    }
    
    public Respuesta(String respuesta, UsuarioAcademico user , Pregunta p){
        this.respuesta = respuesta ;
        this.fecha = new Date();
        this.usuario = user ;
        this.pregunta = p ;
        this.votos = new ArrayList<>() ;
    }
    
    public int getId() {
        return id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFecha() {
        DefaultListModel modelo = new DefaultListModel();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy HH:mm");
        return f.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public UsuarioAcademico getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioAcademico usuario) {
        this.usuario = usuario;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
    
    public void agregarVotos(Voto voto){
        this.votos.add(voto) ;
    }
    
    public void quitarVoto(Voto voto){
        this.votos.remove(voto) ;
    }
    
    public int getCantVotosPos(){
        int cantidad = 0;
        for(Voto v : this.votos){
            if(v.getValor()){
                cantidad +=1 ;
            }
        }
        return cantidad ;
    }
    
    public int genCantVotosNeg(){
        int cantidad = 0;
        for(Voto v : this.votos){
            if(v.getValor() == false){
                cantidad +=1 ;
            }
        }
        return cantidad ;
    }
    
    public void eliminarVoto(Voto v){
        this.votos.remove(v) ;
    }
    
    

    @Override
    public String toString() {
        return this.respuesta ;
    }
    
    
    
    
}
