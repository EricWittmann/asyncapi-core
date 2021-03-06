///<reference path="../node_modules/@types/jasmine/index.d.ts"/>
///<reference path="@types/karma-read-json/index.d.ts"/>

/**
 * @license
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

import {AaiDocument} from "../src/io/apicurio/asyncapi/core/models/AaiDocument";
import {AaiReader} from "../src/io/apicurio/asyncapi/core/io/readers/AaiReader";
import {AaiLibrary} from "../src/io/apicurio/asyncapi/core/AaiLibrary";
import {AaiValidationProblem} from "../src/io/apicurio/asyncapi/core/validation/AaiValidationProblem";
import {readText} from "./util/readText";
import {AaiValidationProblemSeverity} from "../src/io/apicurio/asyncapi/core/validation/AaiValidationProblemSeverity";


export interface TestSpec {
    name: string;
    test: string;
}

function formatSeverity(severity: AaiValidationProblemSeverity): string {
    if (severity == AaiValidationProblemSeverity.ignore) {
        return "ignore";
    } else if (severity == AaiValidationProblemSeverity.low) {
        return "low";
    } else if (severity == AaiValidationProblemSeverity.medium) {
        return "medium";
    } else if (severity == AaiValidationProblemSeverity.high) {
        return "high";
    }
}

function formatProblems(problems: AaiValidationProblem[]): string {
    let rval: string = "";
    problems.sort((p1, p2) => {
        return p1.errorCode.localeCompare(p2.errorCode);
    }).forEach(problem => {
        rval += (
            problem.errorCode + "::" +
            formatSeverity(problem.severity) + "::" +
            problem.property + "::" +
            problem.message + "\n"
        );
    });
    return rval;
}

function normalize(value: string): string {
    return value.trim().replace("\r\n", "\n");
}

/**
 * Full I/O Tests for the AsyncAPI library.
 */
describe("Validation", () => {

    let TESTS: TestSpec[] = readJSON("tests/fixtures/validation/tests.json");

    // All tests in the list above.
    TESTS.forEach( spec => {
        it(spec.name, () => {
            let testPath: string = "tests/fixtures/validation/" + spec.test;
            let json: any = readJSON(testPath);
            expect(json).not.toBeNull();
            let reader: AaiReader = new AaiReader();
            let document: AaiDocument = new AaiDocument();
            reader.readDocument(json, document);

            let problems: AaiValidationProblem[] = AaiLibrary.validate(document, null);
            let actual: string = normalize(formatProblems(problems));

            let expected: string = normalize(readText(testPath + ".expected"));
            expect(expected).not.toBeNull();
            expect(expected).toEqual(actual);
        });
    });

});

