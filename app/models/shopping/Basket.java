package models.shopping;

import com.avaje.ebean.Model;
import models.users.Customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;


// Product entity managed by Ebean
@Entity
public class Basket extends Model {

    @Id
    private Long id;
    
    @OneToMany(mappedBy = "basket", cascade = CascadeType.PERSIST)
    private List<OrderItem> basketItems;
    
    @OneToOne
    private Customer customer;

    // Default constructor
    public  Basket() {
    }

    public void addProduct(Product p){
        boolean itemFound = false;
        for(OrderItem i : basketItems)
        {
            if(i.getProduct().getId() == p.getId()){
                i.increaseQty();
                itemFound = true;
                break;
            }
        }
        if(itemFound == false){
            oderItem newItem = new OrderItem(p);
            basketItems.add(newItem);
        }
    }

    

    public void removeAllItems() {
        for(OrderItem i: this.basketItems) {
            i.delete();
        }
        this.basketItems = null;
    }

    public double getBasketTotal() {
        
        double total = 0;
        
        for (OrderItem i: basketItems) {
            total += i.getItemTotal();
        }
        return total;
    }
	
	//Generic query helper
    public static Finder<Long,Basket> find = new Finder<Long,Basket>(Basket.class);

    //Find all Products in the database
    public static List<Basket> findAll() {
        return Basket.find.all();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<OrderItem> basketItems) {
        this.basketItems = basketItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

