/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author julian-blaschke
 */
public enum Statements {
    SELECT_HOTELS("select * from hotels"),
    SELECT_BOOKINGS_BY_HOTEL("select id,id_hotel,check_in,check_out,number_rooms,guest,ora_rowscn from bookings where id_hotel = ?"),
    INSERT_BOOKING("insert into bookings values (SEQBOOKINGS.nextval,?,?,?,?,?)"),
    UPDATE_BOOKING("update bookings set guest = ?, check_in = ?, check_out = ?, number_rooms = ? where id = ? AND NVL(ora_rowscn,0) = ?"),
    DELETE_BOOKING("delete from bookings where id = ? AND NVL(ora_rowscn,0) = ?");
    
    private final String statement;
    
    private Statements(String name){
        this.statement = name;
    }
    
    public String getStatement(){
        return this.statement;
    }

}
