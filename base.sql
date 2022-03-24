DROP VIEW Prix_reviens;
DROP VIEW Total_ingredient;
DROP VIEW Menu;

-- DROP TABLE Marge;
DROP TABLE Addition;
DROP TABLE Detail_commande;
DROP TABLE Commande;
DROP TABLE Stock_produit;
DROP TABLE Stock_ingredient;
DROP TABLE Recette;
DROP TABLE Prix_produit;
DROP TABLE Produit;
DROP TABLE Categorie;
DROP TABLE Prix_ingredient;
DROP TABLE Ingredient;
DROP TABLE Rel_table_client;
DROP TABLE Tables;
DROP TABLE Client;

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20),
    mdp VARCHAR(255)
);

CREATE TABLE Tables (
    id SERIAL PRIMARY KEY,
    numero INT
);

CREATE TABLE Rel_table_client (
    id SERIAL PRIMARY KEY,
    id_table INT REFERENCES Tables(id),
    id_client INT REFERENCES Client(id)
);

CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20)
);

CREATE TABLE Prix_ingredient (
    id_ingredient INT REFERENCES Ingredient(id),
    montant DOUBLE PRECISION,
    date DATE
);

CREATE TABLE Categorie (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20)
);

CREATE TABLE Produit (
    id SERIAL PRIMARY KEY,
    id_categorie INT REFERENCES Categorie(id),
    nom VARCHAR(100)
);

CREATE TABLE Prix_produit (
    id_produit INT REFERENCES Produit(id),
    montant DOUBLE PRECISION,
    date DATE
);

CREATE TABLE Recette (
    id_produit INT REFERENCES Produit(id),
    id_ingredient INT REFERENCES Ingredient(id),
    quantite DOUBLE PRECISION
);

CREATE TABLE Stock_produit (
    id_produit INT REFERENCES Produit(id),
    designation VARCHAR(10),
    quantite DOUBLE PRECISION,
    date DATE
);

CREATE TABLE Stock_ingredient (
    id_ingredient INT REFERENCES Ingredient(id),
    designation VARCHAR(10),
    quantite DOUBLE PRECISION,
    date DATE
);

CREATE TABLE Commande (
    id SERIAL PRIMARY KEY,
    date_heure DATE,
    id_rel_tab_client INT REFERENCES Rel_table_client(id),
    etat VARCHAR(10)
);

CREATE TABLE Detail_commande (
    id SERIAL PRIMARY KEY,
    id_commande INT REFERENCES Commande(id),
    id_produit INT REFERENCES Produit(id),
    validite INT
);

CREATE TABLE Addition (
    id SERIAL PRIMARY KEY,
    id_commande INT REFERENCES Commande(id),
    id_rel_tab_client INT REFERENCES Rel_table_client(id),
    est_paye BOOLEAN
);

-- CREATE TABLE Marge (
--     id SERIAL PRIMARY KEY,
--     prix_min DOUBLE PRECISION,
--     prix_max DOUBLE PRECISION,
--     marge DOUBLE PRECISION,
--     date DATE
-- );

CREATE VIEW Menu AS
SELECT  p.id as id_produit, p.nom as produit, c.nom as categorie, pp.montant as prix, pp.date
        FROM Produit p
        JOIN Prix_produit pp ON( pp.id_produit=p.id)
        JOIN Categorie c ON (c.id=p.id_categorie)
        WHERE pp.date = (select max(date) from Prix_produit);

CREATE VIEW Total_ingredient AS
SELECT  p.*, SUM(pi.montant*r.quantite) as montant
        FROM Produit p
        JOIN Recette r ON (r.id_produit = p.id)
        JOIN Ingredient i ON (i.id = r.id_ingredient)
        JOIN Prix_ingredient pi ON (pi.id_ingredient = i.id)
        WHERE pi.date = (select max(date) from Prix_ingredient)
        GROUP BY p.id;

CREATE VIEW Prix_reviens AS
SELECT  ti.id as id_produit, ti.nom as nom_produit, m.prix-ti.montant as prix_reviens
        FROM Total_ingredient ti
        JOIN Menu m ON (m.id_produit = ti.id);

INSERT INTO Categorie VALUES
(DEFAULT, 'Entree'),
(DEFAULT, 'Plat de resistance'),
(DEFAULT, 'Dessert');


INSERT INTO Produit VALUES
(DEFAULT, 1, 'Pate a choux'),
(DEFAULT, 2, 'Ravitoto sy henakisoa'),
(DEFAULT, 3, 'Glace'),
(DEFAULT, 3, 'Salade de fruit'),
(DEFAULT, 3, 'Yaourt');

INSERT INTO Prix_produit VALUES
(1, 2000, '2022-03-22'),
(2, 3000, '2022-03-22'),
(3, 2500, '2022-03-22'),
(4, 3500, '2022-03-22'),
(5, 4000, '2022-03-22');

INSERT INTO Ingredient VALUES
(DEFAULT, 'farine'),
(DEFAULT, 'poivre'),
(DEFAULT, 'sucre'),
(DEFAULT, 'sel'),
(DEFAULT, 'carotte');

INSERT INTO Prix_ingredient VALUES
(1, 100, '2022-03-22'),
(2, 200, '2022-03-22'),
(3, 300, '2022-03-22'),
(4, 150, '2022-03-22'),
(5, 250, '2022-03-22');

INSERT INTO Recette VALUES
(1, 1, 2),
(1, 2, 3),
(2, 1, 4),
(2, 2, 5),
(2, 3, 2),
(3, 4, 2),
(3, 5, 1),
(4, 1, 4),
(4, 2, 2),
(4, 3, 1),
(5, 1, 8);