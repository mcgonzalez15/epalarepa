

CREATE TABLE  CLIENTES
(
ID NUMBER(15,0) NOT NULL UNIQUE,
NOMBRE VARCHAR(20) NOT NULL,
CORREO VARCHAR2(50)NOT NULL,
ID_VINCULO VARCHAR(20) NOT NULL,
CONSTRAINT CHECK_ID_VINCULO
CHECK (VINCULO IN ('ESTUDIANTE','HOSTAL' ,'PROFESOR', 'HOTEL', 'EMPLEADO',,'FENICIA','PROFESOR INVITADO','REGISTRADO','EGRESADO','PADRE ESTUDIANTE','ADMININISTRADOR VIVIENDA UNIVERSITARIA' ))
);



CREATE TABLE  OPERADORES
(
ID NUMBER(15,0) NOT NULL UNIQUE,
NOMBRE VARCHAR(20) NOT NULL,
CORREO VARCHAR2(50)NOT NULL,
ID_VINCULO VARCHAR(20) NOT NULL,
CONSTRAINT CHECK_ID_VINCULO
CHECK (VINCULO IN ('ESTUDIANTE','HOSTAL' ,'PROFESOR', 'HOTEL', 'EMPLEADO',,'FENICIA','PROFESOR INVITADO','REGISTRADO','EGRESADO','PADRE ESTUDIANTE','ADMININISTRADOR VIVIENDA UNIVERSITARIA' ))
);



CREATE TABLE  SERVICIOS
(
ID NUMBER(4,0) NOT NULL UNIQUE,
NOMBRE   VARCHAR2(20) NOT NULL,
DESCRIPCION   VARCHAR2(60) NOT NULL,
COSTO   NUMBER(10,0),
 CONSTRAINT CHECK_COSTO_SERVICIO
CHECK (COSTO>=0));




CREATE  TABLE OFERTAS(
ID NUMBER(5,0) NOT NULL UNIQUE,
UBICACION VARCHAR2(60) NOT NULL,
PRECIO NUMBER(10,2) NOT NULL,
CAPACIDAD NUMBER(2,0) NOT NULL,
DISPONIBLE  VARCHAR2(1) DEFAULT 'T' NOT NULL ,
FECHA_RETIRO DATE  DEFAULT NULL,
TIPO VARCHAR2(40) NOT NULL,
ID_OPERADOR NUMBER(15,0) NOT NULL,
CONSTRAINT CHECK_TIPO_OFERTAS
CHECK (TIPO IN ('HOSTAL','HOTEL','VIVIENDA UNIVERSITARIA','HABITACION','APARTAMENTO'))
)
CONSTRAINT CHECK_PRECIO_OFERTA
CHECK (PRECIO>=0));
------------------------



CREATE  TABLE RESERVAS(
ID NUMBER(5,0) NOT NULL UNIQUE,
FECHA_INICIO DATE  NOT NULL,
FECHA_FIN DATE NOT NULL,
CANCELADA VARCHAR2(1) DEFAULT'F',
NUM_PERSONAS NUMBER(2,0) NOT NULL ,
FECHA_CANCELACION DATE DEFAULT NULL,
CANCELACION_OPORTUNA DATE NOT NULL,
PRECIO_TOTAL NUMBER(10,2) NOT NULL,
TERMINADA VARCHAR2(1) DEFAULT'F' NOT NULL,
ID_OFERTA NUMBER NOT NULL,
ID_CLIENTE NUMBER NOT NULL,
COLECTIVA VARCHAR2(1) DEFAULT'F',
ID_COLECTIVA NUMBER(8,0) DEFAULT NULL,
CONSTRAINT CHECK_NUM_PERSONAS_VALUE
CHECK (NUM_PERSONAS >0),
 CONSTRAINT CHECK_NUM_DIAS_VALUE
CHECK (NUM_DIAS>0),
 CONSTRAINT CHECK_COSTO_DEFINITIVO_VALUE
CHECK (COSTO_DEFINITIVO>0),
CONSTRAINT  CHECK_CANCELACION
CHECK ((CANCELADA='T' AND FECHA_CANCELACION IS NOT  NULL) OR (CANCELADA='F' AND FECHA_CANCELACION IS   NULL))
);


CREATE  TABLE CONTRATOS(
ID NUMBER(6,0) NOT NULL UNIQUE,
NUM_DIAS NUMBER(3,0) NOT NULL,
FECHA DATE  NOT NULL,
PRECIO NUMBER(10,2) NOT NULL,
VALORSEGURO NUMBER(5,0) NOT NULL
ID_CLIENTE NUMBER NOT NULL,
ID_OFERTA NUMBER(5,0) NOT NULL,
CONSTRAINT CHECK_NUM_DIAS_CONT
CHECK (NUM_DIAS>0),
CONSTRAINT CHECK_PRECIO_CONT
CHECK (PRECIO>0)
CONSTRAINT CHECK_VALOR_SEGURO
CHECK (VALORSEGURO>0)
);



CREATE TABLE APARTAMENTO
(
ID NUMBER(5,0) NOT NULL UNIQUE,
AMOBLADO VARCHAR(1) NOT NULL,
NUM_HABITACIONES NUMBER(2,0),
INCLUYE_ADMI  VARCHAR2(1) NOT NULL,
SERVICIOS_PUBLICOS  VARCHAR2(1)  NOT NULL,
TIPO_ALQUILER VARCHAR(20) NOT NULL,
CONSTRAINT CHECK_NUM_HABITACIONES_APTO
CHECK (NUM_HABITACIONES >0),
CONSTRAINT CHECK_TIPO_ALQUILER_APTO
CHECK (TIPO_ALQUILER IN 'ALQUILER DIAS', 'ALQUILER MES'),
);



