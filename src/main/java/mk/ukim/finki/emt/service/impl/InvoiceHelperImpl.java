package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.DeliveryStatus;
import mk.ukim.finki.emt.model.enums.InvoiceStatus;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import mk.ukim.finki.emt.model.jpa.Invoice;
import mk.ukim.finki.emt.persistence.DeliveryPackageRepository;
import mk.ukim.finki.emt.persistence.InvoiceRepository;
import mk.ukim.finki.emt.service.InvoiceServerHelper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CekovskiMrGjorche
 */
@Service
public class InvoiceHelperImpl implements InvoiceServerHelper {

    private InvoiceRepository invoiceRepository;
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    public InvoiceHelperImpl(
            InvoiceRepository invoiceRepository,
            DeliveryPackageRepository deliveryPackageRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public void markInvoiceAsExpired(Long invoiceId) {
        Invoice invoice = getInvoice(invoiceId);
        invoice.status = InvoiceStatus.EXPIRED;
        invoiceRepository.save(invoice);
    }

    @Override
    public DeliveryPackage markInvoiceAsPayed(Long invoiceId) {
        Invoice invoice = getInvoice(invoiceId);
        invoice.status = InvoiceStatus.PAYED;
        invoiceRepository.save(invoice);

        DeliveryPackage deliveryPackage = new DeliveryPackage();
        deliveryPackage.checkout = invoice.checkout;
        deliveryPackage.status = DeliveryStatus.PENDING_PACKAGE_CREATION;
        return deliveryPackageRepository.save(deliveryPackage);
    }

    private Invoice getInvoice(Long invoiceId){
        Invoice invoice = invoiceRepository.findOne(invoiceId);
        if (invoice == null)
            throw new ObjectNotFoundException(invoiceId, "Invoice");
        return invoice;
    }
}
