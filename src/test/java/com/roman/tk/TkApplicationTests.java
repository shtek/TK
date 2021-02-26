package com.roman.tk;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class TkApplicationTests {
	@Autowired
	LoadResourceConfig loadResourceConfig;
	@Test
	void contextLoads() {
	}
@Test
	void subsetOfItems(){
	List<String> listOfInterestingBrands = loadResourceConfig.getBrands();
	//Assert.isTrue(listOfInterestingBrands.size() ==2);
	ProductItem item = new ProductItem();
	item.setCode("1");
	item.setBrandName("a");
	item.setLabel("a");
	item.setUrl("a");
	ProductItem item2 = new ProductItem();
	item2.setCode("2");
	item2.setBrandName("a");
	item2.setLabel("a");
	item2.setUrl("a");
	ProductItem item3 = new ProductItem();
	item3.setCode("3");
	item3.setBrandName("a");
	item3.setLabel("a");
	item3.setUrl("a");


	Set<ProductItem> storedItems= new HashSet<>();
	storedItems.add(item);

	Set<ProductItem> arrivedItems = new HashSet<>();
	arrivedItems.add(item);
	Assertions.assertTrue(CollectionUtils.containsAll(storedItems,arrivedItems));
	arrivedItems.add(item2);
	Assertions.assertFalse(CollectionUtils.containsAll(storedItems,arrivedItems));

}

	//Set<ProductItem> interestingItems = productItems.stream().filter(x -> listOfInterestingBrands.contains(x.getBrandName())).collect(Collectors.toSet());
    //        if (interestingItems.size()> 0)
//	{
//		if (! CollectionUtils.containsAll(persistLayer.getProducts(),interestingItems))
//		{


	}
