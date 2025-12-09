#### prog3-blackjack

Made by Habibi <3

# Blackjack (21)
####  Felhasználói dokumentáció
A játék a közismert kártyajátékot, a blackjacket avagy huszonegyet veszi alapul és valósítja meg.
#### A program célja
A program célja, hogy egy letisztult felhasználói kezelőfelülettel és egyszerű játékmenettel imitálja a sokak által kedvelt kártya alapú szerencsejátékot játékpénzzel (JMF).
#### A program menete
A program elindulásakor megjelenik a profilválasztó párbeszédablak, mögötte a játék grafikus felülete, amit játékosprofil választása jobban szemügyre vehetünk.  Amennyiben a játékos a párbeszédablakot bezárja, egy alapértelmezett játékosprofil lesz kiválasztva számára: „Guest”, ha pedig az „Új profil létrehozása” opciót választja a legördülő listában, és utána a „mégse” gombra nyom, vagy bezárja a párbeszédablakot, egy „Játékos X” nevű profil lesz kiválasztva, ahol X a soron következő profil sorszáma lesz.
A grafikus felületen a következők láthatók: felül egy menüsáv, középen a játéktér, ami két részre van bontva: felül az osztó lapjai, amik közül csak az első látszik kezdetben, alul a játékos lapjai. Jobb oldalt található a sáv, amin láthatjuk a játékos egyenlegét, valamint megadhatja a játékos a tétjét, aminek pozitív egész számnak kell lennie, és nem lehet nagyobb, mint a játékos egyenlege. A kezdő egyenleg 2000 JMF.
Az ablak tetején lévő menüsávon az alábbi menüpontok, azokon belül különböző opciók találhatóak meg:
-	File
- 	Mentés
	A program által tárolt játékosprofilok adatait (név, győzelmek száma, vereségek száma, egyenleg) menti egy „Profiles.ser” nevű fájlba
o	Betöltés
	A „Profiles.ser” nevű fájl által tárolt játékosprofilok adatait (név, győzelmek száma, vereségek száma, egyenleg) tölti be és tárolja a program
•	Játék
o	Új játék
	Új játék (kör) indítása
o	Játékosprofil váltása
	Egy párbeszédablak megnyílása után a legördülő listából választható egy játékosprofil, vagy létrehozható egy új játékosprofil
•	Dicsőségfal
o	Egy új ablakban megjeleníti táblázat formájában a tárolt játékosprofilok adatait (név, győzelem, vereség, egyenleg). A táblázat attribútumonként rendezhető.
Alapvető játékszabályok:
•	Kártyaértékek: A számmal ellátott lapok a rajtuk levő számot (2-10) érik, a figurás lapok: bubi (J), dáma (Q), király (K), 10-et érnek, az ász (A) pedig 1-et vagy 11-et ér, attól függően, melyik a kedvezőbb a játékosnak anélkül, hogy 21 fölé menne.
•	Játékmenet: A játékos és az osztó is 2-2 kártyát kap. Az osztó egyik kártyája színével lefelé van (rejtett).
•	Játékos köre: A játékos dönthet, hogy új lapot kér (hit) vagy megáll (stand). Előbbit addig ismételheti, amíg el nem éri a 21-et, vagy meg nem áll. Ha a lapjainak értéke 21 fölé megy, azonnal veszít.
•	Osztó köre: Miután a játékos megállt, az osztó felfedi a rejtett lapját. Az osztó köteles lapot húzni, amíg a kártyáinak összértéke el nem éri a 17-et, vagy meg nem haladja azt. Az osztó az ász értékét csak abban az esetben tekintheti 1-nek, ha a lapjainak összértéke az ász 11-es értékével számolva meghaladná a 21-et.
•	A játék végeredménye: Ha az osztó lapjainak értéke meghaladja a 21-et, a játékos nyer, feltéve, hogy az ő lapjainak értéke nem nagyobb, mint 21. Egyébként az nyer, akinek a lapjainak összértéke közelebb van a 21-hez. Egyenlőség esetén a játék döntetlen (push).

Egy kör menete az alábbiakból áll:
1.	Tét rakása
2.	Kártyák kiosztása
3.	Játékos köre
      a.	Lapot kér (hit)
      b.	Megáll (stand)
4.	Osztó köre
5.	Kiértékelés, győztes eldöntése, egyenleg frissítése
      A játék akkor ér véget egy adott játékosprofil számára, ha az egyenleg nulla lesz.

