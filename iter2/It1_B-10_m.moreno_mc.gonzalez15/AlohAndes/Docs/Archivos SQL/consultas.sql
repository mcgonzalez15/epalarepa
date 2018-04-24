SELECT op.NOMBRE, SUM(cont.PRECIO)
FROM CONTRATOS cont, OPERADORES op, OFERTAS ofe
WHERE cont.FECHA LIKE '%2018' AND op.ID = ofe.ID_OPERADOR AND ofe.ID = cont.id_oferta
GROUP BY op.NOMBRE;

SELECT ofe.*, SUM(cont.ID)
FROM CONTRATOS cont, OFERTAS ofe
WHERE ofe.ID = cont.id_oferta
ORDER BY SUM(cont.ID)
ROWNUM = 20 ;

