package uni.posgrado.server;

import javax.naming.ldap.LdapContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import uni.posgrado.gwt.LoginGwtRPCDSService;


public class LoginGwtRPCDSServiceImpl
extends RemoteServiceServlet
implements LoginGwtRPCDSService {

	private static final long serialVersionUID = 1L;

	public Boolean login(String username, String password) {
		// TODO Auto-generated method stub
		
		/*Properties propiedades = new Properties();
		try {
			propiedades.load(new FileInputStream(this.getServletContext().getRealPath("/")+"resources/posgrado.properties"));
			String server1 = propiedades.getProperty("ldap_server1");
			String server2 = propiedades.getProperty("ldap_server2");
			try{
				LdapContext ctx = ActiveDirectory.getConnection(username, password, "upch.edu.pe", server1);
			    ctx.close();
			    return true;
			}
			catch(Exception e){			
				try{
					LdapContext ctx = ActiveDirectory.getConnection(username, password, "upch.edu.pe", server2);
				    ctx.close();
				    return true;
				}
				catch(Exception e1){			
					//System.out.println(e.getMessage());
					e1.printStackTrace();
					e.printStackTrace();
					return true;
				}
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		} 	*/
		return true;

	   
		
	}
}