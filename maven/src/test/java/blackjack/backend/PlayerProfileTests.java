package blackjack.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerProfileTests {

    @Test
    @DisplayName("8. win/lose számlálók teszt")
    public void testWinLossCounters() {
        PlayerProfile profile = new PlayerProfile("csüli");

        profile.setWinCount();
        assertEquals(1, profile.getWinCount());
        assertEquals(0, profile.getLoseCount());

        profile.setLoseCount();
        profile.setLoseCount();

        assertEquals(1, profile.getWinCount());
        assertEquals(2, profile.getLoseCount());
    }

    @Test
    @DisplayName("9. netWorth teszt")
    public void testNetWorth() {
        PlayerProfile profile = new PlayerProfile("kuli");

        // default állapot ellenőrzése
        assertEquals(2000, profile.getNetWorth());

        // nyeremény hozzáadása
        profile.setNetWorth(420);
        assertEquals(2420, profile.getNetWorth());

        // veszteség levonása
        profile.setNetWorth(-20);
        assertEquals(2400, profile.getNetWorth());

        // 0-ra futás ellenőrzése
        profile.setNetWorth(-2400);
        assertEquals(0, profile.getNetWorth());
    }

    @Test
    @DisplayName("10. szerializálás, fájlkezelés teszt")
    public void testSerialization() throws IOException, ClassNotFoundException {
        PlayerProfile profile = new PlayerProfile("Teszt Jakab");
        profile.setWinCount();
        profile.setNetWorth(1000);

        File tempFile = File.createTempFile("blackjack_test_profile", ".ser");
        tempFile.deleteOnExit(); // safety net, ha exception miatt elszállna a teszt

        // fájlba mentés
        try (FileOutputStream fileOut = new FileOutputStream(tempFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(profile);
        }

        // fájlból olvasás
        PlayerProfile loadedProfile = null;
        try (FileInputStream fileIn = new FileInputStream(tempFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            loadedProfile = (PlayerProfile) in.readObject();
        }

        // profil nem null, az adatok egyeznek
        assertNotNull(loadedProfile);
        assertEquals(profile.getName(), loadedProfile.getName());
        assertEquals(profile.getWinCount(), loadedProfile.getWinCount());
        assertEquals(profile.getNetWorth(), loadedProfile.getNetWorth());

        boolean isDeleted = tempFile.delete();
        assertTrue(isDeleted);
    }
}
