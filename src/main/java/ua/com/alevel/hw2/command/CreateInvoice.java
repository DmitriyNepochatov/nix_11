package ua.com.alevel.hw2.command;

import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.service.invoiceservice.InvoiceService;
import ua.com.alevel.hw2.service.services.CargoPlaneService;
import ua.com.alevel.hw2.service.services.FighterService;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreateInvoice implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final FighterService FIGHTER_SERVICE = FighterService.getInstance();
    private static final CargoPlaneService CARGO_PLANE_SERVICE = CargoPlaneService.getInstance();
    private static final PassengerPlaneService PASSENGER_PLANE_SERVICE = PassengerPlaneService.getInstance();
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.println("\nEnter id planes which you want add to invoice:");
        List<Plane> planeList = new ArrayList<>();
        boolean check = false;
        while (!check) {
            try {
                System.out.print("\nEnter plane id >> ");
                String id = BUFFERED_READER.readLine();
                if (id.isEmpty()) {
                    throw new RuntimeException();
                }

                FIGHTER_SERVICE.findById(id).ifPresent(plane -> planeList.add(plane));
                CARGO_PLANE_SERVICE.findById(id).ifPresent(plane -> planeList.add(plane));
                PASSENGER_PLANE_SERVICE.findById(id).ifPresent(plane -> planeList.add(plane));

                System.out.print("Continue ? (y/n) >> ");
                String exit = BUFFERED_READER.readLine();
                if (exit.equals("y")) {
                    continue;
                }
                else if (exit.equals("n")) {
                    INVOICE_SERVICE.save(INVOICE_SERVICE.createInvoice(planeList));
                    check = true;
                }
                else {
                    throw new RuntimeException();
                }
            }
            catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }
}
