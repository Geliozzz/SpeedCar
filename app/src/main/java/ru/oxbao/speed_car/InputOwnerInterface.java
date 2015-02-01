package ru.oxbao.speed_car;


public abstract class InputOwnerInterface
{
    public abstract void Amass(double XAxis, double YAxis, double ZAxis, long time);

    public abstract void Stop();

    public void SetNumberOfMeasurements(int number)
    {
    }

    public void OnDataCollected()
    {
    }

    public abstract String GetFileName();

    public abstract void ShowFailSensor();
}
