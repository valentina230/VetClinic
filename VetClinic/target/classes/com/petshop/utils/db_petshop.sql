-- Create client table first
CREATE TABLE client (
   CLIENT_ID SERIAL PRIMARY KEY,
   NAME varchar NOT NULL, 
   LAST_NAME varchar NOT NULL, 
   EMAIL varchar NOT NULL, 
   PHONE varchar NOT NULL, 
   IDENTIFICATION varchar NOT NULL, 
   ADDRESS varchar NOT NULL 
);

-- Insert data into client table
INSERT INTO client (CLIENT_ID, NAME, LAST_NAME, EMAIL, PHONE, IDENTIFICATION, ADDRESS) VALUES
(1, 'Alice', 'Smith', 'alice.smith@example.com', '555-1234', 'ID123456', '123 Maple Street'),
(2, 'Bob', 'Johnson', 'bob.johnson@example.com', '555-5678', 'ID789012', '456 Oak Avenue'),
(3, 'Carol', 'Williams', 'carol.williams@example.com', '555-8765', 'ID345678', '789 Pine Road'),
(4, 'David', 'Brown', 'david.brown@example.com', '555-4321', 'ID901234', '321 Birch Lane'),
(5, 'Eve', 'Davis', 'eve.davis@example.com', '555-5555', 'ID567890', '987 Cedar Boulevard'),
(6, 'Frank', 'Miller', 'frank.miller@example.com', '555-6666', 'ID678901', '654 Spruce Street'),
(7, 'Grace', 'Wilson', 'grace.wilson@example.com', '555-7777', 'ID789012', '321 Elm Avenue');

-- Create vet_doctor table
CREATE TABLE vet_doctor (
   VET_DOCTOR_ID SERIAL PRIMARY KEY,
   NAME varchar NOT NULL, 
   LAST_NAME varchar NOT NULL, 
   EMAIL varchar NOT NULL, 
   PHONE varchar NOT NULL, 
   IDENTIFICATION varchar NOT NULL, 
   ADDRESS varchar NOT NULL,
   SPECIALITATION varchar NOT NULL,
   STATUS varchar NOT NULL
);

-- Insert data into vet_doctor table
INSERT INTO vet_doctor (VET_DOCTOR_ID, NAME, LAST_NAME, EMAIL, PHONE, IDENTIFICATION, ADDRESS, SPECIALITATION, STATUS) VALUES
(1, 'John', 'Doe', 'john.doe@example.com', '555-1010', 'VET123456', '101 Elm Street', 'Surgeon', 'DISMISSED'),
(2, 'Emily', 'Clark', 'emily.clark@example.com', '555-2020', 'VET789012', '202 Cedar Drive', 'Wildlife Specialist', 'ON_VACATION'),
(3, 'Michael', 'Lewis', 'michael.lewis@example.com', '555-3030', 'VET345678', '303 Spruce Way', 'Rehabilitation', 'WORKING'),
(4, 'Sarah', 'Walker', 'sarah.walker@example.com', '555-4040', 'VET901234', '404 Fir Avenue', 'Surgeon', 'DISMISSED'),
(5, 'Thomas', 'Hall', 'thomas.hall@example.com', '555-5050', 'VET012345', '505 Beech Lane', 'Dermatologist', 'ON_VACATION'),
(6, 'Nancy', 'Green', 'nancy.green@example.com', '555-6060', 'VET678901', '606 Oak Circle', 'Nutritionist', 'DISMISSED'),
(7, 'Eric', 'White', 'eric.white@example.com', '555-7070', 'VET789123', '707 Maple Drive', 'Dentist', 'DISMISSED');

-- Create pet table
CREATE TABLE pet (
   PET_ID SERIAL PRIMARY KEY,
   PET_NAME varchar NOT NULL,
   SPECIES varchar NOT NULL,
   RACE varchar NOT NULL,
   SEX varchar NOT NULL,
   YEAR_BORN int NOT NULL,
   STATUS varchar NOT NULL,
   ID_CLIENT int REFERENCES client(CLIENT_ID)
);

