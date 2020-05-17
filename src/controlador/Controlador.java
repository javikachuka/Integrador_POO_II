/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.*;
import persistencia.Persistencia;

/**
 *
 * @author kachu
 */
public class Controlador {

    private Persistencia persistencia;

    public Controlador(Persistencia p) {
        this.persistencia = p;
    }

    public List listarUsuarios() {
        return this.persistencia.buscarTodos(Usuario.class);
//        List<Usuario> filtro = new ArrayList<>();
//        for (Usuario u : users) {
//            if (!u.getBorrado() & !u.getTipoUsuario().equals(Usuario.REGISTRADOR)) {
//                filtro.add(u);
//            }
//        }
//        return filtro;
    }

    public List listarAdministradores() {
        List<UsuarioAcademico> lista = this.persistencia.buscarTodos(UsuarioAcademico.class);
        List<UsuarioAcademico> filtro = new ArrayList<>();
        for (UsuarioAcademico user : lista) {
            if (user.getTipoUsuario().toUpperCase().equals(Usuario.ADMINISTRADOR)) {
                filtro.add(user);
            }
        }
        return filtro;
    }

    public void registrarUsuario(String nombre, String apellido, String dni, String tipoUsuario, String email, String pass) {
        this.persistencia.iniciarTransaccion();

        if (tipoUsuario.equals(Usuario.REGISTRADOR)) {
            Usuario userReg = new Usuario(nombre, apellido, dni, tipoUsuario, email, pass);
            this.persistencia.insertar(userReg);
        } else {
            UsuarioAcademico userAcad = new UsuarioAcademico(nombre, apellido, dni, tipoUsuario, email, pass);
            this.persistencia.insertar(userAcad);
        }
        this.persistencia.confirmarTransaccion();
    }

    public void registrarUsuarioAcademico(String nombre, String apellido, String dni, String tipoUsuario, String email, String pass) {
        this.persistencia.iniciarTransaccion();

        UsuarioAcademico userAcad = new UsuarioAcademico(nombre, apellido, dni, tipoUsuario, email, pass);
        this.persistencia.insertar(userAcad);

        this.persistencia.confirmarTransaccion();
    }

