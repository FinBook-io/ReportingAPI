package io.finbook.pdf;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDFCommand {

	public PDFCommand() {

	}

	public static ResponseCreator init() throws IOException {
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
	}

}
