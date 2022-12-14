# Travel Agency Application

## Az alkalmazás célja:

A vizsgaremekként létrehozott program lehetőséget ad egy utazási iroda komplett utazási csomagjainak összeállítására 
szállással és programokkal együtt, majd az elkészült csomaghoz utasok regisztrálására.

## Az alkalmazás entitásai:

### Destination:
Előre definiált úti célok a desztináció nevével és napi árával. Ennek meghatározásával kezdődik meg az utazási csomag
összeállítása.

### Travel:
Maga az utazási csomag, vagyis a fő entitás. Attribútumai az adott desztináció, az utazáshoz tartozó szállás, az utazás
kezdő-, illetve végdátuma, a hozzárendelt programok, a hozzárendelt utazók, a napok, amik a megadott dátumokból kerülnek
kiszámításra, a teljes ár az alapár, a szállás ára és a programok ára alapján kiszámolva az utazás napjainak megfelelően,
a teljes bevétel, ami az utazók összköltségéből adódik össze, és egy jelölő, hogy törlésre került-e az entitás. Ha már
utazó is lett rendelve hozzá, akkor nem módosítható, csak törölhető.

### Accommodation:
Az utazáshoz kapcsolódó szállás, amiből utazásonként csak egy lehet. Attribútumai a neve, a típusa, az ellátás típusa,
a hozzá tartozó utazás, az alap napi ára, illetve a törlést jelölő változó. Ha az utazáshoz már utazó is lett rendelve, 
akkor nem módosítható és nem törölhető külön, csak a teljes csomaggal együtt.

### Program:
Az utazáshoz tartozó program, amiből utazásonként több is lehet. Attribútumai a neve, a leírása, a vezetője, az ára,
a hozzá kapcsolódó utazás és a törlést jelölő változó. Ha az utazáshoz már utazó is lett rendelve, akkor nem módosítható
és nem törölhető külön, csak a teljes csomaggal együtt.

### Traveller:
Az utazó, aki regisztrálásra került egy adott utazási csomaghoz. Attribútumai a neve, az e-mail címe, a születési dátuma,
a születési dátumból számolt életkora, a kapcsolódó utazás, az összes költsége és a törlést jelölő változó. Utazóból 
többen is tartozhatnak egy úthoz. Csak olyan utazási csomaghoz lehet utazót rendelni, ami már el lett látva szállással 
és programokkal.

## Az alkalmazás végpontjai:

**/api/travels:**
Utazások mentése, listázása, keresése, módosítása és törlése.

**/api/accommodations:**
Szállások mentése, listázása, keresése, módosítása és törlése.

**/api/programs:**
Programok mentése, listázása, keresése, módosítása és törlése.

**/api/travellers:**
Utazók mentése, listázása, keresése, módosítása és törlése.

**Az alkalmazás telepítése a docker-cheat-sheet.md dokumentumban található lépések alapján lehetséges.**

**Az alkalmazás kapcsolati diagramja a travel-agency-diagram.jpg képfájt megnyitva tekinthető meg.**

## Swagger dokumentáció a konténerek létrehozása és indítása után:
**http://localhost:8088/swagger-ui.html**