/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.edu.ittepic.ecommerce.entities.Category;
import mx.edu.ittepic.ecommerce.entities.Product;
import mx.edu.ittepic.ecommerce.entities.Role;
import mx.edu.ittepic.ejerciciosu2.utils.Message;

/**
 *
 * @author israel
 */
@Stateless
public class EJBEcommerce {

    @PersistenceContext
    private EntityManager entity;

    public String getRoles() {
        List<Role> listRoles;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Query q = entity.
                createNamedQuery("Role.findAll");
        //q.getResultList().size();
        listRoles = q.getResultList();

        /*String roles = "[";
        for (Role listRole : listRoles) {
            roles=roles+"{\"roleid\":"+listRole.getRoleid()+", \"rolename\":\""+listRole.getRolename()+"\"},";
        }
        
        roles = roles.substring(0, roles.length()-1);
        
        roles = roles + "]";*/
        
        for(int i=0; i <listRoles.size(); i++)
            listRoles.get(i).setUsersList(null);
        
        m.setCode(200);
        //m.setMsg(roles);
        m.setMsg(gson.toJson(listRoles));
        m.setDetail("OK");

        return gson.toJson(m);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public String updateRole(String roleid, String name) {
        Message m = new Message();
        /*Creación del objeto rol*/
        Role r; 
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        r = entity.find(Role.class, Integer.parseInt(roleid));
        
        if(r==null) {
            m.setCode(404);
            m.setMsg("No se encontró el elemento");
            m.setDetail("");
        } else {
            r.setRolename(name);
            entity.merge(r);
            
            m.setCode(200);
            m.setMsg("Todo bien");
            m.setDetail("OK");
        }
        /*Query q = entity.
                createNamedQuery("Role.updateRole").
                setParameter("name", name).
                setParameter("id", Integer.parseInt(roleid));*/
        
        /*Query q = entity.
                createNativeQuery("UPDATE role set rolename='"+name+"' WHERE roleid="+roleid);
        
        if(q.executeUpdate() == 1) {
            m.setCode(200);
            m.setMsg("Todo bien");
            m.setDetail("OK");
        } else {
            m.setCode(404);
            m.setMsg("No se encontró");
            m.setDetail("");
        }*/
        
        /*Query q = entity.
                createNamedQuery("Role.updateRole").
                setParameter("name", name).
                setParameter("id", Integer.parseInt(roleid));*/
        /*Query q = entity.createNativeQuery("UPDATE Role SET rolename='"+name+"' WHERE roleid="+roleid);
        
        if(q.executeUpdate()==1) {
            m.setCode(200);
            m.setMsg("Se actualizó correctamente");
            m.setDetail("OK");
        } else {
            m.setCode(200);
            m.setMsg("No se realizó la actualización");
            m.setDetail("");
        }*/
            
        
        
        /*try {

            r = entity.find(Role.class, 
                    Integer.parseInt(roleid));
            if (r == null) {
                m.setCode(406);
                m.setMsg("Error de tipo de dato '" + roleid + "'");
                m.setDetail("No se encontró el objeto");
            } else {

                //r.setRoleid(Integer.parseInt(roleid));
                r.setRolename(name);

                entity.merge(r);
                m.setCode(200);
                m.setMsg("El rol se actualizó correctamente");
                m.setDetail(r.getRoleid().toString());
            }

            //entity.flush();
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error de tipo de dato '" + roleid + "'");
            m.setDetail(e.toString());
        }*/
        return gson.toJson(m);
    }

    public String getRole(String roleid) {

        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Role role;

        /*Query q
                = entity.
                createNamedQuery("Role.findByRoleid").
                setParameter("roleid", Integer.parseInt(roleid));*/

        //role = (Role) q.getSingleResult();
        
        Query q = entity.createNativeQuery("Select roleid, rolename from role where roleid="+roleid,Role.class);
        
        role = (Role) q.getSingleResult();
        
        role.setUsersList(null);

        m.setCode(200);
        m.setMsg(gson.toJson(role));
        m.setDetail("OK");

        return gson.toJson(m);
    }

    public String deleteRole(String roleid) {
        Role role;

        Message m = new Message();
        /*Creación del objeto rol*/
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        //Query q = entity.createNamedQuery("Role.findByRoleid").setParameter("roleid", Integer.parseInt(roleid));
        //role = q.getSingleResult();
        role = entity.find(Role.class, Integer.parseInt(roleid));
        entity.remove(role);

        m.setCode(200);
        m.setMsg("Todo bien");
        m.setDetail("OK");

        return gson.toJson(m);
    }

    public String newRole(String name) {
        Message m = new Message();
        /*Creación del objeto rol*/
        Role r = new Role();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            r.setRolename(name);

            /*Actualiza la BD*/
            entity.persist(r);
            entity.flush();

            m.setCode(200);
            m.setMsg("El rol se actualizó correctamente");
            m.setDetail(r.getRoleid().toString());
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error de tipo de dato");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    /*public String newProduct(String code, String productname, String brand, String purchprice, String stock, String salepricemin, String reorderpoint, String currency,String salepricemay, String categoryid) {
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        Category cat =  entity.find(Category.class, Integer.parseInt(categoryid));
        
        Product p = new Product();
        p.setCode(code);
        p.setCurrency(currency);
        p.setProductname(productname);
        p.setBrand(brand);
        p.setCategoryid(cat);
        p.setPurchprice(Double.parseDouble(purchprice));
        p.setReorderpoint(Integer.parseInt(reorderpoint));
        p.setSalepricemay(Double.parseDouble(salepricemay));
        p.setSalepricemin(Double.parseDouble(salepricemin));
        p.setStock(Integer.parseInt(stock));
        
        entity.persist(p);
        entity.flush();
        
        m.setCode(200);
        m.setMsg("Se insertó correctamente");
        m.setDetail(p.getProductid().toString());
        
        return gson.toJson(m);
    }*/
    
}
