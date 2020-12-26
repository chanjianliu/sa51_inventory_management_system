package com.team9.motors.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.team9.motors.interfacemethods.CatalogueInterface;
import com.team9.motors.interfacemethods.UserInterface;
import com.team9.motors.mail.JavaMailUtil;
import com.team9.motors.model.DateSelector;
import com.team9.motors.model.Inventory;
import com.team9.motors.model.ProductState;
import com.team9.motors.model.StockUsage;
import com.team9.motors.model.StockUsageInventory;
import com.team9.motors.service.CatalogueImplementation;
import com.team9.motors.service.UserImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/all/catalogue")
@SessionAttributes("userdetails")
public class CatalogueController {


    @Autowired
    private CatalogueInterface cservice;

    @Autowired
    public void setCatalogue(CatalogueImplementation catalogue) {
        this.cservice = catalogue;
    }
    
    @Autowired
	private UserInterface uservice;
    
    @Autowired
    public void setCatalogue(UserImplementation uimpl) {
        this.uservice = uimpl;
    }

    //add customer
    @RequestMapping(value = "/addcustomer")
    public String addCustomerForm(ModelMap model) {

        StockUsage stockUsage = new StockUsage();

        model.addAttribute("stockUsage", stockUsage);
        return "addcustomer";
    }

    //update a new customer
    @RequestMapping(value = "/editcustomer/{id}")
    public String updateCustomerForm(@PathVariable("id") Integer id, ModelMap model) {

        model.addAttribute("stockUsage", cservice.findCustomerById(id));
        return "addcustomer";
    }

    @RequestMapping(value = "/savecustomer")
    public String saveCustomerForm(@ModelAttribute("stockUsage") StockUsage customer, ModelMap model) {
        cservice.saveStockUsage(customer);
        return "forward:/all/catalogue/showcustomer/"+customer.getId();
    }

    //deleting a customer record (StockUsage)
    @RequestMapping(value = "/deletecustomer/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, ModelMap model) {
        StockUsage customer = cservice.findCustomerById(id);
        List<StockUsageInventory> customerRecords = cservice.listUsageByCustomerId(id);
        for (StockUsageInventory record : customerRecords) {
            cservice.deleteStockUsageInventory(record);
        }

        cservice.deleteStockUsage(customer);
        return "forward:/all/catalogue/customers";
    }



    //add usage
    @RequestMapping(value = "/addusageform/{id}", method=RequestMethod.GET)
    public String addUsageForm(ModelMap model, @PathVariable ("id") Integer id) {

        StockUsage customer = cservice.findCustomerById(id);
        StockUsageInventory newUsageRecord = new StockUsageInventory();
        newUsageRecord.setStockUsage(customer);

        model.addAttribute("newUsageRecord", newUsageRecord);
        model.addAttribute("inventories", cservice.listAllInventories());

//		Map<StockUsage, List<Inventory>> doubleObj = new HashMap<>();
//		doubleObj.put(stockUsage, cservice.listAllInventories());
//		model.addAllAttributes(attributes);
        return "usageform";
    }

    @RequestMapping(value = "/saveusagerecord/{id}", method = RequestMethod.POST)
    public String saveNewRecord(@PathVariable("id") int id,
                                @ModelAttribute("newUsageRecord") @Valid StockUsageInventory usage, BindingResult bindingResult,
                                Model model) {

        // find the customer by using the record id (which was save in database when
        // user press add item)
        StockUsage customer = cservice.findCustomerById(id);
        // add record into customer record List
        customer.addStockUsageInventory(usage);
        // find Inventory object with the inventory id retrieved above
        Inventory dbItem = cservice.findPartById(usage.getProductId());
        // save the item into the record
        usage.setInventory(dbItem);
        usage.setStockUsage(customer);

        //deducting quantity from inventory
        dbItem.setQuantity(dbItem.getQuantity() - usage.getQuantity());

        cservice.saveStockUsageInventory(usage);

        //--- Mail ---
        if (dbItem.getQuantity() < dbItem.getProduct().getReorderLevel()
				&& dbItem.getProductState() == ProductState.InStock) {
        	dbItem.setProductState(ProductState.BelowReorderLevel);
        	JavaMailUtil.sendEmail(dbItem, uservice.findAdminEmail());
        	cservice.saveInventory(dbItem);
        }
        //--- Mail ---
        
        return "forward:/all/catalogue/showcustomer/" + customer.getId();
    }

