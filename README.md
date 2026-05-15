# Documentation API - Gestion de Réservation Coopérative (PReserve)

**Base URL** : `http://localhost:8080`

**Format** : JSON

**Content-Type** : `application/json`

---

## Codes de réponse

| Code | Description |
|------|-------------|
| 200  | Succès |
| 400  | Erreur métier (place déjà occupée, données invalides) |
| 404  | Ressource introuvable |
| 500  | Erreur serveur |

---

## Voiture

### Créer une voiture

**POST** `/api/voitures`

Les places sont créées automatiquement selon le nombre de places défini.

Corps de la requête :
```json
{
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 50000
}
```

Valeurs possibles pour `type` : `SIMPLE`, `PREMIUM`, `VIP`

Réponse :
```json
{
    "idvoit": 1,
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 50000
}
```

---

### Lister toutes les voitures

**GET** `/api/voitures`

Aucun corps requis.

---

### Trouver une voiture par ID

**GET** `/api/voitures/{idvoit}`

Aucun corps requis.

---

### Modifier une voiture

**PUT** `/api/voitures/{idvoit}`

Corps de la requête :
```json
{
    "design": "Volkswagen Crafter",
    "type": "PREMIUM",
    "nbrplace": 12,
    "frais": 55000
}
```

---

### Nombre de places libres

**GET** `/api/voitures/{idvoit}/places-libres`

Aucun corps requis.

Réponse : nombre entier représentant les places disponibles.

---

## Client

### Créer un client

**POST** `/api/clients`

Corps de la requête :
```json
{
    "nom": "Andry",
    "numtel": "0335111821"
}
```

Réponse :
```json
{
    "idcli": 1,
    "nom": "Andry",
    "numtel": "0335111821"
}
```

---

### Lister tous les clients

**GET** `/api/clients`

Aucun corps requis.

---

### Trouver un client par ID

**GET** `/api/clients/{idcli}`

Aucun corps requis.

---

### Modifier un client

**PUT** `/api/clients/{idcli}`

Corps de la requête :
```json
{
    "nom": "Tojo",
    "numtel": "0335111821"
}
```

---

### Rechercher un client

**GET** `/api/clients/recherche?q={mot}`

Recherche par nom ou numéro de téléphone avec LIKE.

Exemple : `/api/clients/recherche?q=And`

Aucun corps requis.

---

## Place

### Lister toutes les places

**GET** `/api/places`

Aucun corps requis.

---

### Lister les places d'une voiture

**GET** `/api/places/{idvoit}`

Aucun corps requis.

---

### Lister toutes les places libres

**GET** `/api/places/libres`

Aucun corps requis.

---

### Lister toutes les places occupées

**GET** `/api/places/occupees`

Aucun corps requis.

---

### Occuper une place

**PUT** `/api/places/{idvoit}/{numeroPlace}/occuper`

Aucun corps requis.

Erreurs possibles :
- `400` : Place déjà occupée
- `404` : Place introuvable ou voiture introuvable

---

### Libérer une place

**PUT** `/api/places/{idvoit}/{numeroPlace}/liberer`

Aucun corps requis.

Erreurs possibles :
- `400` : Place déjà libre
- `404` : Place introuvable ou voiture introuvable

---

## Reservation

### Créer une réservation

**POST** `/api/reservations`

La date de réservation est générée automatiquement. La place est marquée comme occupée automatiquement.

Corps de la requête :
```json
{
    "idvoit": 1,
    "idcli": 1,
    "place": 1,
    "dateVoyage": "2026-05-25",
    "payment": "AVEC_AVANCE",
    "montantAvance": 5000
}
```

Valeurs possibles pour `payment` : `SANS_AVANCE`, `AVEC_AVANCE`, `TOUT_PAYE`

Réponse :
```json
{
    "idreserv": 1,
    "idvoit": 1,
    "idcli": 1,
    "place": 1,
    "dateReserv": "2026-05-15T10:00:00",
    "dateVoyage": "2026-05-25",
    "payment": "AVEC_AVANCE",
    "montantAvance": 5000
}
```

Erreurs possibles :
- `400` : Place déjà occupée
- `404` : Voiture introuvable, client introuvable, place introuvable

---

### Lister toutes les réservations

**GET** `/api/reservations`

Aucun corps requis.

---

### Trouver une réservation par ID

**GET** `/api/reservations/{idreserv}`

Aucun corps requis.

---

### Modifier une réservation

**PUT** `/api/reservations/{idreserv}`

Corps de la requête :
```json
{
    "place": 3,
    "dateVoyage": "2026-05-25",
    "payment": "SANS_AVANCE",
    "montantAvance": 0
}
```

La date de réservation originale est conservée automatiquement.

---

### Réservations par voiture

**GET** `/api/reservations/voiture/{idvoit}`

Aucun corps requis.

---

### Réservations par type de paiement

**GET** `/api/reservations/payment/{payment}`

Valeurs possibles : `SANS_AVANCE`, `AVEC_AVANCE`, `TOUT_PAYE`

Exemple : `/api/reservations/payment/AVEC_AVANCE`

Aucun corps requis.

---

### Réservations par voiture et type de paiement

**GET** `/api/reservations/voiture/{idvoit}/payment/{payment}`

Exemple : `/api/reservations/voiture/1/payment/TOUT_PAYE`

Aucun corps requis.

---

### Nombre de réservations par voiture et type de paiement

**GET** `/api/reservations/voiture/{idvoit}/payment/{payment}/count`

Exemple : `/api/reservations/voiture/1/payment/SANS_AVANCE/count`

Aucun corps requis.

Réponse : nombre entier.

---

### Recette totale

**GET** `/api/reservations/recette`

Aucun corps requis.

Réponse : montant total.

---

## Variables de collection Postman

| Variable | Description | Exemple |
|----------|-------------|---------|
| `spring_url` | URL de base du serveur | `http://localhost:8080` |
| `idvoit` | ID de la voiture (stocke automatiquement apres POST) | `1` |
| `idcli` | ID du client (stocke automatiquement apres POST) | `1` |
| `idreserv` | ID de la reservation (stocke automatiquement apres POST) | `1` |
| `numeroPlace` | Numero de la place | `1` |
