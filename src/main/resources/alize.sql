-- phpMyAdmin SQL Dump
<<<<<<< HEAD
-- version 3.4.11.1deb2+deb7u1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Ven 14 Novembre 2014 à 16:14
-- Version du serveur: 5.5.40
-- Version de PHP: 5.4.34-0+deb7u1
=======
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 16 Novembre 2014 à 12:54
-- Version du serveur :  5.5.40-0+wheezy1
-- Version de PHP :  5.4.34-0+deb7u1
>>>>>>> develop

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
<<<<<<< HEAD
-- Base de données: `alize`
=======
-- Base de données :  alize
>>>>>>> develop
--

-- --------------------------------------------------------

--
-- Structure de la table arret
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `arret` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` int(11) DEFAULT NULL,
  `estCommercial` tinyint(1) DEFAULT NULL,
  `estEntreeSortieDepot` tinyint(1) DEFAULT NULL,
  `estOccupe` tinyint(1) DEFAULT NULL,
  `tempsImmobilisation` int(11) DEFAULT NULL,
  `estLieuEchangeConducteur` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu d''arret du véhicule' AUTO_INCREMENT=1 ;
=======
CREATE TABLE IF NOT EXISTS arret (
  id int(11) NOT NULL AUTO_INCREMENT,
  nom int(11) varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  estCommercial tinyint(1) DEFAULT NULL,
  estEntreeSortieDepot tinyint(1) DEFAULT NULL,
  estOccupe tinyint(1) DEFAULT NULL,
  tempsImmobilisation int(11) DEFAULT NULL,
  estLieuEchangeConducteur tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu darret du véhicule';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table conducteur
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `associationconducteurservice` (
  `id` int(11) NOT NULL DEFAULT '0',
  `date` date DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  `conducteur_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Assure l''association entre un conducteur et un service';
