package io.finbook.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class VATReturnsCanary425 implements PDFGenerate {

	PdfFont timesRoman;
	PdfFont helveticaBold;

	public VATReturnsCanary425() throws IOException {
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

		Paragraph p = new Paragraph("IGIC (Mod. 425)")
				.setTextAlignment(TextAlignment.CENTER).setFont(helveticaBold).setFontSize(18).setUnderline();
		document.add(p);

		// Section 1
		Table tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("1. - Ejercicio").setFont(timesRoman));
		Table tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("Ejercicio").addCell(content.get("year").toString())
				.addCell("Periodo").addCell(content.get("period").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 2
		tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("2. - Datos identificativos").setFont(timesRoman));
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("N.I.F.").addCell(content.get("currentUserId").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 4
		tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("4. - Datos del representante y firma de la declaración").setFont(timesRoman))
				.addCell(nextTopHeaderCell("PERSONAS FÍSICAS y COMUNIDADES DE BIENES (REPRESENTANTE)"));
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("Fecha").addCell(content.get("signDate").toString());
		tableTop.addCell(tableInside);
		document.add(tableTop);

		// Section 5
		tableTop = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
		tableTop.addCell(topHeaderCell("5. - Operaciones realizadas en régimen general").setFont(timesRoman))
				.addCell(nextTopHeaderCell("BASE IMPONIBLE, TIPOS y CUOTAS"));
		tableInside = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
		tableInside.addCell("")
				.addCell(headerCell("Base imponible"))
				.addCell(headerCell("Tipo %"))
				.addCell(headerCell("Cuota"));

		tableInside.addCell("Régimen ordinario")
				.addCell(content.get("incomesWithoutTaxes").toString())
				.addCell(content.get("taxRate").toString())
				.addCell(content.get("incomesTaxes").toString());
		Cell cellWithColspan = new Cell(1, 3).add(new Paragraph("Total cuotas devengadas"));
		tableInside.addCell(cellWithColspan)
				.addCell(content.get("incomesTaxes").toString());

		tableTop.addCell(tableInside);

		tableTop.addCell(nextTopHeaderCell("DEDUCCIONES"));
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

		tableTop.addCell(tableInside);

		tableTop.addCell(nextTopHeaderCell("RESULTADO DE LAS AUTOLIQUIDACIONES"));
		tableInside = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		tableInside.addCell("Resultado régimen general")
				.addCell(content.get("totalTaxesDue").toString());

		tableTop.addCell(tableInside);

		document.add(tableTop);

		// Document must be closed
		document.close();
	}

	private Cell topHeaderCell(String textToAdd) {
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setBackgroundColor(new DeviceRgb(20, 225, 225));
		cell.setBold();
		return cell;
	}

	private Cell nextTopHeaderCell(String textToAdd) {
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setBackgroundColor(new DeviceRgb(159, 163, 163));
		cell.setFontColor(new DeviceRgb(255, 255, 255));
		cell.setBold();
		return cell;
	}

	private Cell headerCell(String textToAdd) {
		Cell cell = new Cell(1, 0).add(new Paragraph(textToAdd));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setBold();
		return cell;
	}
}
