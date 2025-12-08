# 🏥 Sistema de Clínica Médica

Sistema completo para gerenciamento de uma clínica médica, incluindo **cadastro de pacientes e médicos, agendamentos, prontuários, relatórios e login**.  
Cada módulo foi desenvolvido em uma branch própria e cada integrante ficou responsável por um conjunto completo de funcionalidades.

---

##  Equipe & Responsabilidades

###  Camile
**Feature:** `feature/cadastro-pacientes`  
- Modelagem da entidade **Paciente**  
- Tela de cadastro e edição  
- Regras de validação (CPF, histórico, contato)  
- Integração com banco e backend  

---

###  Isabely
**Feature:** `feature/cadastro-medicos`  
- Modelagem da entidade **Médico**  
- Cadastro de CRM, especialidade e horários  
- Integração com módulo de agendamentos  
- CRUD completo na interface  

---

###  Náthaly
**Feature:** `feature/agendamentos`  
- Agendamento paciente + médico + horário  
- Verificação de disponibilidade  
- Regras de conflito entre consultas  
- Atualização automática da agenda  

---

###  Sofia
**Feature:** `feature/prontuario`  
- Registro de sintomas, diagnóstico e observações  
- Associa prontuário à consulta realizada  
- Histórico médico do paciente  
- Edição e armazenamento seguro  

---

###  Anna
**Feature:** `feature/relatorios-login`  
- Sistema de **login** para secretária/médico  
- Relatórios de consultas por médico  
- Relatórios de consultas canceladas  
- Histórico de atendimentos  

---

##  Estrutura do Projeto (Branches)

| Branch                       | Responsável | Descrição                                   |
|-----------------------------|-------------|-----------------------------------------------|
| `main`                      | Todos       | Versão final estável                          |
| `feature/cadastro-pacientes` | Camile      | Cadastro e gestão de pacientes                |
| `feature/cadastro-medicos`   | Isabely     | Cadastro e gestão de médicos                  |
| `feature/agendamentos`       | Náthaly     | Agendamento de consultas                      |
| `feature/prontuario`         | Sofia       | Registro e consulta de prontuários            |
| `feature/relatorios-login`   | Anna        | Relatórios e sistema de login                 |

---

## 📦 Funcionalidades

### ✔️ Cadastros
- Cadastro de pacientes  
- Cadastro de médicos (CRM, especialidade, horários)

### ✔️ Atendimentos
- Agendamento de consultas  
- Verificação de disponibilidade  
- Registro de prontuário (consulta, sintomas, diagnóstico)

### ✔️ Administração
- Login de usuários  
- Controle básico do sistema

### ✔️ Relatórios
- Consultas por médico  
- Consultas canceladas  
- Histórico geral de atendimentos  

---

## 🚀 Como Executar o Projeto

###  Clonar o repositório
```sh
git clone https://github.com/seu-usuario/sistema-clinica-medica.git
cd sistema-clinica-medica
