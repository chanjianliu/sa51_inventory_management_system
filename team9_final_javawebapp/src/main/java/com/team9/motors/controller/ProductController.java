package com.team9.motors.controller;

import com.team9.motors.interfacemethods.InventoryInterface;
import com.team9.motors.interfacemethods.ProductInterface;
import com.team9.motors.interfacemethods.StockUsageInventoryInterface;
import com.team9.motors.interfacemethods.SupplierInterface;
import com.team9.motors.interfacemethods.UserInterface;
import com.team9.motors.mail.JavaMailUtil;
import com.team9.motors.model.Inventory;
import com.team9.motors.model.Product;
import com.team9.motors.model.ProductState;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.model.Supplier;
import com.team9.motors.service.InventoryImplementation;
import com.team9.motors.service.ProductImplementation;
import com.team9.motors.service.StockUsageInventoryImplementation;
import com.team9.motors.service.SupplierImplementation;
import com.team9.motors.service.UserImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("")
@SessionAttributes("userdetails")
public class ProductController {

    @Autowired
    private SupplierInterface sservice;

    @Autowired
    public void setSupplierInterface(SupplierImplementation simpl) {
        this.sservice = simpl;
    }

    @Autowired
    private ProductInterface pservice;

    @Autowired
    public void setProductService(ProductImplementation pserviceImpl) {
        this.pservice = pserviceImpl;
    }

    @Autowired
    private InventoryInterface iservice;

    @Autowired
    public void setInventoryService(InventoryImplementation iserviceImpl) {
        this.iservice = iserviceImpl;
    }

    @Autowired
    private StockUsageInventoryInterface suiservice;

    @Autowired
    public void setStockUsageInventoryService(StockUsageInventoryImplementation suiserviceImpl) {
        this.suiservice = suiserviceImpl;
    }
    
    @Autowired
    private UserInterface uservice;

    @Autowired
    public void setStockUsageInventoryService(UserImplementation uimpl) {
        this.uservice = uimpl;
    }

    @RequestMapping(value = "/all/product/list")
    public String listusers(Model model) {

        return listByPage(model, 1,"inventory.id","asc");
    }
    @GetMapping("/all/product/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("sortField")String sortField,
                             @Param("sortDir") String sortDir) {
        Page<Product> page = pservice.findAllProduts(currentPage,sortField,sortDir);

        long total = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> list = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("list", list);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        String reverseSortDir=sortDir.equals("asc")?"desc":"asc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("products", pservice.findAllProduts(currentPage,sortField,sortDir));

        return "products";
    }

/*
    @RequestMapping(value = "/all/product/list")
    public String list(Model model) {
        model.addAttribute("products", pservice.findAllProducts());
        return "products";
    }
    */


    @RequestMapping(value = "/admin/product/add")
    public String addForm(ModelMap model) {
        model.addAttribute("product", new Product());
        model.addAttribute("suppliers", sservice.listAllSuppliers());
        return "addproduct";
    }

    @RequestMapping(value = "/admin/product/edit/{id}")
    public String editProductForm(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("product", pservice.findProductById(id));
        model.addAttribute("suppliers", sservice.listAllSuppliers());
        return "product-form";
    }

