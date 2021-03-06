-----------------------------------------------------------------------------------------------------------------
-------------------------------------------------INSERTIONS--------------------------------------------------------
-------------------------------------------------------------------------------------------------------------

----------------------------RELACIONES-----------------------------


---------------PRUEBAS UNITARIAS


---------------

-----1  UNICCIDAD
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8002','CC','el señor funciona','DIRECCION:funciona  ','8002');
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8002','CC','CACERES FALSO','DIRECCION:LA QUE NO FUNCIONA ','8003');
-----2 INTEGRIDAD FK


--INSERCION CUMPLE  
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('10000','CC','EL VECI','DIRECCION:CARRERA 1 CALLE20  ','8004');

-- INSERCION NO CUMPLE
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('10001','CC','EL VECI_QUE NO SIRVE','DIRECCION:LA MALA  ','9999');



---BORRADO DE TUPLAS DEPENDIENTES

DELETE FROM OPERADORES WHERE ID='8002';
DELETE FROM OPERADORES WHERE ID='10001';

----3 INTEGRIDAD CHEQUEO
--CUMPLE  INSERCION
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8005','CC','BOGTA CONFORT ','DIRECCION:LA SEPTIMA  ','8005');

--NO CUMPLE
--CHECK_TIPO_ID  INSERCION
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8006','CEDULA','ANDRES TORRES','DIRECCION:LA MALA  ','8006');



---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE

DELETE FROM OPERADORES WHERE ID='8005';
DELETE FROM OPERADORES WHERE ID='8006';



--------------CLIENTESPRUEBAS UNITARIAS----------------------




---1)UNICIDAD

Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('9001','CC','EL NICO','EL QUE SIRVE','9001');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('9001','CC','EL NICO FALSO','DEL QUE NO SIRVE','9002');---NO INSERTA
---2)INTEGRIDAD FK
--SIRVE LA INSERCION
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('9003','CC','EL SENOR FUNCIONA','FK ESTA EN RELACIONES','9003');
--NO SIRVE LA INSERCION
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('9004','CC','EL NICO FALSO','FK NO ESTA EN RELACIONES','10000');



---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE BOORRAR

DELETE FROM CLIENTES WHERE ID='9003';--BORRA
DELETE FROM CLIENTES WHERE ID='9004';--NO BORRA PORQUE NO EXISTE, PERO NO MANDA ERROR



--3 INTEGRIDAD CHEQUEO
--CUMPLE  INSERCION
Insert into CLIENTES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('9005','CC','BBUENA INSERCION ','BUENA INSERCION  ','9005');

--NO CUMPLE
Insert into CLIENTES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('9006','CEDULA','CEDULA ','MALA INSERCION  ','9006');



---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE

DELETE FROM CLIENTES WHERE ID='9005';
DELETE FROM CLIENTES WHERE ID='9006';---NO BORRA PUES NUNCA INSERTO PERO NO MANDA ERROR


--------------------------------------------------------------------------------------------
----------------SERVICIOS: PRUEBAS UNITARIAS----------------------------------
--------------------------------------------------------------------------------------------
----------------------1)UNICIDAD
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('1001','SERVICIO 1','SERVICIO 2','10000');---

Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('1001','SERVICIO 2','SERVICIO 2','5000');---NO INSERTABLE
--------------------2)INTEGRIDAD FK
----no hay llaves foraneas

------------------3 INTEGRIDAD CHEQUEO
--CUMPLE  INSERCION
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('1003','SERVICIO 3','SERVICIO 3','10000');----INSERTABLE
--NO CUMPLE
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('1004','SERVICIO 4','valor negativo','-10000');

----BORRADO DE TUPLAS MAESTRAS:NO hay llaves maestras
---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE

DELETE FROM SERVICIOS WHERE ID='1003';
DELETE FROM SERVICIOS WHERE ID='1004';---NO BORRA PUES NUNCA INSERTO PERO NO MANDA ERROR

