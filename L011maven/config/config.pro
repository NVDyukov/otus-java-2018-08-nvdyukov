-injars		gson-2.8.5.jar(!META-INF/MANIFEST.MF)
-injars 	fatJar.jar(!com/google/gson/**,!META-INF/maven/**)
-outjars	fJ070901.jar
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.base.jmod"(!**.jar;!module-info.class)
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.logging.jmod"(!**.jar;!module-info.class)
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.xml.jmod"(!**.jar;!module-info.class)
-libraryjars	"c:\Program Files\Java\jdk-10.0.2\jmods\java.sql.jmod"(!**.jar;!module-info.class)

-keep		public class ru.otus.Run { 
      		public static void main(java.lang.String[]); 
}

-dontnote com.google.gson.**
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses,EnclosingMethod
-repackageclasses 'gameofthrones' 
-allowaccessmodification
