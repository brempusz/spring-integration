/*
 * Copyright 2002-2010 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.springframework.integration.config.xml;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.ExpressionControlBusFactoryBean;
import org.springframework.integration.control.ControlBusMessageProcessor;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Dave Syer
 * @since 2.0
 */
public class ControlBusParser extends AbstractConsumerEndpointParser {

	@Override
	protected BeanDefinitionBuilder parseHandler(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.genericBeanDefinition(ExpressionControlBusFactoryBean.class);
		builder.addConstructorArgValue(new RootBeanDefinition(ControlBusMessageProcessor.class));
		BeanMetadataElement beanResolver = getBeanResolver(element, parserContext);
		if (beanResolver!=null) {
			builder.addPropertyValue("beanResolver", beanResolver);
		}
		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "send-timeout");
		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element, "order");
		return builder;
	}

	protected BeanMetadataElement getBeanResolver(Element element, ParserContext parserContext) {
		String ref = element.getAttribute("bean-resolver");
		if (!StringUtils.hasText(ref)) {
			return null;
		}
		return new RuntimeBeanReference(ref);
	}

}
