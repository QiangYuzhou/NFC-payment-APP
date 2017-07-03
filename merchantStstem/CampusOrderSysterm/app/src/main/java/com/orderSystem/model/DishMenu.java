package com.orderSystem.model;

import java.util.ArrayList;

/**
 * Created by CT on 08/06/2017.
 */

public class DishMenu {
    private String menuName;
    private ArrayList<Dish> dishList;

    public DishMenu(){

    }

    public DishMenu(String menuName,ArrayList dishList){
        this.menuName = menuName;
        this.dishList = dishList;
    }

    public ArrayList<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<Dish> dishList) {
        this.dishList = dishList;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
