/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Marcel Judth
 */
@Path("/Calculator")
public class CalculatorWS {
    @GET
    @Path("/info/")
    @Produces("application/json")
    public String getCalculationInfo(){
        return "Hello this is the Calculator";
    }
}
