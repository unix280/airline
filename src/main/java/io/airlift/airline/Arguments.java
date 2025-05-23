/*
 * Copyright (C) 2010 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.airlift.airline;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface Arguments
{
    /**
     * Name of the arguments.
     */
    String title() default "";

    /**
     * A description of the arguments.
     */
    String description() default "";

    /**
     * Argument usage for help.
     */
    String usage() default "";

    /**
     * Whether this arguments are required.
     */
    boolean required() default false;
}
