--RFC1
SELECT ope.NOMBRE, SUM(cont.PRECIO)
FROM OPERADORES ope, CONTRATOS cont, OFERTAS ofe
WHERE cont.FECHA LIKE '%18' AND cont.ID_OFERTA = ofe.ID AND ope.ID = ofe.ID_OPERADOR
GROUP BY ope.NOMBRE;

--RFC2
SELECT ofe.ID, COUNT(cont.ID) AS NumVeces 
FROM CONTRATOS cont, OFERTAS ofe
WHERE cont.ID_OFERTA = ofe.ID  
GROUP BY ofe.ID
ORDER BY COUNT(cont.ID) DESC
WHERE ROWNUM<20;

--RFC3
SELECT ope.ID, ope.NOMBRE, SUM(res.NUM_PERSONAS)/SUM(ofe.CAPACIDAD) AS PORCENTAJEOCUPACION
FROM OPERADORES ope INNER JOIN OFERTAS ofe ON ope.ID=ofe.ID_OPERADOR INNER JOIN RESERVAS res ON res.ID_OFERTA = ofe.ID
WHERE res.CANCELADA='F' AND '23/03/18' BETWEEN res.FECHA_INICIO AND res.FECHA_FIN
GROUP BY ope.ID,ope.NOMBRE;

--RFC4
SELECT * FROM OFERTAS ofe
WHERE ofe.ID NOT IN 
(SELECT re.ID_OFERTA
FROM  RESERVAS re
WHERE( re.FECHA_INICIO  BETWEEN '27/08/18' AND '02/12/18')
OR  ( re.FECHA_FIN  BETWEEN '27/08/18' AND '02/12/18')
OR (  re.FECHA_INICIO <'27/08/18'  AND   re.FECHA_FIN> '02/12/18'))
AND ofe.ID IN
(SELECT serv.ID_OFERTA
FROM  OFRECEN serv
INNER JOIN  SERVICIOS servi
ON servi.ID=serv.ID_SERVICIO
WHERE servi.NOMBRE='mobile'AND servi.NOMBRE='policy');

--RFC5
SELECT ofe.TIPO AS TIPOALOJAMIENTO,SUM(re.PRECIO_TOTAL) AS COSTO,SUM(re.NUM_DIAS) AS NUMDIAS
FROM CLIENTES cli
INNER JOIN  RESERVAS re
ON re.ID_CLIENTE=cli.ID
INNER JOIN OFERTAS ofe
ON ofe.ID=re.ID_OFERTA
GROUP BY cli.ID_VINCULO,ofe.TIPO;

--RFC6
SELECT cli.ID,  cli.NOMBRE, ofe.TIPO AS TIPOALOJAMIENTO,SUM(re.PRECIO_TOTAL) AS COSTO,SUM(re.NUM_DIAS) AS NUMDIAS
FROM CLIENTES cli
INNER JOIN  RESERVAS re
ON re.ID_CLIENTE=cli.ID
INNER JOIN OFERTAS ofe
ON ofe.ID=re.ID_OFERTA
WHERE cli.NOMBRE='Garek Ivanonko'
GROUP BY cli.NOMBRE,cli.ID,ofe.TIPO;

--RFC7
SELECT COUNT(*) AS OCUPACION  
FROM RESERVAS res 
INNER JOIN OFERTAS ofe 
ON ofe.ID=res.ID_OFERTA 
WHERE "+xi+" 
BETWEEN res.FECHA_INICIO 
AND res.FECHA_FIN  
AND res.CANCELADA='F' 
AND  ofe.TIPO = %1$s"

SELECT SUM(COSTO_DEFINITIVO) AS TOTAL_COBRADO_POR_DIA 
FROM RESERVAS res 
INNER JOIN OFERTAS ofe 
ON res.ID_OFERTA=ofe.ID 
WHERE (res.TERMINADA='T' 
AND res.FECHA_FIN= +xi+  )
OR (res.CANCELADA='T' 
AND res.FECHA_CANCELACION= +xi +) 
AND  ofe.TIPO = $1$S

--RFC8
SELECT res.ID_OFERTA,res.ID_CLIENTE,cli.NOMBRE,cli.CORREO,COUNT(*) AS NUMERO_OCASIONES,SUM(TRUNC(res.FECHA_FIN)-TRUNC(res.FECHA_INICIO)) AS NUM_NOCHES
FROM RESERVAS res 
INNER JOIN CLIENTES cli 
ON cli.ID=res.ID_CLIENTE 
WHERE res.ID_OFERTA = %1$d 
AND res.CANCELADA='F'
GROUP BY res.ID_CLIENTE, res.ID_OFERTA,cli.NOMBRE,cli.CORREO 
HAVING COUNT(*)>=3 
OR SUM(TRUNC(res.FECHA_FIN)-TRUNC(res.FECHA_INICIO)) >=15

