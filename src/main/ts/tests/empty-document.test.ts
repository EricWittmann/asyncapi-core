///<reference path="../node_modules/@types/jasmine/index.d.ts"/>

import {AaiDocument} from "../src/io/apicurio/asyncapi/core/models/AaiDocument";
import {AaiReader} from "../src/io/apicurio/asyncapi/core/io/readers/AaiReader";

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

describe("TS Test", () => {

    it("String not null", () => {
        let thing: string = "hello";
        expect(thing).not.toBeNull();
    });

    it("Null", () => {
        expect(null).toBeNull();
    });

    it("Empty Document", () => {
        let doc: AaiDocument = new AaiDocument();
        doc.asyncapi = "2.0.0";
        doc.id = "12345";
        expect(doc).not.toBeNull();
    });

    it("Document Reader", () => {
        let json: any = {
            asyncapi: "2.0.0",
            id: "12345",
            info: {
                title: "API Title",
                version: "1.0.0",
                description: "This is the API description."
            }
        };
        let reader: AaiReader = new AaiReader();
        let doc: AaiDocument = new AaiDocument();
        reader.readDocument(json, doc);

        expect(doc.asyncapi).toEqual("2.0.0");
        expect(doc.id).toEqual("12345");
        expect(doc.info).not.toBeNull();
        expect(doc.info.title).toEqual("API Title");
        expect(doc.info.version).toEqual("1.0.0");
        expect(doc.info.description).toEqual("This is the API description.");
    });

});
