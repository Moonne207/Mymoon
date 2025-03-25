/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.procot.DAO;

import java.util.ArrayList;

/**
 *
 * @author skmyg
 */
public interface interfaceDAO<nameModel> {

    public int insert(nameModel model);

    public int update(nameModel model);

    public int delete(nameModel model);

    public ArrayList<nameModel> selectAll();

    public ArrayList<nameModel> search(String column,String keyword);
    
    public ArrayList<nameModel> searchAll(String keyword);
}
