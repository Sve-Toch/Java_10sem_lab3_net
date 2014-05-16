/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vichargrave;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author user
 */
@Path("hello")
public class PageLoad {
    DataBase dB = new DataBase();
    
    @Context
    private UriInfo context;
    public PageLoad()
    {
         dB = new DataBase();
    } 
    
     @Path("index")
     @GET
     @Produces("text/html")
   public String getIndex( @DefaultValue("Nothing to say") @QueryParam("value") String name) {       
       
       Thread pi = new Thread(new PageIndex(name, dB));
       pi.start();
       return  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\" />"
      + "<h1>Индексация страницы:"+name+"</h1>";
   }
       @Path("find")
     @GET
       @Produces("text/html")
   public String getFind(@DefaultValue("Nothing to say") @QueryParam("value") String name) {
       

        return "<h1>"+dB.search(name.toLowerCase())+"</h1>";
    }
     @Path("clean")
     @GET
       @Produces("text/html")
   public String getClean() {
       try {
       dB.cleanTable();
        return  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\" />"
                +"<h1>Таблица очищена</h1>";
       }
       catch (SQLException ex)
       {          return  "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\" />"
+ "Ошибка очистки";}
    }
    @Path("givetable")
       @GET
    @Produces("text/html")
   public String getTable() {
       String result = "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\" />"
               + "<table border=\"1\">"
               + "<caption>Таблица индексированных страниц</caption>"
               + "<tr><th>ID</th><th>Слово</th><th>Сколько раз упомянуто на странице</th><th>страница</th></tr>";
       try{
      ResultSet rs = dB.selectAll();
       if (!rs.next()){
result="На ваш запрос ничего не найдено";
}
else{
           result= result+"<tr><td>"+rs.getObject(1) +"</td><td>"+ rs.getObject(2)+"</td><td>"+rs.getObject(3)+"</td><td>" + rs.getObject(4)+"</td></tr>";
 while ( rs.next() ) {
 
        
         result= result+"<tr><td>"+rs.getObject(1) +"</td><td>"+ rs.getObject(2)+"</td><td>"+rs.getObject(3)+"</td><td>" + rs.getObject(4)+"</td></tr>";
        
    }
       result=result+"</table>";}
       }
       catch (SQLException ex)
       {result=result+"<h1>Ошибка вывода таблицы</h1>";}
        return result;
    }
    @Path("hello2")
    @GET
    @Produces("text/html")
   public String getHtml2() {
       
        return "<h1>Ну что поехали2))</h1>";
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
   public String getHtml() {
       
        return   "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\" />"
                + "<h1>Лабораторная работа номер 3</h1>"
                + "<h1>Точилина Светлана Константиновна </h1>" 
             + "<font color=#0000ff size=\"8\"><p> Для индексации  - /index?value=INDEXNAME <br>" 
               + " Для поиска  - /find?value=WORD <br>"
                +  "Для очистки данных - /clean<br>"
               + "Для вывода таблицы - /givetable</p> ";

    }
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
