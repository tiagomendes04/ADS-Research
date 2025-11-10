```java
package com.smartthermostat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* Temperature sensor simulation */
public class TemperatureSensor {
    private double currentTemp;
    private final double ambientTemp;
    private final double heatingRate;   // °C per second when heater is on
    private final double coolingRate;   // °C per second when cooler is on

    public TemperatureSensor(double initialTemp, double ambientTemp,
                             double heatingRate, double coolingRate) {
        this.currentTemp = initialTemp;
        this.ambientTemp = ambientTemp;
        this.heatingRate = heatingRate;
        this.coolingRate = coolingRate;
    }

    public synchronized double getCurrentTemp() {
        return currentTemp;
    }

    public synchronized void update(boolean heaterOn, boolean coolerOn, double deltaSeconds) {
        if (heaterOn && !coolerOn) {
            currentTemp += heatingRate * deltaSeconds;
        } else if (coolerOn && !heaterOn) {
            currentTemp -= coolingRate * deltaSeconds;
        } else {
            // natural drift towards ambient
            double drift = (ambientTemp - currentTemp) * 0.1 * deltaSeconds;
            currentTemp += drift;
        }
    }
}

/* Abstract actuator */
abstract class Actuator {
    protected final AtomicBoolean isOn = new AtomicBoolean(false);

    public void turnOn() { isOn.set(true); }
    public void turnOff() { isOn.set(false); }
    public boolean isOn() { return isOn.get(); }
}

/* Heater implementation */
class Heater extends Actuator {}

/* Cooler implementation */
class Cooler extends Actuator {}

/* Thermostat controller */
public class Thermostat {
    private final TemperatureSensor sensor;
    private final Heater heater;
    private final Cooler cooler;
    private double setPoint;
    private double tolerance; // ± tolerance around setPoint

    public Thermostat(TemperatureSensor sensor, Heater heater, Cooler cooler,
                      double initialSetPoint, double tolerance) {
        this.sensor = sensor;
        this.heater = heater;
        this.cooler = cooler;
        this.setPoint = initialSetPoint;
        this.tolerance = tolerance;
    }

    public synchronized void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public synchronized double getSetPoint() {
        return setPoint;
    }

    public synchronized void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public synchronized double getTolerance() {
        return tolerance;
    }

    public void controlLoop() {
        double temp = sensor.getCurrentTemp();
        if (temp < setPoint - tolerance) {
            heater.turnOn();
            cooler.turnOff();
        } else if (temp > setPoint + tolerance) {
            cooler.turnOn();
            heater.turnOff();
        } else {
            heater.turnOff();
            cooler.turnOff();
        }
    }

    public Heater getHeater() { return heater; }
    public Cooler getCooler() { return cooler; }
    public TemperatureSensor getSensor() { return sensor; }
}

/* Simulation runner */
public class ThermostatSimulation {
    private static final long UPDATE_INTERVAL_MS = 1000; // 1 second

    public static void main(String[] args) {
        TemperatureSensor sensor = new TemperatureSensor(
                20.0,   // initial temperature °C
                22.0,   // ambient temperature °C
                0.5,    // heating rate °C/s
                0.7     // cooling rate °C/s
        );
        Heater heater = new Heater();
        Cooler cooler = new Cooler();

        Thermostat thermostat = new Thermostat(sensor, heater, cooler,
                21.5,   // desired set point °C
                0.2);   // tolerance °C

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            private long lastTick = System.currentTimeMillis();

            @Override
            public void run() {
                long now = System.currentTimeMillis();
                double deltaSec = (now - lastTick) / 1000.0;
                lastTick = now;

                // Update sensor based on actuator states
                sensor.update(heater.isOn(), cooler.isOn(), deltaSec);

                // Run control logic
                thermostat.controlLoop();

                // Output status
                System.out.printf("Time: %tT | Temp: %.2f°C | Heater: %s | Cooler: %s%n",
                        now,
                        sensor.getCurrentTemp(),
                        heater.isOn() ? "ON" : "OFF",
                        cooler.isOn() ? "ON" : "OFF");
            }
        }, 0, UPDATE_INTERVAL_MS);

        // Let the simulation run for a defined period
        try {
            Thread.sleep(60_000); // run for 60 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            timer.cancel();
        }
    }
}
```