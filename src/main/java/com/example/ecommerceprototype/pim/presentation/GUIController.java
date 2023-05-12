package com.example.ecommerceprototype.pim.presentation;

import com.example.ecommerceprototype.pim.exceptions.DuplicateEntryException;
import com.example.ecommerceprototype.pim.exceptions.IncompleteProductInformationException;
import com.example.ecommerceprototype.pim.product_information.*;
import com.example.ecommerceprototype.pim.util.SafeRemoveArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GUIController {

    @FXML
    public TextField productInputName;
    @FXML
    public TextField productInputSerialNumber;
    @FXML
    public TextField productInputCategoryName;
    @FXML
    public TextField productInputManufactureName;
    @FXML
    public TextField productInputPrice;
    @FXML
    public TextArea productInputShortDescription;
    @FXML
    public TextArea productInputLongDescription;
    @FXML
    public Button productInputSubmit;

    @FXML
    TableView<ProductInformation> productsTable;
    @FXML
    TableColumn<ProductInformation, String> productsTableName;
    @FXML
    TableColumn<ProductInformation, String> productsTableUUID;
    @FXML
    TableColumn<ProductInformation, Boolean> productsTableHidden;
    @FXML
    TableColumn<ProductInformation, String> productsTablePrice;
    @FXML
    TableColumn<ProductInformation, String> productsTableCategory;
    @FXML
    TableColumn<ProductInformation, String> productsTableManufacture;
    @FXML
    TableColumn<ProductInformation, String> productsTableSerialNumber;
    @FXML
    TableColumn<ProductInformation, String> productsTableShortDescription;
    @FXML
    TableColumn<ProductInformation, String> productsTableLongDescription;


    @FXML
    public TableView<ProductCategory> categoriesTable;
    @FXML
    public TableColumn<ProductCategory, String> categoriesTableName;
    @FXML
    public TableColumn<ProductCategory, String> categoriesTableParentCategoryName;


    @FXML
    public TableView<ManufacturingInformation> manufacturesTable;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableName;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableSupportPhone;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableSupportMail;


    @FXML
    public TableView<DiscountInformation> discountsTable;
    @FXML
    public TableColumn<DiscountInformation, String> discountsTableName;
    @FXML
    public TableColumn<DiscountInformation, String> discountsTableStartDate;
    @FXML
    public TableColumn<DiscountInformation, String> discountsTableEndDate;


    private PIMDriver pimDriverInstance;
    private final SafeRemoveArrayList<ProductInformationUpdater> productInformationUpdaterList = new SafeRemoveArrayList<>();
    private final SafeRemoveArrayList<ProductCategoryUpdater> productCategoryUpdatersList = new SafeRemoveArrayList<>();
    private final SafeRemoveArrayList<ManufacturingInformationUpdater> manufacturingInformationUpdatersList = new SafeRemoveArrayList<>();
    private final SafeRemoveArrayList<DiscountInformationUpdater> discountInformationUpdatersList = new SafeRemoveArrayList<>();

    @FXML
    public void initialize() {
        pimDriverInstance = new PIMDriver();
    }

    private void alertDuplicateEntry(String object) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate entry error");
        alert.setContentText("Similar " + object + " already exists!");
        alert.showAndWait();
    }

    private void alertObjectDoesNotExist(String object, String name) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(object + " does not exist");
        alert.setContentText("There does not exist any " + object + " with the given name: " + name);
        alert.showAndWait();
    }

    private void alertObjectDoesExist(String objectType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(objectType + " with same name already exists!");
        alert.setContentText("There already exist a " + objectType + " with with the same name");
        alert.showAndWait();
    }

    private void alertInvalidPriceInput() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid price input!");
        alert.showAndWait();
    }



    private void successAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    @FXML
    public void submitInsertNewProduct(ActionEvent actionEvent) {
        // Checks if any inputs are empty
        if (Objects.equals(productInputName.getText(), "") ||
                Objects.equals(productInputSerialNumber.getText(), "") ||
                Objects.equals(productInputCategoryName.getText(), "") ||
                Objects.equals(productInputManufactureName.getText(), "") ||
                Objects.equals(productInputPrice.getText(), "") ||
                Objects.equals(productInputShortDescription.getText(), "") ||
                Objects.equals(productInputLongDescription.getText(), "")) {

            // Sends an error informing not all inputs were filled.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("All inputs were not filled");
            alert.setContentText("Please fill in all inputs before inserting a new product!");
            alert.showAndWait();
            return;
        }

        // Checks if a product with the same name exists
        try {
            if (pimDriverInstance.getProductByName(productInputName.getText()) != null) {
                alertObjectDoesExist("Product");
                return;
            }
        } catch (RuntimeException ignored) {
        }

        // Checks if the price is a valid input
        try {
            new BigDecimal(productInputPrice.getText());
        } catch (NumberFormatException ignored) {
            alertInvalidPriceInput();
            return;
        }

        // Checks if a category with the given name exists
        try {
            pimDriverInstance.getCategoryByName(productInputCategoryName.getText());
        } catch (RuntimeException ignored) {
            alertObjectDoesNotExist("Category", productInputCategoryName.getText());
            return;
        }

        // Checks if a manufacture with the given name exists
        try {
            pimDriverInstance.getManufactureByName(productInputManufactureName.getText());
        } catch (RuntimeException ignored) {
            alertObjectDoesNotExist("Manufacture", productInputManufactureName.getText());
            return;
        }



        ProductCategoryBuilder categoryBuilder = (ProductCategoryBuilder) new ProductCategoryBuilder()
                .setName(productInputCategoryName.getText());

        ManufacturingInformationBuilder manufactureBuilder = (ManufacturingInformationBuilder) new ManufacturingInformationBuilder()
                .setName(productInputManufactureName.getText());

        try {
            String responseProductUUID = new ProductInformationBuilder()
                    .setName(productInputName.getText())
                    .setSerialNumber(productInputCategoryName.getText())
                    .setProductCategory(categoryBuilder.getProductCategory())
                    .setManufacturingInformation(manufactureBuilder.getManufacturingInformation())
                    .setShortDescription(productInputShortDescription.getText())
                    .setLongDescription(productInputLongDescription.getText())
                    .setProductSpecification(new ProductSpecification()).submit().getProductUUID();

            new PriceInformationBuilder()
                    .setPrice(new BigDecimal(productInputPrice.getText()))
                    .setWholeSalePrice(new BigDecimal(0))
                    .setProductUUID(responseProductUUID).submit();
        } catch (DuplicateEntryException e) {
            alertDuplicateEntry("product");
            return;
            // TODO: Should try to delete the product, because it could have been inserted
            //  without the price information being inserted.
        } catch (IncompleteProductInformationException e) {
            // This should not be reachable by the user
            throw new RuntimeException(e);
        }

        productInputName.clear();
        productInputSerialNumber.clear();
        productInputCategoryName.clear();
        productInputManufactureName.clear();
        productInputPrice.clear();
        productInputShortDescription.clear();
        productInputLongDescription.clear();

        successAlert("Product creation success", "Successfully created product");
        productsWindowChange();
    }

    @FXML
    public void productsWindowChange() {
        productsTable.setEditable(true);
        productsTable.getItems().clear();

        productsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsTableName.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableName.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setName(productInformationStringCellEditEvent.getNewValue());
            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableUUID.setCellValueFactory(new PropertyValueFactory<>("productUUID"));

        productsTableHidden.setCellValueFactory(new PropertyValueFactory<>("isHidden"));

        productsTablePrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPriceInformation().getPrice())));
        productsTablePrice.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTablePrice.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setPriceInformation(rowValue.getPriceInformation());

            PriceInformationBuilder priceInformationBuilder = new PriceInformationBuilder();
            priceInformationBuilder.setPrice(new BigDecimal(productInformationStringCellEditEvent.getNewValue()));

            priceInformationBuilder.setWholeSalePrice(new BigDecimal(0));
            priceInformationBuilder.setProductUUID(rowValue.getProductUUID());
            try {
                priceInformationBuilder.submit();
            } catch (IncompleteProductInformationException e) {
                throw new RuntimeException(e);
            }

            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory().getName()));
        productsTableCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableCategory.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setProductCategory(rowValue.getProductCategory());

            ProductCategoryUpdater productCategoryUpdater = new ProductCategoryUpdater(rowValue.getProductCategory());
            productCategoryUpdater.setName(productInformationStringCellEditEvent.getNewValue());


            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableManufacture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturingInformation().getName()));
        productsTableManufacture.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableManufacture.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setManufacturingInformation(rowValue.getManufacturingInformation());

            ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(rowValue.getManufacturingInformation());
            manufacturingInformationUpdater.setName(productInformationStringCellEditEvent.getNewValue());

            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        productsTableSerialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableSerialNumber.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setSerialNumber(productInformationStringCellEditEvent.getNewValue());
            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableShortDescription.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));
        productsTableShortDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableShortDescription.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setShortDescription(productInformationStringCellEditEvent.getNewValue());
            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });

        productsTableLongDescription.setCellValueFactory(new PropertyValueFactory<>("longDescription"));
        productsTableLongDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableLongDescription.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setLongDescription(productInformationStringCellEditEvent.getNewValue());
            productInformationUpdaterList.forEach(e -> {
                if (e.getProductInformation() == rowValue) {
                    productInformationUpdaterList.safeRemoveAdd(e);
                }
            });
            productInformationUpdaterList.safeRemoveSubmit();
            productInformationUpdaterList.add(productInformationUpdater);
        });


        List<ProductInformation> productInformationList = pimDriverInstance.getAllProducts();
        for (ProductInformation productInformation : productInformationList) {
            productsTable.getItems().add(productInformation);
        }
    }

    @FXML
    public void submitProductsChanges(ActionEvent ignored) {
        for (ProductInformationUpdater productInformationUpdater : productInformationUpdaterList) {
            try {
                productInformationUpdater.submit();
            } catch (DuplicateEntryException | IncompleteProductInformationException e) {
                throw new RuntimeException(e);
            }
        }
        productInformationUpdaterList.clear();
        productsWindowChange();
    }


    @FXML
    public void categoriesWindowChange() {
        categoriesTable.setEditable(true);
        categoriesTable.getItems().clear();

        categoriesTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTableParentCategoryName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getProductCategoryParent() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getProductCategoryParent().getName());
            }
        });

        List<ProductCategory> productCategoryList = pimDriverInstance.getAllCategories();
        for (ProductCategory productCategory : productCategoryList) {
            categoriesTable.getItems().add(productCategory);
        }
    }

    @FXML
    public void submitCategoriesChanges(ActionEvent actionEvent) {
    }

    @FXML
    public void manufacturesWindowChange() {
        manufacturesTable.setEditable(true);
        manufacturesTable.getItems().clear();

        manufacturesTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturesTableSupportPhone.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSupportPhoneNumber() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getSupportPhoneNumber());
            }
        });
        manufacturesTableSupportMail.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSupportMail() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getSupportMail());
            }
        });

        List<ManufacturingInformation> manufacturingInformationList = pimDriverInstance.getAllManufactures();
        for (ManufacturingInformation manufacturingInformation : manufacturingInformationList) {
            manufacturesTable.getItems().add(manufacturingInformation);
        }
    }

    @FXML
    public void submitManufacturesChanges(ActionEvent actionEvent) {
    }

    @FXML
    public void discountsWindowChange() {
        discountsTable.setEditable(true);
        discountsTable.getItems().clear();

        discountsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        discountsTableStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartingDate().toString()));
        discountsTableEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExpiringDate().toString()));

        List<DiscountInformation> discountInformationList = pimDriverInstance.getAllDiscounts();
        for (DiscountInformation discountInformation : discountInformationList) {
            discountsTable.getItems().add(discountInformation);
        }
    }

    @FXML
    public void submitDiscountsChanges(ActionEvent actionEvent) {
    }
}
