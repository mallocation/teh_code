package utilities;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

/**
 * This class will be used to represent specific settings within the program.
 * @author teh_code
 *
 */
public class Settings {
	
	@SuppressWarnings("deprecation")
	public String getMutationsFolderPath() {
		URL oRelativeURL = this.getClass().getResource("Settings.class");
		File oThisClass = new File(URLDecoder.decode(oRelativeURL.getPath()));
		File oUtilsDir = new File(oThisClass.getParent());
		File oMainDir = new File(oUtilsDir.getParent());
		File oMutationsDir = new File(oMainDir, "generated_mutations");
		if (!oMutationsDir.exists() || !oMutationsDir.isDirectory()) {
			oMutationsDir.mkdirs();
		}
		return oMutationsDir.getAbsolutePath();		
	}
}