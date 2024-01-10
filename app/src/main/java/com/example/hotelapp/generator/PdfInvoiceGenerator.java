package com.example.hotelapp.generator;

import android.os.Environment;

import com.example.hotelapp.model.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.TableWrapper;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PdfInvoiceGenerator {



    public static void generateInvoice(ArrayList<Product> products,String name,int subtotal,int totalprice){
        Document document=new Document();
        try {
            String pdfpath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file=new File(/*Environment.getExternalStorageDirectory()*/pdfpath,name);

            FileOutputStream fileOutputStream=new FileOutputStream(file);
            PdfWriter.getInstance(document,fileOutputStream);

            document.open();

            float fnt=26.7f;
            float columnwidth[]={62,162,112,112,112};
            document.add(new Paragraph("                                                              Invoice"));
            document.add(new Paragraph("\n\n\n\n\n\n"));
            PdfPTable table=new PdfPTable(columnwidth);
            table.addCell(new PdfPCell(new Paragraph("S.No")));
            table.addCell(new PdfPCell(new Paragraph("Item Name")));
            table.addCell(new PdfPCell(new Paragraph("Item Price")));
            table.addCell(new PdfPCell(new Paragraph("Item Quantity")));
            table.addCell(new PdfPCell(new Paragraph("Item Total Price")));

            for (int i =0;i<products.size();i++){

                Product product=products.get(i);
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(i+1))));
                table.addCell(new PdfPCell(new Paragraph(product.getName())));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf((int)product.getPrice()))));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(product.getQuantity()))));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(product.getPrice()*product.getQuantity()))));
            }


            table.addCell(new PdfPCell(new Paragraph("")));
            table.addCell(new PdfPCell(new Paragraph("")));
            table.addCell(new PdfPCell(new Paragraph("")));
            table.addCell(new PdfPCell(new Paragraph("Subtotal")));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(subtotal))));

            PdfPCell pdfPCell=new PdfPCell(new Phrase("Terms & Condition"));
            pdfPCell.setRowspan(1);pdfPCell.setColspan(3);
            table.addCell(pdfPCell);

            table.addCell(new PdfPCell(new Paragraph("GST(5%)")));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(subtotal * 5 / 100))));

            PdfPCell pdfPCell1=new PdfPCell(new Phrase("Good Sold Are Not Returnable & Exchangeable."));
            pdfPCell1.setRowspan(1);pdfPCell1.setColspan(3);
            table.addCell(pdfPCell1);

            table.addCell(new PdfPCell(new Paragraph("Grand Total")));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(totalprice))));




            document.add(table);

            document.close();
            fileOutputStream.close();


        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }
}
