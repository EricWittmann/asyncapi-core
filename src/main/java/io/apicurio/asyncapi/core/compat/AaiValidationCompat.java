/*
 * Copyright 2019 JBoss Inc
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
 */

package io.apicurio.asyncapi.core.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.apicurio.asyncapi.core.validation.AaiValidationRule;
import io.apicurio.asyncapi.core.validation.AaiValidationRuleMetaData;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiValidationCompat {

    public static AaiValidationRule instantiate(AaiValidationRuleMetaData ruleInfo) {
        try {
            Constructor<?> constructor = ruleInfo.ruleClass.getConstructor(AaiValidationRuleMetaData.class);
            return (AaiValidationRule) constructor.newInstance(ruleInfo);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
}
