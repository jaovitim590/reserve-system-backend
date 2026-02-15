-- =============================================
-- POPULANDO USUÁRIOS COM HASH DE SENHAS
-- =============================================

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('admin@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'admin', 'ADMIN', CURRENT_TIMESTAMP, true);

-- =============================================
-- POPULANDO QUARTOS
-- =============================================

-- Quartos Solteiro
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto compacto e confortável com uma cama de solteiro', 1, 'Quarto 101', 150.00, 'SOLTEIRO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto simples ideal para viajantes solo', 1, 'Quarto 102', 150.00, 'SOLTEIRO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO', 'Quarto aconchegante com vista para o jardim', 1, 'Quarto 103', 160.00, 'SOLTEIRO', CURRENT_TIMESTAMP);

-- Quartos Casal
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto romântico com cama de casal queen size', 2, 'Quarto 201', 250.00, 'CASAL', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO', 'Quarto elegante com decoração moderna', 2, 'Quarto 202', 250.00, 'CASAL', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto espaçoso com varanda privativa', 2, 'Quarto 203', 280.00, 'CASAL', CURRENT_TIMESTAMP);

-- Quartos Suíte
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Suíte ampla com sala de estar e hidromassagem', 3, 'Suíte 301', 450.00, 'SUITE', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Suíte familiar com cama king size e sofá-cama', 3, 'Suíte 302', 450.00, 'SUITE', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO', 'Suíte confortável com vista panorâmica', 3, 'Suíte 303', 480.00, 'SUITE', CURRENT_TIMESTAMP);

-- Quartos Luxo
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Quarto de luxo com acabamentos premium e vista para o mar', 4, 'Luxo 401', 800.00, 'LUXO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('OCUPADO', 'Suíte presidencial com jacuzzi e terraço privativo', 4, 'Luxo 402', 850.00, 'LUXO', CURRENT_TIMESTAMP);
INSERT INTO quarto (status, descricao, capacidade, name, valor, tipo, data_criacao) VALUES ('DISPONIVEL', 'Penthouse luxuoso com serviço de mordomo', 4, 'Luxo 403', 900.00, 'LUXO', CURRENT_TIMESTAMP);

-- =============================================
-- POPULANDO RESERVAS
-- =============================================

-- Reserva para Quarto SOLTEIRO (Quarto 101)
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA', 1, 1, '2026-02-20', '2026-02-23', 450.00, CURRENT_TIMESTAMP);

-- Reserva para Quarto CASAL (Quarto 201)
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA', 4, 1, '2026-03-01', '2026-03-05', 1000.00, CURRENT_TIMESTAMP);

-- Reserva para Quarto SUITE (Suíte 301)
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA', 7, 1, '2026-03-15', '2026-03-18', 1350.00, CURRENT_TIMESTAMP);

-- Reserva para Quarto LUXO (Luxo 401)
INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVA', 10, 1, '2026-04-10', '2026-04-13', 2400.00, CURRENT_TIMESTAMP);