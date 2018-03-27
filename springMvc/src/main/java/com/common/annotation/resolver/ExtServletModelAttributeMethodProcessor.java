package com.common.annotation.resolver;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import com.common.annotation.ExtModelAttribute;

public class ExtServletModelAttributeMethodProcessor implements
		HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

	public ExtServletModelAttributeMethodProcessor() {
		this.annotationNotRequired = false;
	}

	public ExtServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
		this.annotationNotRequired = annotationNotRequired;
	}

	// ->From ModelAttributeMethodProcessor

	protected Log logger = LogFactory.getLog(this.getClass());

	private final boolean annotationNotRequired;

	/**
	 * @return true if the parameter is annotated with {@link ModelAttribute} or
	 *         in default resolution mode also if it is not a simple type.
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(ExtModelAttribute.class)) {
			return true;
		} else if (this.annotationNotRequired) {
			return !BeanUtils.isSimpleProperty(parameter.getParameterType());
		} else {
			return false;
		}
	}

	/**
	 * Resolve the argument from the model or if not found instantiate it with
	 * its default if it is available. The model attribute is then populated
	 * with request values via data binding and optionally validated if
	 * {@code @java.validation.Valid} is present on the argument.
	 * 
	 * @throws BindException
	 *             if data binding and validation result in an error and the
	 *             next method parameter is not of type {@link Errors}.
	 * @throws Exception
	 *             if WebDataBinder initialization fails.
	 */
	public final Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		// change here
		String name = ModelFactory_getNameForParameter(parameter);
		Object attribute = (mavContainer.containsAttribute(name)) ? mavContainer
				.getModel().get(name) : createAttribute(name, parameter,
				binderFactory, request);

		WebDataBinder binder = binderFactory.createBinder(request, attribute,
				name);
		if (binder.getTarget() != null) {
			bindRequestParameters(binder, request);
			validateIfApplicable(binder, parameter);
			if (binder.getBindingResult().hasErrors()) {
				if (isBindExceptionRequired(binder, parameter)) {
					throw new BindException(binder.getBindingResult());
				}
			}
		}

		// Add resolved attribute and BindingResult at the end of the model

		Map<String, Object> bindingResultModel = binder.getBindingResult()
				.getModel();
		mavContainer.removeAttributes(bindingResultModel);
		mavContainer.addAllAttributes(bindingResultModel);

		return binder.getTarget();
	}

	// ->Rename as native method! because ServletModelAttributeMethodProcessor
	// has Override it,and use it!
	/**
	 * Extension point to create the model attribute if not found in the model.
	 * The default implementation uses the default constructor.
	 * 
	 * @param attributeName
	 *            the name of the attribute, never {@code null}
	 * @param parameter
	 *            the method parameter
	 * @param binderFactory
	 *            for creating WebDataBinder instance
	 * @param request
	 *            the current request
	 * @return the created model attribute, never {@code null}
	 */
	protected Object ModelAttributeMethodProcessor_CreateAttribute(
			String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory, NativeWebRequest request)
			throws Exception {

		return BeanUtils.instantiateClass(parameter.getParameterType());
	}

	// ->Delete! ServletModelAttributeMethodProcessor has Override it but not
	// use it
	/**
	 * Extension point to bind the request to the target object.
	 * 
	 * @param binder
	 *            the data binder instance to use for the binding
	 * @param request
	 *            the current request
	 */
	// protected void bindRequestParameters(WebDataBinder binder,
	// NativeWebRequest request) {
	// ((WebRequestDataBinder) binder).bind(request);
	// }

	/**
	 * Validate the model attribute if applicable.
	 * <p>
	 * The default implementation checks for {@code @javax.validation.Valid}.
	 * 
	 * @param binder
	 *            the DataBinder to be used
	 * @param parameter
	 *            the method parameter
	 */
	protected void validateIfApplicable(WebDataBinder binder,
			MethodParameter parameter) {
		Annotation[] annotations = parameter.getParameterAnnotations();
		for (Annotation annot : annotations) {
			if (annot.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = AnnotationUtils.getValue(annot);
				binder.validate(hints instanceof Object[] ? (Object[]) hints
						: new Object[] { hints });
				break;
			}
		}
	}

	/**
	 * Whether to raise a {@link BindException} on validation errors.
	 * 
	 * @param binder
	 *            the data binder used to perform data binding
	 * @param parameter
	 *            the method argument
	 * @return {@code true} if the next method argument is not of type
	 *         {@link Errors}.
	 */
	protected boolean isBindExceptionRequired(WebDataBinder binder,
			MethodParameter parameter) {
		int i = parameter.getParameterIndex();
		Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class
				.isAssignableFrom(paramTypes[i + 1]));

		return !hasBindingResult;
	}

	/**
	 * Return {@code true} if there is a method-level {@code @ModelAttribute} or
	 * if it is a non-simple type when {@code annotationNotRequired=true}.
	 */
	public boolean supportsReturnType(MethodParameter returnType) {
		if (returnType.getMethodAnnotation(ModelAttribute.class) != null) {
			return true;
		} else if (this.annotationNotRequired) {
			return !BeanUtils.isSimpleProperty(returnType.getParameterType());
		} else {
			return false;
		}
	}

	/**
	 * Add non-null return values to the {@link ModelAndViewContainer}.
	 */
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {

		if (returnValue != null) {
			String name = ModelFactory.getNameForReturnValue(returnValue,
					returnType);
			mavContainer.addAttribute(name, returnValue);
		}
	}

	// <-From ModelAttributeMethodProcessor

	// ->From ServletModelAttributeMethodProcessor
	/**
	 * Instantiate the model attribute from a URI template variable or from a
	 * request parameter if the name matches to the model attribute name and if
	 * there is an appropriate type conversion strategy. If none of these are
	 * true delegate back to the base class.
	 * 
	 * @see #createAttributeFromRequestValue(String, String, MethodParameter,
	 *      WebDataBinderFactory, NativeWebRequest)
	 */
	protected final Object createAttribute(String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory,
			NativeWebRequest request) throws Exception {

		String value = getRequestValueForAttribute(attributeName, request);
		if (value != null) {
			Object attribute = createAttributeFromRequestValue(value,
					attributeName, parameter, binderFactory, request);
			if (attribute != null) {
				return attribute;
			}
		}

		return ModelAttributeMethodProcessor_CreateAttribute(attributeName,
				parameter, binderFactory, request);
	}

	/**
	 * Obtain a value from the request that may be used to instantiate the model
	 * attribute through type conversion from String to the target type.
	 * <p>
	 * The default implementation looks for the attribute name to match a URI
	 * variable first and then a request parameter.
	 * 
	 * @param attributeName
	 *            the model attribute name
	 * @param request
	 *            the current request
	 * @return the request value to try to convert or {@code null}
	 */
	protected String getRequestValueForAttribute(String attributeName,
			NativeWebRequest request) {
		Map<String, String> variables = getUriTemplateVariables(request);
		if (StringUtils.hasText(variables.get(attributeName))) {
			return variables.get(attributeName);
		} else if (StringUtils.hasText(request.getParameter(attributeName))) {
			return request.getParameter(attributeName);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected final Map<String, String> getUriTemplateVariables(
			NativeWebRequest request) {
		Map<String, String> variables = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		return (variables != null) ? variables : Collections
				.<String, String> emptyMap();
	}

	/**
	 * Create a model attribute from a String request value (e.g. URI template
	 * variable, request parameter) using type conversion.
	 * <p>
	 * The default implementation converts only if there a registered
	 * {@link Converter} that can perform the conversion.
	 * 
	 * @param sourceValue
	 *            the source value to create the model attribute from
	 * @param attributeName
	 *            the name of the attribute, never {@code null}
	 * @param parameter
	 *            the method parameter
	 * @param binderFactory
	 *            for creating WebDataBinder instance
	 * @param request
	 *            the current request
	 * @return the created model attribute, or {@code null}
	 * @throws Exception
	 */
	protected Object createAttributeFromRequestValue(String sourceValue,
			String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory, NativeWebRequest request)
			throws Exception {
		DataBinder binder = binderFactory.createBinder(request, null,
				attributeName);
		ConversionService conversionService = binder.getConversionService();
		if (conversionService != null) {
			TypeDescriptor source = TypeDescriptor.valueOf(String.class);
			TypeDescriptor target = new TypeDescriptor(parameter);
			if (conversionService.canConvert(source, target)) {
				return binder.convertIfNecessary(sourceValue,
						parameter.getParameterType(), parameter);
			}
		}
		return null;
	}

	// The ModelFactory only support ModelAttribute must change it!
	/**
	 * {@inheritDoc}
	 * <p>
	 * Downcast {@link WebDataBinder} to {@link ServletRequestDataBinder} before
	 * binding.
	 * 
	 * @see ServletRequestDataBinderFactory
	 */
	protected void bindRequestParameters(WebDataBinder binder,
			NativeWebRequest request) {
		ServletRequest servletRequest = request
				.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder
				.setFieldDefaultPrefix(servletBinder.getObjectName() + ".");
		servletBinder.bind(servletRequest);
	}

	// ->From ServletModelAttributeMethodProcessor

	// The ModelFactory only support ModelAttribute must change it!
	// ->From ModelFactory
	public static String ModelFactory_getNameForParameter(
			MethodParameter parameter) {
		// ModelAttribute annot =
		// parameter.getParameterAnnotation(ModelAttribute.class);
		ExtModelAttribute annot = parameter
				.getParameterAnnotation(ExtModelAttribute.class);
		String attrName = (annot != null) ? annot.value() : null;
		return StringUtils.hasText(attrName) ? attrName : Conventions
				.getVariableNameForParameter(parameter);
	}
	// <-From ModelFactory
}