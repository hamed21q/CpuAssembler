import java.io.*;
import java.util.Arrays;

public class PipeLine {
    public static void main(String[] args) throws IOException {

        Opcode opcode = new Opcode("src/PipeLineOpcodes.txt");
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
            if (Integer.parseInt(s[3]) < 0) {
                BA = addOneToBinaryString(BA);
                BA = BA.substring(BA.length() - 15);



            }

            //if the input is 2, we got 10, but we need 00010
            DA = zeroExtend(DA);
            AA = zeroExtend(AA);
            if(isImd((opc))){
                BA = BAZeroExtend(BA);
            }
            else{
                BA = zeroExtend(BA) + "0000000000";
            }


            //combine upcode and addresses
            //7 bit for opcode + 5 * 3 for each address(DA,BA,AA) = 22
            //the instruction is 32 bit we add 10 zero at the end (22 + 10 = 32)
            String ins = opc + DA + AA + BA ;
            //System.out.println(ins);
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
    private static String zeroExtend(String number){
        if(number.length() >= 5) return number;
        int numberOfZeroToExtend = 5 - number.length();
        String extendedNumber = number;
        for (int i = 0; i < numberOfZeroToExtend; i++) {
            extendedNumber = "0" + extendedNumber;
        }
        return extendedNumber;
    }
    private static String BAZeroExtend(String number){
        if(number.length() >= 15) return number;
        int numberOfZeroToExtend = 15 - number.length();
        String extendedNumber = number;
        for (int i = 0; i < numberOfZeroToExtend; i++) {
            extendedNumber = "0" + extendedNumber;
        }
        return extendedNumber;
    }
    private static boolean isImd(String opcode){
        String[] arrays = new String[] {"0100010", "0100101","0101000","0101001","0101010","1000010","1000101","0110000","1101000","1001000","1100000"};
        for (int i = 0; i < arrays.length; i++) {
            if(opcode.equals(arrays[i])){
                return true;
            }
        }
        return false;
    }
    public static String addOneToBinaryString(String binaryString) {
        StringBuilder result = new StringBuilder();
        int carry = 1;
        for (int i = binaryString.length() - 1; i >= 0; i--) {
            int sum = carry + binaryString.charAt(i) - '0';
            result.append(sum % 2);
            carry = sum / 2;
        }
        if (carry > 0) {
            result.append(carry);
        }
        return result.reverse().toString();
    }



}