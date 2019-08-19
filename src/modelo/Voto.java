/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author kachu
 */

@Entity
@Table(name="votos")
public class Voto {
    
    @Id
    @SequenceGenerator(name="sec_user",initialValue=1,allocationSize = 1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sec_user")
    private int id ;
    
    private Boolean valor ;
    
    @ManyToOne
    private UsuarioAcademico usuario ;
    
    @ManyToOne
    private Respuesta respuesta ;
    
    public Voto(){
        
    }
    
    public Voto(Respuesta respuesta, Boolean valor , UsuarioAcademico user){
        this.valor = valor ;
        this.respuesta = respuesta ;
        this.usuario = user ;
    }

    public int getId() {
        return id;
    }

    public Boolean getValor() {
        return valor;
    }

    public void setValor(Boolean voto) {
        this.valor = voto;
    }

    public UsuarioAcademico getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioAcademico usuario) {
        this.usuario = usuario;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
    
    
    
    
    
    
}
