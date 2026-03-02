-- Migration: Convert issuance_date and returnDate from DATE to DATETIME for accurate timestamps
-- MSSQL Syntax: Use ALTER COLUMN for MSSQL

-- Update issuance_requests table
ALTER TABLE issuance_requests
  ALTER COLUMN issuance_date DATETIME NOT NULL;

ALTER TABLE issuance_requests
  ALTER COLUMN return_date DATETIME NULL;

-- Update return_records table
ALTER TABLE return_records
  ALTER COLUMN actual_return_date DATETIME NOT NULL;
