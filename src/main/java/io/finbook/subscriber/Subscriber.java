package io.finbook.subscriber;

import io.finbook.datahub.DatamartTerminal;
import io.finbook.datahub.events.ProcessedInvoice;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.util.Utils;
import io.intino.alexandria.terminal.JmsConnector;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Subscriber {

	private static final String TERMINALS_PATH = "src/datamart/terminals/";
	private static InvoiceService invoiceService;

	public static void init() {
		invoiceService = new InvoiceService();

		JmsConnector connector = new JmsConnector(
				"failover:(tcp://localhost:63001)", //BrokerURL
				"io.finbook.datamart",
				"io.finbook.datamart",
				"reporting.finbook-datamart",
				new File(TERMINALS_PATH)
		);
		connector.start();
		DatamartTerminal dt = new DatamartTerminal(connector);
		dt.subscribe(Subscriber::processedInvoice,"reporting.finbook");
	}

	private static void processedInvoice(ProcessedInvoice e){
		// Parse Instant to LocalDateTime
		LocalDateTime date = LocalDateTime.ofInstant(e.date(), ZoneOffset.UTC);

		// Encode content file to Base64
		String xmlFileEncode = Utils.encodeStringToBase64(e.xml());

		Invoice invoice = new Invoice(e.uUID(), date, e.pC(), e.type(), e.use(),
				e.issuerName(), e.issuerRFC(), e.receiverName(), e.receiverRFC(), e.concept(), e.subtotal(),
				e.taxRate(), e.total(), e.currency(), xmlFileEncode
		);

		invoiceService.add(invoice);
	}
}
