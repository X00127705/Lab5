package controllers;

import models.shopping.ShopOrder;
import models.users.Customer;
import models.users.User;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

// Import models
// Import security controllers



public class ShoppingCtrl extends Controller {
    
    // Get a user - if logged in email will be set in the session
	private Customer getCurrentUser() {
		return (Customer)User.getLoggedIn(session().get("email"));
	}


    @Scurity.Authenticated(Secured.class)

    @With(CheckIfCustomer.class)

    @Transactional
    public Result addToBasket(Long id){
        Product p = Product.find.byId(Id);

        Customer customer = (Customer)User.getLoggedIn(session().get("email"));

        if(customerr.getBasket()==null){
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }

        customer.getBasket().addProduct(p);
        customer.update();

        return ok(basket.render(customer));
    }

    @Transactional
    public Result showBasket(){
        return ok(basket.render(getCurrentUser()));
    }

    @Transactional
    public Result addOne(Long itemId){
        OrderItem item = OrderItem.find.byId(itemId);
        item.increaseQty();
        item.update();
        reurn redirect(routes.ShoppingCrtl.showBasket());
    }

    @Transactional
    public Result removeOne(Long itemId){
        OrderItem = OrderItem.find.byId(itemId);
        Customer c = getCurrentUser();
        c.getBasket().removeItem(item);
        c.getBasket().update();
        return ok(basket.render(c));
    }

    // Empty Basket
    @Transactional
    public Result emptyBasket() {
        
        Customer c = getCurrentUser();
        c.getBasket().removeAllItems();
        c.getBasket().update();
        
        return ok(basket.render(c));
    }


    
    // View an individual order
    @Transactional
    public Result viewOrder(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(orderConfirmed.render(getCurrentUser(), order));
    }

}