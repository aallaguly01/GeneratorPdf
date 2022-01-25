package ru.itis.pdfgenerator.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.pdfgenerator.model.Data;
import ru.itis.pdfgenerator.repositories.DataRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class GeneeratePDFImpl implements GeneratePDF {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public String generate(String data) {

        JSONObject jsonInfo = new JSONObject(data);

        Data newData = Data.builder()
                .firstName(jsonInfo.getString("firstName"))
                .lastName(jsonInfo.getString("lastName"))
                .path(UUID.randomUUID() + ".pdf")
                .build();

        Document generatedPdf = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(generatedPdf, new FileOutputStream(newData.getPath()));
            generatedPdf.open();

            generatedPdf.add(new Paragraph(newData.getFirstName() + " " + newData.getLastName()));

            generatedPdf.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Error on creating pdf");
        }

        dataRepository.save(newData);

        return newData.getPath();
    }
}
