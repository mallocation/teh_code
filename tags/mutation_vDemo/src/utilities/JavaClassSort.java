package utilities;

import java.util.Comparator;

import org.apache.bcel.classfile.JavaClass;

public class JavaClassSort implements Comparator<JavaClass> {

	@Override
	public int compare(JavaClass oClass1, JavaClass oClass2) {
		return oClass1.getClassName().compareToIgnoreCase(oClass2.getClassName());
	}

}
