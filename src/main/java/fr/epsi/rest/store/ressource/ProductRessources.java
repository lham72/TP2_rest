package fr.epsi.rest.store.ressource;

import fr.epsi.rest.store.db.ProductDB;
import fr.epsi.rest.store.model.Produit;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;


@Path("/")
public class ProductRessources {
    private List<Produit> myDB = new ProductDB().getInstance().getDB(); // our database
    Produit myProd; // using to contains product object in our operations


    //**************** http://localhost:<port>/api/products/ ******************************//

    // Get all products from database
    @GET
    @Path("products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produit> getAllProducts() {
        return myDB;
    }

    // Insert in DB all products send in Json format
    @POST
    @Path("products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produit> insertProducts(List<Produit> products) {

        products.stream().forEach(produit -> {
            int size = myDB.size() + 1;
            produit.setId(size);
            myDB.add(produit);
        });
        return products;
    }



    //**************** http://localhost:<port>/api/product/{id} ******************************//

    // Get one product identified by id
    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produit getById(@PathParam("id") Integer id) {
        return myDB.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

    }

    // Modify one product identified by is id
    @PUT
    @Path("product/{id}")
    @Consumes("*/*")
    @Produces(MediaType.APPLICATION_JSON)
    public Produit addProduct(@PathParam("id") Integer id,
                              @FormParam("name") String name,
                              @FormParam("info") String info,
                              @FormParam("detail") String detail,
                              @FormParam("price") Integer price,
                              @FormParam("quantity") Integer quantity,
                              @FormParam("image") String urlImage)
    {

        Produit prod = myDB.stream().filter(p -> p.getId() == id).findFirst()
                .map(produit -> {
                    produit.setName(name);
                    produit.setDetail(detail);
                    produit.setPrice(price);
                    produit.setQuantity(quantity);
                    produit.setInfo(info);
                    produit.setImage(urlImage);
                    return produit;
                }).get();

        return prod;
    }

    // Delete a product
    @DELETE
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produit delProduct(@PathParam("id") Integer id) {
        myProd = myDB.stream().filter(produit -> produit.getId() == id).findFirst().get();
        myDB.remove(myProd);

        return myProd;
    }


    //**************** http://localhost:<port>/api/product/search/{string to search} ***********************//

    // Must find one or several products which contains a string
    @GET
    @Path("product/search/{searchedStr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produit> findByName(@PathParam("searchedStr") String searchedStr) {

       List<Produit> myProds = myDB.stream().filter(p -> p.getName().contains(searchedStr)).collect(Collectors.toList());

        return myProds;
    }


    //**************** http://localhost:<port>/api/product/buy/{id}/{quantity} ******************************//

    @POST
    @Path("product/buy/{id}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produit buyProduct(@PathParam("id") Integer id,
                              @PathParam("quantity") Integer quantity) {
       Produit prod = myDB.stream().filter(p -> p.getId() == id).findFirst()
               .map(produit -> {
           produit.setQuantity(quantity);
           return produit;
       }).get();

       // in error case
       return prod;
    }

}
