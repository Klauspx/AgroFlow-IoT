package br.unifap.pi.projetoirrigacao.config;

import br.unifap.pi.projetoirrigacao.model.Sensor;
import br.unifap.pi.projetoirrigacao.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public void run(String... args) throws Exception {
        // ATENÇÃO: Descomente a linha abaixo UMA VEZ para resetar o banco com as novas regras
        sensorRepository.deleteAll();

        if (sensorRepository.count() == 0) {
            System.out.println("Criando Regiões...");

            // Região A e B: Começam ATIVAS (Verde/Funcionando)
            Sensor regiaoA = new Sensor("Região A", 45.0);
            regiaoA.setAtivo(true);
            sensorRepository.save(regiaoA);

            Sensor regiaoB = new Sensor("Região B", 60.0);
            regiaoB.setAtivo(true);
            sensorRepository.save(regiaoB);

            // Região C e D: Começam em MANUTENÇÃO (Vermelho/False)
            Sensor regiaoC = new Sensor("Região C", 50.0);
            regiaoC.setAtivo(false); // Manutenção
            sensorRepository.save(regiaoC);

            Sensor regiaoD = new Sensor("Região D", 50.0);
            regiaoD.setAtivo(false); // Manutenção
            sensorRepository.save(regiaoD);

            System.out.println("Regiões configuradas!");
        }
    }
}