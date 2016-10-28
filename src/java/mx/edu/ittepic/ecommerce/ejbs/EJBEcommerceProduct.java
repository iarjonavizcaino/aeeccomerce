/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.edu.ittepic.ecommerce.entities.Category;
import mx.edu.ittepic.ecommerce.entities.Product;
import mx.edu.ittepic.ejerciciosu2.utils.Message;

/**
 *
 * @author israel
 */
@Stateless
public class EJBEcommerceProduct {
    @PersistenceContext
    private EntityManager entity;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public String newProduct(String code, String brand, String purchprice, String productname, String stock, String salepricemin, String salepricemay, String reorderpoint, String categoryid, String currency){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        Category cat = entity.find(Category.class, Integer.parseInt(categoryid));
        
        Product p = new Product();
        
        p.setBrand(brand);
        p.setCategoryid(cat);
        p.setCode(code);
        p.setCurrency(currency);
        p.setProductname(productname);
        p.setPurchprice(Double.parseDouble(purchprice));
        p.setReorderpoint(Integer.parseInt(reorderpoint));
        p.setSalepricemay(Double.parseDouble(salepricemay));
        p.setStock(Integer.parseInt(stock));
        p.setSalepricemin(Double.parseDouble(salepricemin));
        
        entity.persist(p);
        entity.flush();
        
        m.setCode(200);
        m.setMsg("Todo bien");
        m.setDetail(p.getProductid().toString());
        
        return gson.toJson(m);
    }
}
