package com.team9.motors.controller;

import java.util.List;

import javax.validation.Valid;

import com.team9.motors.interfacemethods.InventoryInterface;
import com.team9.motors.interfacemethods.ProductInterface;
import com.team9.motors.interfacemethods.StockUsageInventoryInterface;
import com.team9.motors.interfacemethods.SupplierInterface;
import com.team9.motors.model.Inventory;
import com.team9.motors.model.Product;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.model.Supplier;
import com.team9.motors.service.InventoryImplementation;
import com.team9.motors.service.ProductImplementation;
import com.team9.motors.service.StockUsageInventoryImplementation;
import com.team9.motors.service.SupplierImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("")
@SessionAttributes("userdetails")
public class SupplierController {

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
    @RequestMapping(value = "/all/supplier/list")
    public String listusers(Model model) {

        return listByPage(model, 1,"id","asc");
    }

    @GetMapping("all/supplier/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
    		@Param("sortField")String sortField,
			@Param("sortDir") String sortDir){
        Page<Supplier> page = sservice.listAllSuppliers(currentPage,sortField,sortDir);

        long total = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Supplier> list = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("list", list);
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		String reverseSortDir=sortDir.equals("asc")?"desc":"asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("supplierlist", sservice.listAllSuppliers(currentPage,sortField,sortDir));

        return "supplierlist";
    }

    @RequestMapping(value = "/all/supplier/detail/{id}")
    public String showSupplier(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("supplier", sservice.findSupplierById(id));
        model.addAttribute("products", pservice.findBySupplierId(id));
        return "showsupplier";
    }


    @RequestMapping(value = "/admin/supplier/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("supplier", sservice.findSupplierById(id));
        return "EditSupplierDetails";
    }


    @GetMapping(value = "/admin/supplier/add")
    public String addForm(Model model) {

        model.addAttribute("supplier", new Supplier());
        return "addsupplier";
    }

    @RequestMapping(value = "/admin/supplier/save")
    public String saveSupplier(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "addsupplier";
        }
        sservice.createSupplier(supplier);
        return "forward:/all/supplier/detail/"+supplier.getId();
    }


    @RequestMapping(value = "/admin/supplier/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Integer id) {
    	Supplier supplier =  sservice.findSupplierById(id);
		List<Product> supplierProducts = supplier.getProductList();
		for (Product p : supplierProducts) {
			Inventory productInventory = p.getInventory();
			
			List<StockUsageInventory> inventoryRecords = productInventory.getStockUsageInventory();
			for (StockUsageInventory record : inventoryRecords) {
				suiservice.deleteStockUsageInventory(record);
			}
			
			iservice.deleteInventory(productInventory);
			pservice.deleteProduct(p);
		}

        sservice.deleteSupplier(supplier);
        return "forward:/all/supplier/list";
    }
}
