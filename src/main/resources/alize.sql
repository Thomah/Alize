-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Lun 17 Novembre 2014 à 18:40
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
  `estEntreeSortieDepot` tinyint(1) DEFAULT NULL,
  `estOccupe` tinyint(1) DEFAULT NULL,
  `tempsImmobilisation` int(11) DEFAULT NULL,
  `estLieuEchangeConducteur` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu darret du véhicule';

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
  `arret_id` int(11) NOT NULL,
  `tempsImmobilisation_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Le terminus est le premier ou le dernier arrêt dune voie';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Donne le domaine de définition dune variable';

--
-- Contenu de la table `intervalle`
--

INSERT INTO `intervalle` (`id`, `min`, `pref`, `max`) VALUES
(0, '00:20:14', '00:20:14', '00:20:14');

-- --------------------------------------------------------

--
-- Structure de la table `ligne`
--

CREATE TABLE IF NOT EXISTS `ligne` (
`id` int(11) NOT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='La ligne est la représentation de la route quemprunte les véhicules.';

-- --------------------------------------------------------

--
-- Structure de la table `ligne_voie`
--

CREATE TABLE IF NOT EXISTS `ligne_voie` (
`id` int(11) NOT NULL,
  `ligne_id` int(11) NOT NULL,
  `voie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description dun réseau de transport';

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
  `arret_id` int(11) NOT NULL,
  `tempsImmobilisation_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `transition`
--

CREATE TABLE IF NOT EXISTS `transition` (
`id` int(11) NOT NULL,
  `duree` date DEFAULT NULL,
  `arretPrecedent_id` int(11) NOT NULL,
  `arretSuivant_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Troncon entre deux arrêts';

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

-- --------------------------------------------------------

--
-- Structure de la table `voie`
--

CREATE TABLE IF NOT EXISTS `voie` (
`id` int(11) NOT NULL,
  `direction` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `terminusDepart_id` int(11) NOT NULL,
  `terminusArrivee_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Partie de la ligne marqué par un sens de circulation';

-- --------------------------------------------------------

--
-- Structure de la table `voie_arret`
--

CREATE TABLE IF NOT EXISTS `voie_arret` (
`id` int(11) NOT NULL,
  `voie_id` int(11) NOT NULL,
  `arret_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu darret du véhicule';

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
 ADD PRIMARY KEY (`id`), ADD KEY `arret_id` (`arret_id`), ADD KEY `tempsImmobilisation_id` (`tempsImmobilisation_id`);

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
 ADD PRIMARY KEY (`id`), ADD KEY `arret_id` (`arret_id`), ADD KEY `tempsImmobilisation_id` (`tempsImmobilisation_id`);

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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `feuilledeservice`
--
ALTER TABLE `feuilledeservice`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `intervalle`
--
ALTER TABLE `intervalle`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `ligne`
--
ALTER TABLE `ligne`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `ligne_voie`
--
ALTER TABLE `ligne_voie`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `terminus`
--
ALTER TABLE `terminus`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `transition`
--
ALTER TABLE `transition`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `voie`
--
ALTER TABLE `voie`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `voie_arret`
--
ALTER TABLE `voie_arret`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
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
ADD CONSTRAINT `depot_ibfk_1` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `depot_ibfk_2` FOREIGN KEY (`tempsImmobilisation_id`) REFERENCES `intervalle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`feuilledeservice_id`) REFERENCES `feuilledeservice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `terminus`
--
ALTER TABLE `terminus`
ADD CONSTRAINT `terminus_ibfk_1` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `terminus_ibfk_2` FOREIGN KEY (`tempsImmobilisation_id`) REFERENCES `intervalle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
ADD CONSTRAINT `voie_ibfk_1` FOREIGN KEY (`terminusDepart_id`) REFERENCES `terminus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `voie_ibfk_2` FOREIGN KEY (`terminusArrivee_id`) REFERENCES `terminus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `voie_arret`
--
ALTER TABLE `voie_arret`
ADD CONSTRAINT `voie_arret_ibfk_1` FOREIGN KEY (`voie_id`) REFERENCES `voie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `voie_arret_ibfk_2` FOREIGN KEY (`arret_id`) REFERENCES `arret` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