CREATE TABLE HOTEL
(
ID NUMBER(6,0) NOT NULL UNIQUE,
TIPO_HABITACION VARCHAR(20),
CONSTRAINT CHECK_TIPO_HABITACION
CHECK(TIPO_HABITACION  IN('SUITE','SEMISUITE','ESTANDAR') )
);


CREATE TABLE HOSTAL
(
ID NUMBER(5,0) NOT NULL UNIQUE,
HORARIO_APERTURA DATE ,
HORARIO_CIERRE DATE, 
INDIVIDUAL VARCHAR2(1)
);

CREATE TABLE VIVIENDA_UNIVERSITARIA
(
ID NUMBER(5,0) NOT NULL UNIQUE,
NUM_NOCHES NUMBER(3,0) NOT NULL ,
INDIVIDUAL VARCHAR2(1)
CONSTRAINT CHECK_NUM_NOCHES_VIVI
CHECK(NUM_NOCHES IN(1,180,360) )
);


CREATE TABLE HABITACION
(
ID NUMBER(5,0) NOT NULL UNIQUE,
INDIVIDUAL  VARCHAR2(1) NOT NULL
COSTO_SERVICIOS(5,0) NUMBER(6,0) 
CONSTRAINT CHECK_COSTO_SERVICIOS_HABI
CHECK(COSTO_SERVICIOS >=0)
);

------------------------TABLAS QUE HACEN REFERENCIA A RELACIONES MUCHOS AMUCHOS------------------------


--------- OFERTA/CLIENTE---------
CREATE TABLE ALOJAMIENTOS_PREFERIDOS
(
ID_OFERTA NUMBER(5,0)   NOT NULL ,
ID_CLIENTE NUMBER(15,0)   NOT NULL ,
CONSTRAINT ALOJAMIENTOS_PREFERIDOS_PK PRIMARY KEY(ID_OFERTA,ID_CLIENTE)
);


------- SERVICIO/ CLIENTE-------------
CREATE TABLE SERVICIOS_PREFERIDOS
(
ID_SERVICIO NUMBER(4,0)   NOT NULL ,
ID_CLIENTE NUMBER(15,0)   NOT NULL ,
CONSTRAINT SERVICIOS_PREFERIDOS_PK PRIMARY KEY(ID_SERVICIO,ID_CLIENTE)
);
------CREACION DE FOREIGN KEYS
ALTER TABLE SERVICIOS_PREFERIDOS
ADD CONSTRAINT FK_F_SERVICO
FOREIGN KEY(ID_SERVICIO) REFERENCES SERVICIOS(ID);

ALTER TABLE SERVICIOS_PREFERIDOS
ADD CONSTRAINT FK_F_CLIENTES
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES(ID);


----------- OFERTA/SERVICIO-----------

CREATE TABLE OFRECEN
(
ID_OFERTA NUMBER(5,0)   NOT NULL ,
ID_SERVICIO NUMBER(4,0)   NOT NULL ,
CONSTRAINT SERVICIOS_OFRECEN_PK PRIMARY KEY(ID_OFERTA,ID_SERVICIO)
);

ALTER TABLE OFRECEN
ADD CONSTRAINT FK_F_OFERTA
FOREIGN KEY(ID_OFERTA) REFERENCES OFERTAS(ID);

ALTER TABLE OFRECEN
ADD CONSTRAINT FK_F_SERVICIO
FOREIGN KEY(ID_SERVICIO) REFERENCES SERVICIOS(ID);






-------------------------------CREACION DE FOREIGN KEYS PARA TABLAS PRINCIPALES--------------------------

ALTER TABLE RESERVAS
ADD CONSTRAINT FK_F_OFERTA_1
FOREIGN KEY(ID_OFERTA) REFERENCES OFERTAS(ID);


ALTER TABLE RESERVAS
ADD CONSTRAINT FK_F_CLIENTE
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES(ID);


ALTER TABLE CONTRATOS
ADD CONSTRAINT FK_F_OFERTA_C
FOREIGN KEY(ID_OFERTA) REFERENCES OFERTAS(ID);


ALTER TABLE CONTRATOS
ADD CONSTRAINT FK_F_CLIENTE_C
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES(ID);


ALTER TABLE OFERTAS 
ADD CONSTRAINT FK_F_OPERADOR_A
FOREIGN KEY(ID_OPERADOR) REFERENCES OPERADORES(ID);

ALTER TABLE APARTAMENTO
ADD CONSTRAINT FK_F_OFERTAS_APTO
FOREIGN KEY(ID) REFERENCES OFERTAS (ID);

ALTER TABLE HOTEL
ADD CONSTRAINT FK_F_OFERTA_HOTEL
FOREIGN KEY(ID) REFERENCES OFERTAS (ID);

ALTER TABLE HOSTAL
ADD CONSTRAINT FK_F_OFERTAS_HOSTA
FOREIGN KEY(ID) REFERENCES OFERTAS (ID);

ALTER TABLE VIVIENDA_UNIVERSITARIA
ADD CONSTRAINT FK_F_OFERTAS_VIVI
FOREIGN KEY(ID) REFERENCES OFERTAS (ID);


ALTER TABLE HABITACIONES
ADD CONSTRAINT FK_F_OFERTAS_HABI
FOREIGN KEY(ID) REFERENCES OFERTAS (ID);

ALTER TABLE ALOJAMIENTOS_PREFERIDOS
ADD CONSTRAINT FK_F_CLIENTES_S
FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTES (ID);


ALTER TABLE ALOJAMIENTOS_PREFERIDOS
ADD CONSTRAINT FK_F_CLIENTES_S_H
FOREIGN KEY(ID_OFERTA) REFERENCES OFERTAS (ID);