--RFC9
SELECT * 
FROM OFERTAS ofe 
WHERE ofe.ID
NOT IN 
( SELECT res.ID_OFERTA 
FROM RESERVAS res 
WHERE( res.FECHA_INICIO  
BETWEEN x1 AND x2 )



--------------------------------------
-----------------RFC10-----------------
--------------------------------------
RFC10 - CONSULTAR CONSUMO EN ALOHANDES

SELECT * FROM CLIENTES CLIEN WHERE CLIEN.ID IN (
    SELECT RESERV.ID_CLIENTE FROM RESERVAS RESERV WHERE
    
        (( RESERV.FECHA_INICIO  BETWEEN '10/10/18' AND '30/10/18')

        OR  ( RESERV.FECHA_FIN  BETWEEN '10/10/18' AND '30/10/18')

        OR (  RESERV.FECHA_INICIO <'10/10/18'  AND   RESERV.FECHA_FIN >  '30/10/18')

        ) AND RESERV.ID_ALOJAMIENTO = 2 ) 
    ORDER BY CLIEN.NOMBRE; 
    
    
--------------------------------------
-----------------RFC11-----------------
--------------------------------------

    SELECT * FROM CLIENTES CLIEN WHERE CLIEN.ID IS NOT IN (
    SELECT RESERV.ID_CLIENTE FROM RESERVAS RESERV WHERE
    
        (( RESERV.FECHA_INICIO  BETWEEN '10/10/18' AND '30/10/18')

        OR  ( RESERV.FECHA_FIN  BETWEEN '10/10/18' AND '30/10/18')

        OR (  RESERV.FECHA_INICIO <'10/10/18'  AND   RESERV.FECHA_FIN >  '30/10/18')

        ) AND RESERV.ID_ALOJAMIENTO = 2 ) 
    ORDER BY CLIEN.NOMBRE; 
--------------------------------------
-----------------RFC12-----------------
--------------------------------------

  WITH Q1 AS
(SELECT ALO.ID AS ALOID ,RES.NUM_PERSONAS/ALO.CAPACIDAD AS CAPACI,TO_CHAR(RES.FECHA_INICIO,'IW')AS SEMA
FROM ALOJAMIENTOS ALO
INNER JOIN RESERVAS RES
ON ALO.ID=RES.ID_ALOJAMIENTO
WHERE EXTRACT( YEAR FROM FECHA_INICIO) = 2018),

Q2 AS
(SELECT MAX(RESE.NUM_PERSONAS/ALOJ.CAPACIDAD) AS CAPACIMAX, TO_CHAR(RESE.FECHA_INICIO,'IW') AS SEMA, MIN(RESE.NUM_PERSONAS/ALOJ.CAPACIDAD) AS CAPACIMIN
FROM ALOJAMIENTOS ALOJ
INNER JOIN RESERVAS RESE
ON ALOJ.ID=RESE.ID_ALOJAMIENTO
GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(RESE.FECHA_INICIO,'IW')
HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018),

Q3 AS
(SELECT Q1.SEMA AS SEMANA, Q1.ALOID AS ALOJAMIENTOMAX, Q2.CAPACIMAX AS OCUPACIONMAX, Q2.CAPACIMIN AS OCUPACIONMIN
FROM Q1 
INNER JOIN Q2
ON  Q1.SEMA=Q2.SEMA AND Q1.CAPACI=Q2.CAPACIMAX),

Q4 AS(
SELECT Q3.SEMANA, Q3.ALOJAMIENTOMAX, Q3.OCUPACIONMAX, Q1.ALOID AS ALOJAMIENTOMIN, Q3.OCUPACIONMIN
FROM Q1 INNER JOIN Q3
ON  Q1.SEMA=Q3.SEMANA AND Q1.CAPACI=Q3.OCUPACIONMIN
order by Q1.SEMA ASC),

R1 AS
(SELECT SEM, MAX(SOLICI) AS MAXIM, MIN (SOLICI)AS MINIM
FROM
(SELECT tabla1.SEMANA AS SEM, ALOJAMIENTOS.ID_OPERADOR, SUM(tabla1.CUENTA) AS SOLICI
FROM(SELECT TO_CHAR(FECHA_INICIO,'IW')AS SEMANA,ID_ALOJAMIENTO AS ALOJ,  COUNT (ID) AS CUENTA
FROM RESERVAS
GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(FECHA_INICIO,'IW'), ID_ALOJAMIENTO
HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018 )tabla1 inner join ALOJAMIENTOS
ON tabla1.ALOJ = ALOJAMIENTOS.ID
GROUP BY tabla1.SEMANA,ALOJAMIENTOS.ID_OPERADOR)
GROUP BY SEM 
ORDER BY SEM),
R2 AS 
(SELECT tabla1.SEMANA2 AS SEM2, ALOJAMIENTOS.ID_OPERADOR AS OPER, SUM(tabla1.CUENTA2) AS SOLICI2
FROM(SELECT TO_CHAR(FECHA_INICIO,'IW')AS SEMANA2,ID_ALOJAMIENTO AS ALOJ2,  COUNT (ID) AS CUENTA2
FROM RESERVAS
GROUP BY EXTRACT( YEAR FROM FECHA_INICIO), TO_CHAR(FECHA_INICIO,'IW'), ID_ALOJAMIENTO
HAVING EXTRACT( YEAR FROM FECHA_INICIO) = 2018 )tabla1 inner join ALOJAMIENTOS
ON tabla1.ALOJ2 = ALOJAMIENTOS.ID
GROUP BY tabla1.SEMANA2,ALOJAMIENTOS.ID_OPERADOR),
R3 AS (
SELECT R1.SEM , R1.MAXIM, R1.MINIM, R2.OPER
FROM R1 INNER JOIN R2
ON R1.SEM = R2.SEM2 AND  R1.MAXIM = R2.SOLICI2
ORDER BY R1.SEM),
R4 AS (
SELECT  R3.SEM AS SEMANA1, R3.OPER AS OPERADORMAX, R3.MAXIM AS MAXIM, R2.OPER AS OPERADORMIN, R3.MINIM AS MINIM
FROM R3 INNER JOIN R2
ON R3.SEM = R2.SEM2 AND  R3.MINIM = R2.SOLICI2
ORDER BY R3.SEM)
SELECT Q4.SEMANA ,Q4.ALOJAMIENTOMAX, Q4.OCUPACIONMAX, Q4.ALOJAMIENTOMIN, Q4.OCUPACIONMIN, R4.OPERADORMAX , R4.MAXIM , R4.OPERADORMIN, R4.MINIM
FROM Q4 INNER JOIN R4
ON Q4.SEMANA = R4.SEMANA1
ORDER BY Q4.SEMANA
--------------------------------------
-----------------RFC13-----------------
--------------------------------------

WITH Q1 AS(
SELECT CLI.ID AS IDQ1, MIN(RESV.COSTO_DEFINITIVO/(TRUNC (RESV.FECHA_FIN) - TRUNC (RESV.FECHA_INICIO))) AS RAZONQ1
FROM CLIENTES CLI
INNER JOIN RESERVAS RESV
ON CLI.ID=RESV.ID_CLIENTE
WHERE CLI.ID NOT IN
(SELECT ID_CLIENTE 
FROM RESERVAS RESI
WHERE (RESI.COSTO_DEFINITIVO/(TRUNC (RESI.FECHA_FIN) - TRUNC (RESI.FECHA_INICIO)))<125)
GROUP BY CLI.ID ),
Q2 AS (
SELECT CLI.ID AS IDQ2 , COUNT (RES1.ID) AS RAZONQ2
FROM ((CLIENTES CLI
INNER JOIN RESERVAS RES1
ON  RES1.ID_CLIENTE=CLI.ID)
INNER JOIN HABITACIONES_HOTEL HOT 
ON HOT.ID=RES1.ID_ALOJAMIENTO)
WHERE CLI.ID NOT IN
(SELECT RESER1.ID_CLIENTE
FROM RESERVAS  RESER1
INNER JOIN HABITACIONES_HOTEL HO
ON HO.ID=RESER1.ID_ALOJAMIENTO
WHERE HO.TIPO_HABITACION != 'SUITE')
AND CLI.ID NOT IN(
SELECT RESER2.ID_CLIENTE
FROM RESERVAS  RESER2
INNER JOIN  ALOJAMIENTOS ALOJ
ON ALOJ.ID=RESER2.ID_ALOJAMIENTO
WHERE ALOJ.TIPO != 'HAB HOTEL'
)
AND  CLI.ID IN(
SELECT RES.ID_CLIENTE
FROM RESERVAS RES
)
GROUP BY CLI.ID),
Q3 AS (
SELECT  RES.ID_CLIENTE AS IDQ3,COUNT(DISTINCT(TO_CHAR(RES.FECHA_INICIO,'MM'))*(TO_CHAR(RES.FECHA_INICIO,'YY'))) AS RAZONQ3
FROM RESERVAS RES
GROUP BY RES.ID_CLIENTE
HAVING COUNT(DISTINCT(TO_CHAR(RES.FECHA_INICIO,'MM'))*(TO_CHAR(RES.FECHA_INICIO,'YY'))) >= ( SELECT COUNT(*) FROM (SELECT  DISTINCT TO_CHAR(FECHA_INICIO,'YY'),TO_CHAR(FECHA_INICIO,'MM') FROM RESERVAS))),
Q4 AS (
SELECT Q1.IDQ1, Q1.RAZONQ1, Q2.IDQ2, Q2.RAZONQ2
FROM Q1 FULL OUTER JOIN Q2
ON Q1.IDQ1 = Q2.IDQ2)
SELECT Q4.IDQ1, Q4.RAZONQ1, Q4.IDQ2, Q4.RAZONQ2, Q3.IDQ3, Q3.RAZONQ3
FROM Q4 FULL OUTER JOIN Q3