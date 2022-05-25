DROP VIEW Reste_stock_ingredient;
DROP VIEW Stock_sortie_ingredient;
DROP VIEW Stock_entree_ingredient;
DROP VIEW Ingredients_utilises;
DROP VIEW Addition_table;
DROP VIEW Addition_table0;
DROP VIEW Recette_plat;
DROP VIEW Pourboir;
DROP VIEW Com_produit;
DROP VIEW Prix_reviens;
DROP VIEW Total_ingredient;
DROP VIEW Menu;

-- DROP TABLE Marge;
DROP TABLE Paiement;
DROP TABLE Inventaire;
DROP TABLE Addition;
DROP TABLE Detail_commande;
DROP TABLE Commande;
DROP TABLE Livreur;
DROP TABLE Stock_produit;
DROP TABLE Stock_ingredient;
DROP TABLE Recette;
DROP TABLE Prix_produit;
DROP TABLE Produit;
DROP TABLE Categorie;
DROP TABLE Prix_ingredient;
DROP TABLE Ingredient;
DROP TABLE Rel_table_client;
DROP TABLE Point_livraison;
DROP TABLE Client;

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20),
    mdp VARCHAR(255),
    contact VARCHAR(20),
    adresse VARCHAR(100)
);

CREATE TABLE Point_livraison (
    id SERIAL PRIMARY KEY,
    designation VARCHAR(20),
    categorie VARCHAR(20)
);

CREATE TABLE Rel_table_client (
    id SERIAL PRIMARY KEY,
    id_table INT REFERENCES Point_livraison(id),
    id_client INT REFERENCES Client(id)
);

CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(20)
);

CREATE TABLE Prix_ingredient (
    id_ingredient INT REFERENCES Ingredient(id),
    montant DOUBLE PRECISION,
    date TIMESTAMP
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
    date TIMESTAMP
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
    date TIMESTAMP
);

CREATE TABLE Stock_ingredient (
    id_ingredient INT REFERENCES Ingredient(id),
    designation VARCHAR(10),
    quantite DOUBLE PRECISION,
    date TIMESTAMP
);

CREATE TABLE Livreur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50),
    categorie_livraison VARCHAR(20)
);

-- CREATE TABLE Commande (
--     id SERIAL PRIMARY KEY,
--     date_heure TIMESTAMP,
--     id_rel_tab_client INT REFERENCES Rel_table_client(id),
--     etat VARCHAR(10)
-- );

CREATE TABLE Commande (
    id SERIAL PRIMARY KEY,
    date_heure TIMESTAMP,
    id_table INT REFERENCES Point_livraison(id)
);

CREATE TABLE Detail_commande (
    id SERIAL PRIMARY KEY,
    id_commande INT REFERENCES Commande(id),
    id_produit INT REFERENCES Produit(id),
    id_serveur INT REFERENCES Livreur(id),
    etat INT
);

CREATE TABLE Addition (
    id SERIAL PRIMARY KEY,
    id_commande INT REFERENCES Commande(id),
    est_paye INT
);

CREATE TABLE Inventaire (
    id SERIAL PRIMARY KEY,
    id_ingredient INT REFERENCES Ingredient(id),
    quantite DOUBLE PRECISION,
    date TIMESTAMP
);

