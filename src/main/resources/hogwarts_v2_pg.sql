-- Conectarse a la base de datos postgres
\c postgres;

-- Verificar si la base de datos 'hogwarts' ya existe y crearla si no existe
SELECT 'CREATE DATABASE hogwarts'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'hogwarts')\gexec

-- Conectarse a la base de datos hogwarts
\c hogwarts;

-- Borrar tablas si existen
DROP TABLE IF EXISTS Casa, Estudiante, Mascota, Asignatura, Profesor, Estudiante_Asignatura;

-- Crear tabla Asignatura
CREATE TABLE IF NOT EXISTS Asignatura (
    id_asignatura SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    aula VARCHAR(50) NOT NULL,
    obligatoria BOOLEAN NOT NULL CHECK (obligatoria IN (TRUE, FALSE))
);

-- Crear tabla Profesor
CREATE TABLE IF NOT EXISTS Profesor (
    id_profesor SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    id_asignatura INT,
    fecha_inicio DATE NOT NULL CHECK (fecha_inicio >= '1800-01-01'),
    UNIQUE (nombre, apellido),
    FOREIGN KEY (id_asignatura) REFERENCES Asignatura(id_asignatura) ON DELETE SET NULL
);

-- Crear tabla Casa
CREATE TABLE IF NOT EXISTS Casa (
    id_casa SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    fundador VARCHAR(50) NOT NULL,
    id_jefe INT, -- Puede ser NULL si la casa no tiene jefe
    fantasma VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_jefe) REFERENCES Profesor(id_profesor) ON DELETE SET NULL
);

-- Crear tabla Estudiante
CREATE TABLE IF NOT EXISTS Estudiante (
    id_estudiante SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    id_casa INT,
    anyo_curso INT NOT NULL CHECK (anyo_curso BETWEEN 1 AND 7),
    fecha_nacimiento DATE NOT NULL CHECK (fecha_nacimiento <= CURRENT_DATE),
    UNIQUE (nombre, apellido),
    FOREIGN KEY (id_casa) REFERENCES Casa(id_casa) ON DELETE SET NULL
);

-- Crear tabla Mascota
CREATE TABLE IF NOT EXISTS Mascota (
    id_mascota SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    id_estudiante INT NOT NULL, -- No se permite NULL para asegurar asociación estricta
    FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante) ON DELETE CASCADE
);

-- Crear tabla Estudiante_Asignatura
CREATE TABLE IF NOT EXISTS Estudiante_Asignatura (
    id_estudiante INT NOT NULL,
    id_asignatura INT NOT NULL,
    calificacion DECIMAL(3,1) CHECK (calificacion BETWEEN 0 AND 10),
    PRIMARY KEY (id_estudiante, id_asignatura),
    FOREIGN KEY (id_estudiante) REFERENCES Estudiante(id_estudiante) ON DELETE CASCADE,
    FOREIGN KEY (id_asignatura) REFERENCES Asignatura(id_asignatura) ON DELETE RESTRICT
	-- Evita que se elimine una asignatura que esté asociada a algún estudiante
);

-- Insertar Asignaturas (16)
INSERT INTO Asignatura (nombre, aula, obligatoria) VALUES
('Transformaciones', '1B', TRUE),
('Pociones', 'Mazmorras', TRUE),
('Encantamientos', '2E', TRUE),
('Defensa Contra las Artes Oscuras', '3C', TRUE),
('Herbología', 'Invernadero', TRUE),
('Historia de la Magia', '4F', TRUE),
('Astronomía', 'Torre de Astronomía', TRUE),
('Adivinación', 'Torre Norte', FALSE),
('Cuidado de Criaturas Mágicas', 'Terrenos', FALSE),
('Estudios Muggles', '1A', FALSE),
('Aritmancia', '7C', FALSE),
('Runas Antiguas', '6A', FALSE),
('Alquimia', '5D', FALSE),
('Aparición', 'Gran Comedor', FALSE),
('Vuelo', 'Terrenos', TRUE),
('Estudios Avanzados de Aritmancia', '7D', FALSE);