-- Insert data into pet table
INSERT INTO pet (PET_ID, PET_NAME, SPECIES, RACE, SEX, YEAR_BORN, STATUS, ID_CLIENT) VALUES
(1, 'Buddy', 'Dog', 'Golden Retriever', 'Male', 2018, 'Hospitalized', 1),
(2, 'Mittens', 'Cat', 'Maine Coon', 'Female', 2020, 'Under_Observation', 2),
(3, 'Bella', 'Horse', 'Arabian', 'Female', 2015, 'Healthy', 3),
(4, 'Wilbur', 'Pig', 'Yorkshire', 'Male', 2019, 'Recovering', 4),
(5, 'Rocky', 'Dog', 'Bulldog', 'Male', 2017, 'Hospitalized', 5),
(6, 'Luna', 'Cat', 'Siamese', 'Female', 2021, 'Deceased', 6),
(7, 'Shadow', 'Rabbit', 'Dutch', 'Male', 2022, 'Healthy', 7);

-- Create chip table
CREATE TABLE chip (
   CHIP_ID SERIAL PRIMARY KEY,
   FACTORY_ID int NOT NULL,
   TECHNOLOGY varchar Not  NULL,
   STATUS varchar NOT NULL,
   DATE_INSTALATION date NOT NULL,
   EXPIRATION_DATE date DEFAULT NULL,
   PET_ID int REFERENCES pet(PET_ID)
);


-- Insert data into chip table
INSERT INTO chip (CHIP_ID, FACTORY_ID, TECHNOLOGY, STATUS, DATE_INSTALATION, EXPIRATION_DATE, PET_ID) VALUES
(1, 1001, 'Fullduplex_FDX', 'Active', '2010-04-10', NULL, 1),
(2, 1003, 'Fullduplex_FDX', 'Active', '2015-03-15', NULL, 3),
(3, 1004, 'Semiduplex_HDX', 'Active', '2015-03-15', NULL, 4),
(4, 1005, 'Fullduplex_FDX', 'Active', '2024-02-24', NULL, 5),
(5, 1006, 'Semiduplex_HDX', 'Active', '2010-04-10', NULL, 6),
(6, 1007, 'Fullduplex_FDX', 'Active', '2010-04-10', NULL, 7);


-- Create history_pet table
CREATE TABLE history_pet (
   HISTORY_ID SERIAL PRIMARY KEY,
   CLIENT_ID int REFERENCES client(CLIENT_ID),
   PET_ID int REFERENCES pet(PET_ID),
   VET_DOCTOR_ID int REFERENCES vet_doctor(VET_DOCTOR_ID),
   DESCRIPTION varchar NOT NULL,
   DIAGNOSTIC varchar DEFAULT NULL,
   DATE_OF_CONSULT date NOT NULL
);

-- Insert data into history_pet table
INSERT INTO history_pet (HISTORY_ID, CLIENT_ID, PET_ID, VET_DOCTOR_ID, DESCRIPTION, DIAGNOSTIC, DATE_OF_CONSULT) VALUES
(1, 1, 1, 1, 'Routine check-up', 'Healthy', '2024-02-24'),
(2, 2, 2, 2, 'Vaccination', 'Vaccinated', '2015-03-15'),
(3, 3, 3, 3, 'Lameness in leg', 'Tendon injury', '2010-04-10'),
(5, 4, 4, 4, 'Skin rash', 'Allergic reaction', '2020-05-23'),
(6, 5, 5, 5, 'Discomfort when breathing', 'Bronchitis', '2018-06-22'),
(7, 6, 6, 6, 'Spaying surgery', 'Spayed', '2014-07-03'),
(8, 7, 7, 7, 'Annual check-up', 'Healthy', '2022-08-02');

