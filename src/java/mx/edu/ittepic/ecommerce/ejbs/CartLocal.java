/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import javax.ejb.Remote;

/**
 *
 * @author israel
 */
@Remote
public interface CartLocal {
    public String addProduct(String image, String code, String name, String qty, String unitprice);
    public String removeProduct(String title);
    public void remove();
    public void initialize();
}
