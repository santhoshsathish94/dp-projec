/**
 * 
 */
package com.salesmanager.web.shop.controller;

/**
 * Interface contain constant for Controller.These constant will be used throughout
 * sm-shop to  providing constant values to various Controllers being used in the
 * application.
 * @author Umesh A
 *
 */
public interface ControllerConstants
{

    interface Tiles{
        interface ShoppingCart{
            final static String shoppingCart="maincart";
        }
        
        interface Category{
            final static String category="category";
        }
        
        interface Product{
            final static String product="product";
        }
        
        interface Customer{
            final static String customer="customer";
            final static String customerLogon="customerLogon";
            final static String review="review";
            final static String register="register";
            final static String registerConfirmation="registerConfirmation";
        }
        
        interface Content{
            final static String content="content";
        }
        
        interface Pages{
            final static String notFound="404";
        }
        
        interface Merchant{
            final static String contactUs="contactus";
        }
        
        interface Checkout{
            final static String checkout="checkout";
        }
        
        interface Search{
            final static String search="search";
        }
        

        
    }
}
