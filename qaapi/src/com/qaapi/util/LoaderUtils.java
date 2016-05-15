package com.qaapi.util;

import java.util.List;

import com.qaapi.memorydb.SchemaNotExistException;

import static com.qaapi.memorydb.DataHolder.*;

/**
 * 加载文档的时候可能会用到的一些通用方法
 * 
 * @author IceAsh
 *
 */
public class LoaderUtils {

	public static boolean isExistSchemas(List<String> schemaNames)
			throws SchemaNotExistException {

		for (String schemaName : schemaNames) {
			if (!SCHEMAS_NAME.contains(schemaName)) {
				throw new SchemaNotExistException(schemaName);
			}
		}
		return true;
	}
}
