package com.example.hugh.interesting.DesignPattern.BuilderPattern;

import java.util.ArrayList;

/**
 * Created by Hugh on 2019/7/4.
 */
public class ShoppingCart {

    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public void clearCart(){
        products.clear();
    }

    public void settleAccounts(){
        for (Product product:products) {
            System.out.println("----->"+product.getName()+":"+product.getPrice()+"--"+product.packing().pack());
        }
    }

    public static void main(String[] args){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(new Milk());
        shoppingCart.addProduct(new Bread());
        shoppingCart.settleAccounts();
    }
}
