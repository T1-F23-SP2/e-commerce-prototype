package com.example.ecommerceprototype.pim.presentation;

import com.example.ecommerceprototype.pim.exceptions.*;
import com.example.ecommerceprototype.pim.product_information.*;
import com.example.ecommerceprototype.pim.util.SafeRemoveArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class GUIController {

    //region Variables

    // Product variables:
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


    // Category variables:
    @FXML
    public TextField categoryInputName;
    @FXML
    public TextField categoryInputParentName;
    @FXML
    public TableView<ProductCategory> categoriesTable;
    @FXML
    public TableColumn<ProductCategory, String> categoriesTableName;
    @FXML
    public TableColumn<ProductCategory, String> categoriesTableParentCategoryName;


    // Manufacture variables:
    @FXML
    public TextField manufactureInputName;
    @FXML
    public TextField manufactureInputPhoneNumber;
    @FXML
    public TextField manufactureInputMail;
    @FXML
    public TableView<ManufacturingInformation> manufacturesTable;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableName;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableSupportPhone;
    @FXML
    public TableColumn<ManufacturingInformation, String> manufacturesTableSupportMail;


    // Discount variables:
    @FXML
    public TextField discountInputName;
    @FXML
    public TextField discountInputStartingDate;
    @FXML
    public TextField discountInputEndingDate;
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

    //endregion

    //region Alerts

    private void alertAllInputsNotFiled() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Required inputs were not filled");
        alert.setContentText("Please fill in all required inputs before submitting!");
        alert.showAndWait();
    }

    private void alertObjectWithSameNameAlreadyExist(String objectType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(objectType + " with same name already exists!");
        alert.setContentText("There already exist a " + objectType.toLowerCase() + " with with the same name!");
        alert.showAndWait();
    }

    private void alertObjectWithGivenNameDoesNotExist(String objectType, String givenName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(objectType + " with same name doesn't exist");
        alert.setContentText("There doesn't exist any " + objectType.toLowerCase() + " with the given name: " + givenName);
        alert.showAndWait();
    }

    private void alertInvalidPriceInput() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid price input!");
        alert.setContentText("Please enter a valid price input (fx: 5, 29, 4.5, 599.95, etc.)");
        alert.showAndWait();
    }

    private void alertInvalidDateInput() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid date input!");
        alert.setContentText("Please enter a valid date input (YYYY-MM-DD)");
        alert.showAndWait();
    }

    private void alertSQLError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("SQL error");
        alert.setContentText("A unknown SQL error occurred");
        alert.showAndWait();
    }

    private void successAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }


