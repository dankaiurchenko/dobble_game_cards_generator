package com.dankaiurchenko.dobblegame;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class DocumentComposer {
    public void compose(String imagesPath, String imagesRegex, String documentName) {

        try {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph tmpParagraph = document.createParagraph();

            File file = new File(imagesPath);

            if (file.isDirectory()) {
                for (File listFile : Objects.requireNonNull(file.listFiles())) {
                    if (!listFile.isDirectory() && listFile.getName().matches(imagesRegex)) {
                        XWPFRun tmpRun = tmpParagraph.createRun();
                        BufferedImage bimg1 = ImageIO.read(listFile);
                        int width1 = bimg1.getWidth();
                        int height1 = bimg1.getHeight();
                        String imgFile1 = listFile.getName();
                        int imgFormat1 = XWPFDocument.PICTURE_TYPE_PNG;
                        FileInputStream pictureData = new FileInputStream(listFile);
                        tmpRun.addPicture(pictureData, imgFormat1, imgFile1, Units.toEMU(width1*0.70), Units.toEMU(height1*0.70));
                        pictureData.close();
                        // page break
                    }

                }
            }
            document.write(new FileOutputStream(new File(imagesPath + "docs/_" + documentName)));
            document.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }


    }
}
