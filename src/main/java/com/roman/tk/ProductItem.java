package com.roman.tk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductItem {
   String code;
   String label;

   String url;
   String brandName;

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getBrandName() {
      return brandName;
   }

   public void setBrandName(String brandName) {
      this.brandName = brandName;
   }

   @Override
   public String toString() {
      return "ProductItem{" +
              "code='" + code + '\'' +
              ", label='" + label + '\'' +
              ", url='" + url + '\'' +
              ", brandName='" + brandName + '\'' +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ProductItem that = (ProductItem) o;
      return code.equals(that.code) &&
              label.equals(that.label) &&
              url.equals(that.url) &&
              brandName.equals(that.brandName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(code, label, url, brandName);
   }
}
