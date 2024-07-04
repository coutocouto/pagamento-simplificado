CREATE TABLE IF NOT EXISTS users (
id VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wallets (
id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    account_type VARCHAR(10) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(255) PRIMARY KEY,
    receiver VARCHAR(255) NOT NULL,
    payer VARCHAR(255) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_receiver FOREIGN KEY (receiver) REFERENCES wallets(id) ON DELETE CASCADE,
    CONSTRAINT fk_payer FOREIGN KEY (payer) REFERENCES wallets(id) ON DELETE CASCADE
    );

CREATE UNIQUE INDEX idx_unique_cpf_cnpj ON users (cpf_cnpj);
CREATE UNIQUE INDEX idx_unique_email ON users (email);
