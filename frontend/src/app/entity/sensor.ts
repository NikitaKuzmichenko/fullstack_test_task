import { MeasuringUnit } from "./sensor.measuring.unit";
import { SensorType } from "./sensor.type";

export interface Sensor{
    id : number,
    name : string,
    model : string,
    lowerOperationalBound : number,
    higherOperationalBound : number,
    measurementsUnit : MeasuringUnit,
    type : SensorType,
    location : string,
    description : string
}