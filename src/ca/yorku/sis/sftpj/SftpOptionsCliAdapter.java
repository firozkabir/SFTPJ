package ca.yorku.sis.sftpj;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class SftpOptionsCliAdapter {
	
	
	private static SftpOptions sftpOptions = new SftpOptions();
	
	private static void prepareCliOptions(String[] args) {
		
		// setting up CLI options
		Options options = new Options();
				
		Option sftpHostOption = new Option("h","sftp-host",true,"SFTP Host: -h server.domain.ca");
		sftpHostOption.setRequired(true);
		options.addOption(sftpHostOption);
		
		
		Option sftpPortOption = new Option("p","sftp-port",true,"SFTP Port: -p 22");
		sftpPortOption.setRequired(true);
		options.addOption(sftpPortOption);
		
		Option sftpUsernameOption = new Option("u","sftp-username",true,"SFTP Username: -u myusername");
		sftpUsernameOption.setRequired(true);
		options.addOption(sftpUsernameOption);
		
		Option sftpPasswordOption = new Option("pw","sftp-password",true,"SFTP Password or Private Key: -pw mypassword | -pw /home/user/.ssh/privateKeyFile -k");
		sftpPasswordOption.setRequired(true);
		options.addOption(sftpPasswordOption);
		
		Option sftpAction = new Option("a","sftp-action",true,"SFTP Action: -a upload | download | list | download-all ");
		sftpAction.setRequired(true);
		options.addOption(sftpAction);
		
		Option sftpRemoteFileOption = new Option("r","sftp-remotefile",true,"SFTP Remote File Path: -r remote/file.path");
		sftpRemoteFileOption.setRequired(true);
		options.addOption(sftpRemoteFileOption);
		
		
		Option sftpLocalFileOption = new Option("l","sftp-localfile",true,"SFTP Local File Path: -l /local/file.path");
		sftpLocalFileOption.setRequired(true);
		options.addOption(sftpLocalFileOption);
		
		Option sftpVerbosityOption = new Option("v", "verbose", false, "Verbose Information");
		sftpVerbosityOption.setRequired(false);
		options.addOption(sftpVerbosityOption);
		
		Option sftpUsePrivateKeyOption = new Option ("k", "use-key", false, "Use Private Key");
		sftpUsePrivateKeyOption.setRequired(false);
		options.addOption(sftpUsePrivateKeyOption);
		
		Option sftpCipherOption = new Option("c", "cipher", false, "Cipher Required");
		sftpCipherOption.setRequired(false);
		options.addOption(sftpCipherOption);
		
		
		Option sftpDeleteRemoteOption = new Option("d", "delete-remote", false, "Delete Remote File After Download");
		sftpDeleteRemoteOption.setRequired(false);
		options.addOption(sftpDeleteRemoteOption);
		
		Option sftpListLongOption = new Option("ll", "list-long", false, "List remote files in long format");
		sftpListLongOption.setRequired(false);
		options.addOption(sftpListLongOption);
		
		
		Option sftpQuietModeOption = new Option("q", "quiet-mode", false, "Echoes nothing other than file listing. Opposite of verbose");
		sftpQuietModeOption.setRequired(false);
		options.addOption(sftpQuietModeOption);
		
			
		
		CommandLineParser parser = new org.apache.commons.cli.DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		
		
		try {
			
			
			CommandLine cmd = parser.parse(options, args);
			
			sftpOptions.setHost(cmd.getOptionValue("h"));
			sftpOptions.setPort(Integer.parseInt(cmd.getOptionValue("p")));
			sftpOptions.setUsername(cmd.getOptionValue("u"));
			sftpOptions.setPasswordOrPrivateKey(cmd.getOptionValue("pw"));
			sftpOptions.setAction(cmd.getOptionValue("a"));
			sftpOptions.setRemoteFilePath(cmd.getOptionValue("r"));
			sftpOptions.setLocalFilePath(cmd.getOptionValue("l"));
			sftpOptions.setVerbose(cmd.hasOption("v"));
			sftpOptions.setUsePrivateKey(cmd.hasOption("k"));
			sftpOptions.setCipher(cmd.getOptionValue("c"));
			sftpOptions.setDeleteRemoteFile(cmd.hasOption("d"));
			sftpOptions.setListLong(cmd.hasOption("ll"));
			sftpOptions.setQuiet(cmd.hasOption("q"));
			
			
		} catch (ParseException e) {
		
			System.out.println("SFTPJ v0.0.7 FK 20190925 ");
			System.err.println(e.getMessage());
			
			String helpString = "java -jar sftpj.jar -h server.domain.ca -p 22 -u username -pw mypassword -a download -d -r remote/file.path -l /local/file.path -v \n" + 
					            " OR java -jar sftpj.jar -h server.domain.ca -p 22 -u username -pw /path/to/key/file -k -a download -d -r remote/file.path -l /local/file.path -v" + 
					            " OR java -jar sftpj.jar -h server.domain.ca -p 22 -u username -pw /path/to/key/file -k -a list -ll -r remote/file.path -l /local/file.path -v";
			
			formatter.printHelp( helpString, options);
			System.exit(1); return;
			
		} catch (NumberFormatException e) {
			
			System.err.println(e.getMessage());
			formatter.printHelp("cli", options);
			System.exit(1); return;
			
		}
		
		
		
		
		
	}
	
	private static void checkIfRunning (int port) {
		
		try {
			
			
			ServerSocket socket = new ServerSocket(port, 0, InetAddress.getByAddress( new byte[] { 127,0, 0,1 } ) );
			
		} catch (BindException e) {
			
									
			System.err.println("Port " + port + " is already taken. This application will not start. Calling system exit with exit code 99.");
			System.exit(99);
			
			
		} catch (IOException e) {
			
			e.printStackTrace(System.err);
			
			
		}
		
		
	} // end of checkIfRunning (int)
	
	
	
	public static SftpOptions getSftpOptionsObject(String[] args) {
		
		prepareCliOptions(args);
		
		checkIfRunning(5901);
		
		return sftpOptions;
		
	}
	
	
	 
	
}
