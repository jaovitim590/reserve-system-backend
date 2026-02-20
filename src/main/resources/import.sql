-- =============================================
-- USUÁRIOS
-- =============================================

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('admin@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'admin', 'ADMIN', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('joao.silva@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'João Silva', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('maria.souza@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Maria Souza', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('carlos.pereira@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Carlos Pereira', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('ana.costa@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Ana Costa', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('pedro.alves@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Pedro Alves', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('julia.lima@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Julia Lima', 'USER', CURRENT_TIMESTAMP, true);
INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('roberto.fernandes@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'Roberto Fernandes', 'USER', CURRENT_TIMESTAMP, true);

-- =============================================
-- QUARTOS
-- =============================================

-- Solteiro
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto compacto e confortável com uma cama de solteiro', 1, 'Quarto 101', 150.00, 'SOLTEIRO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto simples ideal para viajantes solo', 1, 'Quarto 102', 150.00, 'SOLTEIRO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO',    'Quarto aconchegante com vista para o jardim', 1, 'Quarto 103', 160.00, 'SOLTEIRO', CURRENT_TIMESTAMP);

-- Casal
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto romântico com cama de casal queen size', 2, 'Quarto 201', 250.00, 'CASAL', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO',    'Quarto elegante com decoração moderna', 2, 'Quarto 202', 250.00, 'CASAL', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto espaçoso com varanda privativa', 2, 'Quarto 203', 280.00, 'CASAL', CURRENT_TIMESTAMP);

-- Suíte
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Suíte ampla com sala de estar e hidromassagem', 3, 'Suíte 301', 450.00, 'SUITE', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Suíte familiar com cama king size e sofá-cama', 3, 'Suíte 302', 450.00, 'SUITE', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO',    'Suíte confortável com vista panorâmica', 3, 'Suíte 303', 480.00, 'SUITE', CURRENT_TIMESTAMP);

-- Luxo
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto de luxo com acabamentos premium e vista para o mar', 4, 'Luxo 401', 800.00, 'LUXO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO',    'Suíte presidencial com jacuzzi e terraço privativo', 4, 'Luxo 402', 850.00, 'LUXO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Penthouse luxuoso com serviço de mordomo', 4, 'Luxo 403', 900.00, 'LUXO', CURRENT_TIMESTAMP);

-- =============================================
-- RESERVAS (usuario_id 1=admin, 2=joao, 3=maria, 4=carlos, 5=ana, 6=pedro, 7=julia, 8=roberto)
-- =============================================

-- João
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     1,  2, '2026-02-20', '2026-02-23', 450.00,  CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 4,  2, '2026-01-10', '2026-01-13', 750.00,  CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     7,  2, '2026-05-01', '2026-05-05', 1800.00, CURRENT_TIMESTAMP);

-- Maria
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     4,  3, '2026-03-01', '2026-03-05', 1000.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     8,  3, '2026-04-10', '2026-04-14', 1800.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 1,  3, '2026-01-05', '2026-01-07', 300.00,  CURRENT_TIMESTAMP);

-- Carlos
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     10, 4, '2026-03-20', '2026-03-25', 4000.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     11, 4, '2026-06-01', '2026-06-05', 3400.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 5,  4, '2026-02-01', '2026-02-03', 500.00,  CURRENT_TIMESTAMP);

-- Ana
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     7,  5, '2026-03-15', '2026-03-18', 1350.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     2,  5, '2026-04-20', '2026-04-22', 300.00,  CURRENT_TIMESTAMP);

-- Pedro
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     6,  6, '2026-03-10', '2026-03-14', 1120.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 9,  6, '2026-01-20', '2026-01-23', 1440.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     12, 6, '2026-05-15', '2026-05-18', 2700.00, CURRENT_TIMESTAMP);

-- Julia
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     3,  7, '2026-04-01', '2026-04-03', 320.00,  CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     5,  7, '2026-05-10', '2026-05-13', 750.00,  CURRENT_TIMESTAMP);

-- Roberto
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     10, 8, '2026-04-10', '2026-04-13', 2400.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 4,  8, '2026-02-14', '2026-02-16', 500.00,  CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     8,  8, '2026-06-10', '2026-06-14', 1800.00, CURRENT_TIMESTAMP);

-- Admin (reservas de teste)
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     1,  1, '2026-02-20', '2026-02-23', 450.00,  CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     4,  1, '2026-03-01', '2026-03-05', 1000.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     7,  1, '2026-03-15', '2026-03-18', 1350.00, CURRENT_TIMESTAMP);
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA',     10, 1, '2026-04-10', '2026-04-13', 2400.00, CURRENT_TIMESTAMP);