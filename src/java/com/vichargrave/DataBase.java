/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vichargrave;
import java.sql.*;
import java.util.HashMap;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author user
 */
public class DataBase {
Logger log=LoggerFactory.getLogger(DataBase.class);
private Connection c=null;
public DataBase(){
String driver ="com.mysql.jdbc.Driver";
try {
Class.forName(driver).newInstance();
} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
log.debug("Error of DB Driver" );
}
try {
c = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

}

catch (SQLException e) {
log.debug("Error DB connect" );
}

}
public void cleanTable() throws SQLException
{
Statement st;

st = c.createStatement();
st.executeUpdate("TRUNCATE TABLE WordCount");
log.info("Таблица очищена");
}
public synchronized void insetPage(String pageName, HashMap<String, Integer> wordToCount)
{
for (Entry<String, Integer> entry : wordToCount.entrySet())
{
Statement st;
try {
st = c.createStatement();
String key = entry.getKey().toString();
String text ="INSERT INTO WordCount(Word, Count, Adress) VALUES (\""+key+"\",'"+entry.getValue()+"','"+pageName+"')";
st.executeUpdate(text);
} catch (SQLException e) {
log.error("Insert error");
}	
}
log.info("Еще одна запись в таблицу");
}
public void closeConn()
{
if (c!=null)
{try {
c.close();
} catch (SQLException e) {
log.error("Error connection close" );


}}
}
public String selectAll()
{String result=new String();
try (
Statement st = c.createStatement();
ResultSet rs = st.executeQuery("select * from WordCount");
) {
    while ( rs.next() ) {
 
        
         result= result+rs.getObject(1) +"--"+ rs.getObject(2)+"--"+rs.getObject(3)+"--" + rs.getObject(4)+"\n";
        
    }
} catch (SQLException e) {
log.error("Selrct errror");
}
return result;
}
public String search(String word)
{
    String result=new String();
try (
Statement st = c.createStatement();
ResultSet rs = st.executeQuery("SELECT Count, Adress, Word FROM test.WordCount Where Word=\""+word+"\" Order by Count Desc ");
) {	
if (!rs.next()){
result="На ваш запрос ничего не найдено";
}
else{System.out.println( rs.getObject(1) +"--"+ rs.getObject(2)+"--"+rs.getObject(3));
while ( rs.next() ) {	
result=result+rs.getObject(1) +"--"+ rs.getObject(2)+"--"+rs.getObject(3)+"\n";
}}	
} catch (SQLException e) {
log.error("Selrct errror");
}
return  result;

}
}