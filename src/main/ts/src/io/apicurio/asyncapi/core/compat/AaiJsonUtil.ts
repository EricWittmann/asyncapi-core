
export class AaiJsonUtil {
    
    public static objectNode(): any {
        return {};
    }
    public static arrayNode(): any {
        return [];
    }
    
    public static property(json: any, propertyName: string): any {
        if (json[propertyName]) {
            return json[propertyName];
        }
        return null;
    }
    
    public static propertyString(json: any, propertyName: string): string {
        let value: any = AaiJsonUtil.property(json, propertyName);
        if (value == null) {
            return null;
        } else {
            return value;
        }
    }

    public static setProperty(json: any, propertyName: string, propertyValue: any): void {
        json[propertyName] = propertyValue;
    }
    
    public static setPropertyString(json: any, propertyName: string, propertyValue: string): void {
        if (propertyValue != null) {
            AaiJsonUtil.setProperty(json, propertyName, propertyValue);
        }
    }

    
}