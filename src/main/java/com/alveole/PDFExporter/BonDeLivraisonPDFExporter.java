package com.alveole.PDFExporter;


import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alveole.model.BonDeLivraison;
import com.alveole.model.BonDeLivraisonDetails;
import jakarta.servlet.http.HttpServletResponse;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class BonDeLivraisonPDFExporter {
    private BonDeLivraison bonDeLivraison;

    public BonDeLivraisonPDFExporter(BonDeLivraison bonDeLivraison) {
        this.bonDeLivraison = bonDeLivraison;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
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
        List<BonDeLivraisonDetails> bonDeLivraisonDetails = bonDeLivraison.getBonDeLivraisonDetails();
        BigDecimal totalHT = BigDecimal.ZERO;

        for (BonDeLivraisonDetails detail : bonDeLivraisonDetails) {
            table.addCell(detail.getProduit());
            table.addCell(String.valueOf(detail.getQuantiteCommande()));
            table.addCell(detail.getPrixUnitaire().toString());

            BigDecimal quantite = new BigDecimal(detail.getQuantiteCommande());
            BigDecimal prixUnitaire = detail.getPrixUnitaire();
            BigDecimal totalLigneHT = quantite.multiply(prixUnitaire);

            table.addCell(totalLigneHT.toString());
            totalHT = totalHT.add(totalLigneHT);
        }

        bonDeLivraison.setTotalHT(totalHT);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        Color darkGreen = new Color(0x00, 0x64, 0x00);
        font.setColor(darkGreen);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        Paragraph title = new Paragraph("Bon de Livraison", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Paragraph subTitle = new Paragraph(String.valueOf(bonDeLivraison.getDateLivraison()), font);
        subTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subTitle);

        Paragraph blankLine = new Paragraph(" ");
        document.add(blankLine);

        PdfPTable subtitlesTable = new PdfPTable(2);
        subtitlesTable.setWidthPercentage(50);
        subtitlesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell subtitle1Cell1 = new PdfPCell(new Phrase("N° bon de livraison: "));
        subtitle1Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle1Cell2 = new PdfPCell(new Phrase(bonDeLivraison.getNumeroLivraison()));

        subtitlesTable.addCell(subtitle1Cell1);
        subtitlesTable.addCell(subtitle1Cell2);

        PdfPCell subtitle2Cell1 = new PdfPCell(new Phrase("N° bon de commande: "));
        subtitle2Cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell subtitle2Cell2 = new PdfPCell(new Phrase(bonDeLivraison.getNumeroCommande()));

        subtitlesTable.addCell(subtitle2Cell1);
        subtitlesTable.addCell(subtitle2Cell2);

        document.add(subtitlesTable);
        document.add(blankLine);

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
        clientCell.addElement(new Paragraph("Expedier à:\n" + bonDeLivraison.getClient()));
        clientCell.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(clientCell);

        document.add(infoTable);
        document.add(blankLine);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);

        PdfPTable outerTable = new PdfPTable(1);
        outerTable.setWidthPercentage(45);
        outerTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);


        PdfPCell totalTTCCell1 = new PdfPCell(new Phrase("Total HT: "));
        totalTTCCell1.setBorder(Rectangle.NO_BORDER | Rectangle.TOP);

        PdfPCell totalTTCCell2 = new PdfPCell(new Phrase(bonDeLivraison.getTotalHT().toString()));
        totalTTCCell2.setBorder(Rectangle.NO_BORDER | Rectangle.TOP);
        totalTTCCell2.setBackgroundColor(new Color(0, 100, 0, 150));

        detailsTable.addCell(totalTTCCell1);
        detailsTable.addCell(totalTTCCell2);

        PdfPCell outerCell = new PdfPCell(detailsTable);
        outerCell.setBorder(Rectangle.NO_BORDER);
        outerTable.addCell(outerCell);
        document.add(outerTable);

        document.add(blankLine);
        document.add(blankLine);

        // sign Table
        PdfPTable SignTable = new PdfPTable(2);
        SignTable.setWidthPercentage(100f);
        SignTable.setWidths(new float[] { 1.5f, 1.5f });
        SignTable.setSpacingBefore(10);
        SignTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Subtitle 1
        PdfPCell clientCell1 = new PdfPCell(new Phrase("Recus le: "));
        clientCell1.setBorder(Rectangle.NO_BORDER);
        clientCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        SignTable.addCell(clientCell1);

        // Subtitle 2
        PdfPCell fournisseurCell1 = new PdfPCell(new Phrase("Livre le: "));
        fournisseurCell1.setBorder(Rectangle.NO_BORDER);
        fournisseurCell1.setHorizontalAlignment(Element.ALIGN_CENTER); 
        SignTable.addCell(fournisseurCell1);




        document.add(SignTable);



        document.close();
    }
}

