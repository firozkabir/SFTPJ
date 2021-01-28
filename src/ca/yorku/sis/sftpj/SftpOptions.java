package ca.yorku.sis.sftpj;

public class SftpOptions {

	private String host;
	private int port;
	private String username;
	private String passwordOrPrivateKey;
	private String action;
	private String remoteFilePath;
	private String localFilePath;
	private boolean verbose;
	private boolean usePrivateKey;
	private String  cipher;
	private boolean deleteRemoteFile;
	private boolean listLong;
	private boolean quiet; 
	
	public SftpOptions() {
		
	}
	
	public SftpOptions(String host, 
			           int port, 
			           String username, 
			           String passwordOrPrivateKey, 
			           String action, 
			           String remoteFilePath, 
			           String localFilePath, 
			           boolean verbose, 
			           boolean usePrivateKey, 
			           String cipher, 
			           boolean deleteRemoteFile,
			           boolean listLong,
			           boolean quiet) {
		
		this.host = host;
		this.port = port;
		this.username = username;
		this.passwordOrPrivateKey = passwordOrPrivateKey;
		this.action = action;
		this.remoteFilePath = remoteFilePath;
		this.localFilePath = localFilePath;
		this.verbose = verbose;
		this.usePrivateKey = usePrivateKey;
		this.cipher = cipher;
		this.deleteRemoteFile = deleteRemoteFile;
		this.listLong = listLong;
		this.quiet = quiet;
		
		
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPasswordOrPrivateKey() {
		return passwordOrPrivateKey;
	}


	public void setPasswordOrPrivateKey(String passwordOrPrivateKey) {
		this.passwordOrPrivateKey = passwordOrPrivateKey;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getRemoteFilePath() {
		return remoteFilePath;
	}


	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}


	public String getLocalFilePath() {
		return localFilePath;
	}


	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	
	public boolean getVerbose() {
		return verbose;
	}
	
	public void setVerbose(boolean verbose) {
		
		this.verbose = verbose;
	}
	
	public boolean getUsePrivateKey() {
		return usePrivateKey;
	}
	
	public void setUsePrivateKey(boolean usePrivateKey) {
		
		this.usePrivateKey = usePrivateKey;
	}
	
	public String getCipher(){
		
		return cipher;
	}
	
	public void setCipher(String cipher) {
		
		this.cipher = cipher;
	}
	
	public boolean getDeleteRemoteFile() {
		return deleteRemoteFile;
	}
	
	public void setDeleteRemoteFile(boolean deleteRemoteFile) {
		
		this.deleteRemoteFile = deleteRemoteFile;
	}
	
		
	public boolean isListLong() {
		return listLong;
	}

	public void setListLong(boolean listLong) {
		this.listLong = listLong;
	}

	
	public boolean isQuiet() {
		return quiet;
	}

	public void setQuiet(boolean quiet) {
		this.quiet = quiet;
	}

	
	public String toString() {
		
		return  "host: " + getHost() + "\n" +
				"port: " + getPort() + "\n" +
				"username: " + getUsername() + "\n" +
				(this.getUsePrivateKey() ?  "private-key: " + this.getPasswordOrPrivateKey() : "password: **********" ) + "\n" +
				"action: " + getAction() + "\n" +
				"remoteFilePath: " + getRemoteFilePath() + "\n" +
				"localFilePath: " + getLocalFilePath() + "\n" +
				"cipher: " + getCipher() + "\n" + 
				"delete remote file after download: " + getDeleteRemoteFile() + "\n" +
				"list long: " + isListLong() + "\n" + 
				"quiet mode: " + isQuiet() + "\n" 
				;
		
	}
	
	
}