-------OFERTAS------------------------
--DATA
---ademas del archivo DATA_DB.sql los siguientes insert

Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('6001','CC','operador 1','operador 1  ','6001');

Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('6002','CC','operador 1','operador 1  ','6002');

----------------------1)UNICIDAD
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('56001','OFERTA 1','85000', '1','6001','HAB HOTEL');--inserta
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('56001','OFERTA 2','6500', '1','6001','HAB HOTEL');--no inserta


------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('56002','OFERTA 3','85000', '1','6001','HAB HOTEL');--inserta

--NO SIRVE LA INSERCION
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('56003','OFERTA 4','78000', '1','9999','HAB HOTEL');--NO inserta

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OPERADORES  WHERE ID='6001';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE BOORRAR

DELETE FROM OFERTAS WHERE ID='56002';--BORRA
DELETE FROM OFERTAS WHERE ID='56003';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION

Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('66001','OFERTA 4','78000', '1','6002','HAB HOTEL');--NO inserta

--NO CUMPLE
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('66002','OFERTA 5','89000', '1','6002','NO CUMPLIDA');--NO inserta

----BORRADO DE TUPLAS MAESTRAS:NO hay llaves maestras

DELETE FROM OPERADORES  WHERE ID='6002';--VIOLA INTEGRIDAD
---BORRADO DE TUPLAS DEPENDIENTES:SI ES POSIBLE

DELETE FROM OFERTAS WHERE ID='66001';
DELETE FROM OFERTAS WHERE ID='66002';---NO BORRA PUES NUNCA INSERTO PERO NO MANDA ERROR



------DATA PARA TODAS LAS SUBCLASES DE OFERTAS 
----------------------------
----------------------------
----------------------------
----------------------------

Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('222','CC','operador 1','operador 1  ','222');

-----HAB HOTEL
--DATA
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('9801','HAB HOTEL 1','85000', '2','222','HAB HOTEL');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('9802','HAB HOTEL 2','45000', '1','222','HAB HOTEL');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('9803','HAB HOTEL 3','110500', '4','222','HAB HOTEL');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('9804','HAB HOTEL 4','110500', '4','222','HAB HOTEL');

------------1) UNICIDAD
Insert into HOTEL(ID,TIPO_HABITACION) VALUES('9801','SEMISUITE');
Insert into HOTEL(ID,TIPO_HABITACION) VALUES('9801','SUITE');--VIOLA UNICIDAD

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into HOTEL(ID,TIPO_HABITACION) VALUES('9802','SEMISUITE');
--NO SIRVE LA INSERCION
Insert into HOTEL(ID,TIPO_HABITACION) VALUES('13321','SEMISUITE');--NO CONSERVA LA REGLA DE SAME ID

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='9802';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='13321';--LA LLAVE NO EXISTE

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR

DELETE FROM HOTEL WHERE ID='9802';--BORRA
DELETE FROM HOTEL WHERE ID='13321';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into HOTEL(ID,TIPO_HABITACION) VALUES('9803','ESTANDAR');

--NO CUMPLE
Insert into HOTEL(ID,TIPO_HABITACION) VALUES('9804','NO CUMPLE');

----BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM OFERTAS  WHERE ID='9803';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='9804';--VIOLA INTEGRIDAD NO BORRA

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM HOTEL WHERE ID='9803';
DELETE FROM HOTEL WHERE ID='9803';---NO BORRA PUES NUNCA INSERTO PERO NO MANDA ERROR



------HOSTAL
--DATA

Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('4301','HAB HOSTAL 1','15000', '1','222','HAB HOSTAL');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('4302','HAB HOSTAL 2','15000', '1','222','HAB HOSTAL');

