package pmcs.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import pmcs.exception.configuration.CanNotPassEmptyKey;
import pmcs.exception.configuration.ConfigureFileNotExitException;
import pmcs.exception.configuration.KeyNotExitException;

/**
 * this class load the xml file to configure the program
 * 
 * @author steven
 * 
 */
public class Configuration {

	// cache the configuration information
	private Map<String, String> properties = new HashMap<String, String>();

	// the default path of configuration file
	private String filePath = System.getProperty("user.dir") + File.separator
			+ "conf" + File.separator + "config.xml";

	public Configuration(String filePath) throws ConfigureFileNotExitException {
		super();
		this.filePath = filePath;
		configure(filePath);
	}

	/**
	 * constructor
	 * 
	 * @throws ConfigureFileNotExitException
	 */
	public Configuration() throws ConfigureFileNotExitException {
		configure();
	}

	/**
	 * read the configuration file with Dom4j
	 * 
	 * @param filePath
	 *            the configuration file's relative location
	 * @throws ConfigureFileNotExitException
	 */
	private void configure(String filePath)
			throws ConfigureFileNotExitException {

		if (filePath == null) {
			filePath = this.filePath;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			File file = new File(filePath);

			if (!file.exists()) {
				throw new ConfigureFileNotExitException(filePath);
			}

			document = reader.read(file);
			Element rootElm = document.getRootElement();
			List<Element> nodes = rootElm.elements("property");

			for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String value = elm.getText();
				Iterator<Attribute> attributes = elm.attributes().iterator();
				while (attributes.hasNext()) {
					Attribute attr = (Attribute) attributes.next();
					if (attr.getName().equals("name")) {
						String key = attr.getText();
						properties.put(key, value);
					}

				}

			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * reload the configure file
	 * 
	 * @throws ConfigureFileNotExitException
	 */
	private void configure() throws ConfigureFileNotExitException {
		configure(null);
	}

	/**
	 * get the property
	 * 
	 * @param key
	 *            the key of the configuration item
	 * @return the value of the configuration item
	 * @throws CanNotPassEmptyKey
	 * @throws KeyNotExitException
	 */
	public String getProperty(String key) throws CanNotPassEmptyKey,
			KeyNotExitException {
		if (key == null)
			throw new CanNotPassEmptyKey();
		if (properties.containsKey(key)) {
			return properties.get(key);
		} else {
			throw new KeyNotExitException(key);
		}
	}

}
