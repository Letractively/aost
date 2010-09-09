package telluriumworks

import javax.swing.ImageIcon
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 22, 2010
 * 
 */
class ResourceUtils {

  private static final ResourceUtils INSTANCE = new ResourceUtils();
  private ResourceBundle bundle;
  private ClassLoader defaultClassLoader;

  private ResourceUtils()
  {
      defaultClassLoader = (ResourceUtils.class).getClassLoader();
  }

  public static void setBundlePath(String path)
  {
/*
      java.net.URL url = ClassLoader.getResource(path);
      System.out.println("PATH: resolved name = " + url);
      def input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)
      System.out.println("Resolved input = " + input);
*/

//      INSTANCE.bundle = new PropertyResourceBundle(ResourceUtils.class.getResourceAsStream(path));
     INSTANCE.bundle = new PropertyResourceBundle(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
  }

  public static Properties loadProperties(name) throws Exception {
      Properties properties = new Properties();
      java.util.Properties p = System.getProperties();
//      String serverBase = p.getProperty(SERVER_BASE);
//      if(serverBase != null && serverBase.trim().length() > 0){
//          name = serverBase + "/resources/sacct.properties";
//      }
      FileInputStream fin = new FileInputStream(name);
      BufferedReader br = new BufferedReader(new InputStreamReader(fin));
      properties.load(br);

      br.close();
      fin.close();

      return properties;
  }

  public static String getString(String key)
  {
      try {
          return INSTANCE.bundle.getString(key);
      } catch (MissingResourceException e) {
//			 Not an error because we will use the default
//			System.err.println("Missing resource for key:" + key);
//			e.printStackTrace();
      }
      return null;
  }

  public static ImageIcon getIcon(String key)
  {
      String path = getString(key);
      return null != path ? readImageIcon(path) : null;
  }

  private static URL getURL(String path)
  {
      return getURL(path, INSTANCE.defaultClassLoader);
  }

  private static URL getURL(String path, ClassLoader classLoader)
  {
      URL url = classLoader.getResource(path);
      if(null == url)
          System.err.println(path + " not found.");
      return url;
  }

  public static ImageIcon readImageIcon(String path)
  {
      URL url = getURL(path);
      return null != url ? new ImageIcon(url) : null;
  }  
}
