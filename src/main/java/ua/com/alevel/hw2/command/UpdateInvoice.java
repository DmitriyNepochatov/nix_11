package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateInvoice implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        boolean check = false;

        System.out.println();
        while (!check) {
            try {
                System.out.print("Enter id >> ");
                String id = BUFFERED_READER.readLine();
                if (id.isEmpty()) {
                    throw new RuntimeException();
                }

                System.out.print("Enter date (format: yyyy-MM-dd) >> ");
                String date = BUFFERED_READER.readLine();
                System.out.println();
                if (date.isEmpty()) {
                    throw new RuntimeException();
                }
                System.out.print("Enter time (format: HH:mm:ss) >> ");
                String time = BUFFERED_READER.readLine();
                System.out.println();
                if (time.isEmpty()) {
                    throw new RuntimeException();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date dateClass = formatter.parse(date + "T" + time + "Z");

                if (INVOICE_SERVICE.findById(id).isPresent()) {
                    INVOICE_SERVICE.update(id, dateClass);
                    check = true;
                }
                else {
                    System.out.println("id not found");
                }
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }
}
