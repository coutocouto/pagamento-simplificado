INSERT INTO users (id, full_name, cpf_cnpj, email, password) VALUES
     ('1', 'João Silva', '12345678901', 'joao.silva@example.com', 'senha123'),
     ('2', 'Maria Oliveira', '23456789012', 'maria.oliveira@example.com', 'senha456'),
     ('3', 'Carlos Pereira', '34567890123', 'carlos.pereira@example.com', 'senha789'),
     ('4', 'Ana Costa', '45678901234', 'ana.costa@example.com', 'senha101112');

INSERT INTO wallets (id, user_id, account_type, balance, created_at) VALUES
    ('10', '1', 'USER', 1500.00, NOW()),
    ('11', '2', 'SELLER', 2500.00, NOW()),
    ('12', '3', 'USER', 3000.00, NOW()),
    ('13', '4', 'SELLER', 4000.00, NOW());
