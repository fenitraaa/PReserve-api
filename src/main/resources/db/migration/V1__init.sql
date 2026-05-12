CREATE TABLE IF NOT EXISTS voiture (
    idvoit      BIGSERIAL PRIMARY KEY,
    design      VARCHAR(100) NOT NULL,
    type        VARCHAR(20)  NOT NULL CHECK (type IN ('SIMPLE', 'PREMIUM', 'VIP')),
    nbrplace    INT          NOT NULL,
    frais       INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    idcli       SERIAL PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    numtel      VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS place (
    idvoit      BIGINT       NOT NULL REFERENCES voiture(idvoit),
    place       INT          NOT NULL,
    occupation  BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (idvoit, place)
);

CREATE TABLE IF NOT EXISTS reserver (
    idreserv        BIGSERIAL PRIMARY KEY,
    idvoit          BIGINT       NOT NULL REFERENCES voiture(idvoit),
    idcli           INT          NOT NULL REFERENCES client(idcli),
    place           INT          NOT NULL,
    date_reserv     TIMESTAMP    NOT NULL,
    date_voyage     DATE         NOT NULL,
    payment         VARCHAR(20)  NOT NULL CHECK (payment IN ('SANS_AVANCE', 'AVEC_AVANCE', 'TOUT_PAYE')),
    montant_avance  INT          NOT NULL DEFAULT 0
);