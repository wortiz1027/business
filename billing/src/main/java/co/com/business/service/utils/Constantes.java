package co.com.business.service.utils;

public class Constantes {
	
	public static final String RESOURCE_ID_KEY = "oauth2.resource_id";
	public static final String CLIENT_ID_KEY = "oauth2.client_id";
	public static final String CLIENT_SECRET_KEY = "oauth2.client_secret";
	public static final String SERVER_CHECK_TOKEN_URL_KEY = "oauth2.server_check_token_url";
	public static final String SERVER_CHECK_TOKEN_URL_SSL_KEY = "oauth2.ssl.server_check_token_url";
	
	public static final String BASE_MESSAGES_KEY = "message.source.basename";
	public static final String ERROR_CLIENT_NOT_FOUND_KEY = "error.client.not.found";
	
	public static final String JNDI_DATASOURCE_KEY = "jndi.datasource";
	
	public static final String MSG_ERROR_CLIENTE_NO_REGISTRADO = "El cliente '%s' no está registrado";
	public static final String MSG_ERROR_USUARIO_NO_REGISTRADO = "El usuario '%s' no está registrado";
	
	public static final String WINDOWS_FILE_FOLDER = "C:\\software\\files\\"; 
	public static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5MB
    
	public static final String DATE_FILE = "dd/MM/yyyy hh:mm:ss.sss";
	public static final String FORMAT_DATE_FILE = "ddMMyyyy_hhmmss";
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	public static final String[] CONTENT_TYPES = new String[] {
		"application/pdf",
		"application/doc",			
		"application/msword",
		"application/rtf",			
		"text/richtext" , 
		"text/rtf" , 
		"text/plain" , 
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document" , 
		"application/vnd.sun.xml.writer" ,
		"application/x-soffice" ,
		};

	public static final String[] EXTENSIONS = new String[] {
		"doc",
		"pdf",
		"docx",
		"rtf",	
		"txt",	
	};
	
}