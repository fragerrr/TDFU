package com.example.demo.Controller;

import com.example.demo.DBManager;
import com.example.demo.Item;
import com.example.demo.MyDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String getItems(Model model){
        ArrayList<Item> items = DBManager.getItems();

        MyDate myDate = new MyDate();

        for(Item item : items){
            if(item.getDate().getTime() - myDate.getTime() < (24*60*60*1000) && !item.isDone()){
                item.setColor("#ed2f2f");
                item.setFColor("white");
            } else{
                item.setColor("white");
                item.setFColor("black");
            }
            if(item.isDone()){
                item.setColor("#8f8f8f");
            }
        }
        Date date = new Date(myDate.getTime());
        model.addAttribute("date", date);
        model.addAttribute("items", items);

        return "index";
    }

    @GetMapping(value = "/sorted")
    public String getIndex(Model model, @RequestParam(name = "id") Integer id){
        ArrayList<Item> items = null;

        if(id != null){
            switch (id){
                case 0:
                    return "redirect:/";
                case 1:
                    items = DBManager.getActiveItems();
                    model.addAttribute("select", "Active");
                    break;
                case 2:
                    items = DBManager.getDoneItems();
                    model.addAttribute("select", "Done");
                    break;
            }
        } else{
            items = DBManager.getItems();
        }

        MyDate myDate = new MyDate();

        for(Item item : items){
            if(item.getDate().getTime() - myDate.getTime() < (24*60*60*1000) && !item.isDone()){
                item.setColor("#ed2f2f");
                item.setFColor("white");
            } else{
                item.setColor("white");
                item.setFColor("black");
            }

            if(item.isDone()){
                item.setColor("#8f8f8f");
            }
        }

        Date date = new Date(myDate.getTime());
        model.addAttribute("date", date);

        model.addAttribute("items", items);
        return "index";
    }

    @PostMapping(value = "/")
    public String addItem(@RequestParam(name="text") String text,
                        @RequestParam(name="date") Date date){

        Item item = new Item(null, text, false, date);
        DBManager.addItem(item);
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteItem(@RequestParam(name="id") Integer id){
        DBManager.deleteItem(id);

        return "redirect:/";
    }

    @GetMapping(value = "/edit")
    public String getEdit(Model model, @RequestParam(name = "id") Integer id){
        Item item = DBManager.getItem(id);
        model.addAttribute("item", item);
        if(item.isDone()){
            model.addAttribute("select", "Done");
        } else{
            model.addAttribute("select", "Active");
        }
        return "edit";
    }

    @PostMapping(value = "/edit")
    public String changeToDo(Model model,
            @RequestParam(name = "change") String text,
                             @RequestParam(name = "id") Integer id,
                             @RequestParam(name = "done") String done,
                             @RequestParam(name = "date") Date date){
        boolean truth = false;
        if(done.equalsIgnoreCase("Active")){
            truth = false;

        } else{
            truth = true;
        }

        Item item = new Item(id, text, truth, date);

        if(DBManager.saveItem(item)){
            return "redirect:/";
        } else {
            return "redirect:/edit" + id;
        }

    }
}
