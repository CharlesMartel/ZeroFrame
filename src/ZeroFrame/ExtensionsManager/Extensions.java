/**
 * 
 */
package ZeroFrame.ExtensionsManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import ZeroFrame.Extensions.Module;

/**
 * @author Hammer
 * 
 */
public class Extensions {

	// Three lists to hold all loaded extensions
	private static List<ZeroFrame.Extensions.Module> Modules = new ArrayList<ZeroFrame.Extensions.Module>(0);

	/**
	 * Load all modules that exist in the extensions folder. This method is
	 * recursive, so if it exists anywhere in the directory, no matter how many
	 * levels deep, the module will be loaded. Modules are loaded on a single
	 * thread, so if a module blocks, the entire application will not start
	 * 
	 * @param extensionsFolder
	 * @return The number of modules found
	 * @throws Exception
	 */
	public static int loadModules(String extensionsFolder) throws Exception {
		int numberOfModules = 0;

		// get the files from the directory
		File dir = new File(extensionsFolder);
		File[] files = dir.listFiles();

		// check to see if any files are even in the directory
		if (files != null) {
			// loop through the files, add modules as they are found
			for (File f : files) {
				List<String> classNames = getClassNames(f.getAbsolutePath());
				for (String className : classNames) {
					// Remove the ".class" at the back
					String name = className.substring(0, className.length() - 6);
					// retrieve the current class
					Class<?> currentClass = getClass(f, name);
					// get which class the current class is inherited from
					Class<?> superClass = currentClass.getSuperclass();
					// check if is valid module
					if (superClass.getName().equals("ZeroFrame.Extensions.Module")) {
						// instantiate new instance, add to modules list
						Modules.add((Module) currentClass.newInstance());
						numberOfModules += 1;
					}
				}
			}
		}
		return numberOfModules;
	}

	/**
	 * Retrieves an array list of all currently loaded modules.
	 * 
	 * @return List<ZeroFrame.Extensions.Module>
	 */
	public static List<ZeroFrame.Extensions.Module> getModuleList() {
		return Modules;
	}

	private static List<String> getClassNames(String jarName) throws FileNotFoundException, IOException {
		ArrayList<String> classes = new ArrayList<String>(10);
		JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
		JarEntry jarEntry;
		while (true) {
			jarEntry = jarFile.getNextJarEntry();
			if (jarEntry == null) {
				break;
			}
			if (jarEntry.getName().endsWith(".class")) {
				classes.add(jarEntry.getName().replaceAll("/", "\\."));
			}
		}
		return classes;
	}

	private static Class<?> getClass(File file, String name) throws Exception {
		addURL(file.toURI().toURL());
		URLClassLoader loader;
		Class<?> currentClass;
		String filePath = file.getAbsolutePath();
		filePath = "jar:file://" + filePath + "!/";
		URL url = new File(filePath).toURI().toURL();
		loader = new URLClassLoader(new URL[] { url });
		currentClass = loader.loadClass(name);
		return currentClass;
	}

	private static final Class<?>[] parameters = new Class[] { URL.class };

	private static void addURL(URL u) throws IOException {
		URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		URL urls[] = sysLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {
			if (urls[i].toString().equalsIgnoreCase(u.toString())) {
				return;
			}
		}
		Class<?> sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
}