    //save an edited product record 
    @RequestMapping(value = "/admin/product/save")
    public String saveProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "product-form";
        }

        Supplier supplier = sservice.findSupplierById(product.getSupId());
        product.setSupplier(supplier);

        Product dbProduct = pservice.findProductById(product.getId());
        if (dbProduct != null) {
            Inventory productInventory = dbProduct.getInventory();
            if (productInventory != null)
                product.setInventory(productInventory);
        }

        pservice.saveProduct(product);

        return "forward:/all/product/list";
    }

    //method to save a newly created product
    @RequestMapping(value = "/admin/product/savenew")
    public String saveNewProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            return "product-form";
        }

        Supplier supplier = sservice.findSupplierById(product.getSupId());
        product.setSupplier(supplier);
        pservice.saveProduct(product);

        return "forward:/admin/product/inventory/add/"+product.getId();
    }

    //method to delete a product and its inventory as well as the StockUsageInventory related to it
    @RequestMapping(value = "/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        Product product = pservice.findProductById(id);
        Inventory productInventory = iservice.findInventoryById(product.getInventory().getId());

        List<StockUsageInventory> recordLists = productInventory.getStockUsageInventory();

        if (recordLists != null)
        {
            for (StockUsageInventory record : recordLists) {
                suiservice.deleteStockUsageInventory(record);
            }
        }

        pservice.deleteProduct(product);

        return "forward:/all/product/list";
    }


    @RequestMapping(value = "/admin/product/inventory/add/{id}")
    public String addForm(@PathVariable("id") Integer id, ModelMap model) {
        Product product = pservice.findProductById(id);
        Inventory inventory = new Inventory();
        model.addAttribute("product", product);
        model.addAttribute("inventory", inventory);
        return "inventory-form";
    }

    @RequestMapping(value = "/all/product/inventory/save/{id}")
    public String saveInventory(@PathVariable("id") Integer id, @ModelAttribute("inventory") @Valid Inventory inventory,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "inventory-form";
        }

        Product product = pservice.findProductById(id);
        product.setInventory(inventory);
        pservice.saveProduct(product);

        return "forward:/all/product/list";
    }

    @RequestMapping(value = "/all/product/inventory/saveedit/{id}")
    public String saveInventoryEdit(@PathVariable("id") Integer id, @ModelAttribute("inventory") @Valid Inventory inventory,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "inventoryedit";
        }

        Product product = pservice.findProductById(id);
        product.setInventory(inventory);
        pservice.saveProduct(product);

        return "forward:/all/product/showinventory/"+inventory.getId();
    }

    @RequestMapping(value = "/all/product/inventory/edit/{id}")
    public String editInventoryForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("inventory", iservice.findInventoryById(id));
        return "inventoryedit";
    }


    @RequestMapping(value="/all/product/searching")
    public String search(@RequestParam("keyword") String k, @RequestParam("searchtype") String t, Model model)
    {
        System.out.println(t);
        System.out.println(k);
        String name=new String("name");
        String brand=new String("brand");
        String colour=new String("colour");
        String catagory=new String("category");
        String description=new String("description");
        String supplier = new String("supplier");
        if(t.equals(name))
        {
            model.addAttribute("products", pservice.SearchProductByName(k));
        }
        else if(t.equals(brand))
        {
            model.addAttribute("products", pservice.SearchProductByBrand(k));
        }
        else if(t.equals(colour))
        {
            model.addAttribute("products", pservice.SearchProductByColour(k));
        }
        else if(t.equals(catagory))
        {
            model.addAttribute("products", pservice.SearchProductByCategory(k));
        }
        else if(t.equals(description))
        {
            model.addAttribute("products", pservice.SearchProductByDescription(k));
        }
        else if (t.equals(supplier))
        {
        	model.addAttribute("products", pservice.SearchProductBySupplier(k));
        }
        else
        {
            System.out.println("Something wrong");
        }


        return "searchResult";
    }

    
    @RequestMapping(value="/admin/product/report")
    public String supplier(Model model) {
        model.addAttribute("supplier", sservice.listAllSuppliers());
        return "inventoryreorderreport";

    }

    //Generate a reorder report according to supplier
    @RequestMapping(value="/admin/product/reorderreport/{id}")
    public String reportbyId(@PathVariable("id") Integer id,Model model) {
        pservice.reorderReport(id);
        return  "reordermsg";
    }

    
    @RequestMapping(value = "/admin/changeStatus/{id}")
    public String changeInventoryStatus(@PathVariable("id") Integer id, ModelMap model) {
        Inventory inventory = iservice.findInventoryById(id);
        model.addAttribute("inventoryToBeChanged", inventory);
        //model.addAttribute("pStates", ProductState.values());

        return "singleInventoryDetail";
    }
    
    @RequestMapping(value = "/admin/saveupdatedinventory/{id}")
    public String saveUpdatedInventory(@ModelAttribute("inventoryToBeChanged") Inventory inventory,
                                       @PathVariable("id") Integer id) {
        Inventory inv= iservice.findInventoryById(id);
        inv.setProductState(inventory.getProductState());
        inv.setQuantity(inventory.getQuantity());
        //add back current damaged quantity - newly total damaged quantity
        inv.setQuantity(inv.getQuantity() + inv.getDamagedQuantity() - inventory.getDamagedQuantity());
        //report damagedQuantity
        inv.setDamagedQuantity(inventory.getDamagedQuantity());
        
        iservice.saveInventory(inv);
        
        //--- Mail ---
        if (inv.getQuantity() < inv.getProduct().getReorderLevel()
				&& inv.getProductState() == ProductState.InStock) {
        	inv.setProductState(ProductState.BelowReorderLevel);
        	JavaMailUtil.sendEmail(inv, uservice.findAdminEmail());
        	iservice.saveInventory(inv);
        }
        //--- Mail ---
        
        return "forward:/all/catalogue/showinventory/" + inventory.getId();
    }

}