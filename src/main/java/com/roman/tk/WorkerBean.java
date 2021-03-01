package com.roman.tk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     returns true is there are any items
     from the supplied list of  brands in the list of product Items
     */
    //public boolean areInterestingItems(List<ProductItem> productItems, List<String> brands) {

    //    long howMany = productItems.stream().filter(x -> brands.contains(x.getBrandName())).count();
        //long howMany = productItems.size();
  //      log.debug("Found " + howMany + "branded items");
   //     return howMany > 0;
   // }
    public List<ProductItem> brandedItems(List<ProductItem> productItems, List<String> brands)
    {
       return productItems.stream().filter(x -> brands.contains(x.getBrandName())).collect(Collectors.toList());
    }

    public String getCLeanJSON (String productListJSON) {
        String productListCleanedUp = romanStringUtils.extractItems(productListJSON);
        //Convert quotes to "
        String removedQuotes = productListCleanedUp.replaceAll("&quot;", "\"");
        //Wrap with [] to make valid JSON
        String json = "[" + removedQuotes + "]";
        return json;
    }

  /*
  1. received new items
  2. filter out only branded items
  3. Add items to storage
  4. if any new items were added to storage then alert
 */
    public synchronized void checkForNewArrivals() {
        List<String> brands = loadResourceConfig.getBrands();
        String xml = tkClient.fetchRawData();
        //
        StringBuilder response =  new StringBuilder("This is response from chekForNewArrivals");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
        response.append("Brands that we are interested in are:");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
        brands.stream().forEach(i->{response.append(i);
        response.append("  ");});
        response.append("<-");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
        response.append("Received XML-->");
        response.append(xml.substring(0,20));
        response.append(System.getProperty("line.separator"));
        response.append("</br>");

        if (xml != null) {
            String productListJSON = romanStringUtils.extractJspResponse(xml);
            String json = getCLeanJSON(productListJSON);

            List<ProductItem> productItems = convertJsonToObject.jsonToObject(json);
            response.append("received in total ->");
            response.append(productItems.size());
            response.append("items") ;
        response.append(System.getProperty("line.separator"));
            response.append("</br>");

            List<ProductItem> brandedItems = brandedItems(productItems, brands);
            response.append("Received Branded items-->");

            brandedItems.stream().forEach(i -> {log.info(i.toString()); response.append(i); });
            log.info("--------" + brandedItems.size());
            response.append(brandedItems.size());

            //handle the case of the first run
            if (itemsCounter.getCounter() == 0) {
                persistLayer.addProducts(brandedItems);
                log.debug("First case handled");
                itemsCounter.increment();
            } else if (persistLayer.addProducts(brandedItems))
                emailService.sendSimpleMessage();
        }




        //          log.debug("Managed to extract " + productItems.size());
       //          log.debug("These are brands->");
                // productItems.stream().forEach(p -> System.out.println(p.getBrandName()));
                 //first N items in the list are new
                // List<ProductItem> newProductItems = productItems.subList(0, numberOfNewItems(howManyNewItemsArrived));


      //           log.debug("I am looking at the first " + newProductItems.size() + " items");
        //         log.debug("They are " + newProductItems + " <--");

                 //filter items of the Interested Brands
                 //  List<String> listOfInterestingBrands = loadResourceConfig.getBrands();
                 //  log.debug("I am interested in the following brands" );
                 //  listOfInterestingBrands.stream().forEach(x->log.debug(x));
                 //now I need to find if the items that arrived are of the brands that I am interested in
                 // Set<ProductItem> interestingItems = newProductItems.stream().filter( x -> listOfInterestingBrands.contains(x.getBrandName())).collect(Collectors.toSet());
                 // log.debug("This is a set of items that just arrived");
                 // interestingItems.stream().forEach(x->log.debug(x.toString()));
                // if (areInterestingItems(newProductItems, brands)) {

                     // if (interestingItems.size()> 0)
                     //      {
                     //        log.debug("this is all products that are currenntly in persistance->");
                     //          persistLayer.getProducts().stream().forEach(x->log.debug(x.toString()));
                     //         if (! CollectionUtils.containsAll(persistLayer.getProducts(),interestingItems))
                     //         {
                  //     log.debug("Sending email");
                    //     emailService.sendSimpleMessage();
                // }
                 //save items
                 //          log.info("Items added to the collection");
                 //          log.debug("Items added to collection here is a list->");
                 //          interestingItems.stream().forEach(p -> log.debug(p.toString()));
                 //          persistLayer.addProducts(interestingItems);
                 //       }


          // }



    }
    public String monitor(){
        List<String> brands = loadResourceConfig.getBrands();

        StringBuilder response =  new StringBuilder("!This is response from chekForNewArrivals");
        response.append(System.getProperty("line.separator"));
         response.append("</br>");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
        response.append("Brands that we are interested in are:");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
        brands.stream().forEach(i->{response.append(i);
            response.append("  ");});
        response.append("<-");
        response.append(System.getProperty("line.separator"));
        response.append("</br>");
       try {
     //      String xml = tkClient.fetchRawData();
     //      response.append("xML->");
      //     response.append(xml);
           response.append(("<-"));
           response.append(System.getProperty("line.separator"));

           response.append("</br>");
       }
       catch (Exception e)
       {
           response.append("exception happned");
           response.append(e);
       }

        return  response.toString();

}

}
/*
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
 */