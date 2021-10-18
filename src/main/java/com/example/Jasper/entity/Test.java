package com.example.Jasper.entity;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File(
                "D:\\Downloads\\student.jrxml");

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)

            System.out.println(st);
    }

    }


