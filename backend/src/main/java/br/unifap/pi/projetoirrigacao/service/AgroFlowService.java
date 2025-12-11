package br.unifap.pi.projetoirrigacao.service;

import br.unifap.pi.projetoirrigacao.dto.ConfiguracaoSensorRequest;
import br.unifap.pi.projetoirrigacao.dto.LeituraArduinoRequest;
import br.unifap.pi.projetoirrigacao.model.HistoricoAtividade;
import br.unifap.pi.projetoirrigacao.model.Sensor;
import br.unifap.pi.projetoirrigacao.model.Usuario;
import br.unifap.pi.projetoirrigacao.repository.HistoricoAtividadeRepository;
import br.unifap.pi.projetoirrigacao.repository.SensorRepository;
import br.unifap.pi.projetoirrigacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AgroFlowService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private HistoricoAtividadeRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- SENSORES E REGIÕES ---

    public List<Sensor> getSensores() {
        return sensorRepository.findAllByOrderByIdAsc();
    }

    // Salva alteração MANUAL (Botão do Site)
    @Transactional
    public void salvarConfiguracoes(List<ConfiguracaoSensorRequest> configs) {
        for (ConfiguracaoSensorRequest config : configs) {
            sensorRepository.findById(config.getId()).ifPresent(sensor -> {

                // Se mudou o estado (Manutenção vs Ativado)
                if (sensor.isAtivo() != config.isAtivo()) {
                    String textoHistorico;
                    if (config.isAtivo()) {
                        textoHistorico = "Sistema Ativado";
                    } else {
                        textoHistorico = "Em Manutenção";
                        // Se entrou em manutenção, "esquecemos" que a bomba estava ligada
                        sensor.setUltimaBombaLigada(false);
                    }
                    historicoRepository.save(new HistoricoAtividade(textoHistorico, sensor.getNome()));
                }

                sensor.setAtivo(config.isAtivo());
                sensorRepository.save(sensor);
            });
        }
    }

    public List<HistoricoAtividade> getHistorico() {
        return historicoRepository.findTop100ByOrderByDataHoraDesc();
    }

    // Processa leitura AUTOMÁTICA (Vinda do Wokwi)
    @Transactional
    public Sensor processarLeitura(LeituraArduinoRequest leitura) {
        Optional<Sensor> sensorOpt = sensorRepository.findByNome(leitura.getNomeSensor());

        if (sensorOpt.isEmpty()) {
            return null; // Retorna null se não achar o sensor
        }

        Sensor sensor = sensorOpt.get();

        if ("umidade".equalsIgnoreCase(leitura.getTipoLeitura())) {
            sensor.setUmidade(leitura.getValor());

            // Só executa lógica automática se estiver "Funcionando" (Verde)
            if (sensor.isAtivo()) {
                // Regra: Umidade menor que limite (padrão 40%) liga a bomba
                double limite = (sensor.getLimiteAtivacao() != null) ? sensor.getLimiteAtivacao() : 40.0;
                boolean deveLigar = sensor.getUmidade() < limite;

                // Só salva no histórico se o estado da bomba MUDOU
                if (deveLigar != sensor.isUltimaBombaLigada()) {
                    String acao = deveLigar ? "Ligou (Auto)" : "Desligou (Auto)";
                    historicoRepository.save(new HistoricoAtividade(acao, sensor.getNome()));

                    // Atualiza a memória
                    sensor.setUltimaBombaLigada(deveLigar);
                }
            }

        } else if ("temperatura".equalsIgnoreCase(leitura.getTipoLeitura())) {
            sensor.setTemperatura(leitura.getValor());
        }

        return sensorRepository.save(sensor); // Retorna o sensor atualizado
    }

    // Criar nova Região (Página Adicionar)
    public Sensor criarNovoSensor(Sensor sensor) {
        sensor.setUmidade(0.0);
        sensor.setTemperatura(0.0);
        sensor.setAtivo(true); // Começa ligado por padrão
        if (sensor.getLimiteAtivacao() == null) sensor.setLimiteAtivacao(40.0);
        return sensorRepository.save(sensor);
    }

    // Excluir Região (Botão Excluir)
    public void excluirSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    // --- USUÁRIOS (LOGIN E CADASTRO) ---

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }
        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String email, String senha) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            return userOpt.get().getSenha().equals(senha);
        }
        return false;
    }
}