package io.finbook.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class VATReturnsCanary420 implements PDFGenerate {

	PdfFont timesRoman;
	PdfFont helveticaBold;

	public VATReturnsCanary420() throws IOException {
		timesRoman = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
	}

	@Override
	public void create(Map<String, Object> content) throws IOException {
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

		Paragraph p = new Paragraph("IGIC (Mod. 420)")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(18).setUnderline();
		document.add(p);

		// Section 1
		Table tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("1. - Periodo de liquidación").setFont(timesRoman));
		Table tableInside = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
		tableInside.addCell("EJERCICIO").addCell(content.get("year").toString())
				.addCell("PERÍODO").addCell(content.get("whichPeriod").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 2
		tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("2. - Datos identificativos").setFont(timesRoman))
				.addCell(nextTopHeaderCell("DOMICILIO FISCAL"));
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("N.I.F.").addCell(content.get("currentUserId").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 3 - 6
		tableTop = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("3. - Ingreso").setFont(timesRoman))
				.addCell(topHeaderCell("6. - Sujeto pasivo").setFont(timesRoman));
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("Importe").addCell(content.get("totalTaxesDue").toString());
		tableTop.addCell(tableInside);
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("Fecha").addCell(content.get("signDate").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 5
		tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("7. - Liquidación").setFont(timesRoman))
				.addCell(nextTopHeaderCell("I.G.I.C. DEVENGADO"));
		tableInside = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
		tableInside.addCell("")
				.addCell(headerCell("Base imponible"))
				.addCell(headerCell("Tipo de gravamen %"))
				.addCell(headerCell("Cuota devengada"));

		tableInside.addCell("")
				.addCell(content.get("incomesWithoutTaxes").toString())
				.addCell(content.get("taxRate").toString())
				.addCell(content.get("incomesTaxes").toString());
		Cell cellWithColspan = new Cell(1, 3).add(new Paragraph("Total cuotas devengadas"));
		tableInside.addCell(cellWithColspan)
				.addCell(content.get("incomesTaxes").toString());
		tableTop.addCell(tableInside);

		tableTop.addCell(nextTopHeaderCell("I.G.I.C. DEDUCIBLE Y RESULTADO AUTOLIQUIDACIÓN"));
		tableInside = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
		tableInside.addCell("")
				.addCell(headerCell("Base"))
				.addCell(headerCell("Cuota"));

		tableInside.addCell("IGIC deducible en operaciones interiores corrientes")
				.addCell(content.get("egressWithoutTaxes").toString())
				.addCell(content.get("egressTaxes").toString());
		cellWithColspan = new Cell(1, 2).add(new Paragraph("Total cuotas deducibles"));
		tableInside.addCell(cellWithColspan)
				.addCell(content.get("egressTaxes").toString());
		cellWithColspan = new Cell(1, 2).add(new Paragraph("Diferencia"));
		tableInside.addCell(cellWithColspan)
				.addCell(content.get("totalTaxesDue").toString());
		cellWithColspan = new Cell(1, 2).add(new Paragraph("Resultado de la autoliquidación"));
		tableInside.addCell(cellWithColspan)
				.addCell(content.get("totalTaxesDue").toString());

		tableTop.addCell(tableInside);

		document.add(tableTop);

		// Document must be closed
		document.close();
	}

	private Cell topHeaderCell(String textToAdd){
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setBackgroundColor(new DeviceRgb(20, 225, 225));
		cell.setBold();
		return cell;
	}
	private Cell nextTopHeaderCell(String textToAdd){
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setBackgroundColor(new DeviceRgb(159, 163, 163));
		cell.setFontColor(new DeviceRgb(255, 255, 255));
		cell.setBold();
		return cell;
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

	private List createList(){
		return new List()
				.setSymbolIndent(12)
				.setListSymbol("\u2022")
				.setFont(timesRoman);
}
}