-- CREATE TABLE Marge (
--     id SERIAL PRIMARY KEY,
--     prix_min DOUBLE PRECISION,
--     prix_max DOUBLE PRECISION,
--     marge DOUBLE PRECISION,
--     date TIMESTAMP
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

CREATE VIEW Com_produit AS
SELECT  c.id as id_commande, t.id as id_table, t.designation as num_table, c.date_heure as date_commande, p.id as id_produit, p.nom as nom_produit, m.prix as prix_produit, dc.id_serveur
        FROM Commande c
        JOIN Point_livraison t ON (t.id = c.id_table)
        JOIN Detail_commande dc ON (dc.id_commande = c.id)
        JOIN Produit p ON (p.id = dc.id_produit)
        JOIN Menu m ON (m.id_produit = p.id);

CREATE VIEW Pourboir AS
SELECT  cp.id_serveur, cp.nom_produit, cp.id_table, cp.num_table, s.nom, SUM(cp.prix_produit)*0.02 as pourbois, cp.date_commande
        FROM
        Com_produit cp
        JOIN Livreur s ON (s.id = cp.id_serveur)
        GROUP BY cp.id_serveur, cp.nom_produit, cp.id_table, cp.num_table, s.nom, cp.date_commande;

CREATE VIEW Recette_plat AS
SELECT  p.id id_produit, p.nom nom_produit, i.id id_ingredient, i.nom nom_ingredient, r.quantite
        FROM Produit p
        JOIN Recette r ON (r.id_produit = p.id)
        JOIN Ingredient i ON (i.id = r.id_ingredient)
        JOIN Prix_ingredient pi ON (pi.id_ingredient = i.id)
        WHERE pi.date = (select max(date) from Prix_ingredient);

CREATE VIEW Addition_table0 AS
SELECT  p.id id_produit, c.id id_commande, c.id_table id_point_livraison, t.designation, p.nom nom_produit, COUNT(p.id) quantite, c.date_heure date_commande, a.id id_addition, a.est_paye
        FROM Produit p
        JOIN Detail_commande dc ON (dc.id_produit = p.id)
        JOIN Commande c ON (c.id = dc.id_commande)
        JOIN Addition a ON (a.id_commande = c.id)
        JOIN Point_livraison t ON (t.id = c.id_table)
        -- WHERE c.date_heure = (select max(date_heure) from Commande)
        GROUP BY p.id, c.id, c.id_table, t.designation, p.nom, a.id;

CREATE VIEW Addition_table AS
SELECT  a.*, m.prix prix_unitaire, a.quantite*m.prix montant
        FROM Addition_table0 a
        JOIN Menu m ON (m.id_produit = a.id_produit);

CREATE VIEW Ingredients_utilises AS
SELECT  c.id id_commande, c.date_heure date_commande, dc.etat, p.id id_produit, p.nom nom_produit, i.id id_ingredient, i.nom nom_ingredient, SUM(r.quantite) as quantite, SUM(pi.montant*r.quantite) as montant
        FROM Commande c
        JOIN Detail_commande dc ON (dc.id_commande = c.id)
        JOIN Produit p ON (p.id = dc.id_produit)
        JOIN Recette r ON (r.id_produit = p.id)
        JOIN Ingredient i ON (i.id = r.id_ingredient)
        JOIN Prix_ingredient pi ON (pi.id_ingredient = i.id)
        WHERE pi.date = (select max(date) from Prix_ingredient)
        GROUP BY c.id, dc.etat, p.id, i.id;

-- entrée
CREATE VIEW Stock_entree_ingredient AS
SELECT  i.*, SUM(si.quantite) quantite, si.date, si.designation
        FROM Stock_ingredient si
        JOIN Ingredient i ON (si.id_ingredient = i.id)
        WHERE si.designation = 'entree'
        GROUP BY i.id, si.date, si.designation;

-- sortie
CREATE VIEW Stock_sortie_ingredient AS
SELECT  i.*, SUM(si.quantite) quantite, si.date, si.designation
        FROM Stock_ingredient si
        JOIN Ingredient i ON (si.id_ingredient = i.id)
        WHERE si.designation = 'sortie'
        GROUP BY i.id, si.date, si.designation;

CREATE VIEW Reste_stock_ingredient AS
select * from Stock_entree_ingredient union select * from Stock_sortie_ingredient;
        
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

INSERT INTO Livreur VALUES
(DEFAULT, 'Rakoto', 'restaurant'),
(DEFAULT, 'Aline', 'restaurant'),
(DEFAULT, 'Rasoa', 'restaurant'),
(DEFAULT, 'Rabe', 'restaurant'),
(DEFAULT, 'Rajao', 'restaurant');

INSERT INTO Point_livraison VALUES
(DEFAULT, 'table 1', 'restaurant'),
(DEFAULT, 'table 2', 'restaurant'),
(DEFAULT, 'table 3', 'restaurant'),
(DEFAULT, 'table 4', 'restaurant'),
(DEFAULT, 'table 5', 'restaurant');

INSERT INTO Client VALUES
(DEFAULT, 'Jean', 'mdp', null, null),
(DEFAULT, 'Bezy', 'mdp', null, null),
(DEFAULT, 'Koto', 'mdp', null, null),
(DEFAULT, 'Randrenja', 'mdp', null, null),
(DEFAULT, 'Raly', 'mdp', null, null);

INSERT INTO Rel_table_client VALUES
(DEFAULT, 1, 1),
(DEFAULT, 2, 2),
(DEFAULT, 3, 3),
(DEFAULT, 4, 4),
(DEFAULT, 5, 5);

INSERT INTO Commande VALUES
(DEFAULT, '2022-03-28 16:46:27', 1),
(DEFAULT, '2022-03-28 16:50:27', 2),
(DEFAULT, '2022-03-28 15:46:27', 3),
(DEFAULT, '2022-03-28 10:46:27', 4),
(DEFAULT, '2022-03-28 12:46:27', 5);

INSERT INTO Addition VALUES
(DEFAULT, 1, 0),
(DEFAULT, 2, 0),
(DEFAULT, 3, 1),
(DEFAULT, 4, 1),
(DEFAULT, 5, 1);

INSERT INTO Detail_commande VALUES
(DEFAULT, 1, 1, 1, 3),
(DEFAULT, 1, 1, 1, 3),
(DEFAULT, 2, 1, 2, 3),
(DEFAULT, 2, 3, 2, 3),
(DEFAULT, 3, 1, 3, 3),
(DEFAULT, 3, 4, 3, 3),
(DEFAULT, 4, 2, 4, 3),
(DEFAULT, 4, 3, 4, 3),
(DEFAULT, 5, 5, 5, 3);

INSERT INTO Commande VALUES
(DEFAULT, '2022-03-26 10:46:27', 1),
(DEFAULT, '2022-03-26 18:46:27', 2),
(DEFAULT, '2022-03-26 19:46:27', 2),
(DEFAULT, '2022-03-26 20:46:27', 4),
(DEFAULT, '2022-03-26 21:46:27', 5);

INSERT INTO Addition VALUES
(DEFAULT, 6, 0),
(DEFAULT, 7, 0),
(DEFAULT, 8, 1),
(DEFAULT, 9, 1),
(DEFAULT, 10, 1);

INSERT INTO Detail_commande VALUES
(DEFAULT, 6, 2, 1, 3),
(DEFAULT, 6, 3, 1, 3),
(DEFAULT, 7, 5, 2, 3),
(DEFAULT, 7, 1, 2, 3),
(DEFAULT, 8, 1, 3, 3),
(DEFAULT, 8, 2, 3, 3),
(DEFAULT, 9, 5, 4, 3),
(DEFAULT, 9, 4, 4, 3),
(DEFAULT, 10, 1, 5, 3);

INSERT INTO Stock_ingredient VALUES
(1, 'entree', 20, '2022-04-01 15:57:27'),
(2, 'entree', 20, '2022-04-02 16:50:27'),
(3, 'entree', 20, '2022-04-03 15:20:27'),
(4, 'entree', 20, '2022-04-05 17:45:27'),
(5, 'entree', 20, '2022-04-06 18:30:27');

INSERT INTO Stock_ingredient VALUES
(1, 'sortie', 10, '2022-04-01 10:46:27'),
(2, 'sortie', 5, '2022-04-02 9:46:27'),
(3, 'sortie', 9, '2022-04-03 8:46:27'),
(4, 'sortie', 17, '2022-04-04 7:46:27'),
(5, 'sortie', 15, '2022-04-05 11:46:27');

INSERT INTO Inventaire VALUES
(default, 1, 20, '2022-04-05 18:15:27'),
(default, 2, 10, '2022-04-05 14:17:27'),
(default, 3, 15, '2022-04-05 12:41:27'),
(default, 4, 5, '2022-04-05 19:10:27'),
(default, 5, 2, '2022-04-05 18:18:27');

select  nom_ingredient, SUM(quantite) quantite, SUM(montant) montant
        FROM Ingredients_utilises
        WHERE date_commande BETWEEN '2022-03-26' AND '2022-03-28'
        AND etat = 3
        GROUP BY nom_ingredient;

CREATE TABLE Paiement (
    id SERIAL PRIMARY KEY,
    id_addition INT REFERENCES Addition(id),
    date TIMESTAMP,
    montant DOUBLE PRECISION,
    type VARCHAR(10)
);

INSERT INTO Paiement VALUES
(default, 1, '2022-04-01 10:40:27', 120000, 'espece'),
(default, 2, '2022-04-02 11:50:27', 150000, 'espece'),
(default, 3, '2022-04-03 12:43:27', 200000, 'en ligne'),
(default, 4, '2022-04-04 13:46:27', 100000, 'espece'),
(default, 5, '2022-04-05 14:47:27', 80000, 'espece'),
(default, 6, '2022-04-06 15:10:27', 50000, 'cheque'),
(default, 7, '2022-04-07 16:20:27', 100000, 'en ligne'),
(default, 8, '2022-04-01 17:30:27', 250000, 'cheque'),
(default, 9, '2022-04-02 18:40:27', 280000, 'cheque'),
(default, 10, '2022-04-03 19:50:27', 42000, 'en ligne');