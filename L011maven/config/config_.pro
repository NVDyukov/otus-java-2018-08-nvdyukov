-injars		guava-23.0.jar(!com/google/common/**,!com/google/thirdparty/publicsuffix/**,!META-INF/MANIFEST.MF)
-injars 	animal-sniffer-annotations-1.14.jar(!org/codehaus/mojo/animal_sniffer/IgnoreJRERequirement.class,!META-INF/MANIFEST.MF)
-injars		error_prone_annotations-2.0.18.jar(!com/google/errorprone/annotations/**,!META-INF/MANIFEST.MF)
-injars		j2objc-annotations-1.1.jar(!com/google/j2objc/annotations/**,!META-INF/MANIFEST.MF)
-injars		jsr305-1.3.9.jar(!javax/annotation/**,!META-INF/MANIFEST.MF)
-injars 	fatJar.jar(!META-INF/maven/**)
-outjars	fJ040901.jar
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.base.jmod"(!**.jar;!module-info.class)
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.logging.jmod"(!**.jar;!module-info.class)
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.compiler.jmod"(!**.jar;!module-info.class)

-keep		public class ru.otus.l011.Main { 
      		public static void main(java.lang.String[]); 
}

-dontwarn sun.misc.Unsafe
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses,EnclosingMethod
-dontnote com.google.common.**
-repackageclasses 'myobfuscated' 
-allowaccessmodification
-printconfiguration config.txt