    public void modificarUsuario(Usuario user, String nombre, String apellido, String dni, String email, String pass) {
        this.persistencia.iniciarTransaccion();
        try {
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setDni(dni);
            user.setEmail(email);
            user.setPassword(pass);
            this.persistencia.modificar(user);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void eliminarUsuario(Usuario user) {
        this.persistencia.iniciarTransaccion();
        user.setBorrado(true);
        this.persistencia.modificar(user);
        this.persistencia.confirmarTransaccion();
    }

    public Boolean existeAdmin() {
        if (this.listarAdministradores().isEmpty()) {
            return false;
        }
        return true;
    }

    public Usuario iniciarSesion(String email, String pass) {

        List<Usuario> usuarios = this.listarUsuarios();
        for (Usuario user : usuarios) {
            String emailUser = user.getEmail();
            String passUser = user.getPassword();
            if (emailUser.equals(email) & passUser.equals(pass)) {
                return user;
            }
        }
        return null;
    }

    public List verPerfil(Usuario user) {
        List<Object> datos = new ArrayList<>();

        if (esRegistrador(user)) {
            String apellido = user.getApellido();
            datos.add(0, apellido);
            String nombre = user.getNombre();
            datos.add(1, nombre);
            String dni = user.getDni();
            datos.add(2, dni);
        } else {
            int idUser = user.getId();
            UsuarioAcademico ua = this.buscarUsuarioAcademico(idUser);
            String apellido = ua.getApellido();
            datos.add(0, user.getApellido());
            String nombre = ua.getNombre();
            datos.add(1, user.getNombre());
            String dni = ua.getDni();
            datos.add(2, user.getDni());
            int cantResp = ua.getCantResp();
            datos.add(3, cantResp);
            int cantPreg = ua.getCantPreg();
            datos.add(4, cantPreg);
            datos.add(5, ua.getReputacion());
            List<Pregunta> preguntas = ua.getPreguntas();
            datos.add(6, preguntas);
            if (ua.getTipoUsuario().equals(Usuario.PROFESOR)) {
                List<Materia> materias = ua.getMaterias();
                datos.add(7, materias);
            }
        }
        return datos;
    }

    public Boolean esRegistrador(Usuario user) {
        if (Usuario.REGISTRADOR.equals(user.getTipoUsuario())) {
            return true;
        } else {
            return false;
        }
    }

    public UsuarioAcademico buscarUsuarioAcademico(int idU) {
        return this.persistencia.buscar(UsuarioAcademico.class, idU);
    }
    //ABM de FOROS

    public void crearForo(String tema) {
        this.persistencia.iniciarTransaccion();
        try {
            Foro foro = new Foro(tema);
            this.persistencia.insertar(foro);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void calcularReputacion(Respuesta r, Voto v) {
        this.persistencia.iniciarTransaccion();
        UsuarioAcademico ua = r.getUsuario();
        if (v.getValor()) {
            ua.sumarReputacion();
        } else {
            ua.restarReputacion();
        }
        this.persistencia.modificar(ua);
        this.persistencia.confirmarTransaccion();
    }

    public void sumarReputacion(UsuarioAcademico ua) {
        ua.sumarReputacion();
    }

    public void eliminarForo(Foro foro) {
        this.persistencia.iniciarTransaccion();
        List<Pregunta> preguntas = foro.getPreguntas();
        for (Pregunta p : preguntas) {
            this.eliminarPreguntaCompleta(p, this.persistencia);
        }
        this.persistencia.eliminar(foro);
        this.persistencia.confirmarTransaccion();
    }

    public List verForos() {
        return this.persistencia.buscarTodosOrdenadosPor(Foro.class, Foro_.tema);
    }

    //ABM de PREGUNTAS
    public void publicarPregunta(String titulo, Foro foro, UsuarioAcademico user) {
        this.persistencia.iniciarTransaccion();
        try {
            Pregunta pregunta = new Pregunta(titulo, foro, user);
            foro.agregarPregunta(pregunta);
            user.agregarPregunta(pregunta);
            this.persistencia.insertar(pregunta);
            this.persistencia.modificar(foro);
            this.persistencia.modificar(user);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }

    }
    
    public void modificarPregunta(Pregunta pregunta, String titulo){
        this.persistencia.iniciarTransaccion();
        pregunta.setTitulo(titulo);
        this.persistencia.modificar(pregunta);
        this.persistencia.confirmarTransaccion();
    }

    public void eliminarPregunta(Pregunta pregunta) {
        this.persistencia.iniciarTransaccion();
        UsuarioAcademico usuario = pregunta.getUsuario();
        if (usuario != null) {
            usuario.eliminarPregunta(pregunta);
            persistencia.modificar(usuario);
        }
        List<Respuesta> respuestas = pregunta.getRespuestas();
        for (Respuesta r : respuestas) {
            //eliminamos la respuesta con sus respectivas asociaciones
            this.eliminarRespuestaCompleta(r, persistencia);
        }
        persistencia.eliminar(pregunta);
        Foro foro = pregunta.getForo();
        foro.eliminarPregunta(pregunta);
        this.persistencia.modificar(foro);
        this.persistencia.confirmarTransaccion();
    }

    public void eliminarPreguntaCompleta(Pregunta pregunta, Persistencia pers) {
        UsuarioAcademico usuarioA = pregunta.getUsuario();
        if (usuarioA != null) {
            usuarioA.eliminarPregunta(pregunta);
            pers.modificar(usuarioA);
        }
        List<Respuesta> listaRespuestas = pregunta.getRespuestas();
        for (Respuesta respuesta : listaRespuestas) {
            //eliminamos la respuesta con sus respectivas asociaciones
            this.eliminarRespuestaCompleta(respuesta, pers);
        }
        pers.eliminar(pregunta);
    }

    public void eliminarRespuestaCompleta(Respuesta respuesta, Persistencia persis) {
        UsuarioAcademico usuarioA = respuesta.getUsuario();
        if (usuarioA != null) {
            usuarioA.eliminarRespuesta(respuesta);
            persis.modificar(usuarioA);
        }
        if (!respuesta.getVotos().isEmpty()) {
            this.eliminarVotos(respuesta, persis);
        }
        persis.eliminar(respuesta);
    }


    public List<Pregunta> verPreguntas(Foro foro) {
        return foro.getPreguntas();
    }

    public List listarPreguntas() {
        return this.persistencia.buscarTodos(Pregunta.class);
    }

    public List verDetallePregunta(Pregunta p) {
        List<Object> detalles = new ArrayList<>();
        detalles.add(0, p.getTitulo());
        detalles.add(1, p.getUsuario());
        detalles.add(2, p.getFecha());
        int cantRespuestas = p.getCantidadRespuestas();
        detalles.add(3, cantRespuestas);
        if (cantRespuestas != 0) {
            detalles.add(4, p.getUltimaRespuesta());
        } else {
            detalles.add(4, "-");
        }
        return detalles;
    }

    //ABM de RESPUESTAS
    public void publicarRespuesta(String respuesta, UsuarioAcademico user, Pregunta pregunta) {
        this.persistencia.iniciarTransaccion();
        try {
            Respuesta resp = new Respuesta(respuesta, user, pregunta);
            pregunta.agregarRespuesta(resp);
            user.agregarRespuesta(resp);
            this.persistencia.insertar(resp);
            this.persistencia.modificar(pregunta);
            this.persistencia.modificar(user);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }
    
    public void modificarRespuesta(Respuesta respuesta , String resp){
        this.persistencia.iniciarTransaccion();
        respuesta.setRespuesta(resp);
        this.persistencia.modificar(respuesta);
        this.persistencia.confirmarTransaccion();
    }

    public void eliminarRespuesta(Respuesta respuesta) {
        this.persistencia.iniciarTransaccion();
        try {
            Pregunta pregunta = respuesta.getPregunta();
            UsuarioAcademico ua = respuesta.getUsuario();
            ua.eliminarRespuesta(respuesta);
            pregunta.eliminarRespuesta(respuesta);
            if (!respuesta.getVotos().isEmpty()) {
                this.eliminarVotos(respuesta, this.persistencia);
                this.persistencia.modificar(ua);
                this.persistencia.modificar(pregunta);
                this.persistencia.eliminar(respuesta);

            } else {
                this.persistencia.modificar(ua);
                this.persistencia.modificar(pregunta);
                this.persistencia.eliminar(respuesta);

            }
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void eliminarVotos(Respuesta respuesta, Persistencia p) {
        try {
            List<Voto> votos = respuesta.getVotos();
            for (int i = 0; i < votos.size(); i++) {
                UsuarioAcademico ua = votos.get(i).getUsuario();
                ua.eliminarVoto(votos.get(i));
                p.modificar(ua);
                p.eliminar(votos.get(i));

            }
        } catch (Exception e) {
        }

    }

    public void votarRespuesta(Respuesta respuesta, Boolean valor, UsuarioAcademico user) {
        this.persistencia.iniciarTransaccion();
        try {
            List<Voto> votos = respuesta.getVotos();
            if (!votos.isEmpty()) {
                for (int i = 0; i < votos.size(); i++) {
                    Voto v = votos.get(i);
                    if ((v.getUsuario().equals(user))) {
                        if (v.getValor() != valor) {
                            v.setValor(valor);
                            this.persistencia.modificar(v);
                            this.persistencia.modificar(respuesta);
                            this.persistencia.modificar(user);
                        }

                    } else {

                        Voto voto = new Voto(respuesta, valor, user);
                        respuesta.agregarVotos(voto);
                        user.agregarVoto(voto);
                        this.persistencia.insertar(voto);
                        this.persistencia.modificar(respuesta);
                        this.persistencia.modificar(user);
                    }
                }
            } else {
                Voto voto = new Voto(respuesta, valor, user);
                respuesta.agregarVotos(voto);
                user.agregarVoto(voto);
                this.persistencia.insertar(voto);
                this.persistencia.modificar(respuesta);
                this.persistencia.modificar(user);
            }
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println("algo paso");
        }

    }

    public int obtenerCantVotosPositivos(Respuesta r) {
        return r.getCantVotosPos();
    }

    public int obtenerCantVotosNegativos(Respuesta r) {
        return r.genCantVotosNeg();
    }

    public List<Respuesta> verRespuestas(Pregunta pregunta) {
        return pregunta.getRespuestas();

    }

    public List verDetalleRespuesta(Respuesta respuesta) {

        List<Object> detalles = new ArrayList<>();
        detalles.add(0, respuesta.getUsuario());
        detalles.add(1, respuesta.getFecha());
        detalles.add(2, this.obtenerCantVotosPositivos(respuesta));
        detalles.add(3, this.obtenerCantVotosNegativos(respuesta));
        return detalles;
    }

    //ABM de MATERIAS
    public void crearMateria(String nombre, String link) {
        this.persistencia.iniciarTransaccion();
        try {
            Materia mate = new Materia(nombre.toUpperCase(), link);
            this.persistencia.insertar(mate);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void modificarMateria(Materia materia, String nombre, String link) {
        this.persistencia.iniciarTransaccion();
        try {
            materia.setNombre(nombre.toUpperCase());
            materia.setEnlace(link);
            this.persistencia.modificar(materia);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void eliminarMateria(Materia materia) {
        this.persistencia.iniciarTransaccion();
        try {
            List<UsuarioAcademico> profesores = materia.getProfesores();
            if (profesores.isEmpty()) {
                this.persistencia.eliminar(materia);
                this.persistencia.confirmarTransaccion();
            } else {
                for (UsuarioAcademico profesor : profesores) {
                    this.quitarMateria(materia, profesor);
                }
                this.persistencia.eliminar(materia);
                this.persistencia.confirmarTransaccion();
            }
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void asignarMateria(Materia materia, UsuarioAcademico profesor) {
        this.persistencia.iniciarTransaccion();
        try {
            profesor.agregarMateria(materia);
            this.persistencia.modificar(profesor);
            this.persistencia.confirmarTransaccion();
        } catch (Exception e) {
            this.persistencia.descartarTransaccion();
            System.out.println(e.getMessage());
        }
    }

    public void quitarMateria(Materia materia, UsuarioAcademico profesor) {
        try {
            profesor.quitarMateria(materia);
            this.persistencia.modificar(materia);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List listarMaterias() {
        return this.persistencia.buscarTodosOrdenadosPor(Materia.class, Materia_.nombre);
    }

    public List<Pregunta> buscarPregunta(String filtro, String busqueda) {
        List<Pregunta> preguntas = this.listarPreguntas();
        List<Pregunta> resultado = new ArrayList();
        if (!preguntas.isEmpty()) {
            if (filtro.equals("PREGUNTA")) {
                for (Pregunta p : preguntas) {
                    if (p.getTitulo().contains(busqueda)) {
                        resultado.add(p);
                    }
                }
            } else if (filtro.equals("FORO")) {
                for (Pregunta p : preguntas) {
                    if (p.getForo().getTema().contains(busqueda.toUpperCase())) {
                        resultado.add(p);
                    }
                }

            } else if (filtro.equals("USUARIO")) {
                for (Pregunta p : preguntas) {
                    if (p.getUsuario().getApellido().contains(busqueda) | p.getUsuario().getNombre().contains(busqueda)) {
                        resultado.add(p);
                    }
                }
            }

        }
        return resultado;

    }
}
