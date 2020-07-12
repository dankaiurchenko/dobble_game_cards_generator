package com.dankaiurchenko.dobblegame;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMImageElement;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ImagesGenerator {

    public void generate(String resultingPath, Set<Card> deckOfCards) {
        List<String> icons = new ArrayList<>(getIconsPack(deckOfCards));
        List<File> templates = getTemplates();

        int j = 0;
        for (Card deckOfCard : deckOfCards) {
            deckOfCard.setIcons(icons);
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
            String uri = templates.get(0).getAbsolutePath();
            try {
                Document doc = f.createDocument(uri);
                NodeList nodeList = doc.getDocumentElement().getElementsByTagName("image");
                for (int i = 0; i < nodeList.getLength() && i < deckOfCard.iconsPaths.size(); i++) {
                    SVGOMImageElement item = (SVGOMImageElement) nodeList.item(i);
                    String value = "file:///" + deckOfCard.iconsPaths.get(i);
                    item.setAttributeNS("http://www.w3.org/1999/xlink", "href", value);
                }

                TranscoderInput input_svg_image = new TranscoderInput(doc);
                OutputStream png_ostream = new FileOutputStream(resultingPath + j++ + ".png");
                TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
                PNGTranscoder my_converter = new PNGTranscoder();
                my_converter.transcode(input_svg_image, output_png_image);
                png_ostream.flush();
                png_ostream.close();

            } catch (IOException | TranscoderException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<String> getIconsPack(Set<Card> deckOfCards) {
        Set<String> iconsNeeded = new HashSet<>(deckOfCards.size());
        File file = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("icons")).getFile()
        );

        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (!listFile.isDirectory() && listFile.getName().endsWith(".svg")) {
                    iconsNeeded.add(listFile.getAbsolutePath());
                }
                if (iconsNeeded.size() == deckOfCards.size())
                    break;
            }
        }
        return iconsNeeded;

    }

    public List<File> getTemplates() {
        List<File> templates = new ArrayList<>();

        File file = new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource("templates")).getFile()
        );

        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (!listFile.isDirectory() && listFile.getName().matches(".+2.svg")) {
                    templates.add(listFile);
                }
            }
        }
        return templates;
    }

}
