package ca.yorku.sis.sftpj;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPJ {

	
	public static ArrayList<String> remoteFileList = new ArrayList<String>();
	
	public static void main (String[] args) {
		
		
		
		
		
		
		
		SftpOptions sftpOptions = SftpOptionsCliAdapter.getSftpOptionsObject(args);
		
		if (!sftpOptions.isQuiet()) {
			System.out.println("SFTPJ v0.0.8 FK 20200907 ");
		}
		
				
		
		if (sftpOptions.getVerbose()) {
			
			System.out.println("Showing parameters: ");
			System.out.println(sftpOptions.toString());
		
		}
		
		
		
		Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;
        
        try {
        	
            
        	JSch jsch = new JSch();
        	
        	if ( sftpOptions.getVerbose() ) {
        		
        		Logger jdkLogger = Logger.getLogger(SFTPJ.class.getName());
        		jdkLogger.setLevel(Level.ALL);
        		jsch.setLogger(new JSCHLogger(jdkLogger));
        		
        	}
        	
        	session = jsch.getSession(sftpOptions.getUsername(), sftpOptions.getHost(), sftpOptions.getPort());
            
            
            
            if ( sftpOptions.getUsePrivateKey() ) {
            	
            	// using private key instead of password 
            	String privateKey = sftpOptions.getPasswordOrPrivateKey();
            	jsch.addIdentity(privateKey);
            	
            	
            } else {
            	
            	// use password
            	String password = sftpOptions.getPasswordOrPrivateKey();
            	session.setPassword(password);
            	
            }
            
            
            
            session.setConfig("StrictHostKeyChecking", "no");
            
           
            
            if ( sftpOptions.getCipher() != null ) {
            	
            	session.setConfig("cipher.c2s",sftpOptions.getCipher());
            	session.setConfig("cipher.s2c",sftpOptions.getCipher());
            	
            }
            
                     
            
            session.setTimeout(15000);
            
            if (!sftpOptions.isQuiet()) {
            	System.out.println("Connecting...");
            }
            
            
            
            session.connect(15000);
         
           
            	
            channel = session.openChannel("sftp");
            channel.connect();
            
            
                        
            channelSftp = (ChannelSftp)channel;
         
            // download 
            if (sftpOptions.getAction().equals("download")) {
            	
            	
            	if (!sftpOptions.isQuiet())
            	System.out.println("downloading... " + sftpOptions.getUsername() + "@" + sftpOptions.getHost() + ":" + sftpOptions.getRemoteFilePath());
            	
            	
            	channelSftp.get(sftpOptions.getRemoteFilePath(), sftpOptions.getLocalFilePath(), new SystemOutProgressMonitor());
            	
            	
            	
            	if ( sftpOptions.getDeleteRemoteFile() ) {
            		
            		for (String remoteFile : remoteFileList) {
            			
            			channelSftp.rm(remoteFile);
            			
            			if (!sftpOptions.isQuiet())
            			System.out.println("deleted " + remoteFile + " from remote");
            			
            		}
            		
            	}
            	
            	            	
            	
            	if (!sftpOptions.isQuiet())
            	System.out.println("Download command for remote: [" + sftpOptions.getRemoteFilePath() + "] -- to -->  local: ["  + sftpOptions.getLocalFilePath() + "] done.");
            	
            	     	
            		
            	
            	
            
            }
            
                                  
            
            // upload 
            if (sftpOptions.getAction().equals("upload")) {
            
            	if (!sftpOptions.isQuiet()) 
            		System.out.println("uploading... " + sftpOptions.getLocalFilePath());
            	
            	channelSftp.put(sftpOptions.getLocalFilePath(), sftpOptions.getRemoteFilePath(), ChannelSftp.OVERWRITE);
            	
            	if (!sftpOptions.isQuiet())
            		System.out.println("Upload command for local: [" + sftpOptions.getLocalFilePath() + "] -- to --> remote: [" +  sftpOptions.getRemoteFilePath() + "] done.");
            
            }
            	
            	
            // list
            if (sftpOptions.getAction().equals("list")) {
            	
            	if (!sftpOptions.isQuiet())
            		System.out.println("listing... "  + sftpOptions.getUsername() + "@" + sftpOptions.getHost() + ":" +sftpOptions.getRemoteFilePath());
            	
            	Vector<ChannelSftp.LsEntry> filelist = channelSftp.ls(sftpOptions.getRemoteFilePath());
            	
            	if (!sftpOptions.isQuiet())
            		System.out.println("Filecount: " + filelist.size() );
            	
            	for(ChannelSftp.LsEntry entry : filelist ){
                    
            		if (sftpOptions.isListLong()) {
            		
            			System.out.println(entry.getLongname());
            			
            		}  else {
            		
            			System.out.println(entry.getFilename());
            			
            		}
            			
            		
                }
            	
            } // end of list
            
            // download-all
            if (sftpOptions.getAction().contentEquals("download-all")) {
            	
            	Vector<ChannelSftp.LsEntry> filelist = channelSftp.ls(sftpOptions.getRemoteFilePath());
            	
            	for(ChannelSftp.LsEntry entry : filelist) {
            		
            		if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
            		
            			System.out.println("Downloading remote file: " + 
     		                   sftpOptions.getRemoteFilePath() + entry.getFilename() + " -to- " +
     		                   "local file: " + 
     		                   sftpOptions.getLocalFilePath() + entry.getFilename());
     		
            			channelSftp.get(sftpOptions.getRemoteFilePath() + entry.getFilename(), 
            							sftpOptions.getLocalFilePath() + entry.getFilename(),
            							new SystemOutProgressMonitor()
            							);
            			
            		}
            		
            		
            	}
            	
            		
            } // end of download-all
                                
            	
        	
        } catch (Exception e) {
        	
        	    if (e.getMessage().equals("No such file")) {
        	    	
        	    	System.out.println(e.getMessage());
        	    	System.exit(99);
        	    	
        	    } else {
        	    	
        	    	
            		e.printStackTrace(System.err);
                    System.exit(1);
        	    }
        	    
        	    
        		
        	    	
        	
        		
        		
        } finally {
        	
        	channel.disconnect();
        	session.disconnect();
        	
        	if (!sftpOptions.isQuiet())
        		System.out.println("Connections closed.");
        	
        }
        
		
		
		
	}
	
}