// This method has not been checked:

    private void alertDuplicateEntry(String typeOfObject) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate entry error");
        alert.setContentText("Similar " + typeOfObject + " already exists!");
        alert.showAndWait();
    }

    //endregion

    // region Updater-submitters

    private void addNewProductUpdaterToSubmit(SafeRemoveArrayList<ProductInformationUpdater> updaterList, ProductInformationUpdater newListToAdd, ProductInformation rowValue) {
        updaterList.forEach(e -> {
            if (e.getProductInformation() == rowValue) {
                updaterList.safeRemoveAdd(e);
            }
        });
        updaterList.safeRemoveSubmit();
        updaterList.add(newListToAdd);
    }

    private void addNewCategoryUpdaterToSubmit(SafeRemoveArrayList<ProductCategoryUpdater> updaterList, ProductCategoryUpdater newListToAdd, ProductCategory rowValue) {
        updaterList.forEach(e -> {
            if (e.getProductCategory() == rowValue) {
                updaterList.safeRemoveAdd(e);
            }
        });
        updaterList.safeRemoveSubmit();
        updaterList.add(newListToAdd);
    }

    private void addNewManufactureUpdaterToSubmit(SafeRemoveArrayList<ManufacturingInformationUpdater> updaterList, ManufacturingInformationUpdater newListToAdd, ManufacturingInformation rowValue) {
        updaterList.forEach(e -> {
            if (e.getManufacturingInformation() == rowValue) {
                updaterList.safeRemoveAdd(e);
            }
        });
        updaterList.safeRemoveSubmit();
        updaterList.add(newListToAdd);
    }

    private void addNewDiscountUpdaterToSubmit(SafeRemoveArrayList<DiscountInformationUpdater> updaterList, DiscountInformationUpdater newListToAdd, DiscountInformation rowValue) {
        updaterList.forEach(e -> {
            if (e.getDiscountInformation() == rowValue) {
                updaterList.safeRemoveAdd(e);
            }
        });
        updaterList.safeRemoveSubmit();
        updaterList.add(newListToAdd);
    }

    // endregion Updater-submitters

    @FXML
    public void initialize() {
        pimDriverInstance = new PIMDriver();
    }


    // Product methods:
    @FXML
    public void submitInsertNewProduct(ActionEvent ignoredActionEvent) {
        // Checks if any inputs are empty
        if (Objects.equals(productInputName.getText(), "") ||
                Objects.equals(productInputSerialNumber.getText(), "") ||
                Objects.equals(productInputCategoryName.getText(), "") ||
                Objects.equals(productInputManufactureName.getText(), "") ||
                Objects.equals(productInputPrice.getText(), "") ||
                Objects.equals(productInputShortDescription.getText(), "") ||
                Objects.equals(productInputLongDescription.getText(), "")) {
            alertAllInputsNotFiled();
            return;
        }

        // Checks if the price is a valid input
        try {
            new BigDecimal(productInputPrice.getText());
        } catch (NumberFormatException ignored) {
            alertInvalidPriceInput();
            return;
        }

        // Checks if a product with the same name exists
        if (pimDriverInstance.checkIfProductByNameExists(productInputName.getText())) {
            alertObjectWithSameNameAlreadyExist("Product");
            return;
        }

        // Checks if a category with the given name exists
        if (!pimDriverInstance.checkIfCategoryByNameExists(productInputCategoryName.getText())) {
            alertObjectWithGivenNameDoesNotExist("Category", productInputCategoryName.getText());
            return;
        }

        // Checks if a manufacture with the given name exists
        if (!pimDriverInstance.checkIfManufactureByNameExists(productInputManufactureName.getText())) {
            alertObjectWithGivenNameDoesNotExist("Manufacture", productInputManufactureName.getText());
            return;
        }

        // Creates a category object for the product object
        ProductCategoryBuilder categoryBuilder = (ProductCategoryBuilder) new ProductCategoryBuilder()
                .setName(productInputCategoryName.getText());

        // Creates a manufacture object for the product object
        ManufacturingInformationBuilder manufactureBuilder = (ManufacturingInformationBuilder) new ManufacturingInformationBuilder()
                .setName(productInputManufactureName.getText());

        // Creates a price object for the product object
        PriceInformationBuilder priceInformationBuilder = new PriceInformationBuilder()
                .setPrice(new BigDecimal(productInputPrice.getText()))
                .setWholeSalePrice(new BigDecimal(0));

        // Inserts the new product
        try {
            new ProductInformationBuilder()
                    .setName(productInputName.getText())
                    .setSerialNumber(productInputSerialNumber.getText())
                    .setProductCategory(categoryBuilder.getProductCategory())
                    .setManufacturingInformation(manufactureBuilder.getManufacturingInformation())
                    .setPriceInformation(priceInformationBuilder.getPriceInformation())
                    .setShortDescription(productInputShortDescription.getText())
                    .setLongDescription(productInputLongDescription.getText())
                    .setProductSpecification(new ProductSpecification())
                    .submit();
        } catch (DuplicateEntryException ignored) {
            alertDuplicateEntry("Product");
            return;
        } catch (ManufactureNotFoundException ignored) {
            alertObjectWithGivenNameDoesNotExist("Manufacture", productInputManufactureName.getText());
            return;
        } catch (CategoryNotFoundException ignored) {
            alertObjectWithGivenNameDoesNotExist("Category", productInputCategoryName.getText());
            return;
        } catch (IncompleteProductInformationException | UUIDNotFoundException | SQLException e) {
            alertSQLError();
            e.printStackTrace();
            return;
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
            productInformationUpdater.setOriginalName(productInformationStringCellEditEvent.getOldValue());
            productInformationUpdater.setName(productInformationStringCellEditEvent.getNewValue());

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableUUID.setCellValueFactory(new PropertyValueFactory<>("productUUID"));

        productsTableHidden.setCellValueFactory(new PropertyValueFactory<>("isHidden"));
        // TODO: Missing CellFactory and OnEditCommit

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

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory().getName()));
        productsTableCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableCategory.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setProductCategory(rowValue.getProductCategory());

            ProductCategoryUpdater productCategoryUpdater = new ProductCategoryUpdater(rowValue.getProductCategory());
            productCategoryUpdater.setName(productInformationStringCellEditEvent.getNewValue());


            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableManufacture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManufacturingInformation().getName()));
        productsTableManufacture.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableManufacture.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setManufacturingInformation(rowValue.getManufacturingInformation());

            ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(rowValue.getManufacturingInformation());
            manufacturingInformationUpdater.setName(productInformationStringCellEditEvent.getNewValue());

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        productsTableSerialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableSerialNumber.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setSerialNumber(productInformationStringCellEditEvent.getNewValue());

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableShortDescription.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));
        productsTableShortDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableShortDescription.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setShortDescription(productInformationStringCellEditEvent.getNewValue());

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });

        productsTableLongDescription.setCellValueFactory(new PropertyValueFactory<>("longDescription"));
        productsTableLongDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        productsTableLongDescription.setOnEditCommit(productInformationStringCellEditEvent -> {
            ProductInformation rowValue = productInformationStringCellEditEvent.getRowValue();
            ProductInformationUpdater productInformationUpdater = new ProductInformationUpdater(rowValue);
            productInformationUpdater.setLongDescription(productInformationStringCellEditEvent.getNewValue());

            addNewProductUpdaterToSubmit(productInformationUpdaterList, productInformationUpdater, rowValue);
        });


        List<ProductInformation> productInformationList = pimDriverInstance.getAllProducts();
        for (ProductInformation productInformation : productInformationList) {
            productsTable.getItems().add(productInformation);
        }
    }

    @FXML
    public void submitProductsChanges(ActionEvent ignoredActionEvent) {
        for (ProductInformationUpdater productInformationUpdater : productInformationUpdaterList) {
            try {
                productInformationUpdater.submit();
            } catch (DuplicateEntryException ignored) {
                alertDuplicateEntry("Product");
                return;
            } catch (ManufactureNotFoundException ignored) {
                alertObjectWithGivenNameDoesNotExist("Manufacture", productInformationUpdater.getProductInformation().getManufacturingInformation().getName());
                return;
            } catch (CategoryNotFoundException ignored) {
                alertObjectWithGivenNameDoesNotExist("Category", productInformationUpdater.getProductInformation().getProductCategory().getName());
                return;
            } catch (UUIDNotFoundException | SQLException e) {
                alertSQLError();
                e.printStackTrace();
                return;
            }
        }
        productInformationUpdaterList.clear();
        productsWindowChange();
    }


    // Category methods:
    @FXML
    public void submitInsertNewCategory(ActionEvent ignoredActionEvent) {
        // Checks if the name is empty
        if (Objects.equals(categoryInputName.getText(), "")) {
            alertAllInputsNotFiled();
            return;
        }

        // Checks if an object with the given category name exist
        if (pimDriverInstance.checkIfCategoryByNameExists(categoryInputName.getText())) {
            alertObjectWithSameNameAlreadyExist("Category");
            return;
        }

        // Checks if the referenced category doesn't exist
        if (!Objects.equals(categoryInputParentName.getText(), "")) {
            if (!pimDriverInstance.checkIfCategoryByNameExists(categoryInputParentName.getText())) {
                alertObjectWithGivenNameDoesNotExist("Category", categoryInputParentName.getText());
                return;
            }
        }

        // Inserts the new category
        try {
            new ProductCategoryBuilder()
                    .setName(categoryInputName.getText())
                    .setCategoryParent(Objects.equals(categoryInputParentName.getText(), "") ? "" : categoryInputParentName.getText())
                    .submit();
        } catch (DuplicateEntryException e) {
            alertDuplicateEntry("Category");
            return;
        } catch (IncompleteProductCategoryInformation ignored) {
            alertSQLError();
            return;
        }

        categoryInputName.clear();
        categoryInputParentName.clear();

        successAlert("Category creation success", "Successfully created the category");
        categoriesWindowChange();
    }

    @FXML
    public void categoriesWindowChange() {
        categoriesTable.setEditable(true);
        categoriesTable.getItems().clear();

        categoriesTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriesTableName.setCellFactory(TextFieldTableCell.forTableColumn());
        categoriesTableName.setOnEditCommit(categoryInformationStringCellEditEvent -> {
            ProductCategory rowValue = categoryInformationStringCellEditEvent.getRowValue();
            ProductCategoryUpdater productCategoryUpdater = new ProductCategoryUpdater(rowValue);
            productCategoryUpdater.setOriginalName(categoryInformationStringCellEditEvent.getOldValue());
            productCategoryUpdater.setName(categoryInformationStringCellEditEvent.getNewValue());

            addNewCategoryUpdaterToSubmit(productCategoryUpdatersList, productCategoryUpdater, rowValue);
        });

        categoriesTableParentCategoryName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getProductCategoryParent() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getProductCategoryParent().getName());
            }
        });
        categoriesTableParentCategoryName.setCellFactory(TextFieldTableCell.forTableColumn());
        categoriesTableParentCategoryName.setOnEditCommit(categoryInformationStringCellEditEvent -> {
            ProductCategory rowValue = categoryInformationStringCellEditEvent.getRowValue();
            ProductCategoryUpdater productCategoryUpdater = new ProductCategoryUpdater(rowValue);
            productCategoryUpdater.setCategoryParent(categoryInformationStringCellEditEvent.getNewValue());

            // This can't be replaced by the a update-submitter because it has an extra line of code.
            productCategoryUpdatersList.forEach(e -> {
                if (e.getProductCategory() == rowValue) {
                    productCategoryUpdater.setOriginalName(e.getOriginalName());
                    productCategoryUpdatersList.safeRemoveAdd(e);
                }
            });
            productCategoryUpdatersList.safeRemoveSubmit();
            productCategoryUpdatersList.add(productCategoryUpdater);
        });

        List<ProductCategory> productCategoryList = pimDriverInstance.getAllCategories();
        for (ProductCategory productCategory : productCategoryList) {
            categoriesTable.getItems().add(productCategory);
        }
    }

    @FXML
    public void submitCategoriesChanges(ActionEvent ignoredActionEvent) {
        for (ProductCategoryUpdater productCategoryUpdater : productCategoryUpdatersList) {
            try {
                productCategoryUpdater.submit();
            } catch (IncompleteProductCategoryInformation e) {
                throw new RuntimeException(e);
            } catch (DuplicateEntryException e) {
                alertDuplicateEntry("Category");
            }
        }
        productCategoryUpdatersList.clear();
        categoriesWindowChange();
    }


    // Manufacture methods:
    @FXML
    public void submitInsertNewManufacture(ActionEvent ignoredActionEvent) {
        // Checks if any input is empty
        if (Objects.equals(manufactureInputName.getText(), "") ||
                Objects.equals(manufactureInputPhoneNumber.getText(), "") ||
                Objects.equals(manufactureInputMail.getText(), "")) {
            alertAllInputsNotFiled();
            return;
        }

        // Checks if an object with the same name exists
        if (pimDriverInstance.checkIfManufactureByNameExists(manufactureInputName.getText())) {
            alertObjectWithSameNameAlreadyExist("Manufacture");
            return;
        }

        // Inserts the new manufacture
        try {
            new ManufacturingInformationBuilder()
                    .setName(manufactureInputName.getText())
                    .setSupportPhoneNumber(Objects.equals(manufactureInputPhoneNumber.getText(), "") ? "" : manufactureInputPhoneNumber.getText())
                    .setSupportMail(Objects.equals(manufactureInputMail.getText(), "") ? "" : manufactureInputMail.getText())
                    .submit();
        } catch (DuplicateEntryException ignored) {
            alertDuplicateEntry("Manufacture");
            return;
        } catch (IncompleteManufacturingInformation | SQLException e) {
            alertSQLError();
            e.printStackTrace();
            return;
        }

        manufactureInputName.clear();
        manufactureInputPhoneNumber.clear();
        manufactureInputMail.clear();

        successAlert("Manufacture creation success", "Successfully create the manufacture");
        manufacturesWindowChange();
    }

    @FXML
    public void manufacturesWindowChange() {
        manufacturesTable.setEditable(true);
        manufacturesTable.getItems().clear();

        manufacturesTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturesTableName.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturesTableName.setOnEditCommit(miCellEditEvent -> {
            ManufacturingInformation rowValue = miCellEditEvent.getRowValue();
            ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(rowValue);
            manufacturingInformationUpdater.setOriginalName(miCellEditEvent.getOldValue());
            manufacturingInformationUpdater.setName(miCellEditEvent.getNewValue());

            addNewManufactureUpdaterToSubmit(manufacturingInformationUpdatersList, manufacturingInformationUpdater, rowValue);
        });


        manufacturesTableSupportPhone.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSupportPhoneNumber() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getSupportPhoneNumber());
            }
        });
        manufacturesTableSupportPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturesTableSupportPhone.setOnEditCommit(miCellEditEvent -> {
            ManufacturingInformation rowValue = miCellEditEvent.getRowValue();
            ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(rowValue);
            manufacturingInformationUpdater.setSupportPhoneNumber(miCellEditEvent.getNewValue());

            // This can't be replaced by an update-submitter because it has an extra line of code.
            manufacturingInformationUpdatersList.forEach(e -> {
                if (e.getManufacturingInformation() == rowValue) {
                    manufacturingInformationUpdater.setOriginalName(e.getOriginalName());
                    manufacturingInformationUpdatersList.safeRemoveAdd(e);
                }
            });
            manufacturingInformationUpdatersList.safeRemoveSubmit();
            manufacturingInformationUpdatersList.add(manufacturingInformationUpdater);
        });


        manufacturesTableSupportMail.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSupportMail() == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(cellData.getValue().getSupportMail());
            }
        });
        manufacturesTableSupportMail.setCellFactory(TextFieldTableCell.forTableColumn());
        manufacturesTableSupportPhone.setOnEditCommit(miCellEditEvent -> {
            ManufacturingInformation rowValue = miCellEditEvent.getRowValue();
            ManufacturingInformationUpdater manufacturingInformationUpdater = new ManufacturingInformationUpdater(rowValue);
            manufacturingInformationUpdater.setSupportMail(miCellEditEvent.getNewValue());

            // This can't be replaced by the a update-submitter because it has an extra line of code.
            manufacturingInformationUpdatersList.forEach(e -> {
                if (e.getManufacturingInformation() == rowValue) {
                    manufacturingInformationUpdater.setOriginalName(e.getOriginalName());
                    manufacturingInformationUpdatersList.safeRemoveAdd(e);
                }
            });
            manufacturingInformationUpdatersList.safeRemoveSubmit();
            manufacturingInformationUpdatersList.add(manufacturingInformationUpdater);
        });

        List<ManufacturingInformation> manufacturingInformationList = pimDriverInstance.getAllManufactures();
        for (ManufacturingInformation manufacturingInformation : manufacturingInformationList) {
            manufacturesTable.getItems().add(manufacturingInformation);
        }
    }

    @FXML
    public void submitManufacturesChanges(ActionEvent ignoredActionEvent) {
        for (ManufacturingInformationUpdater manufacturingInformationUpdater : manufacturingInformationUpdatersList) {
            try {
                manufacturingInformationUpdater.submit();
            } catch (DuplicateEntryException e) {
                alertDuplicateEntry("Manufacture");
            } catch (SQLException e) {
                alertSQLError();
                e.printStackTrace();
            }
        }
    }


    // Discount methods:
    @FXML
    public void submitInsertNewDiscount(ActionEvent ignoredActionEvent) {
        // Checks if any input is empty
        if (Objects.equals(discountInputName.getText(), "") ||
                Objects.equals(discountInputStartingDate.getText(), "") ||
                Objects.equals(discountInputEndingDate.getText(), "")) {
            alertAllInputsNotFiled();
            return;
        }

        // Checks if an object with the same name exists
        if (pimDriverInstance.checkIfDiscountByNameExists(discountInputName.getText())) {
            alertObjectWithSameNameAlreadyExist("Discount");
            return;
        }

        // Checks if both dates are valid
        try {
            LocalDate.parse(discountInputStartingDate.getText());
            LocalDate.parse(discountInputEndingDate.getText());
        } catch (DateTimeException ignored) {
            alertInvalidDateInput();
            return;
        }

        try {
            new DiscountInformationBuilder()
                    .setName(discountInputName.getText())
                    .setStartingDate(LocalDate.parse(discountInputStartingDate.getText()))
                    .setExpiringDate(LocalDate.parse(discountInputEndingDate.getText()))
                    .submit();
        } catch (IncompleteProductCategoryInformation e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (DuplicateEntryException e) {
            throw new RuntimeException(e);
        } catch (IncompleteDiscountInformation e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        discountInputName.clear();
        discountInputStartingDate.clear();
        discountInputEndingDate.clear();

        successAlert("Discount creation success", "Successfully created discount");
        discountsWindowChange();
    }

    @FXML
    public void discountsWindowChange() {
        discountsTable.setEditable(true);
        discountsTable.getItems().clear();

        discountsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        discountsTableName.setCellFactory(TextFieldTableCell.forTableColumn());
        discountsTableName.setOnEditCommit(diCellEditEvent -> {
            DiscountInformation rowValue = diCellEditEvent.getRowValue();
            DiscountInformationUpdater discountInformationUpdater = new DiscountInformationUpdater(rowValue);
            discountInformationUpdater.setOriginalName(diCellEditEvent.getOldValue());
            discountInformationUpdater.setName(diCellEditEvent.getNewValue());

            addNewDiscountUpdaterToSubmit(discountInformationUpdatersList, discountInformationUpdater, rowValue);
        });

        discountsTableStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartingDate().toString()));
        discountsTableStartDate.setCellFactory(TextFieldTableCell.forTableColumn());
        discountsTableStartDate.setOnEditCommit(diCellEditEvent -> {
            try {
                LocalDate.parse(diCellEditEvent.getNewValue());
            } catch (DateTimeException ignored) {
                alertInvalidDateInput();
                return;
            }

            DiscountInformation rowValue = diCellEditEvent.getRowValue();
            DiscountInformationUpdater discountInformationUpdater = new DiscountInformationUpdater(rowValue);
            discountInformationUpdater.setStartingDate(LocalDate.parse(diCellEditEvent.getNewValue()));

            // This can't be replaced by an update-submitter because it has an extra line of code.
            discountInformationUpdatersList.forEach(e -> {
                if (e.getDiscountInformation() == rowValue) {
                    discountInformationUpdater.setOriginalName(e.getOriginalName());
                    discountInformationUpdatersList.safeRemoveAdd(e);
                }
            });
            discountInformationUpdatersList.safeRemoveSubmit();
            discountInformationUpdatersList.add(discountInformationUpdater);
        });

        discountsTableEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExpiringDate().toString()));
        discountsTableEndDate.setCellFactory(TextFieldTableCell.forTableColumn());
        discountsTableStartDate.setOnEditCommit(diCellEditEvent -> {
            try {
                LocalDate.parse(diCellEditEvent.getNewValue());
            } catch (DateTimeException ignored) {
                alertInvalidDateInput();
                return;
            }

            DiscountInformation rowValue = diCellEditEvent.getRowValue();
            DiscountInformationUpdater discountInformationUpdater = new DiscountInformationUpdater(rowValue);
            discountInformationUpdater.setExpiringDate(LocalDate.parse(diCellEditEvent.getNewValue()));

            // This can't be replaced by an update-submitter because it has an extra line of code.
            discountInformationUpdatersList.forEach(e -> {
                if (e.getDiscountInformation() == rowValue) {
                    discountInformationUpdater.setOriginalName(e.getOriginalName());
                    discountInformationUpdatersList.safeRemoveAdd(e);
                }
            });
            discountInformationUpdatersList.safeRemoveSubmit();
            discountInformationUpdatersList.add(discountInformationUpdater);
        });

        List<DiscountInformation> discountInformationList = pimDriverInstance.getAllDiscounts();
        for (DiscountInformation discountInformation : discountInformationList) {
            discountsTable.getItems().add(discountInformation);
        }
    }

    @FXML
    public void submitDiscountsChanges(ActionEvent ignoredActionEvent) {
        for (DiscountInformationUpdater discountInformationUpdater : discountInformationUpdatersList) {
            try {
                discountInformationUpdater.submit();
            } catch (DuplicateEntryException e) {
                throw new RuntimeException(e);
            } catch (IncompleteProductCategoryInformation e) {
                throw new RuntimeException(e);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}