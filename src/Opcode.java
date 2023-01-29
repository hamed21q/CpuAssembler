import java.io.*;
import java.util.Hashtable;

public class Opcode {
    public Hashtable<String, String> dict;
    public Opcode() throws IOException {
        dict = new Hashtable<>();
        File file = new File("src/Opcodes.txt");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bf.readLine()) != null) {
            String[] s = line.split(",");

            dict.put(s[0], s[1]);
        }
    }
}
