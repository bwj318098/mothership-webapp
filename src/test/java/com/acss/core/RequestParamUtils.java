package com.acss.core;

import java.util.Iterator;
import java.util.Map;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RequestParamUtils {
	
	static ObjectMapper mapper = new ObjectMapper();
	
	public static MockHttpServletRequestBuilder addParams(MockHttpServletRequestBuilder builder, Object object) throws JsonProcessingException{
		
		JsonNode root = mapper.valueToTree(object);
		
		addParams(builder, root, null);
		
		return builder;
	}

	public static MockHttpServletRequestBuilder addParams(MockHttpServletRequestBuilder builder, JsonNode root, String parentName) throws JsonProcessingException{
				
		Iterator<Map.Entry<String, JsonNode>> nodes = root.fields();
		while(nodes.hasNext()){
			Map.Entry<String, JsonNode> entry = nodes.next();
			String name = entry.getKey();
			JsonNode node = entry.getValue();
			
			nodetype : switch (node.getNodeType()) {
			case ARRAY:
				int i = 0;
				for(JsonNode jsonNode : (ArrayNode) node){
					addParams(builder, jsonNode, nodeName(name + "[" + (i++) + "]", parentName));
				}
				break nodetype;
			case OBJECT:
				addParams(builder, node, nodeName(name, parentName));
				break nodetype;
			case BOOLEAN:
			case NUMBER:
			case STRING:
				builder.param(nodeName(name, parentName), node.asText());
				break nodetype;
			default:
				break nodetype;
			}
		}
		
		return builder;
	}
	
	private static String nodeName(String nodeName, String parentName){
		return parentName == null ? nodeName : parentName + "." + nodeName;  
	}
	
}