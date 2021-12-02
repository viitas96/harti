package com.example.harti.controller;

import com.example.harti.model.City;
import com.example.harti.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("cities/add")
    public String cityAdd(Model model) {return "city-add";}

    @GetMapping("/cities")
    public String all(Model model){
        model.addAttribute("allCities", cityRepository.findAll());
        return "cities-all";
    }

    @GetMapping("city/{id}/edit")
    public String editCity(@PathVariable(value = "id") long id, Model model){
        City city = cityRepository.findById(id).orElseThrow(null);
        model.addAttribute("city", city);
        return city == null ? "400" : "city-edit";
    }

    @PostMapping("city/add")
    public String addCity(@RequestParam String name,
                          @RequestParam String visitedDate,
                          Model model) throws ParseException {
        Date onlyDate = null;
        if (!visitedDate.isEmpty()){
            onlyDate = new SimpleDateFormat("yyyy-MM-dd").parse(visitedDate);
        }
        City city = new City(name, onlyDate);
        cityRepository.save(city);
        return "redirect:/cities";
    }

    @PostMapping("city/update")
    public String updateCity(@RequestParam long id,
                             @RequestParam String name,
                             @RequestParam String visitedDate,
                             Model model)throws ParseException {
        Date onlyDate = null;
        if (!visitedDate.isEmpty()){
            onlyDate = new SimpleDateFormat("yyyy-MM-dd").parse(visitedDate);
        }
        City city = cityRepository.findById(id).orElseThrow(null);
        city.setName(name);
        city.setVisitedDate(onlyDate);
        cityRepository.save(city);
        return "redirect:/cities";
    }

    @PostMapping("city/delete")
    public String deleteCity(@RequestParam long id,
                             Model model){
        City city = cityRepository.findById(id).orElseThrow(null);
        cityRepository.deleteById(id);
        return "redirect:/cities";
    }
}
