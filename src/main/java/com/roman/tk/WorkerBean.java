package com.roman.tk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerBean {
    private static final int ITEMS_PER_PAGE = 72;
    private static final Logger log = LoggerFactory.getLogger(WorkerBean.class);
    @Autowired
    TkClientWebClient tkClient;
    @Autowired
    RomanStringUtils romanStringUtils;
    @Autowired
    ItemsCounter itemsCounter;
    @Autowired
    ConvertJsonToObject convertJsonToObject;
    @Autowired
    PersistLayer persistLayer;
    @Autowired
    LoadResourceConfig loadResourceConfig;
    @Autowired
    EmailService emailService;

    public int numberOfNewItems(int newItems) {
        if (newItems > ITEMS_PER_PAGE)
            return ITEMS_PER_PAGE;
        else
            return newItems;
    }


    /*
     returns true is there are any items of this brand in the list of product Items
     */
    public boolean areInterestingItems(List<ProductItem> productItems, List<String> brands) {

        long howMany = productItems.stream().filter(x -> brands.contains(x.getBrandName())).count();
        //long howMany = productItems.size();
        log.debug("Found " + howMany + "branded items");
        return howMany > 0;


    }

    public void checkForNewArrivals() {
        List<String> brands = loadResourceConfig.getBrands();
        String productList = romanStringUtils.extractJspResponse(tkClient.fetchRawData());
        //get the number of Items
        //ITEMS  currently
        int currentlyNewItems = romanStringUtils.totalNumberOfResult(productList);
        if (currentlyNewItems != itemsCounter.getCounter())

             log.debug("new counter " + currentlyNewItems + "old " + itemsCounter.getCounter());

             //only do work if the cuounter increased and no the initial run which is 0
             if ((currentlyNewItems > itemsCounter.getCounter()) && (itemsCounter.getCounter()!=0)) {
                 int howManyNewItemsArrived = currentlyNewItems - itemsCounter.getCounter();

                 //only do work if the counter is increased from the last check
                 log.debug("New items arrived ->" + howManyNewItemsArrived);
                 String productListCleanedUp = romanStringUtils.extractItems(productList);
                 //Convert quotes to "
                 String removedQuotes = productListCleanedUp.replaceAll("&quot;", "\"");
                 //Wrap with [] to make valid JSON
                 String json = "[" + removedQuotes + "]";
                 List<ProductItem> productItems = convertJsonToObject.jsonToObject(json);
                 log.debug("Managed to extract " + productItems.size());
                 log.debug("These are brands->");
                 productItems.stream().forEach(p -> System.out.println(p.getBrandName()));
                 //first N items in the list are new
                 List<ProductItem> newProductItems = productItems.subList(0, numberOfNewItems(howManyNewItemsArrived));
                 log.debug("I am looking at the first " + newProductItems.size() + " items");
                 log.debug("They are " + newProductItems + " <--");

                 //filter items of the Interested Brands
                 //  List<String> listOfInterestingBrands = loadResourceConfig.getBrands();
                 //  log.debug("I am interested in the following brands" );
                 //  listOfInterestingBrands.stream().forEach(x->log.debug(x));
                 //now I need to find if the items that arrived are of the brands that I am interested in
                 // Set<ProductItem> interestingItems = newProductItems.stream().filter( x -> listOfInterestingBrands.contains(x.getBrandName())).collect(Collectors.toSet());
                 // log.debug("This is a set of items that just arrived");
                 // interestingItems.stream().forEach(x->log.debug(x.toString()));
                 if (areInterestingItems(newProductItems, brands)) {

                     // if (interestingItems.size()> 0)
                     //      {
                     //        log.debug("this is all products that are currenntly in persistance->");
                     //          persistLayer.getProducts().stream().forEach(x->log.debug(x.toString()));
                     //         if (! CollectionUtils.containsAll(persistLayer.getProducts(),interestingItems))
                     //         {
                       log.debug("Sending email");
                         emailService.sendSimpleMessage();
                 }
                 //save items
                 //          log.info("Items added to the collection");
                 //          log.debug("Items added to collection here is a list->");
                 //          interestingItems.stream().forEach(p -> log.debug(p.toString()));
                 //          persistLayer.addProducts(interestingItems);
                 //       }


             }

        itemsCounter.setCounter(currentlyNewItems);

    }
}
