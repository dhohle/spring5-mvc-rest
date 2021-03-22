package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;

import guru.springfamework.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<CategoryListDTO> getAllCategories(){
        final CategoryListDTO categoryListDTO = new CategoryListDTO(this.categoryService.getAllCategories());
        return new ResponseEntity<>(categoryListDTO, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> getByNameCategories(@PathVariable final String name){
        return new ResponseEntity<>(this.categoryService.getCategoryByName(name), HttpStatus.OK);
    }
}