-- V4__create_chatbot_qa_table.sql
-- MSSQL version: Create table for storing predefined chatbot questions and answers
-- MSSQL conversions:
-- AUTO_INCREMENT -> IDENTITY(1,1)
-- LONGTEXT -> NVARCHAR(MAX)
-- BOOLEAN -> BIT
-- INDEX in definition -> Separate CREATE INDEX statements
-- TIMESTAMP with ON UPDATE -> DATETIME2 with defaults

CREATE TABLE IF NOT EXISTS chatbot_qa (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    question NVARCHAR(500) NOT NULL,
    answer NVARCHAR(MAX) NOT NULL,
    is_active BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Create indexes for performance
CREATE INDEX idx_question ON chatbot_qa(question);
CREATE INDEX idx_is_active ON chatbot_qa(is_active);

-- Insert sample Q&A
INSERT INTO chatbot_qa (question, answer, is_active, created_at, updated_at) 
VALUES ('What is system name?', 'Tools Management System', 1, GETDATE(), GETDATE());
