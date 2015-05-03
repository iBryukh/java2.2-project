package logger;

public class Logger {

	private static boolean logging = true;
	
	private Logger(){}
	
	public static void disableLogs(){
		logging = false;
	}
	
	public static void info(String info){
		if(logging)
			System.out.println("INFO: " + info);
	}
	
	public static void error(String error){
		if(logging)
			System.err.println("ERROR: " + error);
	}
}
