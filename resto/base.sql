DROP VIEW Menu;

DROP TABLE Addition;
DROP TABLE Detail_commande;
DROP TABLE Commande;
DROP TABLE Stock_produit;
DROP TABLE Stock_ingredient;
DROP TABLE Recette;
DROP TABLE Prix_produit;
DROP TABLE Produit;
DROP TABLE Categorie;
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

CREATE VIEW Menu AS
SELECT  p.id as id_produit, p.nom as produit, c.nom as categorie, pp.montant as prix, pp.date
        FROM Produit p
        JOIN Prix_produit pp ON( pp.id_produit=p.id)
        JOIN Categorie c ON (c.id=p.id_categorie);


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

INSERT INTO Profil VALUES
(DEFAULT,'livreur1'),
(DEFAULT,'serveur1');

INSERT INTO Utilisateurs VALUES 
(DEFAULT,'livreur1','livreur1'),
(DEFAULT,'serveur1','serveur1');

