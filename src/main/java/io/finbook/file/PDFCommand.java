package io.finbook.file;

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
import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PDFCommand {

	private static String path = "src/main/resources/public/finbook/files/temp/";
	private static final String FILE_EXTENSION = ".pdf";

	public PDFCommand() {

	}

	public void reportGenerate(String filename, String[] summary, java.util.List<Invoice> invoices) throws IOException {
		PdfFont timesRoman = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		PdfFont helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

		String pathToSave = path.concat(filename).concat(FILE_EXTENSION);

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
		list.add(new ListItem("Name: ".concat("-")))
				.add(new ListItem("ID: ".concat(summary[0])))
				.add(new ListItem("Reporting period: ".concat(summary[1])));
		// Add the list
		document.add(list);

		p = new Paragraph("Summary")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(14);
		document.add(p);

		Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
		table.addCell(headerCell("INCOMES - TAXES"));
		table.addCell(headerCell("EGRESS - TAXES"));
		table.addCell(headerCell("TOTAL - TAXES DUE"));
		table.addCell(centerCell(Utils.doubleToStringFormat(Double.parseDouble(summary[2]))))
				.addCell(centerCell(Utils.doubleToStringFormat(Double.parseDouble(summary[3]))))
				.addCell(centerCell(Utils.doubleToStringFormat(Double.parseDouble(summary[4]))));
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

		for (Invoice invoice : invoices) {
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

	public static ResponseCreator reportInPDF() throws IOException {
		String filename = "testing";
		path = path.concat(filename).concat(FILE_EXTENSION);


		PdfWriter pdfWriter = new PdfWriter(path);
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDocument);

		document.add(new Paragraph("Hello world!"));

		document.close();


		return MyResponse.ok(
				new StandardResponse(null, "home/index")
		);
	}

	/*public static ResponseCreator init() throws IOException {
		//Loading an existing document
		File file = new File("C:/src/reporting/src/main/resources/public/finbook/files/spain/canary/canary_420.pdf");
		PDDocument document = PDDocument.load(file);

		//Retrieving the pages of the document
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		//Begin the Content stream
		contentStream.beginText();

		//Setting the font to the Content stream
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

		//Setting the position for the line
		contentStream.newLineAtOffset(0, 0);

		String text = "10.000 €";

		//Adding text in the form of string
		contentStream.showText(text);

		//Ending the content stream
		contentStream.endText();

		System.out.println("Content added");

		//Closing the content stream
		contentStream.close();

		//Saving the document
		document.save(new File("C:/src/reporting/src/main/resources/public/finbook/files/spain/new.pdf"));

		//Closing the document
		document.close();

		return MyResponse.ok(
				new StandardResponse(null, "home/index")
		);
	}*/

}
