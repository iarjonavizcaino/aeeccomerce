/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import mx.edu.ittepic.ecommerce.entities.CartView;

/**
 *
 * @author israel
 */
@Stateful
@Remote(CartLocal.class)
@EJB(name="ejb/Cart",beanInterface=CartLocal.class,beanName="Cart")
public class Cart implements CartLocal {

    String person;
    List<CartView> cart;
    
    @Override
    public String addProduct(String image, String code, String name, String qty, String unitprice) {
        int i;
        boolean isnew = true;
        for(i=0; i<cart.size(); i++) {
            CartView cv1 = cart.get(i);
            if(code.equals(cv1.getCode())) {
                int newqty = cv1.getQty()+Integer.parseInt(qty);
                cart.get(i).setQty(newqty);
                isnew = false;
                break;
            }
        }
        
        if(isnew) {
            CartView cv = new CartView();
            cv.setImage(image);
            cv.setCode(code);
            cv.setProductName(name);
            cv.setQty(Integer.parseInt(qty));
            cv.setUnitprice(Double.parseDouble(unitprice));
            cart.add(cv);
        }
        
        return new GsonBuilder().create().toJson(cart);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public String removeProduct(String title) {
        if(cart.remove(title))
            return new GsonBuilder().create().toJson(cart);
        else
            return "NO";
    }

    @Remove
    @Override
    public void remove() {
        cart=null;
        person=null;
    }

    @Override
    @PostConstruct
    public void initialize() {
        cart = new ArrayList<>();
        //this.person = person;
    }
    
}