------------1) UNICIDAD
Insert into HOSTAL(ID,HORARIO_APERTURA,HORARIO_CIERRE,COMPARTIDA) VALUES('4301',NULL,NULL,'F');
Insert into HOSTAL(ID,HORARIO_APERTURA,HORARIO_CIERRE,COMPARTIDA) VALUES('4301',NULL,NULL,'T');

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into HOSTAL(ID,HORARIO_APERTURA,HORARIO_CIERRE,COMPARTIDA) VALUES('4302',NULL,NULL,'F');
--NO SIRVE LA INSERCION
Insert into HOSTAL(ID,HORARIO_APERTURA,HORARIO_CIERRE,COMPARTIDA) VALUES('9999',NULL,NULL,'F');---integrdiad fk

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='4302';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='9999';--LA LLAVE NO EXISTE

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR

DELETE FROM HOSTAL WHERE ID='4302';--BORRA
DELETE FROM HOSTAL WHERE ID='9999';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO
--NO HAY CHEQUEOS



----APARTAMENTO
--DATA
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('7801','APARTAMENTO 1','110500', '4','222','APARTAMENTO');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('7802','APARTAMENTO 1','110500', '4','222','APARTAMENTO');

------------1) UNICIDAD

Insert into APARTAMENTOS(ID,AMOBLADO)values('7801','T');
Insert into APARTAMENTOS(ID,AMOBLADO)values('7801','F');
------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION
Insert into APARTAMENTOS(ID,AMOBLADO)values('7802','T');

--NO SIRVE LA INSERCION
Insert into APARTAMENTOS(ID,AMOBLADO)values('9999','T');

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='7802';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='9999';--LA LLAVE NO EXISTE

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR

DELETE FROM APARTAMENTOS WHERE ID='7802';--BORRA
DELETE FROM APARTAMENTOS WHERE ID='9999';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO
--NO HAY CHEQUEOS

---------HABITACIONES UNIVERSITARIAS
--DATA
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('3701','UNIVERSITARIA 1','680000', '1','222','HAB UNIVERSITARIA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('3702','UNIVERSITARIA 2','20000', '1','222','HAB UNIVERSITARIA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('3703','UNIVERSITARIA 3','20000', '1','222','HAB UNIVERSITARIA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('3704','UNIVERSITARIA 5','20000', '1','222','HAB UNIVERSITARIA');

------------1) UNICIDAD
Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('3701','1');
Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('3701','180');--VIOLA UNICIDAD

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('3702','1');
--NO SIRVE LA INSERCION
Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('999','1');

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='3702';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='999';--LA LLAVE NO EXISTE

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR

DELETE FROM VIVIENDA_UNIVERSITARIA WHERE ID='3702';--BORRA
DELETE FROM VIVIENDA_UNIVERSITARIA WHERE ID='999';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('3703','360');

--NO CUMPLE
Insert into VIVIENDA_UNIVERSITARIA(ID,DURACION_DE_HAB) VALUES('3704','45');--NO CUMPLE PLANES SEMESTRAL ANUAL O DIARIO

----BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM OFERTAS  WHERE ID='3703';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='3704';--NO VIOLA INTEGRIDAD NO BORRA

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM VIVIENDA_UNIVERSITARIA WHERE ID='3703'; --BORRA
DELETE FROM VIVIENDA_UNIVERSITARIA WHERE ID='3704';---NO BORRA PUES NUNCA INSERTO PERO NO MANDA ERROR


-------VIVIENDA

--DATA

Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2201','VIVIENDA 1','450000', '1','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2202','VIVIENDA 2','340000','4','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2203','VIVIENDA 3','450000', '1','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2204','VIVIENDA 4','340000','4','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2205','VIVIENDA 5','340000','4','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2206','VIVIENDA 6','340000','4','222','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2207','VIVIENDA 7','340000','4','222','VIVIENDA');

------------1) UNICIDAD
Insert into HABITACION(ID,CEDIDO,COMPARTIDO)values('2201','F','T'); 
Insert into HABITACION(ID,CEDIDO,COMPARTIDO)values('2201','F','T'); 

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into HABITACION(ID,CEDIDO,COMPARTIDO)values('2202','F','T'); 
--NO SIRVE LA INSERCION
Insert into HABITACION(ID,CEDIDO,COMPARTIDO)values('999','F','T'); 

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='2202';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='999';--LA LLAVE NO EXISTE

