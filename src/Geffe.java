import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Geffe {
    boolean[] m1 = new boolean[19];
    boolean[] m2 = new boolean[22];
    boolean[] m3 = new boolean[23];
    boolean x1;
    boolean x2;
    boolean x3;
    static boolean[] key = new boolean[100];
    static ArrayList<String> st = new ArrayList();

    public Geffe() {
    }

    public void key_gen(String begin) {
        String s1 = begin.substring(0, 19);
        String s2 = begin.substring(19, 41);
        String s3 = begin.substring(41);

        int k;
        for(k = 0; k < 19; ++k) {
            if (s1.charAt(k) == '0') {
                this.m1[k] = false;
            } else {
                this.m1[k] = true;
            }
        }

        for(k = 0; k < 22; ++k) {
            if (s2.charAt(k) == '0') {
                this.m2[k] = false;
            } else {
                this.m2[k] = true;
            }
        }

        for(k = 0; k < 23; ++k) {
            if (s3.charAt(k) == '0') {
                this.m3[k] = false;
            } else {
                this.m3[k] = true;
            }
        }

        for(k = 0; k < 100; ++k) {
            this.x1 = this.m1[18] ^ this.m1[17] ^ this.m1[16] ^ this.m1[13];
            this.x2 = this.m2[21] ^ this.m2[20];
            this.x3 = this.m3[22] ^ this.m3[21] ^ this.m3[20] ^ this.m3[7];
            key[k] = this.m1[18] & this.m2[21] ^ this.m2[21] & this.m3[22] ^ this.m3[22];

            int i;
            for(i = 18; i > 0; --i) {
                this.m1[i] = this.m1[i - 1];
            }

            this.m1[0] = this.x1;

            for(i = 21; i > 0; --i) {
                this.m2[i] = this.m2[i - 1];
            }

            this.m2[0] = this.x2;

            for(i = 22; i > 0; --i) {
                this.m3[i] = this.m3[i - 1];
            }

            this.m3[0] = this.x3;
        }

        for(k = 0; k < 100; ++k) {
            if (key[k]) {
                System.out.print("1");
            } else {
                System.out.print("0");
            }
        }

        System.out.println();
    }

    public static void writeFile() {
        for(int i = 0; i < key.length; ++i) {
            if (key[i]) {
                st.add(301 + i + " 0\n");
            } else {
                st.add("-" + (301 + i) + " 0\n");
            }
        }

        String fileIn = "g1.cnf";

        try {
            OutputStream f = new FileOutputStream(fileIn, true);
            OutputStreamWriter writer = new OutputStreamWriter(f);
            BufferedWriter out = new BufferedWriter(writer);
            out.write("\n");

            for(int i = 0; i < st.size(); ++i) {
                out.write((String)st.get(i));
                out.flush();
            }

            out.close();
        } catch (IOException var5) {
            System.err.println(var5);
        }

    }

    public static void main(String[] args) {
        Geffe g1 = new Geffe();
        g1.key_gen("0101000011000010101000001010101111010101000000111010101010101111");
        Scanner scan = new Scanner(System.in);
        System.out.println("Записать выход в файл? y/n");
        String ans = scan.nextLine();
        if (ans.equals("y")) {
            writeFile();
        }

    }
}

