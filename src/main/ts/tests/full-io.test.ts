///<reference path="../node_modules/@types/jasmine/index.d.ts"/>
///<reference path="@types/karma-read-json/index.d.ts"/>

/**
 * @license
 * Copyright 2016 JBoss Inc
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

import {AaiDocument} from "../src/io/apicurio/asyncapi/core/models/AaiDocument";
import {AaiReader} from "../src/io/apicurio/asyncapi/core/io/readers/AaiReader";
import {AaiWriter} from "../src/io/apicurio/asyncapi/core/io/writers/AaiWriter";
import {AaiVisitorUtil} from "../src/io/apicurio/asyncapi/core/visitors/AaiVisitorUtil";


export interface TestSpec {
    name: string;
    test: string;
}

/**
 * This function recursively sorts all objects by property name.  This is so that it is
 * easier to compare two objects.
 * @param original
 * @return {any}
 */
function sortObj(original: any): any {
    let sorted: any = {};
    Object.keys(original).sort().forEach(function(key) {
        let val: any = original[key];
        if (typeof val === 'object') {
            val = sortObj(val);
        }
        sorted[key] = val;
    });
    return sorted;
}

/**
 * Full I/O Tests for the AsyncAPI library.
 */
describe("Full I/O", () => {

    let TESTS: TestSpec[] = readJSON("tests/fixtures/io/tests.json");

    // All tests in the list above.
    TESTS.forEach( spec => {
        it(spec.name, () => {
            let testPath: string = "tests/fixtures/io/" + spec.test;
            let json: any = readJSON(testPath);
            let reader: AaiReader = new AaiReader();
            let document: AaiDocument = new AaiDocument();
            reader.readDocument(json, document);

            let writer: AaiWriter = new AaiWriter();
            AaiVisitorUtil.visitTree(document, writer);
            let jsObj: any = writer.getResult();
            expect(jsObj).toEqual(json);
        });
    });

});