---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR

DELETE FROM HABITACION WHERE ID='2202';--BORRA
DELETE FROM HABITACION WHERE ID='999';--NO BORRA PORQUE NO FUE INSERTADO.PERO NO MANDA ERROR


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into HABITACION(ID,CEDIDO,COMPARTIDO)values('2203','F','T'); 
Insert into HABITACION(ID,NUM_HABITACIONES,CEDIDO,COMPARTIDO)values('2204','5','T','T'); 


--NO CUMPLE
Insert into HABITACION(ID,NUM_HABITACIONES,CEDIDO,COMPARTIDO)values('2205','0','T','T'); 
Insert into HABITACION(ID,NUM_HABITACIONES,CEDIDO,COMPARTIDO)values('2206','4','F','T'); 
Insert into HABITACION(ID,NUM_HABITACIONES,CEDIDO,COMPARTIDO)values('2207',NULL,'T','T'); 



----BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM OFERTAS  WHERE ID='2203';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='2204';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='2205';--NO VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='2206';--NO VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='2207';--NO VIOLA INTEGRIDAD



---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM HABITACION  WHERE ID='2203';--BORRA
DELETE FROM HABITACION  WHERE ID='2204';--BORRA
DELETE FROM HABITACION  WHERE ID='2205';--NADA QUE BORRAR
DELETE FROM HABITACION  WHERE ID='2206';--NADA QUE BORRAR
DELETE FROM HABITACION  WHERE ID='2207';--NADA QUE BORRAR


-----RESERVAS---------------------------------------------------------------

--DATA
Insert into RELACIONES(ID,TIPO,CARNET) VALUES('6701','ESTUDIANTE','201418068');
Insert into RELACIONES(ID,TIPO,CARNET) VALUES('6702','PROFESOR','201418068');

Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('6702','CC','OPERADOR 1','OPERADOR 1  ','6702');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('333','OFERTA 1','340000','4','6702','VIVIENDA');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('6701','CC','CLIENTE 1','CLIENTE 1','6701');
--DELETE FROM RESERVAS WHERE ID LIKE'69%';
------------1) UNICIDAD


Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6901','1','30/08/18','04/09/18','2','85000,12','27/08/18','333','6701');

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6901','2','18/06/18','04/06/18','2','85000,12','18/05/18','333','6701');

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6902','2','18/06/18','04/07/18','2','85000,12','18/05/18','333','6701');


--NO SIRVE LA INSERCION
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6903','2','18/06/18','04/07/18','2','85000,12','18/05/18','9999','6701');
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6904','2','18/06/18','04/07/18','2','85000,12','18/05/18','333','9999');

----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM CLIENTES  WHERE ID='6701';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='333';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM RESERVAS  WHERE ID='6902';--BORRA
DELETE FROM RESERVAS  WHERE ID='6903';--NO BORRA NADA PORQUE NO INGRESO
DELETE FROM RESERVAS  WHERE ID='6904';--NO BORRA NADA PORQUE NO INGRESO


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6904','2','18/06/18','04/07/18','2','85000,12','18/05/18','333','6701');



--NO CUMPLE
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6905','0','18/06/18','04/07/18','2','85000,12','18/05/18','333','6701');---NO CUMPLE NUM DIAS

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6906','10','18/06/18','04/07/18','0','85000,12','18/05/18','333','6701');---NO CUMPLE NUM PERSONAS

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('6906','2','18/06/18','04/07/18','2','0','18/05/18','333','6701');----NO CUMPLE COSTO

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE,CANCELADA,FECHA_CANCELACION)
values('6907','2','18/06/18','04/07/18','2','85000,12','18/05/18','333','6701','F','18/05/18');--NO CUMPLE CHECK CANCELACION

Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE,CANCELADA,FECHA_CANCELACION)
values('6908','2','18/06/18','04/07/18','2','85000,12','18/05/18','333','6701','T',NULL);----NO CUMPLEE CHECK CANCELACION




----BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM CLIENTES  WHERE ID='6701';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='333';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM RESERVAS  WHERE ID='6904';--BORRA
DELETE FROM RESERVAS  WHERE ID='6905';--NADA QUE BORRAR
DELETE FROM RESERVAS  WHERE ID='6906';--NADA QUE BORRAR
DELETE FROM RESERVAS  WHERE ID='6907';--NADA QUE BORRAR



---------------------
--------------------CONTRATOS----------------
--DATA


Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('444','CC','OPERADOR 1','OPERADOR 1  ','3568');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('666','OFERTA 1','340000','4','444','VIVIENDA');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('666','CC','CLIENTE 1','CLIENTE 1','3567');
------------1) UNICIDAD

Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9201','30','F','T','F','01/04/18','450000','666','666');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9201','30','F','T','F','01/04/18','450000','666','666');---NO INSERTA


------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9202','30','F','T','F','01/04/18','450000','666','666');


--NO SIRVE LA INSERCION
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9203','30','F','T','F','01/04/18','450000','7777','666');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9204','30','F','T','F','01/04/18','450000','666','7777');


----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM CLIENTES  WHERE ID='666';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='666';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM CONTRATOS  WHERE ID='9202';--BORRA
DELETE FROM CONTRATOS  WHERE ID='9203';--NO BORRA NADA PORQUE NO INGRESO
DELETE FROM CONTRATOS  WHERE ID='9204';--NO BORRA NADA PORQUE NO INGRESO


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9205','30','F','T','F','01/04/18','450000','666','666');



--NO CUMPLE
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9206','0','F','T','F','01/04/18','450000','666','666');
---NO CUMPLE NUM DIAS

Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('9207','30','F','T','F','01/04/18','0','666','666');---NO CUMPLE COSTO


---BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM CLIENTES  WHERE ID='666';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='666';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM CONTRATOS  WHERE ID='9205';--BORRA
DELETE FROM CONTRATOS  WHERE ID='9206';--NADA QUE BORRAR
DELETE FROM CONTRATOS  WHERE ID='9207';--NADA QUE BORRAR

---------------------
------------SEGUROS

--DATA
Insert into RELACIONES(ID,TIPO,CARNET) VALUES('4567','ESTUDIANTE','201418068');
Insert into RELACIONES(ID,TIPO,CARNET) VALUES('4568','PROFESOR','201418068');

Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('888','CC','OPERADOR 1','OPERADOR 1  ','4568');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('8888','OFERTA 1','340000','4','888','VIVIENDA');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('8888','CC','CLIENTE 1','CLIENTE 1','4567');

Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('888','30','F','T','F','01/04/18','450000','8888','8888');

Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8801','30','F','T','F','01/04/18','450000','8888','8888');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8802','30','F','T','F','01/04/18','450000','8888','8888');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8803','30','F','T','F','01/04/18','450000','8888','8888');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8804','30','F','T','F','01/04/18','450000','8888','8888');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8805','30','F','T','F','01/04/18','450000','8888','8888');
Insert into CONTRATOS(ID,NUM_DIAS,PAGO_ANTICIPADO,INCLUYE_SERVICIOS,INCLUYE_ADMON,FECHA,PRECIO_TOTAL,ID_CLIENTE, ID_OFERTA)
values('8806','30','F','T','F','01/04/18','450000','8888','8888');

------------1) UNICIDAD

Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8801','3900000','8801');
Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8801','3900000','8802');

------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION
Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8802','3900000','8802');

--NO SIRVE LA INSERCION

Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8803','3900000','9999');
----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM CONTRATOS  WHERE ID='8802';--VIOLA INTEGRIDAD
DELETE FROM CONTRATOS  WHERE ID='8803';--NO VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM SEGUROS  WHERE ID='8802';--BORRA
DELETE FROM SEGUROS  WHERE ID='8803';--NO BORRA NADA PORQUE NO INGRESO


