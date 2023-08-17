CREATE ROLE business WITH LOGIN PASSWORD 'business123';
CREATE SCHEMA IF NOT EXISTS business AUTHORIZATION business;
ALTER ROLE business SET search_path TO business;