=======
CREATE TABLE IF NOT EXISTS conducteur (
  id int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  telephone varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Désigne une personne habilité à la conduite';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table intervalle
--

CREATE TABLE IF NOT EXISTS intervalle (
  id int(11) NOT NULL AUTO_INCREMENT,
  min time DEFAULT NULL,
  pref time DEFAULT NULL,
  max time DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Donne le domaine de définition dune variable';

--
-- Contenu de la table intervalle
--

INSERT INTO intervalle (id, min, pref, max) VALUES
(0, '00:20:14', '00:20:14', '00:20:14');

-- --------------------------------------------------------

--
-- Structure de la table ligne
--

CREATE TABLE IF NOT EXISTS ligne (
  id int(11) NOT NULL AUTO_INCREMENT,
  typeVehicule varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='La ligne est la représentation de la route quemprunte les véhicules.';

-- --------------------------------------------------------

--
-- Structure de la table phase
--

CREATE TABLE IF NOT EXISTS phase (
  id int(11) NOT NULL AUTO_INCREMENT,
  debut date DEFAULT NULL,
  fin date DEFAULT NULL,
  periodicite date DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description journalière du régime dexploitation du réseau';

-- --------------------------------------------------------

--
-- Structure de la table reseau
--

CREATE TABLE IF NOT EXISTS reseau (
  id int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description dun réseau de transport';

-- --------------------------------------------------------

--
-- Structure de la table vehicule
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `intervalle` (
  `id` int(11) NOT NULL DEFAULT '0',
  `min` time DEFAULT NULL,
  `pref` time DEFAULT NULL,
  `max` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Donne le domaine de définition d''une variable';
=======
CREATE TABLE IF NOT EXISTS vehicule (
  id int(11) NOT NULL COMMENT 'Numéro de série du véhicule',
  kilometrage decimal(10,0) DEFAULT NULL,
  estEnService tinyint(1) DEFAULT NULL,
  typeVehicule varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Matériel roulant du réseau';
>>>>>>> develop

--
-- Contenu de la table `intervalle`
--

INSERT INTO `intervalle` (`id`, `min`, `pref`, `max`) VALUES
(0, '00:20:14', '00:20:14', '00:20:14');

-- --------------------------------------------------------

--
-- Structure de la table feuilledeservice
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `ligne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='La ligne est la représentation de la route qu''emprunte les véhicules. ' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `ligne_voie`
--

CREATE TABLE IF NOT EXISTS `ligne_voie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ligne_id` int(11) NOT NULL,
  `voie_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
=======
CREATE TABLE IF NOT EXISTS feuilledeservice (
  id int(11) NOT NULL AUTO_INCREMENT,
  couleur varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  debutSaison date DEFAULT NULL,
  finSaison date DEFAULT NULL,
  phase_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (phase_id) REFERENCES phase(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Ensemble de service décrivant lexploitation dune saison';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table service
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `periodedeconduite` (
  `id` int(11) NOT NULL DEFAULT '0',
  `heureDebut` date DEFAULT NULL,
  `heureFin` date DEFAULT NULL,
  `numeroVehicule` int(11) DEFAULT NULL,
  `arretEchangeConducteurDebut_id` int(11) NOT NULL,
  `arretEchangeConducteurFin_id` int(11) NOT NULL,
  `vehicule_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Segment d''un service de conduite';
=======
CREATE TABLE IF NOT EXISTS service (
  id int(11) NOT NULL COMMENT 'Numéro de service',
  nbPeriodeDeConduite int(11) DEFAULT NULL,
  feuilledeservice_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (feuilledeservice_id) REFERENCES feuilledeservice(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Constitue un planning journalier dun employé';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table associationconducteurservice
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `phase` (
  `id` int(11) NOT NULL DEFAULT '0',
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `periodicite` date DEFAULT NULL,
  `phase_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description journalière du régime d''exploitation du réseau';
=======
CREATE TABLE IF NOT EXISTS associationconducteurservice (
  id int(11) NOT NULL AUTO_INCREMENT,
  date date DEFAULT NULL,
  service_id int(11) NOT NULL,
  conducteur_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (service_id) REFERENCES service(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (conducteur_id) REFERENCES conducteur(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Assure lassociation entre un conducteur et un service';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table depot
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `reseau` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description d''un réseau de transport ' AUTO_INCREMENT=1 ;
=======
CREATE TABLE IF NOT EXISTS depot (
  id int(11) NOT NULL AUTO_INCREMENT,
  arret_id int(11) NOT NULL,
  tempsImmobilisation_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arret_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (tempsImmobilisation_id) REFERENCES intervalle(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Le terminus est le premier ou le dernier arrêt dune voie';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table terminus
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `service` (
  `id` int(11) NOT NULL COMMENT 'Numéro de service',
  `nbPeriodeDeConduite` int(11) DEFAULT NULL,
  `feuilledeservice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Constitue un planning journalier d''un employé';
=======
CREATE TABLE IF NOT EXISTS terminus (
  id int(11) NOT NULL AUTO_INCREMENT,
  arret_id int(11) NOT NULL,
  tempsImmobilisation_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arret_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (tempsImmobilisation_id) REFERENCES intervalle(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table voie
--

CREATE TABLE IF NOT EXISTS voie (
  id int(11) NOT NULL AUTO_INCREMENT,
  direction varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  terminusDepart_id int(11) NOT NULL,
  terminusArrivee_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (terminusDepart_id) REFERENCES terminus(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (terminusArrivee_id) REFERENCES terminus(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Partie de la ligne marqué par un sens de circulation';

-- --------------------------------------------------------

--
-- Structure de la table ligne_voie
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `transition` (
  `id` int(11) NOT NULL DEFAULT '0',
  `duree` date DEFAULT NULL,
  `arretPrecedent_id` int(11) NOT NULL,
  `arretSuivant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Troncon entre deux arrêts';
=======
CREATE TABLE IF NOT EXISTS ligne_voie (
  id int(11) NOT NULL AUTO_INCREMENT,
  ligne_id int(11) NOT NULL,
  voie_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (ligne_id) REFERENCES ligne(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (voie_id) REFERENCES voie(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table periodedeconduite
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `vehicule` (
  `id` int(11) NOT NULL COMMENT 'Numéro de série du véhicule',
  `kilometrage` decimal(10,0) DEFAULT NULL,
  `estEnService` tinyint(1) DEFAULT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Matériel roulant du réseau';
=======
CREATE TABLE IF NOT EXISTS periodedeconduite (
  id int(11) NOT NULL AUTO_INCREMENT,
  heureDebut date DEFAULT NULL,
  heureFin date DEFAULT NULL,
  numeroVehicule int(11) DEFAULT NULL,
  arretEchangeConducteurDebut_id int(11) NOT NULL,
  arretEchangeConducteurFin_id int(11) NOT NULL,
  vehicule_id int(11) NOT NULL,
  service_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arretEchangeConducteurDebut_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (arretEchangeConducteurFin_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (vehicule_id) REFERENCES vehicule(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (service_id) REFERENCES service(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Segment dun service de conduite';
>>>>>>> develop

-- --------------------------------------------------------

--
-- Structure de la table transition
--

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS `voie` (
  `id` int(11) NOT NULL DEFAULT '0',
  `direction` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `terminusDepart_id` int(11) NOT NULL,
  `terminusArrivee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Partie de la ligne marqué par un sens de circulation';
=======
CREATE TABLE IF NOT EXISTS transition (
  id int(11) NOT NULL AUTO_INCREMENT,
  duree date DEFAULT NULL,
  arretPrecedent_id int(11) NOT NULL,
  arretSuivant_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (arretPrecedent_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (arretSuivant_id) REFERENCES arret(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci  COMMENT='Troncon entre deux arrêts';
>>>>>>> develop

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
