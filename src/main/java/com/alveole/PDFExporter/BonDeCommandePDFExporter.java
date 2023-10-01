package com.alveole.PDFExporter;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alveole.model.BonDeCommande;
import com.alveole.model.BonDeCommandeDetails;
import jakarta.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class BonDeCommandePDFExporter {
    private BonDeCommande bonDeCommande;

    public BonDeCommandePDFExporter(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
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

        cell.setPhrase(new Phrase("Totale (HT)", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        List<BonDeCommandeDetails> bonDeCommandeDetails = bonDeCommande.getBonDeCommandeDetails();
        BigDecimal totalHT = BigDecimal.ZERO;

        for (BonDeCommandeDetails detail : bonDeCommandeDetails) {
            table.addCell(detail.getProduit());
            table.addCell(String.valueOf(detail.getQuantite()));
            table.addCell(detail.getPrixUnitaire().toString());

            BigDecimal quantite = new BigDecimal(detail.getQuantite());
            BigDecimal prixUnitaire = detail.getPrixUnitaire();
            BigDecimal totalLigneHT = quantite.multiply(prixUnitaire);

            table.addCell(totalLigneHT.toString());
            totalHT = totalHT.add(totalLigneHT);
        }

        bonDeCommande.setTotalHT(totalHT);
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
        Paragraph title = new Paragraph("Bon de Commande", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        //Subtitle date
        Paragraph SubTitleDate = new Paragraph(String.valueOf(bonDeCommande.getDateCommande()), font);
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
        PdfPCell subtitle1Cell1 = new PdfPCell(new Phrase("N° bon de Commande: "));
        subtitle1Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle1Cell2 = new PdfPCell(new Phrase(bonDeCommande.getNumeroCommande()));
        subtitle1Cell2.setBorder(Rectangle.NO_BORDER);

        subtitlesTable.addCell(subtitle1Cell1);
        subtitlesTable.addCell(subtitle1Cell2);

        document.add(subtitlesTable);

        document.add(new Paragraph(" "));

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
        clientCell.addElement(new Paragraph("Expedier à:\n" + bonDeCommande.getClient()));
        clientCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(clientCell);

        document.add(infoTable);

        document.add(new Paragraph(" "));

        // Bon de Commande Details Table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);

        document.add(new Paragraph(" "));

        // Outer Table
        PdfPTable outerTable = new PdfPTable(1);
        outerTable.setWidthPercentage(45);
        outerTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Inner Details Table
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Total HT
        PdfPCell totalHTCell1 = new PdfPCell(new Phrase("Totale HT: "));
        totalHTCell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell totalHTCell2 = new PdfPCell(new Phrase(bonDeCommande.getTotalHT().toString()));
        totalHTCell2.setBorder(Rectangle.NO_BORDER);


        // Set background color for totalTTCCell2
        totalHTCell2.setBackgroundColor(new Color(0, 100, 0, 150));

        detailsTable.addCell(totalHTCell1);
        detailsTable.addCell(totalHTCell2);

        // Add the detailsTable to the outerTable
        PdfPCell outerCell = new PdfPCell(detailsTable);
        outerCell.setBorder(Rectangle.NO_BORDER);
        outerTable.addCell(outerCell);

        document.add(outerTable);

        document.close();
    }
}
