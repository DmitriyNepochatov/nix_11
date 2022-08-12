package ua.com.alevel.command;

import ua.com.alevel.service.analyticalservice.AnalyticalService;

public class MakeAllAnalyticalData implements Command {
    private static final AnalyticalService analyticalservice = AnalyticalService.getInstance();

    @Override
    public void execute() {
        System.out.println("\nAnalytical data:");
        System.out.println("--------------------------------");
        analyticalservice.numberOfItemsSoldByCategory();
        System.out.println("--------------------------------");
        analyticalservice.sumTheLowestInvoice();
        System.out.println("--------------------------------");
        analyticalservice.sumOfAllInvoices();
        System.out.println("--------------------------------");
        analyticalservice.retailInvoicesCount();
        System.out.println("--------------------------------");
        analyticalservice.invoicesContainsOnlyOneProductType();
        System.out.println("--------------------------------");
        analyticalservice.printFirstThreeInvoices();
        System.out.println("--------------------------------");
        analyticalservice.printInvoicesWhichWereMadeMinorsCustomers();
        System.out.println("--------------------------------");
        analyticalservice.sortAllInvoices();
        System.out.println("--------------------------------");
    }
}
