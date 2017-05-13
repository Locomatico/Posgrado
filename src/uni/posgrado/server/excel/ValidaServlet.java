package uni.posgrado.server.excel;


import uni.posgrado.factory.JpaUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
 
 
/**
 * Servlet implementation class ExcelServlet
 */
public class ValidaServlet extends HttpServlet 
{

  private static final long serialVersionUID = 1L;
     
     
    public ValidaServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public boolean validaCodigoPrograma() {    	
    	return true;
    }
 
    
}