-- Insertar Profesores (16)
INSERT INTO Profesor (nombre, apellido, id_asignatura, fecha_inicio) VALUES
('Minerva', 'McGonagall', 1, '1956-09-01'),
('Severus', 'Snape', 2, '1981-09-01'),
('Filius', 'Flitwick', 3, '1971-09-01'),
('Remus', 'Lupin', 4, '1993-09-01'),
('Pomona', 'Sprout', 5, '1975-09-01'),
('Cuthbert', 'Binns', 6, '1800-09-01'),
('Aurora', 'Sinistra', 7, '1985-09-01'),
('Sybill', 'Trelawney', 8, '1979-09-01'),
('Rubeus', 'Hagrid', 9, '1993-09-01'),
('Charity', 'Burbage', 10, '1990-09-01'),
('Septima', 'Vector', 11, '1982-09-01'),
('Bathsheda', 'Babbling', 12, '1988-09-01'),
('Horace', 'Slughorn', 13, '1996-09-01'),
('Alastor', 'Moody', 14, '1994-09-01'),
('Rolanda', 'Hooch', 15, '1974-09-01'),
('Firenze', 'Centauro', 16, '1996-03-01');

-- Insertar Casas
INSERT INTO Casa (nombre, fundador, id_jefe, fantasma) VALUES
('Gryffindor', 'Godric Gryffindor', 1, 'Nick Casi Decapitado'),
('Hufflepuff', 'Helga Hufflepuff', 5, 'Fraile Gordo'),
('Ravenclaw', 'Rowena Ravenclaw', 3, 'Dama Gris'),
('Slytherin', 'Salazar Slytherin', 2, 'Barón Sanguinario');

-- Insertar Estudiantes (60)
INSERT INTO Estudiante (nombre, apellido, id_casa, anyo_curso, fecha_nacimiento) VALUES
('Harry', 'Potter', 1, 5, '1980-07-31'),
('Hermione', 'Granger', 1, 5, '1979-09-19'),
('Ron', 'Weasley', 1, 5, '1980-03-01'),
('Draco', 'Malfoy', 4, 5, '1980-06-05'),
('Neville', 'Longbottom', 1, 5, '1980-07-30'),
('Luna', 'Lovegood', 3, 4, '1981-02-13'),
('Ginny', 'Weasley', 1, 4, '1981-08-11'),
('Fred', 'Weasley', 1, 7, '1978-04-01'),
('George', 'Weasley', 1, 7, '1978-04-01'),
('Cho', 'Chang', 3, 6, '1979-04-07'),
('Cedric', 'Diggory', 2, 6, '1977-09-01'),
('Seamus', 'Finnigan', 1, 5, '1980-03-15'),
('Dean', 'Thomas', 1, 5, '1980-01-20'),
('Lavender', 'Brown', 1, 5, '1980-02-09'),
('Parvati', 'Patil', 1, 5, '1980-04-22'),
('Padma', 'Patil', 3, 5, '1980-04-22'),
('Hannah', 'Abbott', 2, 5, '1980-05-12'),
('Ernie', 'Macmillan', 2, 5, '1980-04-30'),
('Justin', 'Finch-Fletchley', 2, 5, '1980-06-17'),
('Susan', 'Bones', 2, 5, '1980-03-28'),
('Zacharias', 'Smith', 2, 5, '1980-09-05'),
('Terry', 'Boot', 3, 5, '1980-02-13'),
('Michael', 'Corner', 3, 5, '1980-08-18'),
('Anthony', 'Goldstein', 3, 5, '1980-04-03'),
('Pansy', 'Parkinson', 4, 5, '1979-12-30'),
('Blaise', 'Zabini', 4, 5, '1979-11-01'),
('Theodore', 'Nott', 4, 5, '1980-01-15'),
('Millicent', 'Bulstrode', 4, 5, '1979-09-05'),
('Vincent', 'Crabbe', 4, 5, '1979-10-17'),
('Gregory', 'Goyle', 4, 5, '1980-02-22'),
('Angelina', 'Johnson', 1, 7, '1977-10-30'),
('Alicia', 'Spinnet', 1, 7, '1977-11-15'),
('Katie', 'Bell', 1, 6, '1978-12-20'),
('Oliver', 'Wood', 1, 7, '1975-10-01'),
('Percy', 'Weasley', 1, 7, '1976-08-22'),
('Penelope', 'Clearwater', 3, 7, '1976-07-11'),
('Marcus', 'Flint', 4, 7, '1975-09-23'),
('Adrian', 'Pucey', 4, 6, '1977-11-05'),
('Terence', 'Higgs', 4, 6, '1977-12-18'),
('Lee', 'Jordan', 1, 7, '1977-06-08'),
('Cormac', 'McLaggen', 1, 6, '1978-07-14'),
('Romilda', 'Vane', 1, 4, '1981-10-09'),
('Colin', 'Creevey', 1, 3, '1982-05-25'),
('Dennis', 'Creevey', 1, 1, '1984-09-13'),
('Marietta', 'Edgecombe', 3, 6, '1978-11-22'),
('Eddie', 'Carmichael', 3, 7, '1977-03-17'),
('Stewart', 'Ackerley', 3, 1, '1983-08-05'),
('Orla', 'Quirke', 3, 1, '1983-09-30'),
('Eleanor', 'Branstone', 2, 1, '1983-10-12'),
('Owen', 'Cauldwell', 2, 1, '1983-07-07'),
('Laura', 'Madley', 2, 1, '1983-11-20'),
('Kevin', 'Whitby', 2, 1, '1983-12-01'),
('Graham', 'Pritchard', 4, 1, '1983-06-15'),
('Malcolm', 'Baddock', 4, 1, '1983-05-10'),
('Astoria', 'Greengrass', 4, 3, '1982-02-14'),
('Daphne', 'Greengrass', 4, 5, '1980-03-03'),
('Morag', 'MacDougal', 3, 5, '1980-01-23'),
('Lisa', 'Turpin', 3, 5, '1980-05-17'),
('Mandy', 'Brocklehurst', 3, 5, '1980-04-09');

