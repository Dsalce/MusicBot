-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-05-2017 a las 13:07:05
-- Versión del servidor: 10.1.22-MariaDB
-- Versión de PHP: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `musicdb`
--

-- --------------------------------------------------------

-- -----------------------------------------------------
-- Table `musicdb`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `messages` (
  `IdMessage` INT(11) NOT NULL AUTO_INCREMENT,
  `Message` TEXT NOT NULL,
  `IdTypeMessage` INT(11) NOT NULL,
  PRIMARY KEY (`IdMessage`),
  INDEX `IdTypeMessage_idx` (`IdTypeMessage` ASC),
  CONSTRAINT `IdTypeMessage`
    FOREIGN KEY (`IdTypeMessage`)
    REFERENCES `musicdb`.`type_message` (`IdTypeMessage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

--
-- Volcado de datos para la tabla `messages`
--

INSERT INTO `messages` (`IdMessage`, `Message`, `IdTypeMessage`) VALUES
(0, 'Error', 9),
(1, 'Buenas', 0),
(2, 'Hola', 0),
(3, 'Buenos días', 0),
(4, 'Buenas tardes', 0),
(5, 'Buenas noches', 0),
(6, 'Adios', 1),
(7, 'Hasta luego', 1),
(8, 'Hasta pronto', 1),
(9, 'Nos vemos', 1),
(10, '¿Cómo te ha ido?', 7),
(11, '¿Y qué tal?', 7),
(12, '¿Como te lo has pasado?', 7),
(13, 'Que interesante, ¿cuentame más?', 7),
(14, '¿Que deportes practicas?', 2),
(15, 'Yo tambien soy muy aficionado a ese deporte¿cuantas veces a las semana lo practicas?', 2),
(16, 'Nunca he practicado ese deporte no tengo cuerpo fisico,¿Es divertido?', 2),
(17, 'No conozco mucho de ese deporte.¿Me puedes contar algo acerca de el?', 2),
(18, '¿Que tal te ha ido el trabajo hoy?', 3),
(19, '¿A que te dedicas?', 3),
(20, '¿Me parece un trabajo muy interesante?¿Me puedes contar más acerca de el?', 3),
(21, '¿Que tal son tus compañeros de trabajo?', 3),
(22, '¿Que tal con tu familia?', 4),
(23, '¿Que has hecho hoy?', 11),
(24, '¿Que tal está tu familia?', 4),
(25, '¿Que gustos musicales tiene tu familia?', 4),
(26, '¿Cuando fue la ultima vez que comiste en familia?', 4),
(27, '¿Que tal día hace hoy?', 5),
(28, '¿Que opinión tienes acerca de los dias lluviosos?', 5),
(29, 'El tiempo está como loco debe ser por el calentamiento global', 5),
(30, 'Que calor!!,¿ha cuantos grados estamos? esto es inaguantable', 5),
(31, 'Hablame de persona', 6),
(32, '¿Que tal con persona?', 6),
(33, '¿Quien es persona?', 6),
(34, '¿Es persona tu amig@?', 6),
(35, '¿Te pongo una cancion?', 8),
(36, 'No consigo detectar tu estado de animo¿Me puedes ayudar?', 8),
(37, 'Por favor especificame si tu estado de animo es alegre o triste', 8),
(38, 'HTTP 404 Not Found', 9),
(39, 'no soy capaz de entenderte ¿puedes repetir la frase?', 9),
(40, 'Fistro pecador ,tas equivocao', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `musics`
--

CREATE TABLE IF NOT EXISTS `musics` (
  `IdMusic` int(11) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `URL` varchar(250) NOT NULL,
  `IdState` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `states`
--

CREATE TABLE IF NOT EXISTS `states` (
  `idState` int(11) NOT NULL,
  `State` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- -----------------------------------------------------
-- Table `musicdb`.`type_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicdb`.`type_message` (
  `IdTypeMessage` INT(11) NOT NULL AUTO_INCREMENT,
  `TypeMessage` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdTypeMessage`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `chatId` int(11) NOT NULL,
  `state` varchar(256) NOT NULL,
  `password` varchar(10) DEFAULT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_music`
--

CREATE TABLE IF NOT EXISTS `user_music` (
  `Id` int(11) NOT NULL,
  `Correct` bit(1) NOT NULL,
  `chatId` int(11) NOT NULL,
  `IdMusic` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`IdMessage`),
  ADD KEY `IdTypeMessage_idx` (`IdTypeMessage`);

--
-- Indices de la tabla `musics`
--
ALTER TABLE `musics`
  ADD PRIMARY KEY (`IdMusic`),
  ADD KEY `IdState_idx` (`IdState`);

--
-- Indices de la tabla `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`idState`);

--
-- Indices de la tabla `type_message`
--
ALTER TABLE `type_message`
  ADD PRIMARY KEY (`IdTypeMessage`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`chatId`);

--
-- Indices de la tabla `user_music`
--
ALTER TABLE `user_music`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `IdUser_idx` (`chatId`),
  ADD KEY `IdMusic_idx` (`IdMusic`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `messages`
--
ALTER TABLE `messages`
  MODIFY `IdMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT de la tabla `musics`
--
ALTER TABLE `musics`
  MODIFY `IdMusic` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `states`
--
ALTER TABLE `states`
  MODIFY `idState` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `type_message`
--
ALTER TABLE `type_message`
  MODIFY `IdTypeMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `user_music`
--
ALTER TABLE `user_music`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `type_message`
--
ALTER TABLE `type_message`
  ADD CONSTRAINT `type_message_ibfk_1` FOREIGN KEY (`IdTypeMessage`) REFERENCES `messages` (`IdTypeMessage`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
