package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import static spark.Spark.*;


public class Main {
    static int acessos=0;
    public static void main(String[] args)
    {
        port(8080);

        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField altura = new JTextField();
        JTextField massa = new JTextField();
        JTextField imc = new JTextField();

        altura.setFont((new Font("Arial", Font.PLAIN, 30)));
        massa.setFont((new Font("Arial", Font.PLAIN, 30)));
        imc.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Massa:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label2 = new JLabel("Altura:");
        label2.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label3 = new JLabel("IMC:");
        label3.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label1);
        panel.add(altura);
        panel.add(label2);
        panel.add(massa);
        panel.add(label3);
        panel.add(imc);

        String [] buttonLabels = {"Limpar","Fechar"};

        get("/nome", (req, res) ->
        {
            String op1 = req.queryParams("p");
            altura.setText(op1);
            massa.setText(Integer.toString(acessos));

            String content=Integer.toString(acessos);
            acessos++;
            return content;
        });

        post("/api", (req, res) -> {
            double IMC,m,a;

            String corpoRequisicao = req.body();
            System.out.println("Corpo JSON: " + corpoRequisicao);

            JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            altura.setText(jsonObject.get("altura").getAsString());
            massa.setText(jsonObject.get("massa").getAsString());

            a=Double.parseDouble(altura.getText());
            m=Double.parseDouble(massa.getText());
            IMC=m/(a*a);

            imc.setText(String.format("%.2f",IMC));

            return "{\"IMC\":\"" +  String.format("%.2f",IMC) + "\"}";
        });

        for(String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(label.equals("Fechar"))
                    {
                        System.exit(0);
                    }
                    if(label.equals("Limpar"))
                    {
                        altura.setText("");
                        massa.setText("");
                        imc.setText("");
                    }
                }
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}


