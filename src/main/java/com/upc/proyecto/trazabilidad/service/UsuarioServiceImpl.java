package com.upc.proyecto.trazabilidad.service;

import com.upc.proyecto.trazabilidad.entity.Rol;
import com.upc.proyecto.trazabilidad.entity.Usuario;
import com.upc.proyecto.trazabilidad.repository.RolRepository;
import com.upc.proyecto.trazabilidad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Codificar la contraseña si viene nueva o sin codificar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else if (usuario.getIdUsuario() != null) {
            // Si es una actualización y no envía contraseña, mantener la existente
            usuarioRepository.findById(usuario.getIdUsuario()).ifPresent(u -> usuario.setPassword(u.getPassword()));
        }

        // Asignar rol por defecto si no tiene ninguno
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            Rol rolDefault = rolRepository.findByNombre("PRODUCTOR")
                    .orElseGet(() -> {
                        Rol nuevoRol = new Rol();
                        nuevoRol.setNombre("PRODUCTOR");
                        return rolRepository.save(nuevoRol);
                    });
            usuario.setRoles(Set.of(rolDefault));
        }

        return usuarioRepository.save(usuario);
    }
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }
}
