-- =============================================
-- POPULANDO USUÁRIOS COM HASH DE SENHAS
-- =============================================

INSERT INTO usuarios (id, email, password, name, role, data_criado, ativo) VALUES
                                                                               (1, 'admin@teste.com', '$2a$10$FZkDQEj6P2EwSPmW2wFQ6u9e9MHPJJu1DLOp13Pi2mJ7dJ7L4u8Qy', 'Administrador', 'ADMIN', CURRENT_TIMESTAMP, true),
                                                                               (2, 'usuario1@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'João Silva', 'USER', CURRENT_TIMESTAMP, true),
                                                                               (3, 'usuario2@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'Maria Oliveira', 'USER', CURRENT_TIMESTAMP, true),
                                                                               (4, 'usuario3@teste.com', '$2a$10$0JGpDcezkq7VdOH9K6Hc8u1R.D6vMSg6dKaN.8MHZbSNPs53lDD5i', 'Carlos Souza', 'USER', CURRENT_TIMESTAMP, false);

-- =============================================
-- POPULANDO QUARTOS
-- =============================================

INSERT INTO quarto (id, status, descricao, capacidade, name, valor, data_criacao) VALUES
                                                                                      (1, 'DISPONIVEL', 'Quarto Standard com vista para o jardim', 2, 'Quarto 101', 150.00, CURRENT_TIMESTAMP),
                                                                                      (2, 'DISPONIVEL', 'Quarto Deluxe com varanda e cama king size', 2, 'Quarto 102', 250.00, CURRENT_TIMESTAMP),
                                                                                      (3, 'OCUPADO', 'Suíte Família com 3 camas e cozinha', 4, 'Suíte 201', 400.00, CURRENT_TIMESTAMP),
                                                                                      (4, 'DISPONIVEL', 'Quarto Executivo com mesa de trabalho', 1, 'Quarto 103', 180.00, CURRENT_TIMESTAMP);

-- =============================================
-- POPULANDO RESERVAS
-- =============================================

INSERT INTO reserva (id, status, quarto_id, usuario_id, data_inicio, data_fim, valorTotal, data_criado) VALUES
                                                                                                            (1, 'CONFIRMADA', 1, 2, '2026-02-01', '2026-02-04', 450.00, CURRENT_TIMESTAMP),
                                                                                                            (2, 'CANCELADA', 2, 3, '2026-02-10', '2026-02-12', 500.00, CURRENT_TIMESTAMP),
                                                                                                            (3, 'CONFIRMADA', 3, 1, '2026-01-25', '2026-01-28', 1200.00, CURRENT_TIMESTAMP);
