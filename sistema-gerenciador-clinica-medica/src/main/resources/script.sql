-- ============================================================
-- 📘 SISTEMA DE CLÍNICA MÉDICA - BANCO DE DADOS (MySQL)
-- Desenvolvido para o projeto SENAI - Curso de TI
-- Estrutura com prefixo: T_SCM_  (Sistema Clínica Médica)
-- ============================================================
-- 1️⃣ CRIAÇÃO DO BANCO DE DADOS
CREATE DATABASE IF NOT EXISTS clinica_scm CHARACTER
SET
  utf8mb4 COLLATE utf8mb4_unicode_ci;

USE clinica_scm;

-- ============================================================
-- 2️⃣ TABELA DE PACIENTES
-- ============================================================
CREATE TABLE
  IF NOT EXISTS T_SCM_PACIENTES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    historico TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  );

-- ============================================================
-- 3️⃣ TABELA DE MÉDICOS
-- ============================================================
CREATE TABLE
  IF NOT EXISTS T_SCM_MEDICOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    horarios_disponiveis TEXT,
    telefone VARCHAR(20),
    email VARCHAR(150),
    ativo TINYINT (1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  );

-- ============================================================
-- 4️⃣ TABELA DE USUÁRIOS (LOGIN)
-- ============================================================
CREATE TABLE
  IF NOT EXISTS T_SCM_USUARIOS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    nome_completo VARCHAR(150),
    papel ENUM ('admin', 'medico', 'secretaria') DEFAULT 'secretaria',
    ativo TINYINT (1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  );

-- ============================================================
-- 5️⃣ TABELA DE CONSULTAS
-- ============================================================
CREATE TABLE
  IF NOT EXISTS T_SCM_CONSULTAS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    medico_id INT NOT NULL,
    data_hora DATETIME NOT NULL,
    status ENUM ('agendada', 'realizada', 'cancelada') DEFAULT 'agendada',
    sintomas TEXT,
    diagnostico TEXT,
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- RELACIONAMENTOS (chaves estrangeiras)
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES T_SCM_PACIENTES (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES T_SCM_MEDICOS (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    -- Impede que um mesmo médico tenha duas consultas no mesmo horário
    UNIQUE KEY uk_medico_data (medico_id, data_hora)
  );

CREATE TABLE
  IF NOT EXISTS T_SCM_ATENDIMENTO (
    id_atendimento int not null AUTO_INCREMENT,
    ds_diagnostico VARCHAR(255) NOT NULL,
    ds_sintomas VARCHAR(255) NOT NULL,
    ds_observacoes VARCHAR(255) NOT NULL,
    id_paciente int not null,
    id_medico int not null
  )
alter table T_SCM_ATENDIMENTO add CONSTRAINT FK_PACIENTE_ATENDIMENTO FOREIGN KEY (id_paciente) REFERENCES T_SCM_PACIENTES,
add CONSTRAINT FK_MEDICO_ATENDIMENTO FOREIGN KEY (id_medico) REFERENCES T_SCM_MEDICOS;

-- ============================================================
-- 6️⃣ VIEW OPCIONAL (Relatório de Consultas)
-- ============================================================
CREATE
OR REPLACE VIEW V_SCM_CONSULTAS_COMPLETAS AS
SELECT
  c.id AS id_consulta,
  c.data_hora,
  c.status,
  p.nome AS nome_paciente,
  m.nome AS nome_medico,
  m.especialidade,
  c.sintomas,
  c.diagnostico,
  c.observacoes
FROM
  T_SCM_CONSULTAS c
  JOIN T_SCM_PACIENTES p ON p.id = c.paciente_id
  JOIN T_SCM_MEDICOS m ON m.id = c.medico_id;

-- ============================================================
-- 7️⃣ DADOS EXEMPLO (opcionais para testes)
-- ============================================================
-- Pacientes
INSERT INTO
  T_SCM_PACIENTES (nome, cpf, telefone, endereco, historico)
VALUES
  (
    'Maria Silva',
    '12345678901',
    '(47)99999-1111',
    'Rua das Flores, 123',
    'Alergia a penicilina.'
  ),
  (
    'João Oliveira',
    '98765432100',
    '(47)98888-2222',
    'Av. Beira Rio, 456',
    'Hipertenso.'
  );

-- Médicos
INSERT INTO
  T_SCM_MEDICOS (nome, crm, especialidade, telefone, email)
VALUES
  (
    'Dr. Paulo Mendes',
    'CRM1234',
    'Cardiologia',
    '(47)97777-3333',
    'paulo.mendes@clinica.com'
  ),
  (
    'Dra. Ana Costa',
    'CRM5678',
    'Pediatria',
    '(47)96666-4444',
    'ana.costa@clinica.com'
  );

-- Usuários
INSERT INTO
  T_SCM_USUARIOS (username, senha_hash, nome_completo, papel)
VALUES
  ('admin', '123456', 'Administrador Geral', 'admin'),
  (
    'secretaria',
    '123456',
    'Secretária da Clínica',
    'secretaria'
  );

-- Consultas (exemplo)
INSERT INTO
  T_SCM_CONSULTAS (
    paciente_id,
    medico_id,
    data_hora,
    status,
    sintomas,
    diagnostico
  )
VALUES
  (
    1,
    1,
    '2025-11-20 09:00:00',
    'agendada',
    'Dor no peito',
    'Avaliação inicial'
  ),
  (
    2,
    2,
    '2025-11-21 14:00:00',
    'agendada',
    'Febre alta',
    'Em observação'
  );

-- ============================================================
-- ✅ FIM DO SCRIPT
-- ============================================================
USE clinica_scm;

SELECT
  a.*,
  m.nome as nome_medico,
  m.crm ,
  m.especialidade,
  m.horarios_disponiveis,
  m.telefone as telefone_medico,
  m.email as email_medico,
  m.ativo,
  m.created_at,
  m.updated_at,
  p.nome as nome_paciente,
  p.cpf,
  p.telefone as telefone_paciente,
  p.endereco,
  p.historico,
  p.created_at,
  p.updated_at
FROM
  T_SCM_ATENDIMENTO a
  inner join T_SCM_MEDICOS m on m.id = a.id_medico
  inner join T_SCM_PACIENTES p on p.id = a.id_paciente;