interface Sensor{
    id : Number,
    name : string,
    model : string,
    lowerOperationalBound : Number,
    higherOperationalBound : Number,
    measuringUnit : MeasuringUnit,
    type : SensorType,
    location : string,
    description : string
}