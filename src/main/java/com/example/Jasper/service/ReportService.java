package com.example.Jasper.service;

import com.example.Jasper.entity.Student;
import com.example.Jasper.repository.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.minidev.json.parser.JSONParser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private StudentRepository repository;

    List<Student> lineList = new ArrayList<>();

    @Bean
    CommandLineRunner runner() {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("D:\\Downloads\\sample1.json"));
            System.out.println("object us" + obj);
            JsonNode nodes = mapper.readTree(new Gson().toJson(obj));
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode node : nodes) {


                Student newJsonNode = objectMapper.treeToValue(node, Student.class);

                lineList.add(newJsonNode);


            }

        };
    }

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Desktop\\Report";

        File file = ResourceUtils.getFile("classpath:student.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lineList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Programmer");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
        }
        return "report generated in path : " + path;
    }
}