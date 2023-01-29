import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Opcode opcode = new Opcode();
        File file = new File("src/instruction.txt");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bf.readLine()) != null) {
            String[] s = line.split(","); //"ADD,2,3,7" => [Add, 2, 3, 7]
            String opc = opcode.dict.get(s[0]); //convert ADD to equivalent binary command

            //convert decimal address to binary
            String DA =  Integer.toBinaryString(Integer.parseInt(s[1]));
            String AA = Integer.toBinaryString(Integer.parseInt(s[2]));
            String BA = Integer.toBinaryString(Integer.parseInt(s[3]));

            //if the input is 2, we got 10, but we need 00010
            DA = zeroExtend(DA);
            AA = zeroExtend(AA);
            BA = zeroExtend(BA);

            //combine upcode and addresses
            //7 bit for opcode + 5 * 3 for each address(DA,BA,AA) = 22
            //the instruction is 32 bit we add 10 zero at the end (22 + 10 = 32)
            String ins = opc + DA + AA + BA + "0000000000";

            //the process of converting binary to HEX
            String[] part = ins.split("(?<=\\G.{4})");
            int[] parts = new int[8];
            for (int i = 0; i < part.length; i++) {
                parts[i] = Integer.parseInt(part[i], 2);
            }
            String[] hex = new String[8];
            for (int i = 0; i < hex.length; i++) {
                hex[i] = Integer.toHexString(parts[i]);
            }
            String hexIns = "";
            for (int i = 0; i < hex.length; i++) {
                hexIns += hex[i];
            }
            System.out.println(hexIns);
        }

    }
    private static String zeroExtend(String s){
        if(s.length() >= 5) return s;
        int ss = 5 - s.length();
        String sss =s;
        for (int i = 0; i < ss; i++) {
            sss = "0"+sss;
        }
        return sss;
    }
}