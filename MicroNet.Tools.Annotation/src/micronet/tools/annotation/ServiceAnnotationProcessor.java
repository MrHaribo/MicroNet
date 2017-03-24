package micronet.tools.annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import micronet.annotation.MessageService;

public class ServiceAnnotationProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;
	private Map<String, ServiceAnnotatedClass> serviceClasses = new LinkedHashMap<String, ServiceAnnotatedClass>();

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotataions = new LinkedHashSet<String>();
		annotataions.add(MessageService.class.getCanonicalName());
		return annotataions;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(MessageService.class)) {
			// Check if a class has been annotated with @Factory
			if (annotatedElement.getKind() != ElementKind.CLASS) {
				error(annotatedElement, "Only classes can be annotated with @%s", MessageService.class.getSimpleName());
				return true; // Exit processing
			}
			
			if (!generateServiceImplementation(annotatedElement)) {
				error(annotatedElement, "Error processing serviceElement:", annotatedElement.getSimpleName());
				return true; // Exit processing
			}
		}
				
		return true;
	}
	
	private boolean generateServiceImplementation(Element serviceElement) {
		log(serviceElement, "Generating Service implementation: " + serviceElement.getSimpleName());
		
		MessageService annotation = serviceElement.getAnnotation(MessageService.class);
		
		String serviceName = serviceElement.getSimpleName().toString();
		String serviceClassName = serviceElement.getSimpleName() + "Impl";

		InputStream resourceAsStream = ServiceAnnotationProcessor.class.getResourceAsStream("ServiceTemplate.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
		
		try {
			
			
			JavaFileObject file = filer.createSourceFile( "service/" + serviceClassName,  serviceElement);
			Writer writer = file.openWriter();
			
			String line = reader.readLine();
			while (line != null) {
				log(null, "File Content: " + line);
				line = reader.readLine();

				line = line.replaceAll(Pattern.quote("${service_package}"), "service");
				line = line.replaceAll(Pattern.quote("${service_class}"), serviceClassName);
				line = line.replaceAll(Pattern.quote("${service_name}"), serviceName);
				line = line.replaceAll(Pattern.quote("${service_uri}"), annotation.uri());
				line = line.replaceAll(Pattern.quote("${on_start}"), "System.out.println(" + serviceName +  " + \" started...\");");
				line = line.replaceAll(Pattern.quote("${on_stop}"), "System.out.println(" + serviceName +  " + \" stopped...\");");
				
				writer.append(line + "\n");
			}
			
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	private void log(Element e, String msg) {
		messager.printMessage(Kind.NOTE, msg);
	}
	
	private void error(Element e, String msg, Object... args) {
		messager.printMessage(Kind.ERROR, String.format(msg, args), e);
	}
}
