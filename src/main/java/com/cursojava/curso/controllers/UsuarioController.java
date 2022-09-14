package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario(id, "Jeferson", "Jimenez", "jeferson.jl99@gmail.com", "3228186615", "Holaquease");
        return usuario;
    }

    @RequestMapping(value = "api/usuario/{id}", method = RequestMethod.DELETE)
    public void deleteUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (validarToken(token)) {
            return;
        }
        usuarioDao.delete(id);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {
        if (validarToken(token)) {
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    public boolean validarToken(String token) {
        String uduarioId = jwtUtil.getKey(token);
        return uduarioId != null;
    }

//    @RequestMapping(value = "usuarios")
//    public List<Usuario> getUsuarios() {
//
//        List <Usuario> usuarios = new ArrayList<>();
//
//        Usuario usuario1 = new Usuario(1L, "Jeferson", "Jimenez",
//                "jeferson.jl99@gmail.com", "3228186615", "Holaquease");
//        Usuario usuario2 = new Usuario(2L, "Camilo", "Remirez",
//                "camilo.rod@gmail.com", "4584521", "jaj14485");
//        Usuario usuario3 = new Usuario(3L, "David", "Suarez",
//                "suarezdav@gmail.com", "864484548", "nocontra");
//        Usuario usuario4 = new Usuario(4L, "Jaime", "Duarte",
//                "duartejaime@gmail.com", "4534658", "feefea4145");
//        Usuario usuario5 = new Usuario(5L, "Luis", "Linares",
//                "soyluiselperrasaso@gmail.com", "5341575", "dbsdbd485481");
//
//        usuarios.add(usuario1);
//        usuarios.add(usuario2);
//        usuarios.add(usuario3);
//        usuarios.add(usuario4);
//        usuarios.add(usuario5);
//
//        return usuarios;
//    }
}