package com.alveole.PDFExporter;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alveole.model.Facture;
import com.alveole.model.FactureDetails;
import jakarta.servlet.http.HttpServletResponse;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class FacturePDFExporter {
    private Facture facture;

    public FacturePDFExporter(Facture facture) {
        this.facture = facture;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        // Use a specific shade of dark green (#006400)
        Color darkGreen = new Color(0x00, 0x64, 0x00);
        cell.setBackgroundColor(darkGreen);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Produit", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantité ", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prix Unitaire (HT)", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total (HT)", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        List<FactureDetails> factureDetails = facture.getFactureDetails();
        BigDecimal totalHT = BigDecimal.ZERO;

        for (FactureDetails detail : factureDetails) {
            table.addCell(detail.getProduit());
            table.addCell(String.valueOf(detail.getQuantiteCommande()));
            table.addCell(detail.getPrixUnitaire().toString());

            BigDecimal quantite = new BigDecimal(detail.getQuantiteCommande());
            BigDecimal prixUnitaire = detail.getPrixUnitaire();
            BigDecimal totalLigneHT = quantite.multiply(prixUnitaire);

            table.addCell(totalLigneHT.toString());
            totalHT = totalHT.add(totalLigneHT);
        }

        facture.setTotalHT(totalHT);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        // Use a specific shade of dark green (#006400)
        Color darkGreen = new Color(0x00, 0x64, 0x00);
        font.setColor(darkGreen);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        // Title
        Paragraph title = new Paragraph("Facture", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        //Subtitle date
        Paragraph SubTitleDate = new Paragraph(String.valueOf(facture.getDateFacture()), font);
        SubTitleDate.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(SubTitleDate);

        // An empty space after the paragraph
        Paragraph blankLine = new Paragraph(" ");
        document.add(blankLine);


        // Subtitles Table
        PdfPTable subtitlesTable = new PdfPTable(2);
        subtitlesTable.setWidthPercentage(50);
        subtitlesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Subtitle 1
        PdfPCell subtitle1Cell1 = new PdfPCell(new Phrase("N° facture: "));
        subtitle1Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle1Cell2 = new PdfPCell(new Phrase(facture.getNumeroFacture()));

        subtitlesTable.addCell(subtitle1Cell1);
        subtitlesTable.addCell(subtitle1Cell2);

        // Subtitle 2
        PdfPCell subtitle2Cell1 = new PdfPCell(new Phrase("N° bon de commande: "));
        subtitle2Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle2Cell2 = new PdfPCell(new Phrase(facture.getNumeroCommande()));

        subtitlesTable.addCell(subtitle2Cell1);
        subtitlesTable.addCell(subtitle2Cell2);

        // Subtitle 3
        PdfPCell subtitle3Cell1 = new PdfPCell(new Phrase("N° bon de livraison: "));
        subtitle3Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle3Cell2 = new PdfPCell(new Phrase(facture.getNumeroLivraison()));

        subtitlesTable.addCell(subtitle3Cell1);
        subtitlesTable.addCell(subtitle3Cell2);

        document.add(subtitlesTable);

        document.add(blankLine);

        // Vendor and Client Information
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100f);
        infoTable.setWidths(new float[] { 1.5f, 1.5f });
        infoTable.setSpacingBefore(10);
        infoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Vendor Cell
        PdfPCell vendorCell = new PdfPCell();
        vendorCell.setPadding(5);
        vendorCell.setUseBorderPadding(true);
        vendorCell.addElement(new Paragraph("Facturer à: \n"
                + "ALVEOLE DU MAROC\n"
                + "ZONE INDUSTRIELLE LOT N°87\n"
                + "EL JADIDA\n"
                + "Téléphone: +212 663 425 950\n"
                + "Email: alveoledumaroc@gmail.com"));
        vendorCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(vendorCell);

        // Client Cell
        PdfPCell clientCell = new PdfPCell();
        clientCell.setPadding(5);
        clientCell.setUseBorderPadding(true);
        clientCell.addElement(new Paragraph("Expedier à:\n" + facture.getClient()));
        clientCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(clientCell);

        document.add(infoTable);


        document.add(blankLine);


        // Invoice Details Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);

        document.add(blankLine);


        // Outer Table
        PdfPTable outerTable = new PdfPTable(1);
        outerTable.setWidthPercentage(45);
        outerTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Inner Details Table
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Total HT
        PdfPCell totalHTCell1 = new PdfPCell(new Phrase("Total HT: "));
        totalHTCell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell totalHTCell2 = new PdfPCell(new Phrase(facture.getTotalHT().toString()));
        totalHTCell2.setBorder(Rectangle.NO_BORDER);

        detailsTable.addCell(totalHTCell1);
        detailsTable.addCell(totalHTCell2);

        // Total TVA
        PdfPCell totalTVACell1 = new PdfPCell(new Phrase("Total TVA: "));
        totalTVACell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell totalTVACell2 = new PdfPCell(new Phrase(facture.getTotalTVA().toString()));
        totalTVACell2.setBorder(Rectangle.NO_BORDER);

        detailsTable.addCell(totalTVACell1);
        detailsTable.addCell(totalTVACell2);

        // Total TTC with TOP_BORDER
        PdfPCell totalTTCCell1 = new PdfPCell(new Phrase("Total TTC: "));
        totalTTCCell1.setBorder(Rectangle.NO_BORDER | Rectangle.TOP);

        PdfPCell totalTTCCell2 = new PdfPCell(new Phrase(facture.getTotalTTC().toString()));
        totalTTCCell2.setBorder(Rectangle.NO_BORDER | Rectangle.TOP);

        // Set background color for totalTTCCell2
        totalTTCCell2.setBackgroundColor(new Color(0, 100, 0, 150));

        detailsTable.addCell(totalTTCCell1);
        detailsTable.addCell(totalTTCCell2);

        // Add the detailsTable to the outerTable
        PdfPCell outerCell = new PdfPCell(detailsTable);
        outerCell.setBorder(Rectangle.NO_BORDER);
        outerTable.addCell(outerCell);

        document.add(outerTable);


        document.close();
    }
}

