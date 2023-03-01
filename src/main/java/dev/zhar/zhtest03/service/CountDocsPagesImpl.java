package dev.zhar.zhtest03.service;

import dev.zhar.zhtest03.exception.CountDocsPagesException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class CountDocsPagesImpl implements CountDocsPages{
    private static int documentsCount = 0;
    private static int pagesCount = 0;

    @Override
    public Integer[] FileList(File file) {

        for(File item : Objects.requireNonNull(file.listFiles())){
            if(item.isDirectory()){
                File subfolder = new File(item.getAbsolutePath());
                FileList(subfolder);
            } else if (item.getName().endsWith("docx")) {
                documentsCount++;
                XWPFDocument doc = null;
                try {
                    doc = new XWPFDocument(OPCPackage.open(item.getAbsolutePath()));
                } catch (InvalidFormatException e) {
                    throw new CountDocsPagesException("Файл неизвестного формата");
                } catch (IOException e) {
                    throw new CountDocsPagesException("Ошибка чтения файла");
                }
                pagesCount += doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            } else if (item.getName().endsWith("pdf")){
                documentsCount++;
                PDDocument pdfReader = null;
                try {
                    pdfReader = PDDocument.load(item);
                } catch (IOException e) {
                    throw new CountDocsPagesException("Ошибка чтения файла");
                }
                pagesCount += pdfReader.getNumberOfPages();
            }
        }
        return new Integer[]{documentsCount, pagesCount};
    }
}
