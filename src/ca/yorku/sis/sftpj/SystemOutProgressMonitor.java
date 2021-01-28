package ca.yorku.sis.sftpj;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

public class SystemOutProgressMonitor implements SftpProgressMonitor
{

	private String srcFile;
	private String destFile;
	
			
	public void init(int op, java.lang.String src, java.lang.String dest, long max) 
    {
        
    	    	
		this.srcFile = src;
		this.destFile = dest;
		  	
    	
    }

    
    public boolean count(long bytes)
    {
    	    	
        return(true);
    }

     

    public void end()
    {
        
    	
    	           	
    	System.out.println("downloaded " + this.srcFile + " -> " + this.destFile );
    	ca.yorku.sis.sftpj.SFTPJ.remoteFileList.add(this.srcFile);
    	
    	
        
        
    }
    
    
    
    
}
