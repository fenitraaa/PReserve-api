# REST API for managing seat reservations in a cooperative transport system (PReserve)

## Replace all environments variables within .env and deleting .exemple
```
DB_URL= your DB URL
DB_USERNAME= username
DB_PASSWORD= password
```
## Use Docker to launch the project

```sh
docker compose up -d
```
> It downloads automatically the image latest from GHRC
## Use Newman to test automatically all APIs 
Install Newman if you don't have it
```
sudo npm install -g newman
sudo npm install -g newman newman-reporter-htmlextra
```
Run this command
```
newman run postman/PReserve-API.postman_collection.json \
        --globals postman/workspace.postman_globals.json \
        --reporters cli,html \
        --reporter-html-export rapport.html
```
## All APIs ENDPOINTS:
**Base URL** : `http://localhost:8080`

**Format** : JSON

**Content-Type** : `application/json`

---

## Response Codes

| Code | Description |
|------|-------------|
| 200  | Success |
| 400  | Business error (seat already taken, invalid data) |
| 404  | Resource not found |
| 500  | Server error |

---

## Vehicle

### Create a vehicle

**POST** `/api/voitures`

Seats are automatically created based on the defined number of seats.

Request body:
```json
{
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 50000
}
```

Possible values for `type` : `SIMPLE`, `PREMIUM`, `VIP`

Response:
```json
{
    "idvoit": "V001",
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 50000
}
```

> After a successful POST, the `idvoit` is automatically stored in the collection variable `{{idvoit}}`.

---

### List all vehicles

**GET** `/api/voitures`

No request body required.

---

### Find a vehicle by ID

**GET** `/api/voitures/{{idvoit}}`

No request body required.

---

### Update a vehicle

**PUT** `/api/voitures/{{idvoit}}`

Request body:
```json
{
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 55000
}
```

---

### Number of available seats

**GET** `/api/voitures/{{idvoit}}/places-libres`

No request body required.

Response: integer representing the number of available seats.

---

## Client

### Create a client

**POST** `/api/clients`

Request body:
```json
{
    "nom": "Andry",
    "numtel": "0335111821"
}
```

Response:
```json
{
    "idcli": "C001",
    "nom": "Andry",
    "numtel": "0335111821"
}
```

> After a successful POST, the `idcli` is automatically stored in the collection variable `{{idcli}}`.

---

### List all clients

**GET** `/api/clients`

No request body required.

---

### Find a client by ID

**GET** `/api/clients/{{idcli}}`

No request body required.

---

### Update a client

**PUT** `/api/clients/{{idcli}}`

Request body:
```json
{
    "nom": "Tojo",
    "numtel": "0335111821"
}
```

---

### Search for a client

**GET** `/api/clients/recherche?q={keyword}`

Search by name or phone number using LIKE.

Example: `/api/clients/recherche?q=Fen`

No request body required.

---

## Seat

### List all seats

**GET** `/api/places`

No request body required.

---

### List seats of a vehicle

**GET** `/api/places/{{idvoit}}`

No request body required.

---

### List all available seats

**GET** `/api/places/libres`

No request body required.

---

### List all occupied seats

**GET** `/api/places/occupees`

No request body required.

---

### Occupy a seat

**PUT** `/api/places/{{idvoit}}/{{numeroPlace}}/occuper`

No request body required.

Possible errors:
- `400` : Place déjà occupée
- `404` : Place introuvable ou voiture introuvable

**PUT** `/api/places/{{idvoit}}/{{numeroPlace}}/liberer`

No request body required.

Possible errors:
- `400` : Place déjà libre
- `404` : Place introuvable ou voiture introuvable

---

## Reservation

### Create a reservation — AVEC_AVANCE

**POST** `/api/reservations`

The reservation date is automatically generated. The seat is automatically marked as occupied.

Request body:
```json
{
    "idvoit": "{{idvoit}}",
    "idcli": "{{idcli}}",
    "place": 1,
    "dateVoyage": "2026-05-25 09:00:00",
    "payment": "AVEC_AVANCE",
    "montantAvance": 5000
}
```