    //saving the usageOfTheCustomer list after editing
    @PostMapping(value = "/updateusagerecord")
    public String updateExistingRecords(@ModelAttribute("newUsageRecord") @Valid StockUsage customer,
                                        BindingResult bindingResult, Model model) {

        List<StockUsageInventory> usageRecords = customer.getUsageOfTheCustomer();
        for (StockUsageInventory record : usageRecords) {
            Inventory dbItem = cservice.findPartById(record.getProductId());
            record.setInventory(dbItem);
            record.setStockUsage(customer);

            StockUsageInventory dbRecord = cservice.findUsageById(record.getId());
            dbItem.setQuantity(dbItem.getQuantity() + dbRecord.getQuantity() - record.getQuantity());

            cservice.saveStockUsageInventory(record);
            
            //--- Mail ---
            if (dbItem.getQuantity() < dbItem.getProduct().getReorderLevel()
    				&& dbItem.getProductState() == ProductState.InStock) {
            	dbItem.setProductState(ProductState.BelowReorderLevel);
            	JavaMailUtil.sendEmail(dbItem, uservice.findAdminEmail());
            	cservice.saveInventory(dbItem);
            }
            //--- Mail ---
        }
        
        return "forward:/all/catalogue/showcustomer/" + customer.getId();
    }

    //showing the detail of a single customer, his history and allow for adding new record or edit old records
    @RequestMapping(value = "/showcustomer/{id}")
    public String listUsageOfThisCustomer(@PathVariable("id") Integer id, ModelMap model) {

        StockUsage customer = cservice.findCustomerById(id); // to get customerName and CarId
        List<StockUsageInventory> usagesOfTheCustomer = customer.getUsageOfTheCustomer();

        model.addAttribute("customer", customer);
        model.addAttribute("usagesOfTheCustomer", usagesOfTheCustomer);

        return "customerusage";
    }

    //showing the detail of a single inventory, its usage history
    @RequestMapping(value = "/showinventory/{id}")
    public String listUsageOfThisInventory(@PathVariable("id") Integer id, ModelMap model) {

        Inventory item = cservice.findPartById(id); // to get customerName and CarId
        List<StockUsageInventory> usagesOfTheInventory = item.getStockUsageInventory();

        model.addAttribute("item", item);
        model.addAttribute("usagesOfTheInventory", usagesOfTheInventory);

        return "inventoryusage";
    }


    @RequestMapping(value = "/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, ModelMap model) {

        StockUsage stockUsage = cservice.findCustomerById(id);
        List<StockUsageInventory> usageOfTheCustomer = stockUsage.getUsageOfTheCustomer();

        model.addAttribute("stockUsage", stockUsage);
        model.addAttribute("usageOfTheCustomer", usageOfTheCustomer);

        return "editusageform";
    }

    //deleting a single StockUsageInventory record from a StockUsage/customer
    @RequestMapping(value="/delete/{id}")
    public String deleteMethod(Model model, @PathVariable("id") Integer id) {
        StockUsageInventory usageRecord = cservice.findUsageById(id);
        int customerId = usageRecord.getStockUsage().getId();

        cservice.deleteStockUsageInventory(usageRecord);
        return "forward:/all/catalogue/showcustomer/" + customerId;
    }


    //show the list of StockUsage records (customers list)
    @RequestMapping(value = "/customers")
    public String customerList(ModelMap model) {
        List<StockUsage> customerList = cservice.listAllStockUsages();
        model.addAttribute("customers", customerList);
        model.addAttribute("active", "active");
        return "customerlist";
    }

    //stockUsageInventoryPage (can use date search)
    @RequestMapping(value = "/listrecord")
    public String showRecordLsit(ModelMap model) {
        DateSelector dates = new DateSelector();
        model.addAttribute("dates",dates);

        List<StockUsageInventory> records = cservice.listAllStockUsageInventories();
        model.addAttribute("records",records);
        return "allrecordlist";

    }

    @RequestMapping(value = "/stockusgeinventorybydaterange")
    public String showRecordByDateRange(@ModelAttribute("dates")@Valid DateSelector dates, ModelMap model) {
        LocalDate start = LocalDate.parse(dates.getStartDate());
        LocalDate end = LocalDate.parse(dates.getEndDate());

        List<StockUsageInventory> recordsByDateRange = cservice.findStockUsageInventoryByRegistrationDateBetween(start, end);
        model.addAttribute("recordsByDateRange",recordsByDateRange);
        model.addAttribute("dates", dates);
        return "recordsbydates";
    }

	@RequestMapping(value="/usagereport")
	public String reportbyId(@ModelAttribute("dates")@Valid DateSelector dates, ModelMap model) {
//		model.addAttribute("reorder", pservice.reorderReport(id));
//		model.addAttribute(pservice.reorderReport(id));
		LocalDate start = LocalDate.parse(dates.getStartDate());
		LocalDate end = LocalDate.parse(dates.getEndDate());
		cservice.usageReport(start, end);
		return  "reordermsg";
	}

}
