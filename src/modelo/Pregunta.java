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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.swing.DefaultListModel;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="preguntas")
public class Pregunta {
    @Id
    @SequenceGenerator(name="sec_preguntas",initialValue=1,allocationSize = 1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sec_preguntas")
    private int id ;
    private String titulo ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha ;
    @ManyToOne
    private Foro foro ;
    @ManyToOne
    private UsuarioAcademico usuario ;
    @OneToMany(mappedBy="pregunta")
    private List<Respuesta> respuestas ;
    

    public Pregunta(){
        this.fecha = new Date() ;
        this.respuestas = new ArrayList<>() ;
    }
    
    public Pregunta(String titulo , Foro f , UsuarioAcademico user ){
        this.titulo = titulo ;
        this.fecha = new Date() ;
        this.foro = f ;
        this.usuario = user ;
        this.respuestas = new ArrayList<>() ;

    }
    
    
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        DefaultListModel modelo = new DefaultListModel();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy HH:mm");
        return f.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Foro getForo() {
        return foro;
    }

    public void setForo(Foro foro) {
        this.foro = foro;
    }

    public UsuarioAcademico getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioAcademico usuario) {
        this.usuario = usuario;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
    
    public void agregarRespuesta(Respuesta r){
        this.respuestas.add(r) ;
    }
    
    public void eliminarRespuesta(Respuesta r){
        this.respuestas.remove(r );
    }
    
    public int getCantidadRespuestas(){
        return this.respuestas.size() ;
    }
    
    public String getUltimaRespuesta(){
        return this.respuestas.get(respuestas.size()-1).getFecha() ;
    }

    @Override
    public String toString() {
        return titulo ;
    }
    
    
    
    
}
