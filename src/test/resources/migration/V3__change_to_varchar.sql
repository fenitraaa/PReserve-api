ALTER TABLE reserver DROP CONSTRAINT IF EXISTS reserver_idcli_fkey;
ALTER TABLE reserver DROP CONSTRAINT IF EXISTS reserver_idvoit_fkey;
ALTER TABLE place DROP CONSTRAINT IF EXISTS place_idvoit_fkey;

ALTER TABLE voiture ALTER COLUMN idvoit VARCHAR(10);
ALTER TABLE client ALTER COLUMN idcli VARCHAR(10);
ALTER TABLE reserver ALTER COLUMN idreserv VARCHAR(10);
ALTER TABLE reserver ALTER COLUMN idvoit VARCHAR(10);
ALTER TABLE reserver ALTER COLUMN idcli VARCHAR(10);
ALTER TABLE place ALTER COLUMN idvoit VARCHAR(10);

ALTER TABLE reserver ADD CONSTRAINT reserver_idvoit_fkey
    FOREIGN KEY (idvoit) REFERENCES voiture(idvoit);
ALTER TABLE reserver ADD CONSTRAINT reserver_idcli_fkey
    FOREIGN KEY (idcli) REFERENCES client(idcli);
ALTER TABLE place ADD CONSTRAINT place_idvoit_fkey
    FOREIGN KEY (idvoit) REFERENCES voiture(idvoit);