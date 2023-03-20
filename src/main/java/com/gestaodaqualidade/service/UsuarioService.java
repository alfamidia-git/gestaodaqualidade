package com.gestaodaqualidade.service;

import com.gestaodaqualidade.dto.request.SenhaRequest;
import com.gestaodaqualidade.dto.request.UsuarioRequest;
import com.gestaodaqualidade.dto.response.UsuarioResponse;
import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.model.Usuario;
import com.gestaodaqualidade.repository.EmpresaRepository;
import com.gestaodaqualidade.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired EmailService emailService;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public ResponseEntity<UsuarioResponse> criarOuAlterarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = null;
        boolean criarUsuario = false;

        if(usuarioRequest.getId() == null){
            criarUsuario = true;
            usuario = new Usuario();
            usuario.setDataCriacao(LocalDate.now());

            Empresa empresa = null;
            if(usuarioRequest.getEmpresaId() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não pode ser nula");
            }else{
                empresa = this.empresaRepository.findById(usuarioRequest.getEmpresaId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada!"));
            }
            usuario.setEmpresa(empresa);

            String codigoVerificao = UUID.randomUUID().toString().toUpperCase();
            usuario.setCodigoVerificacao(codigoVerificao);

        }else{
            usuario = this.repository.findById(usuarioRequest.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não localizado!"));
        }

        usuario.setNome(usuarioRequest.getNome() != null ? usuarioRequest.getNome() : usuario.getNome());
        usuario.setEmail(usuarioRequest.getEmail() != null ? usuarioRequest.getEmail() : usuario.getEmail());
        usuario.setPermissao(usuarioRequest.getPermissao() != null ? usuarioRequest.getPermissao() : usuario.getPermissao());

        usuario = this.repository.save(usuario);

        if(criarUsuario){
            emailService.enviarEmail(usuarioRequest.getEmail(), "Seu código de verificação é: <b>" + usuario.getCodigoVerificacao() + "</b>",
                    "Usuário criado no sistema de qualidade");
        }

        return new ResponseEntity<>(new UsuarioResponse(usuario), HttpStatus.CREATED);
    }

    public ResponseEntity<Void> criarSenha(SenhaRequest senhaRequest) {
        if(senhaRequest.getSenha() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha em branco");
        }

        if(senhaRequest.getCodigoVerificacao() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo de Verificação em branco");
        }

        if(senhaRequest.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email em branco");
        }

        Usuario usuario = this.repository.findByEmail(senhaRequest.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + senhaRequest.getEmail()));

        if(usuario.getCodigoVerificacao() == null || !usuario.getCodigoVerificacao().equals(senhaRequest.getCodigoVerificacao())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de verificação não coincide");
        }

        usuario.setSenha(this.passwordEncoder.encode(senhaRequest.getSenha()));
        usuario.setCodigoVerificacao(null);

        this.repository.save(usuario);

        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado: " + email));
    }
}