---

### Create a reservation — SANS_AVANCE

**POST** `/api/reservations`

Request body:
```json
{
    "idvoit": "{{idvoit}}",
    "idcli": "{{idcli}}",
    "place": 2,
    "dateVoyage": "2026-05-25 09:00:00",
    "payment": "SANS_AVANCE",
    "montantAvance": 0
}
```

---

### Create a reservation — TOUT_PAYE

**POST** `/api/reservations`

Request body:
```json
{
    "idvoit": "{{idvoit}}",
    "idcli": "{{idcli}}",
    "place": 3,
    "dateVoyage": "2026-05-25 09:00:00",
    "payment": "TOUT_PAYE",
    "montantAvance": 55000
}
```

Possible values for `payment` : `SANS_AVANCE`, `AVEC_AVANCE`, `TOUT_PAYE`

Response:
```json
{
    "idreserv": "R001",
    "idvoit": "V001",
    "idcli": "C001",
    "place": 1,
    "dateReserv": "2026-05-15 10:00:00",
    "dateVoyage": "2026-05-25 09:00:00",
    "payment": "AVEC_AVANCE",
    "montantAvance": 5000
}
```

Possible errors:
- `400` : Place déjà occupée
- `404` : Voiture introuvable, client introuvable, place introuvable

> After a successful POST, the `idreserv` is automatically stored in the collection variable `{{idreserv}}`.

---

### List all reservations

**GET** `/api/reservations`

No request body required.

---

### Find a reservation by ID

**GET** `/api/reservations/{{idreserv}}`

No request body required.

---

### Update a reservation

**PUT** `/api/reservations/{{idreserv}}`

Request body:
```json
{
    "place": 3,
    "dateVoyage": "2026-06-01",
    "payment": "TOUT_PAYE",
    "montantAvance": 55000
}
```

The original reservation date is automatically preserved.

---

### Reservations by vehicle

**GET** `/api/reservations/voiture/{{idvoit}}`

No request body required.

---

### Reservations by payment type — SANS_AVANCE

**GET** `/api/reservations/payment/SANS_AVANCE`

No request body required.

---

### Reservations by payment type — AVEC_AVANCE

**GET** `/api/reservations/payment/AVEC_AVANCE`

No request body required.

---

### Reservations by payment type — TOUT_PAYE

**GET** `/api/reservations/payment/TOUT_PAYE`

No request body required.

---

### Reservations by vehicle and payment type — SANS_AVANCE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/SANS_AVANCE`

No request body required.

---

### Reservations by vehicle and payment type — AVEC_AVANCE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/AVEC_AVANCE`

No request body required.

---

### Reservations by vehicle and payment type — TOUT_PAYE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/TOUT_PAYE`

No request body required.

---

### Count reservations by vehicle and payment type — SANS_AVANCE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/SANS_AVANCE/count`

No request body required.

Response: integer.

---

### Count reservations by vehicle and payment type — AVEC_AVANCE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/AVEC_AVANCE/count`

No request body required.

Response: integer.

---

### Count reservations by vehicle and payment type — TOUT_PAYE

**GET** `/api/reservations/voiture/{{idvoit}}/payment/TOUT_PAYE/count`

No request body required.

Response: integer.

---

### Total revenue

**GET** `/api/reservations/recette`

No request body required.

Response: total amount.

---

## PDF

### Generate receipt

**GET** `/api/pdf/recu/{{idreserv}}`

Headers:
```
Accept: application/pdf
```

No request body required.

Response: PDF file of the reservation receipt.

---

## Postman Collection Variables

| Variable | Description | Example |
|----------|-------------|---------------|
| `spring_url` | Base server URL | `http://localhost:8080` |
| `idvoit` | Vehicle ID (auto-stored after POST vehicle) | `V001` |
| `idcli` | Client ID (auto-stored after POST client) | `C001` |
| `idreserv` | Reservation ID (auto-stored after POST reservation) | `R001` |
| `numeroPlace` | Seat number | `3` |