-- Insertar Mascotas (30)
INSERT INTO Mascota (nombre, especie, id_estudiante) VALUES
('Hedwig', 'Lechuza', 119),
('Crookshanks', 'Gato', 120),
('Scabbers', 'Rata',121),
('Trevor', 'Sapo', 122),
('Arnold', 'Micropuff', 123),
('Hermes', 'Lechuza', 124),
('Binky', 'Conejo', 125);

-- Insertar Estudiante_Asignatura para todos los estudiantes
INSERT INTO Estudiante_Asignatura (id_estudiante, id_asignatura, calificacion) VALUES
                                                                                   (119, 15, 7.3), (119, 8, 8.6), (119, 4, 9.6), (119, 2, 9.8),
                                                                                   (120, 1, 8.3), (120, 16, 5.9), (120, 4, 8.6), (120, 6, 5.6),
                                                                                   (121, 11, 6.4), (121, 8, 6.3), (121, 12, 9.3), (121, 13, 7.9),
                                                                                   (122, 14, 9.5), (122, 13, 9.4), (122, 15, 8.1), (122, 8, 9.5),
                                                                                   (123, 13, 9.6), (123, 16, 5.1), (123, 11, 6.5), (123, 5, 5.8),
                                                                                   (124, 3, 6.7), (124, 16, 7.8), (124, 13, 8.2), (124, 7, 7.9),
                                                                                   (125, 9, 5.2), (125, 2, 9.4), (125, 12, 8.6), (125, 6, 6.4),
                                                                                   (126, 3, 9.3), (126, 8, 5.7), (126, 2, 5.2), (126, 1, 9.9),
                                                                                   (127, 8, 9.6), (127, 16, 8.9), (127, 4, 6.9), (127, 10, 7.9),
                                                                                   (128, 11, 8.3), (128, 7, 7.7), (128, 2, 9.1), (128, 15, 6.7),
                                                                                   (129, 2, 7.1), (129, 9, 8.5), (129, 10, 9.3), (129, 16, 7.3),
                                                                                   (130, 2, 9.5), (130, 1, 7.6), (130, 16, 6.9), (130, 3, 6.7),
                                                                                   (131, 11, 7.2), (131, 9, 9.0), (131, 6, 6.5), (131, 5, 6.8),
                                                                                   (132, 3, 5.8), (132, 14, 7.6), (132, 4, 9.4), (132, 12, 9.2),
                                                                                   (133, 13, 7.9), (133, 1, 9.7), (133, 4, 5.0), (133, 3, 6.8),
                                                                                   (134, 11, 6.9), (134, 15, 6.8), (134, 12, 5.2), (134, 14, 5.5),
                                                                                   (135, 8, 5.7), (135, 1, 5.4), (135, 4, 7.2), (135, 6, 5.7),
                                                                                   (136, 5, 5.5), (136, 10, 5.1), (136, 12, 6.7), (136, 3, 9.5),
                                                                                   (137, 4, 7.2), (137, 8, 9.5), (137, 15, 9.5), (137, 1, 8.5),
                                                                                   (138, 1, 5.6), (138, 3, 9.2), (138, 7, 8.9), (138, 2, 9.1),
                                                                                   (139, 15, 9.2), (139, 13, 7.3), (139, 5, 8.6), (139, 7, 6.8),
                                                                                   (140, 4, 9.1), (140, 9, 8.4), (140, 8, 5.0), (140, 6, 9.1),
                                                                                   (141, 15, 6.1), (141, 10, 10.0), (141, 9, 7.7), (141, 5, 9.1),
                                                                                   (142, 16, 7.2), (142, 4, 6.0), (142, 12, 5.7), (142, 13, 7.9),
                                                                                   (143, 1, 5.6), (143, 14, 5.4), (143, 11, 9.5), (143, 15, 9.2),
                                                                                   (144, 14, 5.4), (144, 1, 8.6), (144, 13, 6.4), (144, 4, 9.9),
                                                                                   (145, 11, 9.8), (145, 16, 7.9), (145, 2, 5.2), (145, 15, 8.9),
                                                                                   (146, 12, 6.6), (146, 9, 5.7), (146, 5, 8.5), (146, 16, 6.4),
                                                                                   (147, 4, 7.1), (147, 7, 5.5), (147, 13, 9.5), (147, 15, 6.4),
                                                                                   (148, 14, 8.1), (148, 16, 8.9), (148, 9, 7.5), (148, 5, 8.8),
                                                                                   (149, 10, 5.6), (149, 7, 9.4), (149, 15, 9.3), (149, 11, 7.5),
                                                                                   (150, 8, 9.8), (150, 1, 8.0), (150, 6, 5.8), (150, 11, 5.5),
                                                                                   (151, 10, 8.0), (151, 6, 5.5), (151, 9, 9.1), (151, 11, 8.9),
                                                                                   (152, 8, 7.2), (152, 7, 5.8), (152, 3, 8.2), (152, 1, 6.1),
                                                                                   (153, 8, 9.4), (153, 5, 5.9), (153, 15, 5.6), (153, 16, 7.7),
                                                                                   (154, 8, 7.1), (154, 2, 5.3), (154, 16, 6.0), (154, 9, 6.4),
                                                                                   (155, 13, 9.1), (155, 7, 7.7), (155, 16, 8.8), (155, 1, 9.2),
                                                                                   (156, 14, 7.7), (156, 12, 8.5), (156, 5, 6.0), (156, 9, 8.3),
                                                                                   (157, 14, 8.4), (157, 11, 7.6), (157, 13, 5.4), (157, 4, 8.7),
                                                                                   (158, 9, 5.5), (158, 16, 7.7), (158, 1, 6.6), (158, 8, 8.6),
                                                                                   (159, 9, 5.4), (159, 4, 8.8), (159, 15, 5.1), (159, 1, 8.2),
                                                                                   (160, 6, 9.1), (160, 2, 8.2), (160, 1, 9.7), (160, 12, 8.7),
                                                                                   (161, 3, 5.8), (161, 15, 7.5), (161, 1, 9.5), (161, 11, 5.4),
                                                                                   (162, 4, 9.6), (162, 10, 8.8), (162, 1, 5.9), (162, 9, 7.0),
                                                                                   (163, 13, 8.7), (163, 16, 9.8), (163, 1, 8.4), (163, 4, 6.9),
                                                                                   (164, 13, 8.9), (164, 6, 8.2), (164, 16, 6.9), (164, 7, 6.2),
                                                                                   (165, 4, 6.7), (165, 12, 7.9), (165, 8, 10.0), (165, 7, 7.0),
                                                                                   (166, 14, 9.9), (166, 4, 7.4), (166, 9, 7.4), (166, 7, 7.2),
                                                                                   (167, 14, 7.7), (167, 1, 8.9), (167, 7, 6.9), (167, 4, 6.5),
                                                                                   (168, 13, 6.6), (168, 14, 9.8), (168, 11, 8.2), (168, 6, 6.5),
                                                                                   (169, 15, 9.8), (169, 7, 9.2), (169, 14, 9.7), (169, 5, 7.6),
                                                                                   (170, 9, 6.6), (170, 2, 9.6), (170, 8, 5.8), (170, 15, 9.2),
                                                                                   (171, 12, 9.2), (171, 11, 9.5), (171, 7, 7.0), (171, 13, 5.5),
                                                                                   (172, 5, 5.7), (172, 1, 8.8), (172, 14, 6.8), (172, 16, 9.4),
                                                                                   (173, 11, 9.4), (173, 7, 6.0), (173, 6, 8.9), (173, 16, 6.0),
                                                                                   (174, 5, 7.4), (174, 8, 6.3), (174, 10, 8.3), (174, 6, 9.5),
                                                                                   (175, 2, 9.8), (175, 13, 9.8), (175, 14, 7.4), (175, 7, 5.9),
                                                                                   (176, 14, 9.9), (176, 5, 5.5), (176, 11, 7.2), (176, 1, 8.4),
                                                                                   (177, 13, 7.8), (177, 3, 6.2), (177, 10, 8.7), (177, 6, 9.7);
