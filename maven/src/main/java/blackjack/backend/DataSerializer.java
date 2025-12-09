package blackjack.backend;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class DataSerializer {
    private ArrayList<PlayerProfile> profiles = new ArrayList<>();

    public DataSerializer(ArrayList<PlayerProfile> p) { this.profiles = p; }
    public void saveFile(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Profiles.ser"))) {
            out.writeObject(profiles);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "A mentés sikertelen :(" + e.getMessage());
        }
    }

    public void loadFile(){
        File f = new File("Profiles.ser");
        if (!f.exists()) return;

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            profiles = (ArrayList<PlayerProfile>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Hiba a betöltés közben :(" + e.getMessage());
        }
    }

    // getter
    public ArrayList<PlayerProfile> getProfiles() { return this.profiles; }
}
