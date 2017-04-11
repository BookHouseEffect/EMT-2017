package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.DeliveryPackage;

/**
 * @author CekovskiMrGjorche
 */
public interface InvoiceServerHelper {
    void markInvoiceAsExpired(
            Long invoiceId
    );

    DeliveryPackage markInvoiceAsPayed(
            Long invoiceId
    );


}