------------------3 INTEGRIDAD CHEQUEO

--CUMPLE  INSERCION
Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8804','3900000','8804');

--NO CUMPLE

Insert into SEGUROS(ID,VALOR,ID_CONTRATO) VALUES('8805','3900000','8804');



---BORRADO DE TUPLAS MAESTRAS:EN PRINCIPIO NO DEBERIA BORRAR

DELETE FROM CONTRATOS  WHERE ID='8804';--VIOLA INTEGRIDAD



---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE

DELETE FROM SEGUROS  WHERE ID='8804';--BORRA
DELETE FROM SEGUROS  WHERE ID='8805';--NADA QUE BORRAR



----------------------SERVICIOS PREFERIDOS----

--DATA

Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('8701','CC','CLIENTE 1','CLIENTE 1','8701');

Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('8702','CC','CLIENTE 2','CLIENTE 2','8702');

Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('8703','CC','CLIENTE 3','CLIENTE 3','8703');

Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8701','SERVICIO 1','SERVICIO 1','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8702','SERVICIO 2','SERVICIO 3','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8703','SERVICIO 3','SERVICIO 3','10000');---
----




------------1) UNICIDAD

Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8701','8701');
Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8701','8701');---NO ENTRA

Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8701','8702');---SI SIRVE
Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8702','8701');---SI SIRVE


------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8703','8703');---SI SIRVE


--NO SIRVE LA INSERCION
Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('9999','8703');---NO SIRVE
Insert into SERVICIOS_PREFERIDOS (ID_SERVICIO,ID_CLIENTE) values('8703','9999');---BO SIRVE


----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM SERVICIOS  WHERE ID='8703';--VIOLA INTEGRIDAD
DELETE FROM CLIENTES  WHERE ID='8703';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM SERVICIOS_PREFERIDOS WHERE ID_CLIENTE='8703' AND ID_SERVICIO='8703';--BORRA

------------------3 INTEGRIDAD CHEQUEO

---NADA QUE CHEQUEAR


-----SERVICIOS OFRECIDOS---------

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('8401','PROFESOR','201418068');
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8401','CC','OPERADOR 1','OPERADOR 1  ','8401');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('8401','OFERTA 1','340000','4','8401','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('8402','OFERTA 2','340000','4','8401','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('8403','OFERTA 3','340000','4','8401','VIVIENDA');


Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8401','SERVICIO 1','SERVICIO 1','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8402','SERVICIO 2','SERVICIO 3','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('8403','SERVICIO 3','SERVICIO 3','10000');---
----
------------1) UNICIDAD

Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8401','8401');--SI SIRVE
Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8401','8401');---NO SIRVE
Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8401','8402');--SI SIRVE
Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8402','8401');--SI SIRVE



------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8403','8403');--SI SIRVE


--NO SIRVE LA INSERCION
Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('8403','9999');--NO SIRVE
Insert into OFRECEN (ID_OFERTA,ID_SERVICIO) values('9999','8403');--SNO SIRVE


----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM SERVICIOS  WHERE ID='8403';--VIOLA INTEGRIDAD
DELETE FROM OFERTAS  WHERE ID='8403';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM OFRECEN WHERE ID_OFERTA='8403' AND ID_SERVICIO='8403';--BORRA



--------SE HAN QUEDADO---------------

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('8601','PROFESOR','201418068');
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('8601','CC','OPERADOR 1','OPERADOR 1  ','8601');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('1801','OFERTA 1','340000','4','8601','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('1802','OFERTA 2','340000','4','8601','VIVIENDA');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('1803','OFERTA 3','340000','4','8601','VIVIENDA');

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('1801','PROFESOR','201418068');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('1801','CC','CLIENTE 1','CLIENTE 1','1801');

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('1802','PROFESOR','201418068');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('1802','CC','CLIENTE 2','CLIENTE 2','1802');

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('1803','PROFESOR','201418068');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('1803','CC','CLIENTE 3','CLIENTE 3','1803');


------------1) UNICIDAD

Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1801','1801');--SI SIRVE
Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1801','1801');---NO SIRVE
Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1801','1802');--SI SIRVE
Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1802','1801');--SI SIRVE



