-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 07 Novembre 2014 à 17:31
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.5.8

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
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `nom` int(11) DEFAULT NULL,
  `estCommercial` tinyint(1) DEFAULT NULL,
  `estEntreeSortieDepot` tinyint(1) DEFAULT NULL,
  `estOccupe` tinyint(1) DEFAULT NULL,
  `tempsImmobilisation` int(11) DEFAULT NULL,
  `estLieuEchangeConducteur` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Designe un lieu d''arret du véhicule';

-- --------------------------------------------------------

--
-- Structure de la table `associationconducteurservice`
--

CREATE TABLE IF NOT EXISTS `associationconducteurservice` (
  `id` int(11) NOT NULL DEFAULT '0',
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Assure l''association entre un conducteur et un service';

-- --------------------------------------------------------

--
-- Structure de la table `conducteur`
--

CREATE TABLE IF NOT EXISTS `conducteur` (
  `id` int(11) NOT NULL DEFAULT '0',
  `nom` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Désigne une personne habilité à la conduite';

-- --------------------------------------------------------

--
-- Structure de la table `depot`
--

CREATE TABLE IF NOT EXISTS `depot` (
  `id` int(11) NOT NULL DEFAULT '0',
  `tempsImmobilisation` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Le terminus est le premier ou le dernier arrêt d''une voie';

-- --------------------------------------------------------

--
-- Structure de la table `feuilledeservice`
--

CREATE TABLE IF NOT EXISTS `feuilledeservice` (
  `id` int(11) NOT NULL DEFAULT '0',
  `couleur` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `debutSaison` date DEFAULT NULL,
  `finSaison` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Ensemble de service décrivant l''exploitation d''une saison';

-- --------------------------------------------------------

--
-- Structure de la table `intervalle`
--

CREATE TABLE IF NOT EXISTS `intervalle` (
  `id` int(11) NOT NULL DEFAULT '0',
  `min` date DEFAULT NULL,
  `pref` date DEFAULT NULL,
  `max` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Donne le domaine de définition d''une variable';

-- --------------------------------------------------------

--
-- Structure de la table `ligne`
--

CREATE TABLE IF NOT EXISTS `ligne` (
  `id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='La ligne est la représentation de la route qu''emprunte les véhicules. ';

-- --------------------------------------------------------

--
-- Structure de la table `periodedeconduite`
--

CREATE TABLE IF NOT EXISTS `periodedeconduite` (
  `id` int(11) NOT NULL DEFAULT '0',
  `heureDebut` date DEFAULT NULL,
  `heureFin` date DEFAULT NULL,
  `numeroVehicule` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Segment d''un service de conduite';

-- --------------------------------------------------------

--
-- Structure de la table `phase`
--

CREATE TABLE IF NOT EXISTS `phase` (
  `id` int(11) NOT NULL DEFAULT '0',
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `periodicite` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description journalière du régime d''exploitation du réseau';

-- --------------------------------------------------------

--
-- Structure de la table `reseau`
--

CREATE TABLE IF NOT EXISTS `reseau` (
  `id` int(11) NOT NULL DEFAULT '0',
  `nom` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Description d''un réseau de transport ';

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `numero` int(11) NOT NULL DEFAULT '0',
  `nbPeriodeDeConduite` int(11) DEFAULT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Constitue un planning journalier d''un employé';

-- --------------------------------------------------------

--
-- Structure de la table `terminus`
--

CREATE TABLE IF NOT EXISTS `terminus` (
  `id` int(11) NOT NULL DEFAULT '0',
  `tempsImmobilisation` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `transition`
--

CREATE TABLE IF NOT EXISTS `transition` (
  `id` int(11) NOT NULL DEFAULT '0',
  `duree` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Troncon entre deux arrêts';

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE IF NOT EXISTS `vehicule` (
  `numeroSerie` int(11) NOT NULL DEFAULT '0',
  `kilometrage` decimal(10,0) DEFAULT NULL,
  `estEnService` tinyint(1) DEFAULT NULL,
  `typeVehicule` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`numeroSerie`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Matériel roulant du réseau';

-- --------------------------------------------------------

--
-- Structure de la table `voie`
--

CREATE TABLE IF NOT EXISTS `voie` (
  `id` int(11) NOT NULL DEFAULT '0',
  `direction` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Partie de la ligne marqué par un sens de circulation';

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
