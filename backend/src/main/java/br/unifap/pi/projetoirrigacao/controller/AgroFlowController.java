package br.unifap.pi.projetoirrigacao.controller;

import br.unifap.pi.projetoirrigacao.dto.ConfiguracaoSensorRequest;
import br.unifap.pi.projetoirrigacao.dto.LeituraArduinoRequest;
import br.unifap.pi.projetoirrigacao.dto.LoginRequest;
import br.unifap.pi.projetoirrigacao.model.HistoricoAtividade;
import br.unifap.pi.projetoirrigacao.model.Sensor;
import br.unifap.pi.projetoirrigacao.model.Usuario;
import br.unifap.pi.projetoirrigacao.service.AgroFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite acesso do Front-end e Wokwi
public class AgroFlowController {

    @Autowired
    private AgroFlowService agroFlowService;

    // --- ENDPOINTS DO SISTEMA DE IRRIGAÇÃO ---

    // Listar todas as regiões (Para o Front-end)
    @GetMapping("/sensores")
    public ResponseEntity<List<Sensor>> getSensores() {
        return ResponseEntity.ok(agroFlowService.getSensores());
    }

    // Salvar botão manual (Manutenção/Ativo)
    @PostMapping("/sensores/config")
    public ResponseEntity<Void> salvarConfiguracoes(@RequestBody List<ConfiguracaoSensorRequest> configs) {
        agroFlowService.salvarConfiguracoes(configs);
        return ResponseEntity.ok().build();
    }

    // Listar histórico
    @GetMapping("/historico")
    public ResponseEntity<List<HistoricoAtividade>> getHistorico() {
        return ResponseEntity.ok(agroFlowService.getHistorico());
    }

    // Receber dados do Wokwi (e responder com status atualizado)
    @PostMapping("/leitura")
    public ResponseEntity<Sensor> receberLeitura(@RequestBody LeituraArduinoRequest leitura) {
        Sensor sensorAtualizado = agroFlowService.processarLeitura(leitura);

        if (sensorAtualizado != null) {
            return ResponseEntity.ok(sensorAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Criar nova região
    @PostMapping("/sensores")
    public ResponseEntity<Sensor> criarSensor(@RequestBody Sensor novoSensor) {
        return ResponseEntity.ok(agroFlowService.criarNovoSensor(novoSensor));
    }

    // Deletar região
    @DeleteMapping("/sensores/{id}")
    public ResponseEntity<Void> excluirSensor(@PathVariable Long id) {
        agroFlowService.excluirSensor(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE USUÁRIO ---

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest login) {
        boolean sucesso = agroFlowService.autenticar(login.getEmail(), login.getSenha());
        if (sucesso) {
            return ResponseEntity.ok("Login aprovado");
        } else {
            return ResponseEntity.status(401).body("Email ou senha incorretos");
        }
    }

    // Cadastro
    @PostMapping("/usuarios")
    public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(agroFlowService.cadastrarUsuario(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}