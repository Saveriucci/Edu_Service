

Edu Service

Realizzazione di un sistema informativo per una società che offre servizi di prenotazione esami. Il sistema e' stato progettato e realizzato usando SpringBoot e Postgres SQL.

L'obbiettivo del sistema è quello di consentire almeno l'esecuzione di 4 casi d'uso:

almeno due che abbiano come attore principale l'amministratore del sistema, in particolare di inserimento e modifica di dati. 
almeno due che abbiano come attore principale un utente generico.

Il sistema prevede un diverso funzionamento in base al tipo di utente, infatti si hanno:

    Utente non autenticato : non e' in grado di vedere nulla
    Utente generico, autenticato: funzionamento completo, è possibile visualizzare i dettagli di esami, corsi e diparimenti.
    Utente amministratore, autenticato: funzionamento completo , con possibilità di apportare modifiche al sistema ed ai suoi dati.

Ogni utente potrà accedere al sistema tramite credenziali del proprio account, previa registrazione.
