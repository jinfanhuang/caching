/**
 * 
 */
package com.hyperats.cache.key;

/**
 * @author weiht
 *
 */
public class MethodSignatureConcatinationStrategy implements
		MethodSignatureKeyValueStrategy {
	private static final String NULL_PARAMETER_ELEMENT = "";
	
	public MethodSignatureConcatinationStrategy() {
	}
	
	@Override
	public String generateKeyValue(Object target, String targetMethod,
			Object[] arguments) {
		KeyValueBuilder builder = new KeyValueBuilder();
		builder.addElement(target.getClass().getName()).addElement(targetMethod);
		for (Object o: arguments) {
			builder.addElement(o == null ? NULL_PARAMETER_ELEMENT : o.toString());
		}
		return builder.getKeyValue();
	}
}
