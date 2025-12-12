# 🌱 AgroFlow - Sistema de Irrigação Inteligente

Sistema IoT completo para monitoramento e automação de irrigação, desenvolvido para auxiliar agricultores no controle de umidade e temperatura do solo.

## 📸 Telas do Sistema

### Painel Principal
Visão geral de todas as regiões monitoradas em tempo real.
![Painel Principal](screenshots/detalhes_regiao.png)

### Detalhes e Histórico
Controle manual da bomba e histórico de atividades da região.
![Detalhes da Região](screenshots/detalhes_regiao.png)

### Simulação IoT (Wokwi)
Circuito com ESP32, sensores DHT22 e relés para as bombas.
![Diagrama Wokwi](screenshots/diagrama_wokwi.png)

## 🚀 Tecnologias Utilizadas

### Backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

### IoT & Hardware
![Arduino](https://img.shields.io/badge/-Arduino-00979D?style=for-the-badge&logo=Arduino&logoColor=white)
![C++](https://img.shields.io/badge/c++-%2300599C.svg?style=for-the-badge&logo=c%2B%2B&logoColor=white)

---

## ⚙️ Funcionalidades

* [x] Monitoramento em tempo real de Umidade e Temperatura.
* [x] Controle automático da bomba de água baseado na umidade.
* [x] Controle manual (Remoto) via Web para ligar/desligar ou entrar em modo Manutenção.
* [x] Histórico de atividades e acionamentos.
* [x] Sistema de Login e Cadastro de Usuários.

## 🛠️ Como rodar o projeto

### Pré-requisitos
* Java JDK 17+
* PostgreSQL
* Wokwi (Simulador Web)

### Passos
1.  Clone o repositório.
2.  Configure o `application.properties` com seu banco de dados local.
3.  Inicie o Backend Java.
4.  Abra o arquivo `index.html` no navegador (via Live Server).
5.  Para acesso externo (celular/IoT), utilize o `localtunnel` nas portas 8080 e 5500.

---
Desenvolvido por **Nadson Klaus**.
