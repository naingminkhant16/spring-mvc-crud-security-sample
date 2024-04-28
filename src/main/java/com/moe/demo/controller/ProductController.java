package com.moe.demo.controller;

import com.moe.demo.entity.Product;
import com.moe.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/store")
    public String store(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "products/create";
        }

        productService.save(product);

        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);

        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);

        return "products/edit";
    }

    @PostMapping("/{id}/update")
    public String update(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "products/edit";
        }

        productService.save(product);

        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        Product product = productService.findById(id);

        if (product == null) {
            return "redirect:/products";
        }

        productService.delete(product);

        return "redirect:/products";
    }
}
