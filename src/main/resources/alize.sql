-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Ven 05 Décembre 2014 à 09:32
-- Version du serveur :  5.5.40-0+wheezy1
-- Version de PHP :  5.4.34-0+deb7u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `alize`
--

-- --------------------------------------------------------

--
-- Structure de la table `arret`
--

CREATE TABLE IF NOT EXISTS `arret` (
`id` int(11) NOT NULL,
  `nom` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `estCommercial` tinyint(1) DEFAULT NULL,
  `estEntreeDepot` tinyint(1) DEFAULT NULL,
  `estSortieDepot` tinyint(1) DEFAULT NULL,
  `estOccupe` tinyint(1) DEFAULT NULL,
  `tempsImmobilisation_id` int(11) DEFAULT NULL,
  `estLieuEchangeConducteur` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu darret du véhicule';

--
-- Contenu de la table `arret`
--

INSERT INTO `arret` (`id`, `nom`, `estCommercial`, `estEntreeDepot`, `estSortieDepot`, `estOccupe`, `tempsImmobilisation_id`, `estLieuEchangeConducteur`) VALUES
(1, 'A001 - Castor', 1, 0, 0, 0, 2, 0),
(2, 'A002 - Zephyr', 1, 0, 0, 0, 1, 0),
(3, 'A003 - Colombus', 1, 1, 0, 0, 1, 0),
(4, 'A004 - Cénée', 1, 0, 1, 0, 1, 1),
(5, 'A005 - Simon BOLIVAR', 1, 0, 0, 0, 1, 0),
(6, 'A006 - Nouveaux mondes', 1, 0, 0, 0, 1, 1),
(7, 'A007 - Atlantides', 1, 0, 0, 0, 1, 0),
(8, 'A008 - Thésés', 1, 0, 0, 0, 1, 0),
(9, 'A009 - Caraïbes', 1, 0, 0, 0, 1, 0),
(10, 'A010 - Pollux', 1, 0, 0, 0, 2, 0),
(11, 'B011 - Le Campus', 1, 0, 0, 0, 2, 0),
(12, 'B012 - Vasco De Gamma', 1, 0, 0, 0, 1, 1),
(13, 'B013 - Fregate', 1, 0, 0, 0, 1, 0),
(14, 'B014 - Ulysse', 1, 0, 0, 0, 1, 0),
(15, 'B015 - Neil Amstrong', 1, 0, 0, 0, 1, 0),
(16, 'B016 - La mouette', 1, 0, 0, 0, 1, 0),
(17, 'B017 - Jacques Cartier', 1, 0, 0, 0, 1, 0),
(18, 'B018 - Green Alchimie', 1, 0, 0, 0, 1, 0),
(19, 'B019 - ESEO', 1, 0, 0, 0, 2, 0),
(21, 'A101 - Castor', 1, 0, 0, 0, 2, 0),
(22, 'A122 - Zephyr', 1, 0, 0, 0, 1, 0),
(23, 'A123 - Colombus', 1, 0, 1, 0, 1, 0),
(24, 'A124 - Cénée', 1, 1, 0, 0, 1, 1),
(25, 'A125 - Simon BOLIVAR', 1, 0, 0, 0, 1, 0),
(26, 'A126 - Nouveaux mondes', 1, 0, 0, 0, 1, 1),
(27, 'A127 - Atlantides', 1, 0, 0, 0, 1, 0),
(28, 'A128 - Thésés', 1, 0, 0, 0, 1, 0),
(29, 'A129 - Caraïbes', 1, 0, 0, 0, 1, 0),
(30, 'A130 - Pollux', 1, 0, 0, 0, 2, 0),
(31, 'B131 - Le Campus', 1, 0, 0, 0, 2, 0),
(32, 'B132 - Vasco De Gamma', 1, 0, 0, 0, 1, 1),
(33, 'B133 - Fregate', 1, 0, 0, 0, 1, 0),
(34, 'B134 - Ulysse', 1, 0, 0, 0, 1, 0),
(35, 'B135 - Neil Amstrong', 1, 0, 0, 0, 1, 0),
(36, 'B136 - La mouette', 1, 0, 0, 0, 1, 0),
(37, 'B137 - Jacques Cartier', 1, 0, 0, 0, 1, 0),
(38, 'B138 - Green Alchimie', 1, 0, 0, 0, 1, 0),
(39, 'B139 - ESEO', 1, 0, 0, 0, 2, 0),
(100, 'Dépôt', 0, 0, 0, 0, 3, 0);

-- --------------------------------------------------------

--
-- Structure de la table `associationconducteurservice`
--

CREATE TABLE IF NOT EXISTS `associationconducteurservice` (
`id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  `conducteur_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Assure lassociation entre un conducteur et un service';

-- --------------------------------------------------------

--
-- Structure de la table `conducteur`
--

CREATE TABLE IF NOT EXISTS `conducteur` (
`id` int(11) NOT NULL,
  `nom` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Désigne une personne habilité à la conduite';

-- --------------------------------------------------------

--
-- Structure de la table `depot`
--

CREATE TABLE IF NOT EXISTS `depot` (
`id` int(11) NOT NULL,
  `arret_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Le terminus est le premier ou le dernier arrêt dune voie';

--
-- Contenu de la table `depot`
--

INSERT INTO `depot` (`id`, `arret_id`) VALUES
(1, 100);

-- --------------------------------------------------------

--
-- Structure de la table `feuilledeservice`
--

CREATE TABLE IF NOT EXISTS `feuilledeservice` (
`id` int(11) NOT NULL,
  `couleur` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `debutSaison` date DEFAULT NULL,
  `finSaison` date DEFAULT NULL,
  `phase_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Ensemble de service décrivant lexploitation dune saison';

-- --------------------------------------------------------

--
-- Structure de la table `intervalle`
--

CREATE TABLE IF NOT EXISTS `intervalle` (
`id` int(11) NOT NULL,
  `min` time DEFAULT NULL,
  `pref` time DEFAULT NULL,
  `max` time DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Donne le domaine de définition dune variable';

--
-- Contenu de la table `intervalle`
--

INSERT INTO `intervalle` (`id`, `min`, `pref`, `max`) VALUES
(1, '00:00:30', '00:00:30', '00:03:00'),
(2, '00:00:30', '00:07:00', '00:17:00'),
(3, '01:00:00', '06:00:00', '23:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `ligne`
--

CREATE TABLE IF NOT EXISTS `ligne` (
`id` int(11) NOT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='La ligne est la représentation de la route quemprunte les véhicules.';

--
-- Contenu de la table `ligne`
--

INSERT INTO `ligne` (`id`, `typeVehicule`) VALUES
(1, 'Tramway'),
(2, 'Tramway');

-- --------------------------------------------------------

--
-- Structure de la table `ligne_voie`
--

CREATE TABLE IF NOT EXISTS `ligne_voie` (
`id` int(11) NOT NULL,
  `ligne_id` int(11) NOT NULL,
  `voie_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `ligne_voie`
--

INSERT INTO `ligne_voie` (`id`, `ligne_id`, `voie_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4);

-- --------------------------------------------------------

--
-- Structure de la table `periodedeconduite`
--

CREATE TABLE IF NOT EXISTS `periodedeconduite` (
`id` int(11) NOT NULL,
  `heureDebut` date DEFAULT NULL,
  `heureFin` date DEFAULT NULL,
  `numeroVehicule` int(11) DEFAULT NULL,
  `arretEchangeConducteurDebut_id` int(11) NOT NULL,
  `arretEchangeConducteurFin_id` int(11) NOT NULL,
  `vehicule_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Segment dun service de conduite';

-- --------------------------------------------------------

--
-- Structure de la table `periodicite`
--

CREATE TABLE IF NOT EXISTS `periodicite` (
  `id` int(11) NOT NULL,
  `debut` time NOT NULL,
  `fin` time NOT NULL,
  `periode` time NOT NULL,
  `id_voie` int(11) NOT NULL,
  `id_arret` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `phase`
--

CREATE TABLE IF NOT EXISTS `phase` (
`id` int(11) NOT NULL,
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `periodicite` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description journalière du régime dexploitation du réseau';

-- --------------------------------------------------------

--
-- Structure de la table `reseau`
--

CREATE TABLE IF NOT EXISTS `reseau` (
`id` int(11) NOT NULL,
  `nom` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description dun réseau de transport';

--
-- Contenu de la table `reseau`
--

INSERT INTO `reseau` (`id`, `nom`) VALUES
(1, 'SpiderMap');

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `id` int(11) NOT NULL COMMENT 'Numéro de service',
  `nbPeriodeDeConduite` int(11) DEFAULT NULL,
  `feuilledeservice_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Constitue un planning journalier dun employé';

-- --------------------------------------------------------

--
-- Structure de la table `terminus`
--

CREATE TABLE IF NOT EXISTS `terminus` (
`id` int(11) NOT NULL,
  `arret_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `terminus`
--

INSERT INTO `terminus` (`id`, `arret_id`) VALUES
(1, 1),
(2, 10),
(5, 11),
(6, 19),
(4, 21),
(3, 30),
(8, 31),
(7, 39);

-- --------------------------------------------------------

--
-- Structure de la table `transition`
--

CREATE TABLE IF NOT EXISTS `transition` (
`id` int(11) NOT NULL,
  `duree` time DEFAULT NULL,
  `arretPrecedent_id` int(11) NOT NULL,
  `arretSuivant_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Troncon entre deux arrêts';

--
-- Contenu de la table `transition`
--

INSERT INTO `transition` (`id`, `duree`, `arretPrecedent_id`, `arretSuivant_id`) VALUES
(1, '00:01:37', 1, 2),
(2, '00:02:03', 2, 3),
(3, '00:01:06', 3, 4),
(4, '00:02:00', 3, 100),
(5, '00:01:19', 100, 4),
(6, '00:01:14', 4, 5),
(7, '00:00:51', 5, 6),
(8, '00:01:40', 6, 7),
(9, '00:00:54', 7, 8),
(10, '00:01:48', 8, 9),
(11, '00:00:49', 9, 10),
(12, '00:00:54', 9, 30),
(13, '00:00:54', 10, 29),
(14, '00:00:49', 30, 29),
(15, '00:01:48', 29, 28),
(16, '00:00:54', 28, 27),
(17, '00:01:40', 27, 26),
(18, '00:00:51', 26, 25),
(19, '00:01:14', 25, 24),
(20, '00:01:06', 24, 23),
(21, '00:02:07', 24, 100),
(23, '00:02:03', 23, 22),
(24, '00:01:37', 22, 21),
(25, '00:01:44', 22, 1),
(26, '00:01:43', 21, 2),
(27, '00:01:29', 11, 12),
(28, '00:00:59', 12, 13),
(29, '00:01:05', 13, 14),
(30, '00:00:43', 14, 6),
(31, '00:01:42', 6, 15),
(32, '00:00:54', 15, 16),
(33, '00:01:24', 16, 17),
(34, '00:00:59', 17, 18),
(35, '00:00:40', 18, 19),
(36, '00:00:49', 18, 39),
(37, '00:00:49', 19, 35),
(38, '00:00:40', 39, 35),
(39, '00:00:59', 38, 37),
(40, '00:01:24', 37, 36),
(41, '00:00:54', 36, 35),
(42, '00:01:42', 35, 26),
(43, '00:00:43', 26, 34),
(44, '00:01:05', 34, 33),
(45, '00:00:59', 33, 32),
(46, '00:01:29', 32, 31),
(47, '00:00:54', 32, 11),
(48, '00:00:54', 31, 12);

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE IF NOT EXISTS `vehicule` (
  `id` int(11) NOT NULL COMMENT 'Numéro de série du véhicule',
  `kilometrage` decimal(10,0) DEFAULT NULL,
  `estEnService` tinyint(1) DEFAULT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Matériel roulant du réseau';

--
-- Contenu de la table `vehicule`
--

INSERT INTO `vehicule` (`id`, `kilometrage`, `estEnService`, `typeVehicule`) VALUES
(1001, 0, 0, 'Tramway'),
(1002, 0, 0, 'Tramway'),
(1003, 0, 0, 'Tramway'),
(1004, 0, 0, 'Tramway'),
(1005, 0, 0, 'Tramway'),
(1006, 0, 0, 'Tramway'),
(1007, 0, 0, 'Tramway'),
(1008, 0, 0, 'Tramway'),
(1009, 0, 0, 'Tramway'),
(1010, 0, 0, 'Tramway'),
(1011, 0, 0, 'Tramway'),
(1012, 0, 0, 'Tramway'),
(1013, 0, 0, 'Tramway'),
(1014, 0, 0, 'Tramway'),
(1015, 0, 0, 'Tramway'),
(1016, 0, 0, 'Tramway'),
(1017, 0, 0, 'Tramway'),
(1018, 0, 0, 'Tramway');

-- --------------------------------------------------------

--
-- Structure de la table `voie`
--

CREATE TABLE IF NOT EXISTS `voie` (
`id` int(11) NOT NULL,
  `direction` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `terminusDepart_id` int(11) NOT NULL,
  `terminusArrivee_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Partie de la ligne marqué par un sens de circulation';

--
-- Contenu de la table `voie`
--

INSERT INTO `voie` (`id`, `direction`, `terminusDepart_id`, `terminusArrivee_id`) VALUES
(1, 'Castor - Pollux', 1, 10),
(2, 'Pollux - Castor', 30, 21),
(3, 'Le Campus - ESEO', 11, 19),
(4, 'ESEO -Le Campus', 39, 31);

-- --------------------------------------------------------

--
-- Structure de la table `voie_arret`
--

CREATE TABLE IF NOT EXISTS `voie_arret` (
`id` int(11) NOT NULL,
  `voie_id` int(11) NOT NULL,
  `arret_id` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu darret du véhicule';

--
-- Contenu de la table `voie_arret`
--

INSERT INTO `voie_arret` (`id`, `voie_id`, `arret_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 2, 21),
(12, 2, 22),
(13, 2, 23),
(14, 2, 24),
(15, 2, 25),
(16, 2, 26),
(17, 2, 27),
(18, 2, 28),
(19, 2, 29),
(20, 2, 30),
(21, 3, 11),
(22, 3, 12),
(23, 3, 13),
(24, 3, 14),
(25, 3, 6),
(26, 3, 15),
(27, 3, 16),
(28, 3, 17),
(29, 3, 18),
(30, 3, 19),
(31, 4, 31),
(32, 4, 32),
(33, 4, 33),
(34, 4, 34),
(35, 4, 26),
(36, 4, 35),
(37, 4, 36),
(38, 4, 37),
(39, 4, 38),
(40, 4, 39);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `arret`
--
ALTER TABLE `arret`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `associationconducteurservice`
--
ALTER TABLE `associationconducteurservice`
 ADD PRIMARY KEY (`id`), ADD KEY `service_id` (`service_id`), ADD KEY `conducteur_id` (`conducteur_id`);

--
-- Index pour la table `conducteur`
--
ALTER TABLE `conducteur`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `depot`
--
ALTER TABLE `depot`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `arret_id_2` (`arret_id`), ADD KEY `arret_id` (`arret_id`);

--
-- Index pour la table `feuilledeservice`
--
ALTER TABLE `feuilledeservice`
 ADD PRIMARY KEY (`id`), ADD KEY `phase_id` (`phase_id`);

--
-- Index pour la table `intervalle`
--
ALTER TABLE `intervalle`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `ligne`
--
ALTER TABLE `ligne`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `ligne_voie`
--
ALTER TABLE `ligne_voie`
 ADD PRIMARY KEY (`id`), ADD KEY `ligne_id` (`ligne_id`), ADD KEY `voie_id` (`voie_id`);

--
-- Index pour la table `periodedeconduite`
--
ALTER TABLE `periodedeconduite`
 ADD PRIMARY KEY (`id`), ADD KEY `arretEchangeConducteurDebut_id` (`arretEchangeConducteurDebut_id`), ADD KEY `arretEchangeConducteurFin_id` (`arretEchangeConducteurFin_id`), ADD KEY `vehicule_id` (`vehicule_id`), ADD KEY `service_id` (`service_id`);

--
-- Index pour la table `periodicite`
--
ALTER TABLE `periodicite`
 ADD PRIMARY KEY (`id`), ADD KEY `id_voie` (`id_voie`), ADD KEY `id_arret` (`id_arret`);

--
-- Index pour la table `phase`
--
ALTER TABLE `phase`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reseau`
--
ALTER TABLE `reseau`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
 ADD PRIMARY KEY (`id`), ADD KEY `feuilledeservice_id` (`feuilledeservice_id`);

--
-- Index pour la table `terminus`
--
ALTER TABLE `terminus`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `arret_id_2` (`arret_id`), ADD KEY `arret_id` (`arret_id`);

--
-- Index pour la table `transition`
--
ALTER TABLE `transition`
 ADD PRIMARY KEY (`id`), ADD KEY `arretPrecedent_id` (`arretPrecedent_id`), ADD KEY `arretSuivant_id` (`arretSuivant_id`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `voie`
--
ALTER TABLE `voie`
 ADD PRIMARY KEY (`id`), ADD KEY `terminusDepart_id` (`terminusDepart_id`), ADD KEY `terminusArrivee_id` (`terminusArrivee_id`);

--
-- Index pour la table `voie_arret`
--
ALTER TABLE `voie_arret`
 ADD PRIMARY KEY (`id`), ADD KEY `voie_id` (`voie_id`), ADD KEY `arret_id` (`arret_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `arret`
--
ALTER TABLE `arret`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=101;
--
-- AUTO_INCREMENT pour la table `associationconducteurservice`
--
ALTER TABLE `associationconducteurservice`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `conducteur`
--
ALTER TABLE `conducteur`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `depot`
--
ALTER TABLE `depot`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `feuilledeservice`
--
ALTER TABLE `feuilledeservice`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `intervalle`
--
ALTER TABLE `intervalle`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `ligne`
--
ALTER TABLE `ligne`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `ligne_voie`
--
ALTER TABLE `ligne_voie`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `periodedeconduite`
--
ALTER TABLE `periodedeconduite`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `phase`
--
ALTER TABLE `phase`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `reseau`
--
ALTER TABLE `reseau`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `terminus`
--
ALTER TABLE `terminus`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `transition`
--
ALTER TABLE `transition`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT pour la table `voie`
--
ALTER TABLE `voie`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `voie_arret`
--
ALTER TABLE `voie_arret`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=41;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `associationconducteurservice`
--
ALTER TABLE `associationconducteurservice`
ADD CONSTRAINT `associationconducteurservice_ibfk_1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `associationconducteurservice_ibfk_2` FOREIGN KEY (`conducteur_id`) REFERENCES `conducteur` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `depot`
--
ALTER TABLE `depot`
ADD CONSTRAINT `depot_ibfk_1` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `feuilledeservice`
--
ALTER TABLE `feuilledeservice`
ADD CONSTRAINT `feuilledeservice_ibfk_1` FOREIGN KEY (`phase_id`) REFERENCES `phase` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `ligne_voie`
--
ALTER TABLE `ligne_voie`
ADD CONSTRAINT `ligne_voie_ibfk_1` FOREIGN KEY (`ligne_id`) REFERENCES `ligne` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `ligne_voie_ibfk_2` FOREIGN KEY (`voie_id`) REFERENCES `voie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `periodedeconduite`
--
ALTER TABLE `periodedeconduite`
ADD CONSTRAINT `periodedeconduite_ibfk_1` FOREIGN KEY (`arretEchangeConducteurDebut_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `periodedeconduite_ibfk_2` FOREIGN KEY (`arretEchangeConducteurFin_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `periodedeconduite_ibfk_3` FOREIGN KEY (`vehicule_id`) REFERENCES `vehicule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `periodedeconduite_ibfk_4` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `periodicite`
--
ALTER TABLE `periodicite`
ADD CONSTRAINT `periodicite_ibfk_1` FOREIGN KEY (`id_voie`) REFERENCES `voie` (`id`),
ADD CONSTRAINT `periodicite_ibfk_2` FOREIGN KEY (`id_arret`) REFERENCES `arret` (`id`);

--
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`feuilledeservice_id`) REFERENCES `feuilledeservice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `terminus`
--
ALTER TABLE `terminus`
ADD CONSTRAINT `terminus_ibfk_1` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `transition`
--
ALTER TABLE `transition`
ADD CONSTRAINT `transition_ibfk_1` FOREIGN KEY (`arretPrecedent_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `transition_ibfk_2` FOREIGN KEY (`arretSuivant_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `voie`
--
ALTER TABLE `voie`
ADD CONSTRAINT `voie_ibfk_1` FOREIGN KEY (`terminusDepart_id`) REFERENCES `terminus` (`arret_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `voie_ibfk_2` FOREIGN KEY (`terminusArrivee_id`) REFERENCES `terminus` (`arret_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `voie_arret`
--
ALTER TABLE `voie_arret`
ADD CONSTRAINT `voie_arret_ibfk_1` FOREIGN KEY (`voie_id`) REFERENCES `voie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `voie_arret_ibfk_2` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
