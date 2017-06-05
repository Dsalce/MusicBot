-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-06-2017 a las 21:28:38
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `musicbd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `messages`
--

CREATE TABLE `messages` (
  `IdMessage` int(11) NOT NULL,
  `Message` text NOT NULL,
  `IdTypeMessage` int(11) NOT NULL,
  `Nivel` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `messages`
--

INSERT INTO `messages` (`IdMessage`, `Message`, `IdTypeMessage`, `Nivel`) VALUES
(1, 'Buenas', 1, 0),
(2, 'Hola', 1, 0),
(3, 'Buenos días', 1, 0),
(4, 'Buenas tardes', 1, 0),
(5, 'Buenas noches', 1, 0),
(6, 'Adios', 2, 0),
(7, 'Hasta luego', 2, 0),
(8, 'Hasta pronto', 2, 0),
(9, 'Nos vemos', 2, 0),
(10, '¿Cómo te ha ido el trabajo hoy?', 8, 0),
(11, '¿Que deportes practicas?', 8, 0),
(12, '¿Has quedado con alguien hoy?', 8, 0),
(14, '¿Has estado haciendo ese deporte ultimamente?', 3, 0),
(15, 'Yo tambien soy muy aficionado a ese deporte¿cuantas veces a las semana lo practicas?', 3, 1),
(16, 'Nunca he practicado ese deporte no tengo cuerpo fisico,¿Es divertido?', 3, 1),
(17, 'No conozco mucho de ese deporte.¿Me puedes contar algo acerca de el?', 3, 1),
(18, '¿Que tal te ha ido el trabajo hoy?', 8, 0),
(19, '¿A que te dedicas?', 4, 0),
(20, '¿Me parece un trabajo muy interesante?¿Me puedes contar más acerca de el?', 4, 1),
(21, '¿Que tal son tus compañeros de trabajo?', 4, 1),
(22, '¿Que tal con tu familia?', 5, 0),
(23, '¿Que has hecho hoy?', 8, 0),
(24, '¿Que tal está tu familia?', 5, 0),
(25, '¿Que gustos musicales tiene tu familia?', 5, 1),
(26, '¿Cuando fue la ultima vez que comiste en familia?', 5, 1),
(27, '¿Que tal día hace hoy?', 8, 0),
(28, '¿Que tiempo te gusta que haga?', 6, 1),
(29, 'El tiempo está como loco debe ser por el calentamiento global', 6, 1),
(30, '¿El tiempo de hoy te ha perjudicado?', 6, 1),
(31, 'Hablame de persona', 7, 0),
(32, '¿Que tal con persona?', 7, 0),
(33, '¿Quien es persona?', 7, 0),
(34, '¿Es persona tu amig@?', 7, 0),
(35, '¿Te pongo una cancion?', 9, 0),
(36, 'No consigo detectar tu estado de animo¿Me puedes ayudar?', 9, 0),
(37, 'Por favor especificame si tu estado de animo es alegre o triste', 9, 0),
(38, 'HTTP 404 Not Found', 10, 0),
(39, 'no soy capaz de entenderte ¿puedes repetir la frase?', 10, 0),
(40, 'Fistro pecador ,tas equivocao', 10, 0),
(41, 'Error', 10, 0),
(42, '¿Estas disfrutando del tiempo de hoy?', 6, 0),
(43, 'Detecto que tu estado de animo es contento', 8, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `musics`
--

CREATE TABLE `musics` (
  `IdMusic` int(11) NOT NULL,
  `Name` varchar(250) NOT NULL,
  `URL` varchar(250) NOT NULL,
  `IdState` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `musics`
--

INSERT INTO `musics` (`IdMusic`, `Name`, `URL`, `IdState`) VALUES
(30, 'ALEX', 'https://youtu.be/kJQP7kiw5Fk', 2),
(31, 'David salcedo', 'https://youtu.be/t_jHrUE5IOk', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `states`
--

CREATE TABLE `states` (
  `idState` int(11) NOT NULL,
  `State` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `states`
--

INSERT INTO `states` (`idState`, `State`) VALUES
(1, 'Triste'),
(2, 'POSITIVE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `type_message`
--

CREATE TABLE `type_message` (
  `IdTypeMessage` int(11) NOT NULL,
  `TypeMessage` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `type_message`
--

INSERT INTO `type_message` (`IdTypeMessage`, `TypeMessage`) VALUES
(1, 'Saludo'),
(2, 'Despedida'),
(3, 'Deporte'),
(4, 'Trabajo'),
(5, 'Familia'),
(6, 'Tiempo'),
(7, 'Person'),
(8, 'General'),
(9, 'Musica'),
(10, 'Error');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `chatId` int(11) NOT NULL,
  `state` varchar(256) NOT NULL,
  `password` varchar(10) DEFAULT NULL,
  `admin` int(1) NOT NULL,
  `SentimientoNegativo` int(11) NOT NULL,
  `SentimientoPositivo` int(11) NOT NULL,
  `lastMessage` varchar(500) NOT NULL,
  `sentimientoNeutral` int(11) NOT NULL,
  `caso` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`chatId`, `state`, `password`, `admin`, `SentimientoNegativo`, `SentimientoPositivo`, `lastMessage`, `sentimientoNeutral`, `caso`) VALUES
(310465742, 'Conversacion0', '', 1, 0, 0, 'Digame que cancion desea añadir', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_music`
--

CREATE TABLE `user_music` (
  `Correct` int(1) NOT NULL,
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
  ADD PRIMARY KEY (`chatId`,`IdMusic`),
  ADD KEY `IdUser_idx` (`chatId`),
  ADD KEY `IdMusic_idx` (`IdMusic`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `messages`
--
ALTER TABLE `messages`
  MODIFY `IdMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT de la tabla `musics`
--
ALTER TABLE `musics`
  MODIFY `IdMusic` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT de la tabla `states`
--
ALTER TABLE `states`
  MODIFY `idState` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `type_message`
--
ALTER TABLE `type_message`
  MODIFY `IdTypeMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `IdTypeMessage` FOREIGN KEY (`IdTypeMessage`) REFERENCES `type_message` (`IdTypeMessage`);

--
-- Filtros para la tabla `musics`
--
ALTER TABLE `musics`
  ADD CONSTRAINT `musics_ibfk_1` FOREIGN KEY (`IdState`) REFERENCES `states` (`idState`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `user_music`
--
ALTER TABLE `user_music`
  ADD CONSTRAINT `IdMusic` FOREIGN KEY (`IdMusic`) REFERENCES `musics` (`IdMusic`),
  ADD CONSTRAINT `chatId` FOREIGN KEY (`chatId`) REFERENCES `users` (`chatId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
