-- =============================================
-- POPULANDO USUÁRIOS COM HASH DE SENHAS
-- =============================================

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('admin@gmail.com', '$2a$10$nGSRU2qTbknjTbek9XfZNeko2JJpjmKsiaagUOS33XTC8BIpd59uq', 'admin', 'ADMIN', CURRENT_TIMESTAMP, true);

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('usuario1@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'João Silva', 'USER', CURRENT_TIMESTAMP, true);

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('usuario2@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'Maria Oliveira', 'USER', CURRENT_TIMESTAMP, true);

INSERT INTO usuarios (email, password, name, role, data_criado, ativo) VALUES ('usuario3@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'Carlos Souza', 'USER', CURRENT_TIMESTAMP, false);

-- =============================================
-- POPULANDO QUARTOS
-- =============================================

INSERT INTO quarto (status, descricao, capacidade, name, valor, data_criacao) VALUES ('VAGO', 'Quarto Standard com vista para o jardim', 2, 'Quarto 101', 150.00, CURRENT_TIMESTAMP);

INSERT INTO quarto (status, descricao, capacidade, name, valor, data_criacao) VALUES ('VAGO', 'Quarto Deluxe com varanda e cama king size', 2, 'Quarto 102', 250.00, CURRENT_TIMESTAMP);

INSERT INTO quarto (status, descricao, capacidade, name, valor, data_criacao) VALUES ('RESERVADO', 'Suíte Família com 3 camas e cozinha', 4, 'Suíte 201', 400.00, CURRENT_TIMESTAMP);

INSERT INTO quarto (status, descricao, capacidade, name, valor, data_criacao) VALUES ('VAGO', 'Quarto Executivo com mesa de trabalho', 1, 'Quarto 103', 180.00, CURRENT_TIMESTAMP);

-- =============================================
-- POPULANDO RESERVAS
-- =============================================

INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVO', 1, 1, '2026-02-01', '2026-02-04', 450.00, CURRENT_TIMESTAMP);

INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('CANCELADO', 2, 2, '2026-02-10', '2026-02-12', 500.00, CURRENT_TIMESTAMP);

INSERT INTO reserva (status, quarto_id, usuario_id, data_inicio, data_fim, valor_total, data_criado) VALUES ('ATIVO', 3, 1, '2026-01-25', '2026-01-28', 1200.00, CURRENT_TIMESTAMP);