/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id ;
    private String titulo ;
    private String descripcion ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha ;
    @ManyToOne
    private Foro foro ;
    @ManyToOne
    private UsuarioAcademico usuario ;
    @OneToMany(targetEntity = Respuesta.class)
    private List<Respuesta> respuestas ;
    

    public Pregunta(){
        this.fecha = new Date() ;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}
