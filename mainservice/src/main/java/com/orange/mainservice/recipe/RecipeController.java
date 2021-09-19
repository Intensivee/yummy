package com.orange.mainservice.recipe;

import com.orange.mainservice.entity.enums.TimeType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("recipes")
@AllArgsConstructor
class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recipeService.getResponseById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(recipeService.getAllPaged(pageable));
    }

    @GetMapping("/search/findByUserId/{id}")
    public ResponseEntity<Page<RecipeResponse>> getByUserIdPaged(@PathVariable("id") Long id, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByUserIdPaged(id, pageable));
    }

    @GetMapping("/search/findByUsername/{username}")
    public ResponseEntity<Page<RecipeResponse>> getByUsernamePaged(@PathVariable("username") String username, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByUsernamePaged(username, pageable));
    }

    @GetMapping("/search/findByCategoryName/{category}")
    public ResponseEntity<Page<RecipeResponse>> getByCategoryName(@PathVariable String category, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByCategoryName(category, pageable));
    }

    @GetMapping("/search/findByComponentName/{component}")
    public ResponseEntity<Page<RecipeResponse>> getByComponentName(@PathVariable String component, Pageable pageable){
        return ResponseEntity.ok(recipeService.getByComponentName(component, pageable));
    }

    @GetMapping("/search/findByTitleContaining/{searchKey}")
    public ResponseEntity<Page<RecipeResponse>> getBySearchKey(@PathVariable String searchKey, Pageable pageable){
        return ResponseEntity.ok(recipeService.getBySearchKey(searchKey, pageable));
    }

    @GetMapping("/search/findByTimeType/{timeType}")
    public ResponseEntity<Page<RecipeResponse>> getByTimeType(@PathVariable TimeType timeType, Pageable pageable){
        return ResponseEntity.ok(recipeService.getRecipesByTimeType(timeType, pageable));
    }

    @GetMapping("/search/top3")
    public ResponseEntity<List<RecipeResponse>> get3TopRatedRecipes() {
        return ResponseEntity.ok(recipeService.getTop3RatedRecipes());
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeCreateRequest request) {
        RecipeResponse created = recipeService.createRecipe(request);

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> edit(@PathVariable("id") Long id,
                                                       @Valid @RequestBody RecipeRequest request){
        RecipeResponse edited = recipeService.editRecipe(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }
}
