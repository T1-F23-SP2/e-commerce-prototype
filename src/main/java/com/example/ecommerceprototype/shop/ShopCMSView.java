//package com.example.ecommerceprototype.shop;
//
//import com.example.ecommerceprototype.oms.DB.StockInterface;
//import com.example.ecommerceprototype.oms.Customers.Customer;
//
//import com.example.ecommerceprototype.cms.CMS;
//import com.example.ecommerceprototype.oms.MockShop.MockShopObject;
//import com.example.ecommerceprototype.oms.OrderStatus.OrderManager;
//import com.example.ecommerceprototype.pim.product_information.ManufacturingInformation;
//import com.example.ecommerceprototype.pim.product_information.PIMDriver;
//
//import com.example.ecommerceprototype.pim.product_information.ProductCategory;
//import com.example.ecommerceprototype.pim.product_information.ProductInformation;
//import com.example.ecommerceprototype.pim.util.FilterableArrayList;
//import com.example.ecommerceprototype.pim.util.ProductList;
//import com.example.ecommerceprototype.shop.pages.ArticlePage;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Objects;
//import java.util.Random;
//
//
//public class ShopCMSView extends Application implements StockInterface {
//
//    private PIMDriver pimDriverInstance;
//    OrderManager orderManager;
//
//    HashMap<ProductInformation, Integer> cart;
//
//    Stage window;
//    public static void main(String[] args) {
//        launch();
//    }
//
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        pimDriverInstance = new PIMDriver();
//        orderManager = new OrderManager();
//        //stockInterfaceInstance = new StockInterface();
//        cart = new HashMap<ProductInformation, Integer>();
//
//        window = stage;
//        loadShopPage();
//
//        stage.setTitle("Arnes ElectroShop!");
//        stage.show();
//
//        stage.setFullScreen(true);
//    }
//
//    public void loadShopPage() throws Exception {
//
//        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//        loadProducts(plate, pimDriverInstance.getAllProducts());
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public ProductInformation findProduct(ProductInformation product) throws Exception {
//        for (ProductInformation each : pimDriverInstance.getAllProducts()) {
//            if (Objects.equals(product.getProductUUID(), each.getProductUUID())) {
//                return each;
//            }
//        }
//        return null;
//    }
//
//    public void loadProducts(Pane plate, ProductList products) throws Exception {
//        Random random = new Random();
//
//        int i = 0;
//        for (ProductInformation product : products) {
//            if (product.getIsHidden()) {
//                continue;
//            }
//
//            Pane view = CMS.getInstance().loadComponent("ProductView");
//
//            ((Label) CMS.getInstance().findNode(view, "productName_Label")).setText(product.getName());
//            if (product.getPriceInformation() == null) {
//                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (findProduct(product).getPriceInformation().getPrice()));
//            } else {
//                ((Label) CMS.getInstance().findNode(view, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
//            }
//            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(String.valueOf(StockInterface.getStockValue("12345")));
//            // ((Label) CMS.getInstance().findNode(view, "productStatus_Label")).setText(StockInterface.getStockValue(products.get(i).getProductUUID()) > 0 ? "In stock" : "Sold out");
//            ((TextArea) CMS.getInstance().findNode(view, "productDescription_TextArea")).setText(product.getShortDescription());
//            Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
//            ((ImageView) CMS.getInstance().findNode(view, "productImage_ImageView")).setImage(productImage);
//            ((Button) CMS.getInstance().findNode(view, "productImage_Button")).setOnAction(actionEvent -> {
//                try {
//                    loadProductPage(product);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//            });
//
//            GridPane.setColumnIndex(view, i % 3);
//            GridPane.setRowIndex(view, (int) Math.floor(i / 3)); // floor(n/3) is the integer sequence for 0, 0, 0, 1, 1, 1, 2, 2, 2... (https://oeis.org/A002264)
//            i += 1;
//
//            CMS.getInstance().loadOnto(plate, view, "contentPlaceholder_GridPane");
//        }
//    }
//
//    public void loadSidebar(Pane plate) throws Exception {
//        //Load sidebar onto template
//        Pane sidebar = CMS.getInstance().loadComponent("CategorySidebar");
//        CMS.getInstance().loadOnto(plate, sidebar, "sidebarPlaceholder_Pane");
//
//        //Set action for article button (on the sidebar)
//        ((Button) CMS.getInstance().findNode(sidebar, "articles_Button")).setOnAction(actionEvent -> {
//            try {loadArticlePage();}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//
//        // Load categories
//        VBox categoryList = (VBox) CMS.getInstance().findNode(sidebar, "categoryList_VBox");
//        VBox allCategoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
//        Button allCategoryButton = (Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button");
//        ((Button) CMS.getInstance().findNode(allCategoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
//            try {loadShopPage();}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//        allCategoryButton.setText("All categories");
//        categoryList.getChildren().add(allCategoryButton);
//
//
//        FilterableArrayList<ProductCategory> allCategories = pimDriverInstance.getAllCategories();
//        for (int i = 0; i < allCategories.size(); i++) {
//            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
//            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
//            int finalI = i;
//            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
//                try {reloadShopPageWithCategory(allCategories.get(finalI).getName());}
//                catch (Exception e) {System.out.println(e.getMessage());}
//            });
//            b.setText("Type: " + allCategories.get(i).getName());
//            categoryList.getChildren().add(b);
//        }
//
//        // filtering by manufacturer
//        FilterableArrayList<ManufacturingInformation> allManufacturers = pimDriverInstance.getAllManufactures();
//        for (int i = 0; i < allManufacturers.size(); i++) {
//            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
//            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
//            int finalI = i;
//            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
//                try {reloadShopPageWithCustomProducts(pimDriverInstance.getProductsByManufactureName(allManufacturers.get(finalI).getName()));}
//                catch (Exception e) {System.out.println(e.getMessage());}
//            });
//            b.setText("Manufacturer: " + allManufacturers.get(i).getName());
//            categoryList.getChildren().add(b);
//        }
//
//        // filtering by price
//        int[] priceRange = {0, 500, 1000, 2000, 5000, 10000};
//        for (int i = 0; i < priceRange.length - 1; i++) {
//            VBox categoryItem = (VBox) CMS.getInstance().loadComponent("CategoryItem");
//            Button b = (Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button");
//            ProductList productOfPrice = new ProductList();
//            for (ProductInformation product : pimDriverInstance.getAllProducts()) {
//                if (product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange[i])) > 0 && product.getPriceInformation().getPrice().compareTo(BigDecimal.valueOf(priceRange[i+1])) < 0) {
//                    productOfPrice.add(product);
//                }
//            }
//            int finalI = i;
//            ((Button) CMS.getInstance().findNode(categoryItem, "categoryItem_Button")).setOnAction(actionEvent -> {
//                try {reloadShopPageWithCustomProducts(productOfPrice);}
//                catch (Exception e) {System.out.println(e.getMessage());}
//            });
//            b.setText("Price: " + priceRange[i] + " - " + priceRange[i+1]);
//            categoryList.getChildren().add(b);
//        }
//    }
//
//    private void reloadShopPageWithCategory(String category) throws Exception {
//        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//        loadProducts(plate, pimDriverInstance.getProductsByCategoryName(category));
//        System.out.println(category);
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    private void reloadShopPageWithCustomProducts(ProductList products) throws Exception {
//        //Load page template (Template 1 has space for a topbanner, a sidebar and content to be arranged in a grid)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate1");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//        loadProducts(plate, products);
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public void search(String searchTerm) throws Exception {
//        ProductList result = new ProductList();
//        if (searchTerm != "") {
//
//            ProductList products = pimDriverInstance.getAllProducts();
//
//            for (int i = 0; i < products.size(); i++) {
//                if (products.get(i).getName().contains(searchTerm)) {
//                    result.add(products.get(i));
//                    continue;
//                }
//                if (products.get(i).getLongDescription().contains(searchTerm)) {
//                    result.add(products.get(i));
//                    continue;
//                }
//                if (products.get(i).getShortDescription().contains(searchTerm)) {
//                    result.add(products.get(i));
//                    continue;
//                }
//                if (products.get(i).getProductCategory().getName() != null)
//                    if (products.get(i).getProductCategory().getName().contains(searchTerm)) {
//                        result.add(products.get(i));
//                        continue;
//                    }
//                if (products.get(i).getProductUUID() == searchTerm) {
//                    result.add(products.get(i));
//                    continue;
//                }
//                if (products.get(i).getSerialNumber() == searchTerm) {
//                    result.add(products.get(i));
//                    continue;
//                }
//                if(products.get(i).getManufacturingInformation().getName() != null)
//                    if (products.get(i).getManufacturingInformation().getName().contains(searchTerm)) {
//                        result.add(products.get(i));
//                    }
//
//            }
//        }
//        reloadShopPageWithCustomProducts(result);
//    }
//
//
//    public void loadTopBanner(Pane plate) throws Exception {
//        //Load top banner onto template and set home button functionality
//        Pane topBanner = CMS.getInstance().loadComponent("TopBanner");
//        ((Button) CMS.getInstance().findNode(topBanner, "home_Button")).setOnAction(actionEvent -> {
//            try {loadShopPage();}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//        ((Button) CMS.getInstance().findNode(topBanner, "search_Button")).setOnAction(actionEvent -> {
//            try {search(((TextField) CMS.getInstance().findNode(topBanner, "search_TextField")).getText());}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//        ((Button) CMS.getInstance().findNode(topBanner, "cart_Button")).setOnAction(actionEvent -> {
//            try {loadCartPage();}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//
//        CMS.getInstance().loadOnto(plate, topBanner, "topBannerPlaceholder_Pane");
//    }
//
//    public void loadArticlePage() throws Exception{
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate2");
//
//        CMS.getInstance().loadOnto(plate, CMS.getInstance().loadComponent("ArticlePage"), "contentPlaceholder_Pane");
//        loadTopBanner(plate);
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public void loadProductPage(ProductInformation product) throws Exception {
//        //Load page template (Template 2 has space for a top banner and some content pane)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//
//        Pane productPage = CMS.getInstance().loadComponent("ProductPage");
//        Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
//        ((ImageView) CMS.getInstance().findNode(productPage, "primaryProductImage_ImageView")).setImage(productImage);
//        ((ImageView) CMS.getInstance().findNode(productPage, "secondaryProductImage_ImageView")).setImage(productImage);
//
//        ((Label) CMS.getInstance().findNode(productPage, "productName_Label")).setText(product.getName());
//        if (product.getPriceInformation() == null) {
//            ((Label) CMS.getInstance().findNode(productPage, "productPrice_Label")).setText("$" + (findProduct(product).getPriceInformation().getPrice()));
//        } else {
//            ((Label) CMS.getInstance().findNode(productPage, "productPrice_Label")).setText("$" + (product.getPriceInformation().getPrice()));
//        }
//        ((TextArea) CMS.getInstance().findNode(productPage, "productDescription_TextArea")).setText(product.getLongDescription());
//        ((TextArea) CMS.getInstance().findNode(productPage, "productSpecification_TextArea")).setText(product.getShortDescription());
//        ((Button) CMS.getInstance().findNode(productPage, "addToCart_Button")).setOnAction(actionEvent -> {
//            try {addToCart(product);}
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//
//        CMS.getInstance().loadOnto(plate, productPage, "contentPlaceholder_Pane");
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public void addToCart(ProductInformation product) {
//        cart.put(product, 1);
//        try {loadCartPage();}
//        catch (Exception e) {System.out.println(e.getMessage());}
//    }
//
//    public void deleteFromCart(ProductInformation product) {
//        cart.remove(product);
//        try {loadCartPage();}
//        catch (Exception e) {System.out.println(e.getMessage());}
//    }
//
//    public void clearCart() {
//        cart.clear();
//    }
//
//    boolean cartReloading = false;
//    public void loadCartPage() throws Exception{
//        //int change = 0;
//        //Load page template (Template 2 has space for a top banner and some content pane)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//
//        Pane cartPage = CMS.getInstance().loadComponent("CartPage");
//        CMS.getInstance().loadOnto(plate, cartPage, "contentPlaceholder_Pane");
//
//        BigDecimal total = BigDecimal.valueOf(0);
//        for (ProductInformation product : cart.keySet()) {
//            total = total.add(findProduct(product).getPriceInformation().getPrice().multiply(BigDecimal.valueOf(cart.get(product))));
//
//            Pane item = CMS.getInstance().loadComponent("CartProductView");
//            CMS.getInstance().loadOnto(cartPage, item, "cartProductView_Vbox");
//            Image productImage = new Image(getClass().getResourceAsStream("Placeholder.jpg"));
//            ((ImageView) CMS.getInstance().findNode(item, "productImage_ImageView")).setImage(productImage);
//            ((Label) CMS.getInstance().findNode(item, "productName_Label")).setText(product.getName());
//            if (product.getPriceInformation() == null) {
//                ((Label) CMS.getInstance().findNode(item, "price_Label")).setText("$" + (findProduct(product).getPriceInformation().getPrice()));
//            } else {
//                ((Label) CMS.getInstance().findNode(item, "price_Label")).setText("$" + (product.getPriceInformation().getPrice()));
//            }
//            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, cart.get(product), 1));
//
//            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
//
//                if (!"".equals(newValue)) {
//                    cart.put(product, (Integer) ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).getValue());
//                }
//            });
//            ((Spinner) CMS.getInstance().findNode(item, "amount_Spinner")).setOnMouseClicked(actionEvent -> {
//                try {
//                    loadCartPage();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//            ((Button) CMS.getInstance().findNode(cartPage, "remove_Button")).setOnAction(actionEvent -> {
//                try {deleteFromCart(product);}
//                catch (Exception e) {System.out.println("!!!" + e.getMessage());}
//            });
//
//
//            updatePrice(cartPage, total);
//        }
//
//        ((Button) CMS.getInstance().findNode(cartPage, "pay_Button")).setOnAction(actionEvent -> {
//            try {loadPaymentPage();}
//            catch (Exception e) {System.out.println("!!!" + e.getMessage());}
//        });
//
//        updatePrice(cartPage, total);
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public void updatePrice(Pane cartPage, BigDecimal total) {
//        ((Label) CMS.getInstance().findNode(cartPage, "priceExclTax_Label")).setText("$" + total);
//        ((Label) CMS.getInstance().findNode(cartPage, "priceTax_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)));
//        ((Label) CMS.getInstance().findNode(cartPage, "priceTotal_Label")).setText("$" + total.multiply(BigDecimal.valueOf(0.25)).add(total));
//    }
//
//    public void loadPaymentPage() throws Exception{
//        //Load page template (Template 2 has space for a top banner and some content pane)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//
//        Pane paymentPage = CMS.getInstance().loadComponent("paymentPage");
//        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");
//
//        ((Button) CMS.getInstance().findNode(paymentPage, "finish_Button")).setOnAction(actionEvent -> {
//            String name = ((TextField) CMS.getInstance().findNode(paymentPage, "fullName_TextField")).getText();
//            String email = ((TextField) CMS.getInstance().findNode(paymentPage, "email_TextField")).getText();
//            int phone = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "phoneNumber_TextField")).getText());
//            String address = ((TextField) CMS.getInstance().findNode(paymentPage, "address_TextField")).getText();
//            int zipcode = Integer.parseInt(((TextField) CMS.getInstance().findNode(paymentPage, "ZIPCode_TextField")).getText());
//
//            HashMap<String, Integer> order = new HashMap<>();
//
//            for (ProductInformation product : cart.keySet()) {
//                order.put(product.getProductUUID(), cart.get(product));
//            }
//
//            Customer customer = new Customer(name, email, phone, address, zipcode);
//            MockShopObject orderInfo = new MockShopObject(order, customer);
//            StockInterface.sendOrderOMSNew(orderInfo);
//            OrderManager.sendOrder(orderInfo);
//
//
//            try {
//                clearCart();
//                loadPurchaseComplete();
//            }
//            catch (Exception e) {System.out.println(e.getMessage());}
//        });
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//
//    public void loadPurchaseComplete() throws Exception{
//        //Load page template (Template 2 has space for a top banner and some content pane)
//        Pane plate = CMS.getInstance().loadComponent("ContentTemplate3");
//
//        loadTopBanner(plate);
//        loadSidebar(plate);
//
//        Pane paymentPage = CMS.getInstance().loadComponent("PurchaseCompletePage");
//        CMS.getInstance().loadOnto(plate, paymentPage, "contentPlaceholder_Pane");
//
//        window.setScene(new Scene(plate, 1920, 1080));
//    }
//}