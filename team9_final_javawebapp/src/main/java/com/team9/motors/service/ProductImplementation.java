package com.team9.motors.service;

import com.team9.motors.interfacemethods.ProductInterface;
import com.team9.motors.model.Product;
import com.team9.motors.model.Supplier;
import com.team9.motors.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ProductImplementation implements ProductInterface {
    @Autowired
    ProductRepository prepo;

    @Override
    @Transactional
    public boolean saveProduct(Product product) {
        if (prepo.save(product) != null)
            return true;
        else
            return false;
    }

    @Transactional
    public Page<Product> findAllProduts(int pageNumber, String sortField, String sortDir) {
        Sort sort=Sort.by(sortField);
        sort=sortDir.equals("asc")?sort.ascending():sort.descending();
        Pageable pageable= PageRequest.of(pageNumber - 1, 5,sort);
        return prepo.findAll(pageable);
    }


    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return prepo.findAll();
    }

    @Override
    @Transactional
    public Product findProductById(Integer id) {
        return prepo.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        prepo.delete(product);
    }

    @Override
    @Transactional
    public List<String> findAllProductNames() {
        List<Product> products = prepo.findAll();
        List<String> names = new ArrayList<String>();
        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
            Product product = (Product) iterator.next();
            names.add(product.getName());
        }
        return names;

    }

    @Override
    @Transactional
    public List<Product> SearchProductByName(String name) {
        return prepo.SearchProductByName(name);
    }

    @Override
    @Transactional
    public List<Product> SearchProductByBrand(String brand) {
        return prepo.SearchProductByBrand(brand);
    }

    @Override
    @Transactional
    public List<Product> SearchProductByColour(String colour) {
        return prepo.SearchProductByColour(colour);
    }

    @Override
    @Transactional
    public List<Product> SearchProductByCategory(String category) {
        return prepo.SearchProductByCategory(category);
    }

    @Override
    @Transactional
    public List<Product> SearchProductByDescription(String description) {
        return prepo.SearchProductByDescription(description);
    }
    
    @Override
    @Transactional
    public List<Product> SearchProductBySupplier(String supplier) {
        return prepo.SearchProductBySupplier(supplier);
    }
    
    @Override
	@Transactional
	public List<Product> findBySupplierId(int id) {
		return prepo.findBySupplierId(id);
	}

    @Transactional
    public void reorderReport(int id) {
    	
        BufferedWriter bw = null;
        try {
            List<Product> mycontent=prepo.reorderReport(id);
            if(mycontent.size()==0) {
                return;
            }
            //REMEMBER TO CHANGE LOCAL PATH
            File file = new File("D:\\reorderreport.dat");
            if (!file.exists()) {
                file.createNewFile();
            }
            
            Supplier supplier = mycontent.get(0).getSupplier();
            
            //Writing the report
            FileWriter fw = new FileWriter(file);

            bw = new BufferedWriter(fw);
            bw.write("\t\tInventory Reorder Report for products from Supplier: " + supplier.getName() + " [" + supplier.getEmail()+"]");
            bw.newLine();
            bw.write("----------------------------------------------------------------------------------------");
            bw.newLine();
            bw.write("=========================================================================================");

            bw.newLine();

            double total = 0;
            for(Product p:mycontent) {
                if(mycontent.get(0) == p)
                {
                    bw.newLine();
                    bw.write(p.toString1());
                    bw.newLine();
                }
                bw.newLine();
                bw.write(p.toString2());
                bw.newLine();
                total = total + p.getPrice();
                bw.newLine();

            }
            bw.newLine();
            bw.write("=========================================================================================");
            bw.newLine();
            bw.write("TOTAL: " +  Math.round(total * 100.0) / 100.0);
            bw.newLine();
            bw.write("=========================================================================================");
            bw.newLine();
            bw.write("End Of Report");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally
        {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
        

    }
}