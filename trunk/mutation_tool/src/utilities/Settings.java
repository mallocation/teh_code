package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Properties;

import sun.misc.IOUtils;

/**
 * This class will be used to represent specific settings within the program.
 * @author cjohnson
 *
 */
public class Settings {
	
	private static Properties _oToolProperties;
	

	private FileOutputStream oSettingsOutput;
	
	public static Properties getToolProperties() {
		if (_oToolProperties == null) {			
			FileInputStream in = null;
			try {
				try {
					URL oFile = new URL("../settings/");
					in = new FileInputStream("toolprops.settings");
					_oToolProperties = new Properties();
					_oToolProperties.load(in);
				} finally {
					in.close();
				}
			} catch (Exception e) {
				createDefaultPropsFile();
			}
		}
		return _oToolProperties;
	}
	
	private static void createDefaultPropsFile() {
		_oToolProperties = new Properties();
		FileOutputStream out = null;
		try {
			try {
				out = new FileOutputStream(System.getProperty("user.dir") + "/settings/toolprops.settings");
				_oToolProperties.store(out, "");
			} finally {
				out.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	
	private static final String sSettingsDirectory = "";
	
	private static final File oSettingsFile = new File(sSettingsDirectory + "../settings");
	
	public static void main(String args[]) {
		System.out.println(Settings.getToolProperties().toString());
	}
	
	
	


}
