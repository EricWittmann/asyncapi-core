
export class AaiJsonUtil {
    
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

    
}