package io.finbook.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import io.finbook.model.Invoice;
import io.finbook.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class IVAReport implements PDFGenerate{

	@Override
	public void create(Map<String, Object> content) throws IOException {
		PdfFont timesRoman = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		PdfFont helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

		String pathToSave = PATH.concat(content.get("filename").toString()).concat(FILE_EXTENSION);

		try {
			Path fileToDeletePath = Paths.get(pathToSave);
			Files.deleteIfExists(fileToDeletePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PdfWriter pdfWriter = new PdfWriter(pathToSave);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDocument);

		Paragraph p = new Paragraph("Finbook report")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(20).setUnderline();
		document.add(p);

		// Add a Paragraph
		document.add(new Paragraph("Company data").setFont(timesRoman));
		// Create a List
		List list = new List()
				.setSymbolIndent(12)
				.setListSymbol("\u2022")
				.setFont(timesRoman);
		// Add ListItem objects
		list.add(new ListItem("ID: ".concat(content.get("currentUserId").toString())))
				.add(new ListItem("Reporting period: ".concat(content.get("period").toString())));
		// Add the list
		document.add(list);

		p = new Paragraph("Summary")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
		document.add(p);

		Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
		table.addCell(headerCell("INCOMES - TAXES"));
		table.addCell(headerCell("EGRESS - TAXES"));
		table.addCell(headerCell("TOTAL - TAXES DUE"));
		table.addCell(centerCell(content.get("incomesTaxes").toString()))
				.addCell(centerCell(content.get("egressTaxes").toString()))
				.addCell(centerCell(content.get("totalTaxes").toString()));
		document.add(table);

		p = new Paragraph("List of invoices")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
		document.add(p);

		table = new Table(UnitValue.createPercentArray(new float[]{4,10,4,5,5})).useAllAvailableWidth();
		table.addCell(headerCell("DATE"));
		table.addCell(headerCell("INVOICE UUID"));
		table.addCell(headerCell("SUBTOTAL"));
		table.addCell(headerCell("TOTAL TAXES"));
		table.addCell(headerCell("TOTAL DUE"));

		for (Invoice invoice : (java.util.List<Invoice>) content.get("invoicesList")) {
			table.addCell(invoice.getInvoiceDate().toLocalDate().toString());
			table.addCell(invoice.getInvoiceUUID().toUpperCase());
			table.addCell(Utils.doubleToStringFormat(invoice.getSubtotal()));
			table.addCell(Utils.doubleToStringFormat(invoice.getTotalTaxes()));
			table.addCell(Utils.doubleToStringFormat(invoice.getTotalDue()));
		}

		document.add(table);
		document.close();
	}

	private Cell headerCell(String textToAdd){
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setBold();
		return cell;
	}

	private Cell centerCell(String textToAdd){
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setTextAlignment(TextAlignment.CENTER);
		return cell;
	}

}
