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
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import micronet.annotation.MessageListener;
import micronet.annotation.MessageService;
import micronet.annotation.OnStart;
import micronet.annotation.OnStop;

public class ServiceAnnotationProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

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
		
		ServiceDescription description = new ServiceDescription();
		
		description.setMessageListeners(roundEnv.getElementsAnnotatedWith(MessageListener.class));
		description.setStartMethods(roundEnv.getElementsAnnotatedWith(OnStart.class));
		description.setStopMethods(roundEnv.getElementsAnnotatedWith(OnStop.class));
		
		
		for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(MessageService.class)) {
			// Check if a class has been annotated with @Factory
			if (annotatedElement.getKind() != ElementKind.CLASS) {
				error(annotatedElement, "Only classes can be annotated with @%s", MessageService.class.getSimpleName());
				return true; // Exit processing
			}
			
			description.setService(annotatedElement);
			
			if (!generateServiceImplementation(description)) {
				error(annotatedElement, "Error processing serviceElement:", annotatedElement.getSimpleName());
				return true; // Exit processing
			}
		}
				
		return true;
	}
	
	private boolean generateServiceImplementation(ServiceDescription description) {
		log(description.getService(), "Generating Service implementation: " + description.getService().getSimpleName());

		MessageService annotation = description.getService().getAnnotation(MessageService.class);
		
		String additionalImports = "import " + description.getService().toString() + ";\n\n";
		String serviceClassName = description.getName() + "Impl";
		
		String startCode = generateStartCode(description);
		String stopCode = generateStopCode(description);
		String listenerCode = generateListenerCode(description);

		InputStream resourceAsStream = ServiceAnnotationProcessor.class.getResourceAsStream("ServiceTemplate.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
		
		try {

			JavaFileObject file = filer.createSourceFile(serviceClassName,  description.getService());
			Writer writer = file.openWriter();
			
			String line = reader.readLine();
			while (line != null) {
				log(null, "File Content: " + line);

				
				line = line.replaceAll(Pattern.quote("${additional_imports}"), additionalImports);
				line = line.replaceAll(Pattern.quote("${service_class}"), serviceClassName);
				line = line.replaceAll(Pattern.quote("${service_name}"), description.getName());
				line = line.replaceAll(Pattern.quote("${service_uri}"), annotation.uri());
				line = line.replaceAll(Pattern.quote("${service_variable}"), description.getServiceVariable());
				line = line.replaceAll(Pattern.quote("${peer_variable}"), description.getPeerVariable());
				line = line.replaceAll(Pattern.quote("${on_start}"), startCode);
				line = line.replaceAll(Pattern.quote("${on_stop}"), stopCode);
				line = line.replaceAll(Pattern.quote("${register_listeners}"), listenerCode);
				
				
				writer.append(line + "\n");
				line = reader.readLine();
			}
			
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	private String generateListenerCode(ServiceDescription description) {
				
		StringBuilder code = new StringBuilder();
		
		for (Element method : description.getMessageListeners()) {
			MessageListener annotation = method.getAnnotation(MessageListener.class);
			code.append(description.getPeerVariable() + ".listen(\"" + annotation.uri() + "\", (Request request) -> ");
			code.append(description.getServiceVariable() + "." + method.getSimpleName() + "(context, request));\n");
		}
		
		// TODO Auto-generated method stub
		return code.toString();
	}

	private String generateStopCode(ServiceDescription description) {
		StringBuilder code = new StringBuilder();
		code.append("System.out.println(\"" + description.getName() +  " stopped...\");\n");
		
		for (Element method : description.getStopMethods()) {
			code.append(description.getServiceVariable() + "." + method.getSimpleName() + "();\n");
		}
		
		return code.toString();
	}

	private String generateStartCode(ServiceDescription description) {
		
		StringBuilder code = new StringBuilder();
		code.append("System.out.println(\"" + description.getName() +  " started...\");\n");
		
		for (Element method : description.getStartMethods()) {
			code.append(description.getServiceVariable() + "." + method.getSimpleName() + "();\n");
		}
		
		return code.toString();
	}

	private void log(Element e, String msg) {
		messager.printMessage(Kind.NOTE, msg);
	}
	
	private void error(Element e, String msg, Object... args) {
		messager.printMessage(Kind.ERROR, String.format(msg, args), e);
	}
}
