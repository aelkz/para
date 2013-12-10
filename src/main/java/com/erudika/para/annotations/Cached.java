/*
 * Copyright 2013 Alex Bogdanovski <albogdano@me.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You can reach the author at: https://github.com/albogdano
 */
package com.erudika.para.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Alex Bogdanovski <albogdano@me.com>
 * 
 * Annotation that marks methods that can modify the cache. Mainly used for weaving through AOP.
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)

public @interface Cached {

	public enum Action {
		NOOP,
		PUT,	// put in cache
		GET,	// get from cache
		DELETE,	// delete from cache
		GET_ALL,
		PUT_ALL,
		DELETE_ALL;	
	}
	
	Action action() default Action.NOOP;
}