------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION

Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1803','1803');--SI SIRVE


--NO SIRVE LA INSERCION
Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('1803','9999');--NO SIRVE
Insert into OFERTAS_PREFERIDOS (ID_OFERTA,ID_CLIENTE) values('9999','1803');--NO SIRVE


----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM OFERTAS  WHERE ID='1803';--VIOLA INTEGRIDAD
DELETE FROM CLIENTES  WHERE ID='1803';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM OFERTAS_PREFERIDOS WHERE ID_OFERTA='1803' AND ID_CLIENTE='1803';--BORRA


-----SERVICIOS ADICIONALES---------------

---DATA
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('5001','SERVICIO 1','SERVICIO 1','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('5002','SERVICIO 2','SERVICIO 3','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('5003','SERVICIO 3','SERVICIO 3','10000');---
Insert into SERVICIOS (ID,NOMBRE,DESCRIPCION,COSTO_ADICIONAL) VALUES ('5004','SERVICIO 4','SERVICIO 3','10000');---


Insert into RELACIONES(ID,TIPO,CARNET) VALUES('2222','PROFESOR','201418068');
Insert into OPERADORES(ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION) VALUES('2222','CC','OPERADOR 1','OPERADOR 1  ','2222');
Insert into OFERTAS(ID,UBICACION,COSTO_BASICO,CAPACIDAD,ID_OPERADOR,TIPO) values('2222','OFERTA 1','340000','4','2222','VIVIENDA');

Insert into RELACIONES(ID,TIPO,CARNET) VALUES('2220','PROFESOR','201418068');
Insert into CLIENTES (ID,TIPO_ID,NOMBRE,CONTACTO,ID_RELACION)  VALUES('2220','CC','CLIENTE 3','CLIENTE 3','2220');


Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('5001','3','20/10/18','24/11/18','3','4509000','13/10/18','2222','2220');
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('5002','3','20/10/18','24/11/18','3','4509000','13/10/18','2222','2220');
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('5003','3','20/10/18','24/11/18','3','4509000','13/10/18','2222','2220');
Insert into RESERVAS(ID,NUM_DIAS,FECHA_INICIO,FECHA_FIN,NUM_PERSONAS,PRECIO_TOTAL,TIEMPO_OPORTUNO,ID_OFERTA,ID_CLIENTE)
values('5004','3','20/10/18','24/11/18','3','4509000','13/10/18','2222','2220');

----
---
------------1) UNICIDAD
SELECT * FROM RESERVAS;

Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5001','5001');--SI SIRVE
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5001','5001');---NO SIRVE
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5001','5002');--SI SIRVE
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5002','5001');--SI SIRVE



------------------2)INTEGRIDAD FK
--SIRVE LA INSERCION
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5003','5003');--SI SIRVE


--NO SIRVE LA INSERCION
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('5003','9999');--NO SIRVE
Insert into SERVICIOS_ADICIONALES (ID_RESERVA,ID_SERVICIO) values('9999','5003');--NO SIRVE


----BORRADO  LLAVES MAESTRAS:NO LAS PUEDE BORRA

DELETE FROM RESERVAS  WHERE ID='5003';--VIOLA INTEGRIDAD
DELETE FROM SERVICIOS  WHERE ID='5003';--VIOLA INTEGRIDAD


---BORRADO DE TUPLAS DEPENDIENTES:EN PRINCIPIO SI ES POSIBLE BOORRAR
DELETE FROM SERVICIOS_ADICIONALES WHERE ID_RESERVA='5003' AND ID_SERVICIO='5003';--BORRA








