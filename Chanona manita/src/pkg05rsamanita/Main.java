/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pkg05rsamanita;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.Arrays;
import javax.swing.*;

public class Main extends JFrame {

    private int tamprimo;
    private BigInteger p, q, n;
    private BigInteger fi;
    private BigInteger e, d;

    private JTextField inputField;
    private JButton encryptButton, decryptButton;
    private JTextArea outputArea;

    public Main(int tamprimo) {
        this.tamprimo = tamprimo;
        generarPrimos();
        generarClaves();

        initComponents();
    }

    private void generarPrimos() {
        p = new BigInteger(tamprimo, 10, new java.util.Random());
        do {
            q = new BigInteger(tamprimo, 10, new java.util.Random());
        } while (q.compareTo(p) == 0);
    }

    private void generarClaves() {
        n = p.multiply(q);
        fi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));

        do
            e = new BigInteger(2 * tamprimo, new java.util.Random());
        while ((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));

        d = e.modInverse(fi);
    }

    private BigInteger[] cifrar(String mensaje) {
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();

        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        for (int i = 0; i < bigdigitos.length; i++) {
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }

        BigInteger[] cifrado = new BigInteger[bigdigitos.length];

        for (int i = 0; i < bigdigitos.length; i++) {
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        return cifrado;
    }

    private String descifrar(BigInteger[] cifrado) {
        BigInteger[] descifrado = new BigInteger[cifrado.length];

        for (int i = 0; i < descifrado.length; i++) {
            descifrado[i] = cifrado[i].modPow(d, n);
        }

        char[] charArray = new char[descifrado.length];

        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (descifrado[i].intValue());
        }
        return (new String(charArray));
    }

    private void showCifradoDialog(BigInteger[] cifrado) {
        StringBuilder cifradoText = new StringBuilder("Cifrado:\n");

        for (BigInteger num : cifrado) {
            cifradoText.append(num).append("\n");
        }

        outputArea.setText(cifradoText.toString());

        JOptionPane.showMessageDialog(Main.this, outputArea, "Texto Cifrado", JOptionPane.PLAIN_MESSAGE);
    }

    private void initComponents() {
        setTitle("RSA Encryptor/Decryptor");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establecer el color de fondo azul claro
        Color lightBlue = new Color(173, 216, 230); // RGB para azul claro
        getContentPane().setBackground(lightBlue);

        JPanel panel = new JPanel();
        inputField = new JTextField(20);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Color lightOrange = new Color(255, 200, 100);
        encryptButton.setBackground(lightOrange);
        decryptButton.setBackground(lightOrange);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = inputField.getText();
                BigInteger[] cifrado = cifrar(mensaje);
                showCifradoDialog(cifrado);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cifradoStr = JOptionPane.showInputDialog(Main.this, "Introduce el mensaje cifrado (separado por comas)");
                String[] cifradoArray = cifradoStr.split(",");
                BigInteger[] cifrado = new BigInteger[cifradoArray.length];
                for (int i = 0; i < cifradoArray.length; i++) {
                    cifrado[i] = new BigInteger(cifradoArray[i].trim());
                }
                String descifrado = descifrar(cifrado);
                JOptionPane.showMessageDialog(Main.this, "Descifrado: " + descifrado);
            }
        });

        panel.add(inputField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main(1024);
    }
}
