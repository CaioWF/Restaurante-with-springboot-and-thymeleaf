package br.com.ufc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ufc.model.Dish;
import br.com.ufc.repository.DishRepository;
import br.com.ufc.util.FileUtils;

@Service
public class DishService {
	
	@Autowired
	private DishRepository dishRepository;

	public void registerDish(Dish dish, MultipartFile photo) {
		dishRepository.save(dish);
		String path = "images/dish/" + dish.getId() + ".png";
		FileUtils.savePhoto(path, photo);
	}
	
	public List<Dish> returnDishes() {
		return dishRepository.findAll();
	}
	
	public void deleteDish(Long id) {
		dishRepository.deleteById(id);
		String path = "images/dish/"+id+".png";
		FileUtils.deleteImage(path);
	}
	
	public Dish getById(Long id) {
		return dishRepository.getOne(id);
	}
}
