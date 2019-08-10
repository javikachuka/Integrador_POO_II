/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author kachu
 */
@Entity
@Table(name="respuestas")
public class Respuesta {
    @Id
    private int id ;
    private String respuesta ;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha ;
    private int votosPositivos ;
    private int votosNegativos ;
    @ManyToOne
    private UsuarioAcademico usuario ;
    @ManyToOne
    private Pregunta pregunta ;

    public Respuesta(){
        this.fecha = new Date() ;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    
    
    
    
}
