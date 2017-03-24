package micronet.tools.annotation;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import org.springframework.util.StringUtils;

import micronet.annotation.MessageService;

public class ServiceAnnotatedClass {

	private TypeElement annotatedClassElement;
	private String qualifiedSuperClassName;
	private String simpleTypeName;
	private String uri;

	public ServiceAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
		this.annotatedClassElement = classElement;
		MessageService annotation = classElement.getAnnotation(MessageService.class);
		uri = annotation.uri();

		if (StringUtils.isEmpty(uri)) {
			throw new IllegalArgumentException(
					String.format("id() in @%s for class %s is null or empty! that's not allowed",
							MessageService.class.getSimpleName(), classElement.getQualifiedName().toString()));
		}

		// Get the full QualifiedTypeName
		try {
			Class<?> clazz = classElement.getClass();
			qualifiedSuperClassName = clazz.getCanonicalName();
			simpleTypeName = clazz.getSimpleName();
		} catch (MirroredTypeException mte) {
			DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
			TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
			qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
			simpleTypeName = classTypeElement.getSimpleName().toString();
		}
	}

	public String getUri() {
		return uri;
	}

	public String getQualifiedFactoryGroupName() {
		return qualifiedSuperClassName;
	}

	public String getSimpleFactoryGroupName() {
		return simpleTypeName;
	}

	public TypeElement getTypeElement() {
		return annotatedClassElement;
	}
}
